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
import com.ls.jobs.ote.GrabCompanyJob;
import com.ls.repository.JobScheduleConfigurationRepository;
import com.ls.service.GrabService;

@Component("OTEGrabNewPublishedCompanyJobStarter")
public class OTEGrabNewPublishedCompanyJobStarter implements InitializingBean {

	private Logger logger = LoggerFactory.getLogger(OTEGrabNewPublishedCompanyJobStarter.class);

	@Autowired
	private JobScheduleConfigurationRepository jobScheduleConfigurationRepository;
	
	@Resource(name = "grabService")
	private GrabService grabService;

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
		
		jobDataMap.put("grabService", grabService);

		JobDetail jobDetail = JobBuilder.newJob(GrabCompanyJob.class).usingJobData(jobDataMap).withIdentity("138_daily_grab_new_company_job", "GRAB_Company").build();
		
		CronTriggerImpl grabCompanyTrigger = (CronTriggerImpl) CronScheduleBuilder.dailyAtHourAndMinute(22, 23).build();
		grabCompanyTrigger.setName("138_daily_grab_new_company_job_trigger");
		grabCompanyTrigger.setGroup("138_GRAB_Company");

		Scheduler scheduler = XinXinJobHelper.getScheduler();
		if (null == scheduler) {
			logger.error("schedular null. ");
			return;
		} else {
			scheduler.scheduleJob(jobDetail, grabCompanyTrigger);
		}

	}
	
}
