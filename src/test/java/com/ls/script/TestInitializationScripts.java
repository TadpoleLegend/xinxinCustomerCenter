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
import com.ls.entity.Role;
import com.ls.entity.Step;
import com.ls.entity.User;
import com.ls.enums.CustomerStatusEnum;
import com.ls.repository.CityRepository;
import com.ls.repository.CompanyRepository;
import com.ls.repository.DropDownRepository;
import com.ls.repository.PhaseRepository;
import com.ls.repository.ProblemCategoryRepository;
import com.ls.repository.ProblemRepository;
import com.ls.repository.ProvinceRepository;
import com.ls.repository.RoleRepository;
import com.ls.repository.StepRepository;
import com.ls.repository.UserRepository;

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
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProvinceRepository provinceRepository;

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
			company.setStatus(CustomerStatusEnum.NO_WILLING_CUSTOMER.getId());
			company.setIsTracked(false);
			company.setStar(0);
			
			companyRepository.save(company);
		}
		
	}
	
	@Test
	public void testInitialPhase() throws Exception {
		
		Phase firstPhase = new Phase(10, "一期");
		Phase secondPhase = new Phase(10, "二期");
		Phase thirdPhase = new Phase(10, "三期");
		Phase fourthPhase = new Phase(10, "四期");
		
		List<Phase> phases = ImmutableList.of(firstPhase, secondPhase, thirdPhase, fourthPhase);
		
		phaseRepository.save(phases);
	}
	
	@Test
	public void testInitialCompanyType() throws Exception {
		
		Dictionary firstDictionary = new Dictionary(XinXinConstants.COMPANY_TYPE, "A", "A类顾客", "");
		Dictionary secondDictionary = new Dictionary(XinXinConstants.COMPANY_TYPE, "B", "B类顾客", "");
		Dictionary threeDictionary = new Dictionary(XinXinConstants.COMPANY_TYPE, "C", "C类顾客", "");
		
		List<Dictionary> dictionaries = ImmutableList.of(firstDictionary, secondDictionary, threeDictionary);
		
		dropDownRepository.save(dictionaries);
	}
	
	@Test
	public void removeBuxianCity() {
		List<City> cities = cityRepository.findAll();
		for (City city : cities) {
			if (city.getName().contains("不限")) {
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
		
		Dictionary allDictionary = new Dictionary(XinXinConstants.STAR_LEVEL_CONDITION, "ALL", "所有星级", "");
		Dictionary firstDictionary = new Dictionary(XinXinConstants.STAR_LEVEL_CONDITION, ">", "大于", "");
		Dictionary secondDictionary = new Dictionary(XinXinConstants.STAR_LEVEL_CONDITION, ">=", "大于或者等于", "");
		Dictionary threeDictionary = new Dictionary(XinXinConstants.STAR_LEVEL_CONDITION, "=", "等于", "");
		Dictionary lessThanDictionary = new Dictionary(XinXinConstants.STAR_LEVEL_CONDITION, "<", "小于", "");
		Dictionary lessOrEqualdDictionary = new Dictionary(XinXinConstants.STAR_LEVEL_CONDITION, "<=", "小于或者等于", "");
		Dictionary notEqualDictionary = new Dictionary(XinXinConstants.STAR_LEVEL_CONDITION, "<>", "不等于", "");
		
		List<Dictionary> dictionaries = ImmutableList.of(allDictionary, firstDictionary, secondDictionary, threeDictionary, lessThanDictionary, lessOrEqualdDictionary, notEqualDictionary);
		
		dropDownRepository.save(dictionaries);
	}
	
	@Test
	public void testAddCustomerProblems () {
		
		ProblemCategory customerProblemCategory = new ProblemCategory("顾客问题");
		ProblemCategory employeeProblemCategory = new ProblemCategory("员工问题");
		ProblemCategory otherCategory = new ProblemCategory("其他问题");
		
		List<ProblemCategory> categories = ImmutableList.of(customerProblemCategory, employeeProblemCategory, otherCategory);
		
		problemCategoryRepository.save(categories);
	}
	
	@Test
	public void testAddBirthdayType() {
		
		Dictionary bossBirthday = new Dictionary(XinXinConstants.BIRTHDAY_TYPE, "bossBirthday", "院长生日", "");
		Dictionary bigKidBirthday = new Dictionary(XinXinConstants.BIRTHDAY_TYPE, "firstKidBirthday", "大孩子生日", "");
		
		Dictionary secondKidBirthday = new Dictionary(XinXinConstants.BIRTHDAY_TYPE, "secondKidBirthday", "二孩子生日", "");
		Dictionary thirdKidBirthday = new Dictionary(XinXinConstants.BIRTHDAY_TYPE, "thirdKidBirthday", "小孩子生日", "");
		Dictionary husbandBirthday = new Dictionary(XinXinConstants.BIRTHDAY_TYPE, "loverBirthday", "老公生日", "");
		Dictionary merryAnniversary = new Dictionary(XinXinConstants.BIRTHDAY_TYPE, "merryAnniversary", "结婚纪念日", "");
		
		Dictionary companyAnniversary = new Dictionary(XinXinConstants.BIRTHDAY_TYPE, "companyAnniversary", "公司年庆", "");
		
		
		List<Dictionary> dictionaries = ImmutableList.of(bossBirthday, bigKidBirthday, secondKidBirthday, thirdKidBirthday, husbandBirthday, merryAnniversary, companyAnniversary);
		
		dropDownRepository.save(dictionaries);
	}
	
	@Test
	public void pureTest() {
		
	}
	
	@Test
	public void testCreateUserAndRoles() {
		
		User adminUser = new User("Jerry Jiang", "jerryjiang", "jerryjiang");
		User bigAreaUser = new User("Allen Li", "allenli", "allenli");
		User huliu = new User("Hu Liu", "huliu", "huliu");
		User liuxiaoxue = new User("Liu JianXia", "liujianxia", "liujianxia");
		
		Role superAdmin = new Role("系统管理者");
		Role bigAreaManager = new Role("大区经理");
		Role customerService = new Role("客服经理");
		Role financeManager = new Role("财务经理");
		Role newbite = new Role("新人");
		
		adminUser.setRoles(ImmutableList.of(superAdmin));
		bigAreaUser.setRoles(ImmutableList.of(bigAreaManager));
		huliu.setRoles(ImmutableList.of(customerService));
		liuxiaoxue.setRoles(ImmutableList.of(newbite));
		
		List<Role> roles = ImmutableList.of(superAdmin, bigAreaManager, customerService, financeManager, newbite);
		
		List<User> users = ImmutableList.of(adminUser, bigAreaUser, huliu, liuxiaoxue);
		
		roleRepository.save(roles);
		userRepository.save(users);
		
	}
	
	@Test
	public void testQueryByNativeSQL() {
		
		System.out.println(provinceRepository.getAllProvinceIds());
	}
}
