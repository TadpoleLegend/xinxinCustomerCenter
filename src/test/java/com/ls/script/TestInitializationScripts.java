package com.ls.script;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ls.entity.Company;
import com.ls.entity.Step;
import com.ls.enums.CustomerStatusEnum;
import com.ls.repository.CompanyRepository;
import com.ls.repository.StepRepository;

/**
 * This class is for basic data preparation for the web app starting up. using this class without retrieving data from the UI. The data must not be
 * changed.
 * 
 * @author jjiang
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestInitializationScripts {

	@Autowired
	private StepRepository stepRepository;
	
	@Autowired
	private CompanyRepository companyRepository;

	@Test
	public void testInitialCompanySteps() throws Exception {

		Step stepOne = new Step(10, "非意向客户", 1);
		Step stepTwo = new Step(20, "申请成为意向客户", 2);
		Step stepThree = new Step(30, "公开课", 4);
		Step stepFour = new Step(40, "内训班", 3);
		Step stepFive = new Step(50, "精品班", 5);
		Step stepSix = new Step(60, "院长班", 6);
		Step stepSeven = new Step(70, "连锁班", 7);
		
		stepRepository.save(stepOne);
		stepRepository.save(stepTwo);
		stepRepository.save(stepThree);
		stepRepository.save(stepFour);
		stepRepository.save(stepFive);
		stepRepository.save(stepSix);
		stepRepository.save(stepSeven);
		
	}

	/**
	 * init company data
	 * 
	 * star : 0
	 * status : no willing 
	 * istracked : false
	 * 
	 * @throws Exception
	 */
	@Test
	public void testInitialCompanyStatus() throws Exception {

		List<Company> companies = companyRepository.findAll();
		for (Company company : companies) {
			company.setStatus(CustomerStatusEnum.NO_WILLING_CUSTOMER.getId().toString());
			company.setIsTracked(false);
			company.setStar(0);
			
			companyRepository.save(company);
		}
		
	}
}
