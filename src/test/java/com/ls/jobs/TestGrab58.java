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
	/*@Test
	public void testGrabCompanyList() throws Exception{
		try {
			Date date  = Calendar.getInstance().getTime();
			List<CityURL> cityUrls = cityURLRepository.findByResourceType(ResourceTypeEnum.FiveEight.getId());
			for(CityURL cityURL:cityUrls){
				 Calendar cal = Calendar.getInstance();
				int days= 0;
				Object [] arr = new Object[3];
				StringBuffer sb1=new StringBuffer();
				cal.add(Calendar.DAY_OF_MONTH, 1);
				sb1.append(cal.get(Calendar.YEAR)).append((cal.get(Calendar.MONTH)+1)>9?(cal.get(Calendar.MONTH)+1):"0"+(cal.get(Calendar.MONTH)+1)).append(cal.get(Calendar.DAY_OF_MONTH)>9?cal.get(Calendar.DAY_OF_MONTH):"0"+cal.get(Calendar.DAY_OF_MONTH));
				arr[2] = sb1.toString();
				if(cityURL.getUpdateDate()==null){
					cal.add(Calendar.DAY_OF_YEAR, -180);
					StringBuffer sb2=new StringBuffer();
					sb2.append(cal.get(Calendar.YEAR)).append((cal.get(Calendar.MONTH)+1)>9?(cal.get(Calendar.MONTH)+1):"0"+(cal.get(Calendar.MONTH)+1)).append(cal.get(Calendar.DAY_OF_MONTH)>9?cal.get(Calendar.DAY_OF_MONTH):"0"+cal.get(Calendar.DAY_OF_MONTH));
					arr[1]=sb2.toString();
				}else{
					days = DateUtils.minusDate(cityURL.getUpdateDate(),date);
					cal.add(Calendar.DAY_OF_YEAR, -(days+1));
					StringBuffer sb2=new StringBuffer();
					sb2.append(cal.get(Calendar.YEAR)).append((cal.get(Calendar.MONTH)+1)>9?(cal.get(Calendar.MONTH)+1):"0"+(cal.get(Calendar.MONTH)+1)).append(cal.get(Calendar.DAY_OF_MONTH)>9?cal.get(Calendar.DAY_OF_MONTH):"0"+cal.get(Calendar.DAY_OF_MONTH));
					arr[1]=sb2.toString();
				}
				for(int i=1;i<1000;i++){
				arr[0]=i;
				String cityUrl = cityURL.getUrl();
				String url =  MessageFormat.format(cityUrl, arr);
				System.err.println("url is : " + url);
				//List<Company> companiesInThisPage = HtmlParserUtilFor58.getInstance().findPagedCompanyList(url);
				List<Company> companiesInThisPage = null;
				if(companiesInThisPage.isEmpty()){
					cityURL.setUpdateDate(date);
					cityURLRepository.save(cityURL);
					break;
				}
				for(Company company:companiesInThisPage){
					company.setCityId(cityURL.getCity().getId());
					company.setResouceType(ResourceTypeEnum.FiveEight.getId());
					System.out.println(company.getPhoneSrc());
					System.out.println(company.getEmailSrc());
					System.out.println(company.getContactor());
					System.out.println(company.getAddress());
					System.out.println(company.getEmployeeCount());
					grabService.mergeCompanyData(company, ResourceTypeEnum.FiveEight.getId());
//					companyRepository.save(company);
				}
				Thread.currentThread().sleep(1000*20);
				
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	*/
	
	

	private Company envelopeCompany(FeCompanyURL feCompanyURL){
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
	public void testGrabCompany() throws Exception{
		try {
			List<City> Cities = cityRepository.findAll();
			for(City City:Cities){
				List<FeCompanyURL> list = feCompanyURLRepository.findByCityId(City.getId());
				for(FeCompanyURL feCompanyURL:list){
					Company company = envelopeCompany(feCompanyURL);
					if(company != null){
						HtmlParserUtilFor58.getInstance().findCompanyDetails(company);
						Company savedCompany = grabService.mergeCompanyData(company, ResourceTypeEnum.FiveEight.getId());
						feCompanyURL.setHasGet(true);
						if (savedCompany != null) {
							feCompanyURL.setSavedCompany(savedCompany.getId().toString());
						}
						feCompanyURLRepository.save(feCompanyURL);
						
						Thread.sleep(700);
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
}
