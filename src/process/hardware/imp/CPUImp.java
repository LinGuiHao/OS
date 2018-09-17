package process.hardware.imp;

import device1.software.EquipmentOSImpl;
import memory.hardware.PCB;
import memory.software.impl.MemoryOSImpl;
import myUtil.Number;
import process.hardware.CPU;
import process.hardware.Register;
import process.software.impl.ProcessOSImp;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class CPUImp implements CPU {
   //cpu时钟
    private int cpuClock = 0;
    //cpu时间片
   private int timeSlock = 6;
   //cpu寄存器
   private Register cpuRegister;
    //cpu运行进程
    private PCB runningProcess = null;
    //设备管理器
    public EquipmentOSImpl equipmentOS;
    //返回结果
    public Map<Short,Short> result;
   //初始化CPU
   public CPUImp(){
        cpuRegister = new Register();
   }

   //启动cpu
   public void runningCPU(ProcessOSImp processOS,MemoryOSImpl memoryOS){
       equipmentOS = new EquipmentOSImpl(processOS);
       cpu(processOS,memoryOS);
   }
   @Override
    public void cpu(ProcessOSImp processOS, MemoryOSImpl memoryOS){
        Timer cputimer = new Timer();
        cputimer.schedule(new TimerTask() {
            @Override
            public void run() {
                cpuClock++;
                if(runningProcess == null){
                    runningProcess = processOS.getReadyProcess().poll();
                    if(runningProcess!=null){
                        initCPURegister();
                        runningProcess.setState(Number.PROCESS_RUNNING);
                        runningProcess.setPsw(Number.PROCESS_READY);
                        cpuRegister.setPsw((short) runningProcess.getPsw());
                    }
                }else {
                    if(timeSlock == 0){
                        //时间片为0的时候阻塞进程，将进程加入到就绪队列
                        runningProcess.setState(Number.PROCESS_BLOCKING);
                        processOS.awake(runningProcess);
                        runningProcess = null;
                        timeSlock = 6;
                    }else {
                        System.out.println("执行");
                        //当时间片不为0的时候，时间片减少
                        timeSlock--;
                        //将数据移入cpu寄存器中
                        cpuRegister.setPc((short)runningProcess.getPc());
                        System.out.println("pc:"+runningProcess.getPc());
                        cpuRegister.setIr((short)(processOS.getData()[cpuRegister.getPc()+1]*Math.pow(2,8)
                                + processOS.getData()[cpuRegister.getPc()]));
                        runningProcess.setPc(runningProcess.getPc()+2);
                        cpuRegister.setPc((short) runningProcess.getPc());
                        //进行逻辑译码并执行
                        cpuController();
                        //通过执行结果执行设备操作
                        if(cpuRegister.getPsw() == Number.DEVICE_INTERRUPT){
                            //使用设备
                            //将使用设备信息传给设备管理器
                            runningProcess.setState(Number.PROCESS_USE_DEVICE);
                            short character = (short) (cpuRegister.getAx()/Math.pow(2, 8) + 'A');
                            int useTime = (int) (cpuRegister.getAx()%Math.pow(2,8));
                            retainPCBSence();
                            equipmentOS.apply(runningProcess,(char)character,useTime);
                            runningProcess = null;
                        }else if(cpuRegister.getPsw() == Number.FINISH_INTERRUPT){
                            //输出结果
                            runningProcess.setState(Number.PROCESS_FINISH);
                            //调用界面接口，输出结果
                            result = cpuRegister.getIntermediaResult();
                            for(Map.Entry<Short, Short> entry : result.entrySet()) {
                                System.out.println((char) (entry.getKey()+'a')+"  :  "+entry.getValue());
                            }
                            memoryOS.collection(runningProcess.getPid());
                            runningProcess = null;
                        }
                    }
                }
            }
        },1000,1000);

    }
    @Override
    public void operationData() {

    }

    //逻辑语句分析及控制
    @Override
    public void cpuController() {
        short op = (short) (cpuRegister.getIr()/Math.pow(2, 13));
        System.out.println("op:"+op);
        if(op==0b0) {
            //赋值操作
            cpuRegister.setAx((short) (cpuRegister.getIr()%Math.pow(2, 13)));
            boolean find = false;
            short number = (short) (cpuRegister.getAx()%Math.pow(2, 8));
            short character = (short) (cpuRegister.getAx()/Math.pow(2, 8));
            if(!findIntermediaResult(cpuRegister.getIntermediaResult(),character,number,true)) {
                cpuRegister.getIntermediaResult().put(character, number);
            }
        }else if(op == 0b001) {
            //自增
            cpuRegister.setAx((short) (cpuRegister.getIr()%Math.pow(2, 13)));
            boolean find = false;
            short character = (short) (cpuRegister.getAx()/Math.pow(2, 8));
            if(!findIntermediaResult(cpuRegister.getIntermediaResult(),character, (short) 1,false)) {
                System.out.println("无该变量");
            }
        }else if(op==0b010) {
            //自减
            cpuRegister.setAx((short) (cpuRegister.getIr()%Math.pow(2, 13)));
            boolean find = false;
            short character = (short) (cpuRegister.getAx()/Math.pow(2, 8));
            if(!findIntermediaResult(cpuRegister.getIntermediaResult(),character, (short) -1,false)) {
                System.out.println("无该变量");
            }
        }else if(op==0b011) {
            //占用设备,未完成
            runningProcess.setPsw(Number.DEVICE_INTERRUPT);
            cpuRegister.setPsw((short) runningProcess.getPsw());
            cpuRegister.setAx((short) (cpuRegister.getIr()%Math.pow(2, 13)));
        }else if(op==-4) {
            //完成
            cpuRegister.setPsw(Number.FINISH_INTERRUPT);
        }
    }

    //寻找中间结果，并操作
    public boolean findIntermediaResult(Map map,short character,short number,boolean isAssigment){
       if(map.isEmpty()){
           map.put(character,number);
           return true;
       }else{
           for(Map.Entry<Short, Short> entry : cpuRegister.getIntermediaResult().entrySet()) {
               if(character==entry.getKey()) {
                   if(isAssigment){
                       entry.setValue(number);
                   }else {
                       entry.setValue((short) (entry.getValue() + number));
                   }
                   return true;
               }
           }
       }
       return false;
    }

    //初始化cpu中的寄存器
    public void initCPURegister(){
       cpuRegister.setPsw((short) Number.PROCESS_READY);
       cpuRegister.setPc((short) runningProcess.getPc());
       cpuRegister.setIr((short) runningProcess.getIr());
        cpuRegister.setAx((short) runningProcess.getAx());
        cpuRegister.setIntermediaResult(runningProcess.getIntermediaResult());
    }

    //保留PCB现场
    public void retainPCBSence(){
       runningProcess.setIntermediaResult(cpuRegister.getIntermediaResult());
       runningProcess.setPc(cpuRegister.getPc());
       runningProcess.setIr(cpuRegister.getIr());
       runningProcess.setAx(cpuRegister.getIr());
    }


    public int getCpuClock() {
        return cpuClock;
    }


}
