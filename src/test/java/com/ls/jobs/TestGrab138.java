package com.ls.jobs;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ls.entity.Company;
import com.ls.repository.CityRepository;
import com.ls.repository.CityURLRepository;
import com.ls.repository.CompanyRepository;
import com.ls.repository.OteCompanyURLRepository;
import com.ls.service.GrabCompanyDetailPageUrlService;
import com.ls.service.GrabService;
import com.ls.vo.ResponseVo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestGrab138 {

	@Autowired
	private CityURLRepository cityURLRepository;
	@Autowired
	private CompanyRepository companyRepository;
	@Resource(name = "grabService")
	private GrabService grabService;
	@Autowired
	private CityRepository cityRepository;
	@Autowired
	private OteCompanyURLRepository oteCompanyURLRepository;

	@Resource(name = "OTEGrabCompanyDetailPageUrlService")
	private GrabCompanyDetailPageUrlService grabCompanyDetailPageUrlService;

	@Test
	public void testGrabCompany() throws Exception {

	}

	@Test
	public void testGrabSingleCompany() throws Exception {
		
		ResponseVo responseVo = grabCompanyDetailPageUrlService.grabSingleCityUrl(2);
		
	}
	
	@Test
	public void testGrabSinglePage() throws Exception {
		
		String testUrl = "http://www.138job.com/shtml/Company/18006/C_810501.shtml";
		grabService.grabSingleOTECompanyByUrl(testUrl);
	}
	
	@Test
	public void testGrabSingleDetailedPage() throws Exception {
		
		String testUrl = "http://www.138job.com/shtml/Company/08705/C_87347.shtml";
		Company company = new Company();
		company.setOteUrl(testUrl);
		
		grabService.getOTECompanyDetails(company, testUrl);
	}
}
