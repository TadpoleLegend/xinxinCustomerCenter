package com.ls.jobs.starter.detail;

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
import com.ls.jobs.fe.GrabCompanyJob;
import com.ls.repository.JobScheduleConfigurationRepository;
import com.ls.service.GrabService;

@Component("FEGrabNewPublishedCompanyJobStarter")
public class FEGrabNewPublishedCompanyJobStarter implements InitializingBean {

	private Logger logger = LoggerFactory.getLogger(FEGrabNewPublishedCompanyJobStarter.class);

	@Resource(name = "grabService")
	private GrabService grabService;

	@Autowired
	private JobScheduleConfigurationRepository jobScheduleConfigurationRepository;

	public void afterPropertiesSet() throws Exception {

		String startHourString = XinXinPropertiesReader.getString("detailStartHour");
		String startMinuteString = XinXinPropertiesReader.getString("detailStartMin");
		
		int startHour = Integer.valueOf(startHourString);
		int startMin = Integer.valueOf(startMinuteString);
		
		JobDataMap jobDataMap = new JobDataMap();

		jobDataMap.put("grabService", grabService);

		JobDetail jobDetail = JobBuilder.newJob(GrabCompanyJob.class).usingJobData(jobDataMap).withIdentity("daily_grab_new_company_job_at_11_00", "GRAB_Company_FE").build();

		CronTriggerImpl sixOclockTrigger = (CronTriggerImpl)CronScheduleBuilder.dailyAtHourAndMinute(startHour, startMin).build();
		sixOclockTrigger.setName("daily_grab_new_company_job_at_11_night");
		sixOclockTrigger.setGroup("GRAB_Company_TRIGGER_FE");

		Scheduler scheduler = XinXinJobHelper.getScheduler();
		if (null == scheduler) {
			logger.error("schedular null. ");
			return;
		} else {
			scheduler.scheduleJob(jobDetail, sixOclockTrigger);
		}

	}

}
