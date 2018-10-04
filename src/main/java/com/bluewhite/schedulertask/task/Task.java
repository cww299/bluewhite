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
	
	 @Scheduled(cron="0 10 23 * * ?")   // 每晚23点触发
     public void aTask(){  
		 
		 CollectInformation collectInformation =  new CollectInformation();
		 collectInformation.setType(1);
		 collectInformationService.collectInformation(collectInformation);
		 collectInformation.setType(2);
		 collectInformationService.collectInformation(collectInformation);
		 collectInformation.setType(3);
		 collectInformationService.collectInformation(collectInformation);
		 collectInformation.setType(4);
		 collectInformationService.collectInformation(collectInformation);
		 collectInformation.setType(5);
		 collectInformationService.collectInformation(collectInformation);
		 
            
     }      

}
