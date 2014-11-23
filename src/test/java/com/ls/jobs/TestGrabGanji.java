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
import com.ls.entity.Company;
import com.ls.enums.ResourceTypeEnum;
import com.ls.grab.HtmlParserUtilForGanJi;
import com.ls.repository.CityURLRepository;
import com.ls.repository.CompanyRepository;
import com.ls.util.DateUtils;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestGrabGanji {
	
	@Autowired
	private CityURLRepository cityURLRepository;
	@Autowired
	private CompanyRepository companyRepository;
	
	
	@Test
	public void testGrabCompanyList() throws Exception{
		try {
			Date date  = Calendar.getInstance().getTime();
			List<CityURL> cityUrls = cityURLRepository.findByResourceType(ResourceTypeEnum.Ganji.getId());
			for(CityURL cityURL:cityUrls){
				int days= 0;
				Object [] arr = new Object[2];
				if(cityURL.getUpdateDate()==null){
					arr[0]=0;
				}else{
					days = DateUtils.minusDate(cityURL.getUpdateDate(),date);
					int u = DateUtils.getGanjiDate(days);
					arr[0]=u;
				}
				for(int i=1;i<1000;i++){
				arr[1]=i;
				String cityUrl = cityURL.getUrl();
				String url =  MessageFormat.format(cityUrl, arr);
				System.err.println("url is : " + url);
				List<Company> companiesInThisPage = HtmlParserUtilForGanJi.getInstance().findPagedCompanyList(url);
				if(companiesInThisPage.isEmpty()){
					cityURL.setUpdateDate(date);
					cityURLRepository.save(cityURL);
					break;
				}
				for(Company company:companiesInThisPage){
					company.setCityId(cityURL.getCity().getId());
					companyRepository.save(company);
				}
				
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*public static void main(String[] args) {
		try {
			Map<String, Map<String, String>> provinces = LocationUtil.getInstance().findGanjiCities();
			if (!provinces.isEmpty()) {
				for (Entry<String, Map<String, String>> et : provinces
						.entrySet()) {
					Map<String, String> map = et.getValue();
					for (Entry<String, String> city : map.entrySet()) {
						String url = city.getValue() + "meirongshi/o1/";
						System.out.println(url);
						List<Company> companiesInThisPage = HtmlParserUtilForGanJi.getInstance().findPagedCompanyList(url);
						for (Company c : companiesInThisPage) {
							System.err.println(c.getName());
							System.err.println(c.getArea());
							System.err.println(c.getAddress());
							System.err.println(c.getContactor());
							System.err.println(c.getEmployeeCount());
							System.err.println(c.getfEurl());
							System.err.println(c.getDescription());
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
}
