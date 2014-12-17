package com.ls.jobs.starter;

import javax.annotation.Resource;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.ls.jobs.XinXinJobHelper;
import com.ls.jobs.fe.FEGrabCompanyDetailDailyJob;
import com.ls.service.GrabService;

@Component("FEGrabNewPublishedCompanyJobStarter")
public class FEGrabNewPublishedCompanyJobStarter implements InitializingBean {

	private Logger logger = LoggerFactory.getLogger(FEGrabNewPublishedCompanyJobStarter.class);

	@Resource(name = "grabService")
	private GrabService grabService;

	public void afterPropertiesSet() throws Exception {
		
		JobDataMap jobDataMap = new JobDataMap();
		
		jobDataMap.put("grabService", grabService);

		JobDetail jobDetail = JobBuilder.newJob(FEGrabCompanyDetailDailyJob.class).usingJobData(jobDataMap).withIdentity("daily_grab_new_company_job_at_11_00", "GRAB_Company").build();
		
		CronTriggerImpl sixOclockTrigger = (CronTriggerImpl) CronScheduleBuilder.dailyAtHourAndMinute(17, 30).build();
		sixOclockTrigger.setName("daily_grab_new_company_job_at_11_night");
		sixOclockTrigger.setGroup("GRAB_Company");

		Scheduler scheduler = XinXinJobHelper.getScheduler();
		if (null == scheduler) {
			logger.error("schedular null. ");
			return;
		} else {
			scheduler.scheduleJob(jobDetail, sixOclockTrigger);
		}

	}
	
}
