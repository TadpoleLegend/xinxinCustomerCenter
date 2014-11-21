package com.ls.jobs;

import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ls.entity.Company;
import com.ls.grab.HtmlParserUtilFor58;
import com.ls.grab.HtmlParserUtilPlanB;
import com.ls.grab.HttpClientGrabUtil;
import com.ls.grab.LocationUtil;
import com.ls.repository.CompanyRepository;
import com.ls.repository.CompanyResourceRepository;
import com.ls.repository.ProblemRepository;
import com.ls.repository.ProvinceRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestGrab58 {


	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
	private ProblemRepository problemRepository;
	
	@Autowired
	private CompanyResourceRepository companyResourceRepository;
	
	@Autowired
	private ProvinceRepository provinceRepository;
	

	@Test
	public void testGrabCompanyList() throws Exception{
		Map<String,Map<String,String>> provinces = LocationUtil.getInstance().find58Cities();
		if(!provinces.isEmpty()){
			for(Entry<String, Map<String, String>>  et:provinces.entrySet()){
				Map<String,String> map = et.getValue();
				for(Entry<String,String> city:map.entrySet()){
					String url = city.getValue()+"meirongshi/pn0";
					System.err.println(url);
					List<Company> companiesInThisPage = HtmlParserUtilFor58.getInstance().findPagedCompanyList(url);
					Thread.currentThread().sleep(1000*10);
				}
			}
		}
	}

	
}
