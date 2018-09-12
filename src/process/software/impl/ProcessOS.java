package process.software.impl;

import memory.hardware.PCB;

public interface ProcessOS {
	/**
	 * 创建新的进程
	 * @return boolean
	 */
	public boolean create(byte[] data);
	
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