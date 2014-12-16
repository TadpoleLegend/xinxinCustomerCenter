package com.ls.jobs;

import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ls.entity.City;
import com.ls.entity.CityURL;
import com.ls.entity.Province;
import com.ls.enums.ResourceTypeEnum;
import com.ls.grab.LocationUtil;
import com.ls.repository.CityRepository;
import com.ls.repository.CityURLRepository;
import com.ls.repository.ProvinceRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestGrabProvince {
	@Autowired
	private ProvinceRepository provinceRepository;
	@Autowired
	private CityRepository cityRepository;
	@Autowired
	private CityURLRepository cityURLRepository;
	
	
	
	@Test
	public void testInitProvinceAndCity() throws Exception{
		try {
			Map<String,Map<String,String>> provinces_138 = LocationUtil.getInstance().find138Cities();
			Map<String,Map<String,String>> provinces_58 = LocationUtil.getInstance().find58Cities();
			Map<String,Map<String,String>> provinces_ganji = LocationUtil.getInstance().findGanjiCities();
			if(!provinces_138.isEmpty()){
				for(Entry<String, Map<String, String>> entry_138 :provinces_138.entrySet()){
					Province province138 = new Province();
					province138.setName(entry_138.getKey());
					System.err.println(entry_138.getKey());
					province138 =  provinceRepository.save(province138);
					Map<String,String> map = entry_138.getValue();
					
					
					for(Entry<String,String> c:map.entrySet()){
						//save city
						City city = new City();
						city.setProvince(province138);
						city.setName(c.getKey());
						city = cityRepository.save(city);
						
						
						//save 138 city url
						CityURL city138URL = new CityURL();
						city138URL.setCity(city);
						city138URL.setUrl(c.getValue());
						city138URL.setResourceType(ResourceTypeEnum.OneThreeEight.getId());
						cityURLRepository.save(city138URL);
						
						if(!provinces_58.isEmpty()){
							for(Entry<String, Map<String, String>>  entry_58:provinces_58.entrySet()){
								boolean find58flag =false;
								if(entry_58.getKey().equals(entry_138.getKey())){
								Map<String,String> map58 = entry_58.getValue();
								for(Entry<String,String> city58:map58.entrySet()){
									if(city58.getKey().equals(c.getKey())){
									String url = city58.getValue()+"meirongshi/pn{0}/?postdate={1}_{2}";
									CityURL city58URL = new CityURL();
									city58URL.setCity(city);
									city58URL.setUrl(url);
									city58URL.setResourceType(ResourceTypeEnum.FiveEight.getId());
									city58URL.setBaseUrl(city58.getValue() + "meirongshi/pn");
									cityURLRepository.save(city58URL);
									find58flag = true;
									}
								}
							}else{
								continue;
							}
								if(find58flag){break;}
							}
						}
						if(!provinces_ganji.isEmpty()){
							for(Entry<String, Map<String, String>>  entry_ganji:provinces_ganji.entrySet()){
								boolean findgajiflag =false;
								if(entry_ganji.getKey().equals(entry_138.getKey())){
								Map<String,String> mapganji = entry_ganji.getValue();
								for(Entry<String,String> cityganji:mapganji.entrySet()){
									if(cityganji.getKey().equals(c.getKey())){
									String url = cityganji.getValue()+"meirongshi/u{1}o{0}";
									CityURL cityGanjiURL = new CityURL();
									cityGanjiURL.setCity(city);
									cityGanjiURL.setUrl(url);
									cityGanjiURL.setBaseUrl(cityganji.getValue()+"/meirongshi/o{0}/");
									cityGanjiURL.setResourceType(ResourceTypeEnum.Ganji.getId());
									cityURLRepository.save(cityGanjiURL);
									findgajiflag = true;
									}
								}
							}else{
								continue;
							}
								if(findgajiflag){break;}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
