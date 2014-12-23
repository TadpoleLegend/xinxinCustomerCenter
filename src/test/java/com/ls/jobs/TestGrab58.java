package com.ls.jobs;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ls.entity.City;
import com.ls.entity.Company;
import com.ls.entity.FeCompanyURL;
import com.ls.enums.ResourceTypeEnum;
import com.ls.grab.HtmlParserUtilFor58;
import com.ls.repository.CityRepository;
import com.ls.repository.CityURLRepository;
import com.ls.repository.CompanyRepository;
import com.ls.repository.FeCompanyURLRepository;
import com.ls.service.GrabService;
import com.ls.vo.ResponseVo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestGrab58 {

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private CityURLRepository cityURLRepository;

	@Resource(name = "grabService")
	private GrabService grabService;
	@Autowired
	private CityRepository cityRepository;
	@Autowired
	private FeCompanyURLRepository feCompanyURLRepository;
	private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

	private Company envelopeCompany(FeCompanyURL feCompanyURL) {

		try {
			Company company = new Company();
			company.setName(feCompanyURL.getName());
			company.setArea(feCompanyURL.getArea());
			company.setUpdateDate(sf.parse(feCompanyURL.getPublishDate()));
			company.setCityId(feCompanyURL.getCityId());
			company.setfEresourceId(feCompanyURL.getCompanyId());
			company.setfEurl(feCompanyURL.getUrl());
			return company;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Test
	public void testGrabCompany() throws Exception {

		try {
			List<City> Cities = cityRepository.findAll();
			for (City City : Cities) {
				List<FeCompanyURL> list = feCompanyURLRepository.findByCityId(City.getId());
				for (FeCompanyURL feCompanyURL : list) {
					Company company = envelopeCompany(feCompanyURL);
					if (company != null) {
						HtmlParserUtilFor58.getInstance().findCompanyDetails(company);
						grabService.saveCompanyToDb(company, ResourceTypeEnum.FiveEight.getId(), feCompanyURL);

						Thread.sleep(700);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGrabSingleCompany() throws Exception {

		try {

			grabService.grabSingleFECompanyByUrlId(7);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGrabSingleCompanyByUrl() {

		String detailPageUrl = "http://qy.58.com/27834769222150/";
		ResponseVo responseVo = grabService.grabSingleFECompanyByUrl(detailPageUrl);
		
		System.out.println(responseVo);
	}
	
	@Test
	public void testGrabDailyJob() {

		grabService.feJobDailyWork();
	}
	
	
}
