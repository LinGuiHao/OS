package process.software.impl;

import com.sun.org.apache.bcel.internal.generic.CPInstruction;
import memory.hardware.PCB;
import memory.software.MemoryOS;
import memory.software.impl.MemoryOSImpl;
import process.hardware.imp.CPUImp;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class ProcessOSImp  implements ProcessOS {
	//PCB队列
	private Queue<PCB> readyProcess;
	private Queue<PCB> blockProcess;
	private Queue<PCB> blankProcess;

	private CPUImp cpuOS;
	private byte[] data;
	private MemoryOSImpl memoryOS;

	public static void main(String[] args){
		MemoryOSImpl memoryOS = new MemoryOSImpl();
	    byte[] datas = new byte[100];
	    datas[0] = 3;
	    datas[1] = 0;
	    datas[2] = 0;
	    datas[3] = 0b00100000;
	    datas[4] = 0;
	    datas[5] = 0b01000000;
	    datas[6] = 5;
	    datas[7] = 0b01100000;
	    datas[8] = 3;
	    datas[9] = 1;
	    datas[10] = 0;
	    datas[11] = (byte) 0b10000000;
		datas[12] = 1;
		datas[13] = 0;
		datas[14] = 0;
		datas[15] = 0b00100000;
		datas[16] = 0;
		datas[17] = 0b01000000;
		datas[18] = 5;
		datas[19] = 0b01100000;
		datas[20] = 3;
		datas[21] = 0;
		datas[22] = 0;
		datas[23] = (byte) 0b10000000;

	    ProcessOSImp processOS = new ProcessOSImp(datas,memoryOS);
	    PCB newpcb1 = new PCB();
		PCB newpcb2 = new PCB();
	    newpcb1.setPc(0);
	    newpcb2.setPc(12);
	    processOS.create(newpcb1);
	    processOS.create(newpcb2);
	}
	public ProcessOSImp(byte[] data,MemoryOSImpl memoryOS) {
		readyProcess = new LinkedList<>();
		blockProcess = new LinkedList<>();
		blankProcess = new LinkedList<>();
		this.data = data;
		cpuOS = new CPUImp();
		cpuOS.runningCPU(this,memoryOS);
	}


	@Override
	public boolean create(PCB newPCB) {

		  readyProcess.add(newPCB);
		  return true;
	}

	//销毁进程
	@Override
	public void destory(PCB destoryPCB) {
		for(PCB pcb:readyProcess){
			if(pcb==destoryPCB){
				readyProcess.remove(pcb);
				System.out.println("success delete");
				break;
			}
		}
		for(PCB pcb:blockProcess){
			if(pcb==destoryPCB){
				blockProcess.remove(pcb);
				System.out.println("success delete");
				break;
			}
		}
	}

	//阻塞进程
	@Override
	public void block(PCB blockPCB) {
		blockProcess.add(blockPCB);
	}

	//唤醒进程
	@Override
	public void awake(PCB awakePCB) {
		if(blockProcess.contains(awakePCB)){
			blockProcess.remove(awakePCB);

		}
		readyProcess.add(awakePCB);
//		System.out.println("唤醒进程");
	}

	public Queue<PCB> getReadyProcess() {
		return readyProcess;
	}

	public byte[] getData() {
		return data;
	}
}
