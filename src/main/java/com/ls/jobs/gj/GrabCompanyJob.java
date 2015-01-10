package com.ls.jobs.gj;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.ls.service.GrabService;

public class GrabCompanyJob implements Job {

	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		System.out.println("INFO: Start to grab Gan Ji web site.................................................");
		GrabService grabService = (GrabService)context.getJobDetail().getJobDataMap().get("grabService");
		
		grabService.gjJobDailyWork();
		
		System.out.println("INFO: Stop to grab Gan Ji web site.................................................");
	}

}
