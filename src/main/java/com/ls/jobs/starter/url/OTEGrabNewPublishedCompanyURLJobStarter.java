package com.ls.jobs.starter.url;

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

import com.ls.constants.XinXinPropertiesReader;
import com.ls.jobs.XinXinJobHelper;
import com.ls.jobs.gj.GrabUrlJob;
import com.ls.repository.GrabDetailUrlLogRepository;
import com.ls.repository.JobScheduleConfigurationRepository;
import com.ls.service.GrabCompanyDetailPageUrlService;

@Component("OTEGrabNewPublishedCompanyURLJobStarter")
public class OTEGrabNewPublishedCompanyURLJobStarter implements InitializingBean {

	private Logger logger = LoggerFactory.getLogger(OTEGrabNewPublishedCompanyURLJobStarter.class);

	@Autowired
	private GrabDetailUrlLogRepository grabDetailUrlLogRepository;

	@Autowired
	private JobScheduleConfigurationRepository jobScheduleConfigurationRepository;

	@Resource(name = "OTEGrabCompanyDetailPageUrlService")
	private GrabCompanyDetailPageUrlService grabCompanyDetailPageUrlService;

	public void afterPropertiesSet() throws Exception {

		String startHourString = XinXinPropertiesReader.getString("urlStartHour");
		String startMinuteString = XinXinPropertiesReader.getString("urlStartMin");
		
		int startHour = Integer.valueOf(startHourString);
		int startMin = Integer.valueOf(startMinuteString);

		JobDataMap jobDataMap = new JobDataMap();

		jobDataMap.put("grabCompanyDetailPageUrlService", grabCompanyDetailPageUrlService);

		JobDetail elevenOclockJobDetail = JobBuilder.newJob(GrabUrlJob.class).usingJobData(jobDataMap).withIdentity("138_Daily_grab_new_company_url_job", "GRAB_URL").build();

		CronTriggerImpl elevenOclockTrigger = (CronTriggerImpl)CronScheduleBuilder.dailyAtHourAndMinute(startHour, startMin + 2).build();
		elevenOclockTrigger.setName("138_Daily_grab_new_company_url_job_trigger");
		elevenOclockTrigger.setGroup("GRAB_URL_TRIGGER");

		Scheduler scheduler = XinXinJobHelper.getScheduler();
		if (null == scheduler) {
			logger.error("schedular null. ");
			return;
		} else {
			scheduler.scheduleJob(elevenOclockJobDetail, elevenOclockTrigger);
		}

	}

}
