package com.ls.jobs;

import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ls.jobs.fe.GrabCompanyJob;

public class XinXinJobHelper {

	private static Logger logger = LoggerFactory.getLogger(XinXinJobHelper.class);

	public static XinXinJobHelper _instance;

	private static Scheduler scheduler;

	private XinXinJobHelper() {

		if (null == scheduler) {
			try {
				scheduler = new StdSchedulerFactory().getScheduler();

				scheduler.start();
			} catch (SchedulerException e) {
				logger.error("can't create schedular." + e.getMessage());

			}
		}
	}

	public static XinXinJobHelper getInstance() {

		if (null == _instance) {
			_instance = new XinXinJobHelper();
		}

		return _instance;
	}

	public static Scheduler getScheduler() {

		if (null == scheduler) {
			try {
				scheduler = new StdSchedulerFactory().getScheduler();
			} catch (SchedulerException e) {
				logger.error("can't create schedular." + e.getMessage());

				return null;
			}
			try {
				scheduler.start();
			} catch (SchedulerException e) {

				logger.error("Starting schedular failed." + e.getMessage());
				return null;

			}
		}
		return scheduler;
	}

	public static void startUpQuartzJobImmunidiately(Class<Job> jobClass, JobDataMap JobDataMap) {
		
		String currentTimestamp = "" + System.currentTimeMillis();
		JobKey jobKey = JobKey.jobKey(currentTimestamp, "MANUALLY");
		JobDetail jobDetail = JobBuilder.newJob(jobClass).usingJobData(JobDataMap).withIdentity(jobKey).build();
		
		try {
			
			getScheduler().addJob(jobDetail, true);
			
			getScheduler().triggerJob(jobKey);
			
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
