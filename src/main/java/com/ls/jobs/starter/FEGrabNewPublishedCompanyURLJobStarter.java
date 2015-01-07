package com.ls.jobs.starter;

import java.util.List;

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

import com.ls.entity.JobScheduleConfiguration;
import com.ls.jobs.XinXinJobHelper;
import com.ls.jobs.fe.GrabUrlJob;
import com.ls.repository.GrabDetailUrlLogRepository;
import com.ls.repository.JobScheduleConfigurationRepository;
import com.ls.service.GrabCompanyDetailPageUrlService;

@Component("FEGrabNewPublishedCompanyURLJobStarter")
public class FEGrabNewPublishedCompanyURLJobStarter implements InitializingBean {

	private Logger logger = LoggerFactory.getLogger(FEGrabNewPublishedCompanyURLJobStarter.class);

	@Autowired
	private GrabDetailUrlLogRepository grabDetailUrlLogRepository;

	@Autowired
	private JobScheduleConfigurationRepository jobScheduleConfigurationRepository;

	@Resource(name = "FEGrabCompanyDetailPageUrlService")
	private GrabCompanyDetailPageUrlService grabCompanyDetailPageUrlService;

	public void afterPropertiesSet() throws Exception {

		int startHour, startMinute;

		List<JobScheduleConfiguration> onlyOneJobScheduleConfigurations = jobScheduleConfigurationRepository.findAll();
		if (onlyOneJobScheduleConfigurations == null || onlyOneJobScheduleConfigurations.isEmpty()) {
			startHour = 20;
			startMinute = 10;
		} else {

			JobScheduleConfiguration configuration = onlyOneJobScheduleConfigurations.get(0);
			startHour = configuration.getFeStartHour();
			startMinute = configuration.getFeStartMinute();
		}

		JobDataMap jobDataMap = new JobDataMap();

		jobDataMap.put("grabCompanyDetailPageUrlService", grabCompanyDetailPageUrlService);

		JobDetail elevenOclockJobDetail = JobBuilder.newJob(GrabUrlJob.class).usingJobData(jobDataMap).withIdentity("five_eight_daily_grab_new_url_job", "FE_GRAB_URL").build();

		CronTriggerImpl elevenOclockTrigger = (CronTriggerImpl)CronScheduleBuilder.dailyAtHourAndMinute(15, 19).build();
		elevenOclockTrigger.setName("five_eight_daily_grab_new_url_job_trigger");
		elevenOclockTrigger.setGroup("FE_GRAB_URL_TRIGGER");

		Scheduler scheduler = XinXinJobHelper.getScheduler();
		if (null == scheduler) {
			logger.error("schedular null. ");
			return;
		} else {
			scheduler.scheduleJob(elevenOclockJobDetail, elevenOclockTrigger);
		}

	}

}
