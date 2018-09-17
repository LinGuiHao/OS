package process.hardware;

import memory.software.impl.MemoryOSImpl;
import process.software.impl.ProcessOSImp;

/**
 * cpu接口
 */
public interface CPU {
    /**
     * cpu运算
     */
    public void operationData();

    /**
     * CPU控制逻辑
     * 解析语句并且执行
     */
    public  void cpuController();

    /**
     * cpu操作，执行进程
     */
    public void cpu(ProcessOSImp processOS, MemoryOSImpl memoryOS);


}
