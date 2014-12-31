package com.ls.jobs.ote;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.ls.service.GrabService;

public class GrabCompanyJob implements Job {

	public void execute(JobExecutionContext context) throws JobExecutionException {

		GrabService grabService = (GrabService)context.getJobDetail().getJobDataMap().get("grabService");

		grabService.oteJobDailyWork();
	}

}
