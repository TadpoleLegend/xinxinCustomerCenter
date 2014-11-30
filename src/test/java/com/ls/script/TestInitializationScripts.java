package com.ls.script;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.parsing.Problem;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.ls.constants.XinXinConstants;
import com.ls.entity.City;
import com.ls.entity.Company;
import com.ls.entity.Dictionary;
import com.ls.entity.Phase;
import com.ls.entity.ProblemCategory;
import com.ls.entity.Step;
import com.ls.enums.CustomerStatusEnum;
import com.ls.repository.CityRepository;
import com.ls.repository.CompanyRepository;
import com.ls.repository.DropDownRepository;
import com.ls.repository.PhaseRepository;
import com.ls.repository.ProblemCategoryRepository;
import com.ls.repository.ProblemRepository;
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
	
	@Autowired
	private PhaseRepository phaseRepository;
	
	@Autowired
	private DropDownRepository dropDownRepository;
	
	@Autowired
	private ProblemRepository problemRepository;
	
	@Autowired
	private CityRepository cityRepository;
	
	@Autowired
	private ProblemCategoryRepository problemCategoryRepository;

	@Test
	public void testInitialCompanySteps() throws Exception {

		Step stepOne = new Step(10, "������ͻ�", 1);
		Step stepTwo = new Step(20, "�����Ϊ����ͻ�", 2);
		Step stepThree = new Step(30, "������", 4);
		Step stepFour = new Step(40, "��ѵ��", 3);
		Step stepFive = new Step(50, "��Ʒ��", 5);
		Step stepSix = new Step(60, "Ժ����", 6);
		Step stepSeven = new Step(70, "������", 7);
		
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
			company.setStatus(CustomerStatusEnum.NO_WILLING_CUSTOMER.getId());
			company.setIsTracked(false);
			company.setStar(0);
			
			companyRepository.save(company);
		}
		
	}
	
	@Test
	public void testInitialPhase() throws Exception {
		
		Phase firstPhase = new Phase(10, "һ��");
		Phase secondPhase = new Phase(10, "����");
		Phase thirdPhase = new Phase(10, "����");
		Phase fourthPhase = new Phase(10, "����");
		
		List<Phase> phases = ImmutableList.of(firstPhase, secondPhase, thirdPhase, fourthPhase);
		
		phaseRepository.save(phases);
	}
	
	@Test
	public void testInitialCompanyType() throws Exception {
		
		Dictionary firstDictionary = new Dictionary(XinXinConstants.COMPANY_TYPE, "A", "A��˿�", "");
		Dictionary secondDictionary = new Dictionary(XinXinConstants.COMPANY_TYPE, "B", "B��˿�", "");
		Dictionary threeDictionary = new Dictionary(XinXinConstants.COMPANY_TYPE, "C", "C��˿�", "");
		
		List<Dictionary> dictionaries = ImmutableList.of(firstDictionary, secondDictionary, threeDictionary);
		
		dropDownRepository.save(dictionaries);
	}
	
	@Test
	public void testProblemCategory() throws Exception {
		
		Dictionary firstDictionary = new Dictionary(XinXinConstants.COMPANY_TYPE, "A", "A��˿�", "");
		Dictionary secondDictionary = new Dictionary(XinXinConstants.COMPANY_TYPE, "B", "B��˿�", "");
		Dictionary threeDictionary = new Dictionary(XinXinConstants.COMPANY_TYPE, "C", "C��˿�", "");
		
		List<Dictionary> dictionaries = ImmutableList.of(firstDictionary, secondDictionary, threeDictionary);
		
		dropDownRepository.save(dictionaries);
	}
	
	@Test
	public void removeBuxianCity() {
		List<City> cities = cityRepository.findAll();
		for (City city : cities) {
			if (city.getName().contains("����")) {
				cityRepository.delete(city);
			}
		}
	}
	
	@Test
	public void removeProblems() {
		problemRepository.deleteAll();
	}
	
	@Test
	public void removeCompany() {
		companyRepository.deleteAll();
	}
	
	@Test
	public void testAddStarLevel() {
		
		Dictionary allDictionary = new Dictionary(XinXinConstants.STAR_LEVEL_CONDITION, "ALL", "�����Ǽ�", "");
		Dictionary firstDictionary = new Dictionary(XinXinConstants.STAR_LEVEL_CONDITION, ">", "����", "");
		Dictionary secondDictionary = new Dictionary(XinXinConstants.STAR_LEVEL_CONDITION, ">=", "���ڻ��ߵ���", "");
		Dictionary threeDictionary = new Dictionary(XinXinConstants.STAR_LEVEL_CONDITION, "=", "����", "");
		Dictionary lessThanDictionary = new Dictionary(XinXinConstants.STAR_LEVEL_CONDITION, "<", "С��", "");
		Dictionary lessOrEqualdDictionary = new Dictionary(XinXinConstants.STAR_LEVEL_CONDITION, "<=", "С�ڻ��ߵ���", "");
		Dictionary notEqualDictionary = new Dictionary(XinXinConstants.STAR_LEVEL_CONDITION, "<>", "������", "");
		
		List<Dictionary> dictionaries = ImmutableList.of(allDictionary, firstDictionary, secondDictionary, threeDictionary, lessThanDictionary, lessOrEqualdDictionary, notEqualDictionary);
		
		dropDownRepository.save(dictionaries);
	}
	
	@Test
	public void testAddCustomerProblems () {
		
		ProblemCategory customerProblemCategory = new ProblemCategory("�ͻ�����");
		ProblemCategory employeeProblemCategory = new ProblemCategory("Ա������");
		ProblemCategory otherCategory = new ProblemCategory("��������");
		
		List<ProblemCategory> categories = ImmutableList.of(customerProblemCategory, employeeProblemCategory, otherCategory);
		
		problemCategoryRepository.save(categories);
	}
	
	@Test
	public void pureTest() {
		
	}
}
