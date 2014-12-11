package com.ls.jobs;

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ls.entity.CityURL;
import com.ls.entity.OteCompanyURL;
import com.ls.enums.ResourceTypeEnum;
import com.ls.grab.HtmlParserUtilFor138;
import com.ls.repository.CityURLRepository;
import com.ls.repository.OteCompanyURLRepository;
import com.ls.util.DateUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestGrad138CompanyURL {
	@Autowired
	private CityURLRepository cityURLRepository;
	@Autowired
	private OteCompanyURLRepository oteCompanyURLRepository;
	@Test
	public void testGrabCompanyList() throws Exception{
		try{

			Date date  = Calendar.getInstance().getTime();
			List<CityURL> cityUrls = cityURLRepository.findByResourceType(ResourceTypeEnum.OneThreeEight.getId());
			for(CityURL cityURL:cityUrls){
				int days= 0;
				Object [] arr = new Object[3];
				if(cityURL.getUpdateDate()==null){
					arr[2]=0;
				}else{
					days = DateUtils.minusDate(date,cityURL.getUpdateDate());
					arr[2]=days;
				}
				for(int i=1;i<1300;i++){
				arr[1]=i;
				String cityUrl = cityURL.getUrl();
				String url =  MessageFormat.format(cityUrl, arr);
				System.err.println("url is : " + url);
				List<OteCompanyURL> companiesInThisPage = HtmlParserUtilFor138.getInstance().findPagedCompanyList(url);
				
				if(companiesInThisPage.isEmpty()){
					cityURL.setUpdateDate(date);
					cityURLRepository.save(cityURL);
					break;
				}
				for(OteCompanyURL company:companiesInThisPage){
					company.setCityId(cityURL.getCity().getId());
					OteCompanyURL oteCompanyURL = this.oteCompanyURLRepository.findCompany(company.getCityId(), company.getCompanyId());
					if(oteCompanyURL == null){
						this.oteCompanyURLRepository.save(company);
					}
				}
				
				}
			}
			
		
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
