package device1.software;

import device1.hardware.Equipment;
import memory.hardware.PCB;
import process.software.impl.ProcessOS;
import process.software.impl.ProcessOSImp;

import java.util.Queue;

public class EquipmentOSImpl implements EquipmentOS {

    /**
     * 总的设备管理器
     */
    //声明三类设备,A设备三台，B设备俩台，C设备1台
    private  Equipment equipmentA;
    private Equipment equipmentB;
    private Equipment equipmentC;


    @Override
    public boolean apply(PCB usingProcess, char equipmentNum, int useTime, ProcessOSImp processOS) {
        equipmentA = new Equipment('A',3,processOS);
        equipmentA = new Equipment('A',3,processOS);
        equipmentA = new Equipment('A',3,processOS);

        if(equipmentNum == 'A'){
            addWaitingProcess(equipmentA,usingProcess,useTime);
            return true;
        }else if(equipmentNum == 'B'){
            addWaitingProcess(equipmentA,usingProcess,useTime);
            return true;
        }else if(equipmentNum == 'C'){
            addWaitingProcess(equipmentA,usingProcess,useTime);
            return true;
        }else{
            System.out.println("无该种设备");
            return false;
        }
    }

    public void addWaitingProcess(Equipment equipment,PCB usingProcess,int useTime){
        DMT useEquipment = new DMT(usingProcess,useTime);
        Queue<DMT> afterDealProcessQueue = equipment.getWaitingProcess();
        afterDealProcessQueue.add(useEquipment);
        equipment.setWaitingProcess(afterDealProcessQueue);
    }

    public static void main(String[] args) {
        EquipmentOSImpl equipmentOS = new EquipmentOSImpl();
        PCB pcb = new PCB();
        equipmentOS.apply(pcb,'A',5,new ProcessOSImp());
        equipmentOS.apply(pcb,'B',5,new ProcessOSImp());
    }
}
