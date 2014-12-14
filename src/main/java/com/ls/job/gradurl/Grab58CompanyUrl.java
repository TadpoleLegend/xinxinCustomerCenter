package com.ls.job.gradurl;

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.ls.entity.CityURL;
import com.ls.entity.FeCompanyURL;
import com.ls.enums.ResourceTypeEnum;
import com.ls.grab.HtmlParserUtilFor58;
import com.ls.repository.CityURLRepository;
import com.ls.repository.FeCompanyURLRepository;
import com.ls.util.DateUtils;

public class Grab58CompanyUrl {
	private CityURLRepository cityURLRepository;
	private FeCompanyURLRepository feCompanyURLRepository;
	
	public CityURLRepository getCityURLRepository() {
		return cityURLRepository;
	}

	public void setCityURLRepository(CityURLRepository cityURLRepository) {
		this.cityURLRepository = cityURLRepository;
	}

	public FeCompanyURLRepository getFeCompanyURLRepository() {
		return feCompanyURLRepository;
	}

	public void setFeCompanyURLRepository(
			FeCompanyURLRepository feCompanyURLRepository) {
		this.feCompanyURLRepository = feCompanyURLRepository;
	}

	public void execute(){

		try{

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
				List<FeCompanyURL> companiesInThisPage = HtmlParserUtilFor58.getInstance().findPagedCompanyList(url);
				
				if(companiesInThisPage.isEmpty()){
					cityURL.setUpdateDate(date);
					cityURLRepository.save(cityURL);
					break;
				}
				for(FeCompanyURL company:companiesInThisPage){
					company.setCityId(cityURL.getCity().getId());
					FeCompanyURL feCompanyURL = this.feCompanyURLRepository.findCompany(company.getCityId(), company.getCompanyId());
					if(feCompanyURL == null){
						this.feCompanyURLRepository.save(company);
					}
				}
				
				}
			}
			
		
		}catch(Exception ex){
			ex.printStackTrace();
		}
	
	}
}
