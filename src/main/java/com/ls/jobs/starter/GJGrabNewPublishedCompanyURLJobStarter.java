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
import com.ls.jobs.gj.GJGrabNewPublishedCompanyURLJob;
import com.ls.repository.GrabDetailUrlLogRepository;
import com.ls.repository.JobScheduleConfigurationRepository;
import com.ls.service.GrabCompanyDetailPageUrlService;

@Component("GJGrabNewPublishedCompanyURLJobStarter")
public class GJGrabNewPublishedCompanyURLJobStarter implements InitializingBean {

	private Logger logger = LoggerFactory.getLogger(GJGrabNewPublishedCompanyURLJobStarter.class);

	@Autowired
	private GrabDetailUrlLogRepository grabDetailUrlLogRepository;

	@Autowired
	private JobScheduleConfigurationRepository jobScheduleConfigurationRepository;

	@Resource(name = "GJGrabCompanyDetailPageUrlService")
	private GrabCompanyDetailPageUrlService grabCompanyDetailPageUrlService;

	public void afterPropertiesSet() throws Exception {

		int startHour, startMinute;

		List<JobScheduleConfiguration> onlyOneJobScheduleConfigurations = jobScheduleConfigurationRepository.findAll();

		if (onlyOneJobScheduleConfigurations == null || onlyOneJobScheduleConfigurations.isEmpty()) {
			startHour = 20;
			startMinute = 20;
		} else {

			JobScheduleConfiguration configuration = onlyOneJobScheduleConfigurations.get(0);
			startHour = configuration.getFeStartHour();
			startMinute = configuration.getFeStartMinute();
		}

		JobDataMap jobDataMap = new JobDataMap();

		jobDataMap.put("grabCompanyDetailPageUrlService", grabCompanyDetailPageUrlService);

		JobDetail elevenOclockJobDetail = JobBuilder.newJob(GJGrabNewPublishedCompanyURLJob.class).usingJobData(jobDataMap).withIdentity("GanJi_Daily_grab_new_company_url_job", "GRAB_URL").build();

		CronTriggerImpl elevenOclockTrigger = (CronTriggerImpl)CronScheduleBuilder.dailyAtHourAndMinute(18, 13).build();
		elevenOclockTrigger.setName("GanJi_Daily_grab_new_company_url_job_trigger");
		elevenOclockTrigger.setGroup("GRAB_URL");

		Scheduler scheduler = XinXinJobHelper.getScheduler();
		if (null == scheduler) {
			logger.error("schedular null. ");
			return;
		} else {
			scheduler.scheduleJob(elevenOclockJobDetail, elevenOclockTrigger);
		}

	}

}
