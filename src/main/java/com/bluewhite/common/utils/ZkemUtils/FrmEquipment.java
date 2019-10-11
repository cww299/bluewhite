package com.bluewhite.common.utils.ZkemUtils;

import com.jacob.com.DispatchEvents;

public class FrmEquipment implements Runnable {

	private DispatchEvents de;

	public FrmEquipment(DispatchEvents de) {
		this.de = de;
	}

	@Override
	public void run() {
		System.out.println("Thread开始====200秒等待设备实时事件");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
		System.out.println("Thread结束=====");
	}

}
