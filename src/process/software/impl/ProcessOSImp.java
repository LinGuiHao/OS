package process.software.impl;

import memory.hardware.PCB;

import java.util.LinkedList;
import java.util.Queue;

public class ProcessOSImp  implements ProcessOS {
	//做测试
	private Queue<PCB> readyProcess = new LinkedList<>();
	private Queue<PCB> blockProcess = new LinkedList<>();
	private Queue<PCB> blankProcess = new LinkedList<>();

	private byte[] data;
	public ProcessOSImp() {
	}
	
	//新建进程，进程初始化


	@Override
	public boolean create(byte[] data, Queue<PCB> blankProcess, Queue<PCB> readyProcess, Queue<PCB> blockProcess) {
		this.data = data;
		this.blockProcess = blockProcess;
		this.blankProcess = blankProcess;
		this.readyProcess = readyProcess;
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
		blockProcess.remove(awakePCB);
		readyProcess.add(awakePCB);
		//System.out.println("唤醒进程");
	}


	public Queue<PCB> getReadyProcess() {
		return readyProcess;
	}

	public void setReadyProcess(Queue<PCB> readyProcess) {
		this.readyProcess = readyProcess;
	}

	public Queue<PCB> getBlockProcess() {
		return blockProcess;
	}

	public void setBlockProcess(Queue<PCB> blockProcess) {
		this.blockProcess = blockProcess;
	}


	public Queue<PCB> getBlankProcess() {
		return blankProcess;
	}

	public void setBlankProcess(Queue<PCB> blankProcess) {
		this.blankProcess = blankProcess;
	}

	public byte[] getData() {
		return data;
	}


}
