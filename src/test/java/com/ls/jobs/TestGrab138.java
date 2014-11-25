package com.ls.jobs;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ls.entity.CityURL;
import com.ls.entity.Company;
import com.ls.enums.ResourceTypeEnum;
import com.ls.grab.HtmlParserUtilFor138;
import com.ls.repository.CityURLRepository;
import com.ls.repository.CompanyRepository;
import com.ls.util.DateUtils;
import com.ls.util.XinXinUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestGrab138 {
	@Autowired
	private CityURLRepository cityURLRepository;
	@Autowired
	private CompanyRepository companyRepository;
	
	
	@Test
	public void testGrabCompanyList() throws Exception{
		try {
			Date date  = Calendar.getInstance().getTime();
			List<CityURL> cityUrls = cityURLRepository.findByResourceType(ResourceTypeEnum.OneThreeEight.getId());
			for(CityURL cityURL:cityUrls){
				int days= 0;
				Object [] arr = new Object[3];
				if(cityURL.getUpdateDate()==null){
					arr[2]=0;
				}else{
					days = DateUtils.minusDate(cityURL.getUpdateDate(),date);
					arr[2]=days;
				}
				for(int i=1;i<1000;i++){
				arr[1]=i;
				String cityUrl = cityURL.getUrl();
				String url =  MessageFormat.format(cityUrl, arr);
				System.err.println("url is : " + url);
				List<Company> companiesInThisPage = HtmlParserUtilFor138.getInstance().findPagedCompanyList(url);
				
				if(companiesInThisPage.isEmpty()){
					cityURL.setUpdateDate(date);
					cityURLRepository.save(cityURL);
					break;
				}
				for(Company company:companiesInThisPage){
					company.setCityId(cityURL.getCity().getId());
					company.setResouceType(ResourceTypeEnum.OneThreeEight.getId());
					companyRepository.save(company);
				}
				
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
