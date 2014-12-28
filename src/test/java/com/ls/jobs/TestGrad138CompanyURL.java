package com.ls.jobs;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ls.repository.CityURLRepository;
import com.ls.repository.OteCompanyURLRepository;
import com.ls.service.GrabCompanyDetailPageUrlService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestGrad138CompanyURL {
	@Autowired
	private CityURLRepository cityURLRepository;
	@Autowired
	private OteCompanyURLRepository oteCompanyURLRepository;

	@Resource(name = "OTEGrabCompanyDetailPageUrlService")
	private GrabCompanyDetailPageUrlService grabCompanyDetailPageUrlService;

	@Test
	public void testgrabTwoDaysRecently() {
		grabCompanyDetailPageUrlService.grabUrl(null);
	}
}
