package com.bluewhite.common.utils.ZkemUtils;

public class SDKRunnable implements Runnable {

	private String address;

	public SDKRunnable(String address) {
		this.address = address;
	}

	@Override
	public void run() {
		System.out.println("Thread开始====200秒等待设备实时事件");
		try {
			Thread.sleep(2000);
			ZkemSDKUtils.regEvent(address);
		} catch (InterruptedException e) {
		}
		System.out.println("Thread结束=====");
	}

}
