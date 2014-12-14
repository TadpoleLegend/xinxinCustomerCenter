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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ls.jobs.XinXinJobHelper;
import com.ls.jobs.fe.FEGrabNewPublishedCompanyURLJob;
import com.ls.repository.GrabDetailUrlLogRepository;
import com.ls.service.GrabCompanyDetailPageUrlService;

@Component("FEGrabNewPublishedCompanyURLJobStarter")
public class FEGrabNewPublishedCompanyURLJobStarter implements InitializingBean {

	private Logger logger = LoggerFactory.getLogger(FEGrabNewPublishedCompanyURLJobStarter.class);

	@Autowired
	private GrabDetailUrlLogRepository grabDetailUrlLogRepository;

	@Resource(name = "FEGrabCompanyDetailPageUrlService")
	private GrabCompanyDetailPageUrlService grabCompanyDetailPageUrlService;

	public void afterPropertiesSet() throws Exception {

		JobDataMap jobDataMap = new JobDataMap();
		
		jobDataMap.put("grabCompanyDetailPageUrlService", grabCompanyDetailPageUrlService);
		jobDataMap.put("grabDetailUrlLogRepository", grabDetailUrlLogRepository);

		JobDetail sixOclockJobDetail = JobBuilder.newJob(FEGrabNewPublishedCompanyURLJob.class).usingJobData(jobDataMap).withIdentity("FEGrabNewPublishedCompanyURLJob_6_00", "GRAB_URL").build();
		JobDetail elevenOclockJobDetail = JobBuilder.newJob(FEGrabNewPublishedCompanyURLJob.class).usingJobData(jobDataMap).withIdentity("FEGrabNewPublishedCompanyURLJob_11_00", "GRAB_URL").build();
		
		CronTriggerImpl sixOclockTrigger = (CronTriggerImpl) CronScheduleBuilder.dailyAtHourAndMinute(18, 0).build();
		sixOclockTrigger.setName("FEGrabNewPublishedCompanyURLJob_afternoon_at_six_clock");
		sixOclockTrigger.setGroup("GRAB_URL");

		CronTriggerImpl elevenOclockTrigger = (CronTriggerImpl) CronScheduleBuilder.dailyAtHourAndMinute(23, 0).build();
		elevenOclockTrigger.setName("FEGrabNewPublishedCompanyURLJob_evening_at_eleven_clock");
		elevenOclockTrigger.setGroup("GRAB_URL");

		Scheduler scheduler = XinXinJobHelper.getScheduler();
		if (null == scheduler) {
			logger.error("schedular null. ");
			return;
		} else {
			//scheduler.scheduleJobs(ImmutableMap.of(jobDetail, ImmutableList.of(sixOclockTrigger, elevenOclockTrigger)), true );
			scheduler.scheduleJob(sixOclockJobDetail, sixOclockTrigger);
			scheduler.scheduleJob(elevenOclockJobDetail, elevenOclockTrigger);
		}

	}
}
