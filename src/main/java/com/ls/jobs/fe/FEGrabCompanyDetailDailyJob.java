package com.ls.jobs.fe;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.ls.service.GrabService;

public class FEGrabCompanyDetailDailyJob implements Job{

	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		GrabService grabService = (GrabService) context.getJobDetail().getJobDataMap().get("grabService");
		
		grabService.feJobDailyWork();
	}

}
