package com.ls.jobs.fe;

import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.ls.entity.FeCompanyURL;
import com.ls.entity.GrabCompanyDetailLog;
import com.ls.repository.GrabCompanyDetailLogRepository;
import com.ls.service.GrabService;
import com.ls.util.XinXinUtils;

public class FEMnuallyGrabCompanyDetailJob implements Job {

	@SuppressWarnings("unchecked")
	public void execute(JobExecutionContext context) throws JobExecutionException {

		GrabService grabService = (GrabService)context.getJobDetail().getJobDataMap().get("grabService");

		List<FeCompanyURL> urls = (List<FeCompanyURL>)context.getJobDetail().getJobDataMap().get("detailPageUrls");
		GrabCompanyDetailLogRepository grabCompanyDetailLogRepository = (GrabCompanyDetailLogRepository)context.getJobDetail().getJobDataMap().get("grabCompanyDetailLogRepository");

		int success = 0;
		int fail = 0;
		GrabCompanyDetailLog grabCompanyDetailLog = new GrabCompanyDetailLog();

		grabCompanyDetailLog.setStartDate(XinXinUtils.getNow());

		for (FeCompanyURL feCompanyURL : urls) {
			try {
				grabService.grabSingleFECompanyByUrl(feCompanyURL);

				success++;
			} catch (Exception e) {
				fail++;
			}
		}

		grabCompanyDetailLog.setEndDate(XinXinUtils.getNow());
		grabCompanyDetailLog.setSuccessCount(success);
		grabCompanyDetailLog.setFailCount(fail);

		if (grabCompanyDetailLogRepository != null) {
			grabCompanyDetailLogRepository.save(grabCompanyDetailLog);
		}

		// TODO starter

	}
}
