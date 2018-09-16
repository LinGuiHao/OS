package process.hardware.imp;

import device1.hardware.Equipment;
import device1.software.EquipmentOSImpl;
import memory.hardware.PCB;
import myUtil.Number;
import process.hardware.CPU;
import process.hardware.Register;
import process.software.impl.ProcessOS;
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
   //进程管理
    private ProcessOSImp processOS = new ProcessOSImp();
    //cpu运行进程
    private PCB runningProcess = null;
    //设备管理器
    public EquipmentOSImpl equipmentOS;
    //返回结果
    public Map<Short,Short> result;
   //初始化CPU
   public CPUImp(){
        cpuRegister = new Register();
        equipmentOS = new EquipmentOSImpl();
   }

   @Override
    public void cpu(){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                cpuClock++;
                if(runningProcess == null){
                    runningProcess = processOS.getReadyProcess().poll();
                }else {
                    if(timeSlock == 0){
                        processOS.block(runningProcess);
                        runningProcess = null;
                        timeSlock = 6;
                    }else {
                        timeSlock--;
                        cpuRegister.setPc((short)runningProcess.getPc());
                        cpuRegister.setIr(processOS.getData()[cpuRegister.getPc()]);
                        runningProcess.setPc(runningProcess.getPc()+1);
                        cpuController();
                        if(cpuRegister.getPsw()==Number.DEVICE_INTERRUPT){
                            //使用设备
                            short character = (short) (cpuRegister.getAx()/Math.pow(2, 8) + 'A');
                            int useTime = (int) (cpuRegister.getAx()%Math.pow(2,8));
                            equipmentOS.apply(runningProcess,(char)character,useTime,processOS);
                        }else if(cpuRegister.getPsw() == Number.FINISH_INTERRUPT){
                            //输出结果
                            result = cpuRegister.getIntermediaResult();
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
            cpuRegister.setPsw(Number.DEVICE_INTERRUPT);
            cpuRegister.setAx((short) (cpuRegister.getIr()%Math.pow(2, 13)));
        }else if(op==0b100) {
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
               if(character==entry.getValue()) {
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
    
    public static void main(String[] args){
       CPU cpu = new CPUImp();
        ProcessOSImp processOSImp = new ProcessOSImp();
    }

    public int getCpuClock() {
        return cpuClock;
    }

    public void setCpuClock(int cpuClock) {
        this.cpuClock = cpuClock;
    }
}
