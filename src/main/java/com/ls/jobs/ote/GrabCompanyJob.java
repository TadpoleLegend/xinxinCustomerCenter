package com.ls.jobs.ote;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.ls.service.GrabService;

public class GrabCompanyJob implements Job {

	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		GrabService grabService = (GrabService)context.getJobDetail().getJobDataMap().get("grabService");
		
		System.out.println("INFO: Start to grab 138 web site.................................................");
		grabService.oteJobDailyWork();
		System.out.println("INFO: Stop to grab 138 web site.................................................");
	}

}
