package myUtil;

import disk.software.impl.DirOSImpl;
import disk.software.impl.DiskOS;

public class Number {
	/**
	 * 常量存放的地方
	 * 这样表示比较容易理解
	 */
	public static int lenOfDiskBlock=64;
	public static int lenOfDisk=256;
	public static int sizeOfUserArea=512;
	//进程状态
	public static final int PROCESS_READY = 0;
	public static final int PROCESS_RUNNING = 1;
	public static final int PROCESS_BLOCKING =2;
	public static  final int PROCESS_USE_DEVICE = 3;
	public static final int PROCESS_FINISH = -4;
	//中断类型
	public static final short NO_INTERRUPT = 0;
	public static final short DEVICE_INTERRUPT = 1;
	public static final short TIME_INTERRUPT = 2;
	public static final short FINISH_INTERRUPT =3;
	
	
	public static int byteToInt(byte num) {
		return (num+256)%256;
	}
	public static byte intToByte(int num) {
		return (byte)(num);
	}
	public static void showFat(DiskOS diskOS,int length) {
		byte[]fat = diskOS.getFatTable().getFatItem();
		for(int i=0;i<length;i++) {
			System.out.println(Number.byteToInt(fat[i]));
		}
	}
	public static void showDisk(DiskOS diskOS,int bnum) {
		byte[]disk = diskOS.getDisk().getDisk()[bnum].getDiskblock();
		for(int i=0;i<64;i++) {
			System.out.println(Number.byteToInt(disk[i]));
		}
	}
}
