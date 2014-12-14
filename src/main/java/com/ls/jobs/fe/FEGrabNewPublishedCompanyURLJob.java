package com.ls.jobs.fe;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ls.entity.GrabDetailUrlLog;
import com.ls.repository.GrabDetailUrlLogRepository;
import com.ls.service.GrabCompanyDetailPageUrlService;
import com.ls.util.DateUtils;
import com.ls.util.XinXinUtils;
import com.ls.vo.ResponseVo;

public class FEGrabNewPublishedCompanyURLJob implements Job {
	
	private Logger logger = LoggerFactory.getLogger(FEGrabNewPublishedCompanyURLJob.class);
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		GrabCompanyDetailPageUrlService grabCompanyDetailPageUrlService = (GrabCompanyDetailPageUrlService) context.getJobDetail().getJobDataMap().get("grabCompanyDetailPageUrlService");
		GrabDetailUrlLogRepository grabDetailUrlLogRepository =  (GrabDetailUrlLogRepository) context.getJobDetail().getJobDataMap().get("grabDetailUrlLogRepository");
		
		Date todayByNow = new Date();
		long yesterdayByNow = todayByNow.getTime() - 24 * 60 * 60 * 1000;
		Date yesterday = new Date(yesterdayByNow);
		String postDateParameter = DateUtils.getPostDateParameter(todayByNow, yesterday);
		
		ResponseVo response = grabCompanyDetailPageUrlService.grabUrl(postDateParameter);
		
		logger.info(response.toString());
		grabDetailUrlLogRepository.save(new GrabDetailUrlLog("58", response.toString(), XinXinUtils.getNow()));
		
	}

}
