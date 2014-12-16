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
import com.ls.entity.GanjiCompanyURL;
import com.ls.enums.ResourceTypeEnum;
import com.ls.grab.HtmlParserUtilForGanJi;
import com.ls.repository.CityURLRepository;
import com.ls.repository.GanjiCompanyURLRepository;
import com.ls.util.DateUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestGradGanjiCompanyURL {

	@Autowired
	private CityURLRepository cityURLRepository;
	@Autowired
	private GanjiCompanyURLRepository ganjiCompanyURLRepository;
	@Test
	public void testGrabCompanyList() throws Exception{
		try{

			Date date  = Calendar.getInstance().getTime();
			List<CityURL> cityUrls = cityURLRepository.findByResourceType(ResourceTypeEnum.Ganji.getId());
			for(CityURL cityURL:cityUrls){
				int days= 0;
				String cityUrl = null;
				Object [] arr =null;
				if(cityURL.getUpdateDate()==null){
					arr = new Object[1];
					cityUrl = cityURL.getBaseUrl();
				}else{
					arr = new Object[2];
					cityUrl = cityURL.getUrl();
					days = DateUtils.minusDate(cityURL.getUpdateDate(),date);
					int u = DateUtils.getGanjiDate(days);
					arr[1]=u;
				}
				for(int i=1;i<1000;i++){
				arr[0]=i;
				String url =  MessageFormat.format(cityUrl, arr);
				System.err.println("url is : " + url);
				List<GanjiCompanyURL> companiesInThisPage = HtmlParserUtilForGanJi.getInstance().findPagedCompanyList(url);
				
				if(companiesInThisPage.isEmpty()){
					cityURL.setUpdateDate(date);
					cityURLRepository.save(cityURL);
					break;
				}
				for(GanjiCompanyURL company:companiesInThisPage){
					company.setCityId(cityURL.getCity().getId());
					GanjiCompanyURL ganjiCompanyURL = this.ganjiCompanyURLRepository.findCompany(company.getCityId(), company.getCompanyId());
					if(ganjiCompanyURL == null){
						this.ganjiCompanyURLRepository.save(company);
					}
				}
				
				}
			}
			
		
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

}
