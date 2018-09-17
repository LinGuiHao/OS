package process.hardware;

import java.util.HashMap;
import java.util.Map;

public class Register {
	private short ax;//保存x的值ֵ
	private short psw;//程序状态字״̬
	private short pc;//指向指令所在位置
	private short ir;//ָ保存执行指令
	//保存中间结果
	private Map<Short,Short> intermediaResult;
	
	public short getAx() {
		return ax;
	}
	public void setAx(short ax) {
		this.ax = ax;
	}
	public short getPsw() {
		return psw;
	}
	public void setPsw(short psw) {
		this.psw = psw;
	}
	public short getPc() {
		return pc;
	}
	public void setPc(short pc) {
		this.pc = pc;
	}
	public short getIr() {
		return ir;
	}
	public void setIr(short ir) {
		this.ir = ir;
	}
	public Map<Short, Short> getIntermediaResult() {
		return intermediaResult;
	}
	public void setIntermediaResult(Map<Short, Short> intermediaResult) {
		this.intermediaResult = intermediaResult;
	}
}