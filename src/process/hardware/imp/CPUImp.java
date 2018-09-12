package process.hardware.imp;

import myUtil.Number;
import process.hardware.CPU;
import process.hardware.Register;

import java.util.Map;

public class CPUImp implements CPU {
   //cpu时钟
    public static int cpuClock = 0;
    //cpu时间片
   private int timeSlock = 6;
   //cpu寄存器
   public static Register cpuRegister = new Register();
   //初始化CPU
   public CPUImp(){

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
        cpuRegister.setIr((short)0b0001111111111111);
        cpu.cpuController();
    }
}
