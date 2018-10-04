package com.bluewhite.schedulertask.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.bluewhite.production.finance.entity.CollectInformation;
import com.bluewhite.production.finance.service.CollectInformationService;

@Component
public class Task {
	
	@Autowired
	private CollectInformationService collectInformationService;
	
	 @Scheduled(cron="0 30 23 * * ?")   // 每晚23点30触发
     public void aTask(){  
		 CollectInformation collectInformation =  new CollectInformation();
		 collectInformation.setType(1);
		 collectInformationService.collectInformation(collectInformation);
		 CollectInformation collectInformation1 =  new CollectInformation();
		 collectInformation1.setType(2);
		 collectInformationService.collectInformation(collectInformation1);
		 CollectInformation collectInformation2 =  new CollectInformation();
		 collectInformation2.setType(3);
		 collectInformationService.collectInformation(collectInformation2);
		 CollectInformation collectInformation4 =  new CollectInformation();
		 collectInformation4.setType(4);
		 collectInformationService.collectInformation(collectInformation4);
		 CollectInformation collectInformation5 =  new CollectInformation();
		 collectInformation5.setType(5);
		 collectInformationService.collectInformation(collectInformation5);
		 
            
     }      

}
