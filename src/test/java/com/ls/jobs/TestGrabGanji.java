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
import com.ls.entity.GanjiCompanyURL;
import com.ls.grab.HtmlParserUtilForGanJi;
import com.ls.repository.CityRepository;
import com.ls.repository.CityURLRepository;
import com.ls.repository.CompanyRepository;
import com.ls.repository.GanjiCompanyURLRepository;
import com.ls.service.GrabCompanyDetailPageUrlService;
import com.ls.service.GrabService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestGrabGanji {

	@Autowired
	private CityURLRepository cityURLRepository;
	@Autowired
	private CompanyRepository companyRepository;
	@Resource(name = "grabService")
	private GrabService grabService;
	@Autowired
	private CityRepository cityRepository;
	@Autowired
	private GanjiCompanyURLRepository ganjiCompanyURLRepository;
	
	@Resource(name= "GJGrabCompanyDetailPageUrlService")
	GrabCompanyDetailPageUrlService grabCompanyDetailPageUrlService;
	
	private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

	private Company envelopeCompany(GanjiCompanyURL ganjiCompanyURL) {

		try {
			Company company = new Company();
			company.setName(ganjiCompanyURL.getName());
			company.setArea(ganjiCompanyURL.getArea());
			company.setUpdateDate(sf.parse(ganjiCompanyURL.getPublishDate()));
			company.setCityId(ganjiCompanyURL.getCityId());
			company.setGanjiresourceId(ganjiCompanyURL.getCompanyId());
			company.setGanjiUrl(ganjiCompanyURL.getUrl());
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
				List<GanjiCompanyURL> list = ganjiCompanyURLRepository.findByCityId(City.getId());
				for (GanjiCompanyURL ganjiCompanyURL : list) {
					Company company = envelopeCompany(ganjiCompanyURL);
					if (company != null) {
						HtmlParserUtilForGanJi.getInstance().findCompanyDetails(company);
						// grabService.mergeCompanyData(company, ResourceTypeEnum.Ganji.getId());
						ganjiCompanyURL.setHasGet(false);
						ganjiCompanyURLRepository.save(ganjiCompanyURL);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGrabWithSingleUrl() {
		String singleUrl = "http://www.ganji.com/gongsi/24170227/";
		grabService.grabSingleGJCompanyByUrl(singleUrl);
	}
	
	@Test
	public void testGrabAllUrl() {
		
		grabCompanyDetailPageUrlService.grabUrl(null);
	}
	
	@Test
	public void testGrabAll() {
		
		grabService.gjJobDailyWork();
	}
}
