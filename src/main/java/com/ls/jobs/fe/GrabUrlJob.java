package com.ls.jobs.fe;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ls.service.GrabCompanyDetailPageUrlService;
import com.ls.util.XinXinUtils;

public class GrabUrlJob implements Job {
	
	private Logger logger = LoggerFactory.getLogger(GrabUrlJob.class);
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		GrabCompanyDetailPageUrlService grabCompanyDetailPageUrlService = (GrabCompanyDetailPageUrlService) context.getJobDetail().getJobDataMap().get("grabCompanyDetailPageUrlService");
		
		grabCompanyDetailPageUrlService.grabTwoDaysRecently();
		
		logger.info("FEGrabNewPublishedCompanyURLJob job done at " + XinXinUtils.getNow().toGMTString());
	}

}
