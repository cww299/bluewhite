package com.bluewhite.schedulertask.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.bluewhite.common.utils.zkemUtils.ZkemSDKRealTime;
import com.jacob.activeX.ActiveXComponent;

@Component
public class Task {

//	@Scheduled(cron = "*/3 * * * * ?") // 3分钟触发一次
//	public void cheakSDK(ZkemSDKRealTime sdk,ActiveXComponent zkem) {
//		boolean b = sdk.ReadRTLog(1,zkem);
//		if(!b){
//			System.out.println("失去连接");
//		}
//	}


}
