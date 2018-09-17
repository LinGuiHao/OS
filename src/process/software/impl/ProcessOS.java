package process.software.impl;

import memory.hardware.PCB;

import java.util.Queue;

public interface ProcessOS {
	/**
	 * 创建新的进程,直接从内存中拿到数据块和队列
	 * @param data 内存数据块
	 * @param newPCB 新建进程
	 * @return
	 */
	public boolean create(byte[] data,PCB newPCB);
	
	/**
	 *销毁一个进程
	 *@param destoryPCB
	 */
	public void destory(PCB destoryPCB);
	
	/**
	 * 阻塞一个正在运行的进程
	 * @param blockPCB
	 */
	public void block(PCB blockPCB);
	
	/**
	 * 唤醒一个阻塞的进程
	 * 进程由阻塞态变成就绪态
	 * @param awakePCB
	 */
	public void awake(PCB awakePCB);
}