package device1.software;

import memory.hardware.PCB;
import process.software.impl.ProcessOSImp;

public interface EquipmentOS {
    /**
     *进程申请设备
     * 参数 usingProcess 使用设备的进程
     * equipmentNum 使用哪种设备
     * useTime 使用时长
     * processOS 进程管理器
     * @return 申请是否成功
     */
    public boolean apply(PCB usingProcess, char equipmentNum, int useTime);

}
