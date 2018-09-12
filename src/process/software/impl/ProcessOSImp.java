package process.software.impl;

import memory.hardware.PCB;

import java.util.LinkedList;
import java.util.Queue;

public class ProcessOSImp  implements ProcessOS {
	//做测试
	private Queue<PCB> readyProcess = new LinkedList<>();
	private Queue<PCB> blockProcess = new LinkedList<>();
	private Queue<PCB> blankProcess = new LinkedList<>();
	private PCB runningProcess = new PCB();

	private byte[] data;
	public ProcessOSImp() {
	}
	
	//新建进程，进程初始化
	@Override
	public boolean create(byte[] data) {
		// TODO Auto-generated method stub
		//为进程定义一个pcb

		//当有新进程新建的时候更新controller的进程队列  
		//Controller.readyProcess.add(pcb)
		this.data = data;
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
		runningProcess = null;
		blockProcess.add(blockPCB);
	}

	//唤醒进程
	@Override
	public void awake(PCB awakePCB) {
		blockProcess.remove(awakePCB);
		blockProcess.add(awakePCB);
	}

}
