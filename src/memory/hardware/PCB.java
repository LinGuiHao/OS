package memory.hardware;

import java.util.HashMap;
import java.util.Map;

public class PCB {

	private int pid; //进程标志符 
	private int state; //状态
	private int priority;  //优先级
	private int runnedTime;  //运行的时间
	private int waitedTime;  //等待的时间

	private int ax; 
	private int psw; 
	private int pc;
	private int  ir;
	private int ppid;//子进程标志符
	
	//进程在内存的位置
	private int startPos; //start代表起点
	private int length; //length代表这个进程的大小

	//保存中间结果
	private Map<Short, Short> intermediaResult = new HashMap<>();
	public PCB() {
		
	}
	public int getStartPos() {
		return startPos;
	}
	public void setStartPos(int startPos) {
		this.startPos = startPos;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public PCB(int pid, int state, int ax, int psw, int pc, int ir, int startPos, int length) {
		super();
		this.pid = pid;
		this.state = state;
		this.ax = ax;
		this.psw = psw;
		this.pc = pc;
		this.ir = ir;
		this.startPos = startPos;
		this.length = length;
	}
	
	public int getPpid() {
		return ppid;
	}
	public void setPpid(int ppid) {
		this.ppid = ppid;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public int getRunnedTime() {
		return runnedTime;
	}
	public void setRunnedTime(int runnedTime) {
		this.runnedTime = runnedTime;
	}
	public int getWaitedTime() {
		return waitedTime;
	}
	public void setWaitedTime(int waitedTime) {
		this.waitedTime = waitedTime;
	}
	public int getAx() {
		return ax;
	}
	public void setAx(int ax) {
		this.ax = ax;
	}
	public int getPsw() {
		return psw;
	}
	public void setPsw(int psw) {
		this.psw = psw;
	}
	public int getPc() {
		return pc;
	}
	public  int  getIr() {
		return ir;
	}
	public void setIr(int  ir) {
		this.ir = ir;
	}
	public void setPc(int pc) {
		this.pc = pc;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ax;
		result = prime * result + ir;
		result = prime * result + length;
		result = prime * result + pc;
		result = prime * result + (int) (pid ^ (pid >>> 32));
		result = prime * result + ppid;
		result = prime * result + priority;
		result = prime * result + psw;
		result = prime * result + runnedTime;
		result = prime * result + startPos;
		result = prime * result + state;
		result = prime * result + waitedTime;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PCB other = (PCB) obj;
		if (ax != other.ax)
			return false;
		if (ir != other.ir)
			return false;
		if (length != other.length)
			return false;
		if (pc != other.pc)
			return false;
		if (pid != other.pid)
			return false;
		if (ppid != other.ppid)
			return false;
		if (priority != other.priority)
			return false;
		if (psw != other.psw)
			return false;
		if (runnedTime != other.runnedTime)
			return false;
		if (startPos != other.startPos)
			return false;
		if (state != other.state)
			return false;
		if (waitedTime != other.waitedTime)
			return false;
		return true;
	}
	public Map<Short, Short> getIntermediaResult() {
		return intermediaResult;
	}
	public void setIntermediaResult(Map<Short, Short> intermediaResult) {
		this.intermediaResult = intermediaResult;
	}

}
