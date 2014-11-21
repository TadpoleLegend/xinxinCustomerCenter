package com.ls.jobs;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.ls.entity.Company;
import com.ls.grab.HtmlParserUtilFor138;
import com.ls.grab.LocationUtil;

//@RunWith(SpringJUnit4ClassRunner.class)
public class TestGrab138 {
	
	
	public static void main(String []args){
		
		try {
			HtmlParserUtilFor138.getInstance().login138();
			Map<String,Map<String,String>> provinces = LocationUtil.getInstance().find138Cities();
			if(!provinces.isEmpty()){
				for(Entry<String, Map<String, String>>  et:provinces.entrySet()){
					Map<String,String> map = et.getValue();
					for(Entry<String,String> city:map.entrySet()){
						System.err.println(city.getValue());
						List<Company> companiesInThisPage = HtmlParserUtilFor138.getInstance().findPagedCompanyList(city.getValue());
						//System.err.println("url is " +MessageFormat.format(place_138_str, city.getValue()));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
