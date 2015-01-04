package com.ls.script;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.ImmutableList;
import com.ls.constants.XinXinConstants;
import com.ls.entity.Dictionary;
import com.ls.entity.JobScheduleConfiguration;
import com.ls.entity.Menu;
import com.ls.entity.Phase;
import com.ls.entity.ProblemCategory;
import com.ls.entity.Role;
import com.ls.entity.Step;
import com.ls.entity.User;
import com.ls.enums.ApplyingCustomerStatus;
import com.ls.enums.CustomerStatusEnum;
import com.ls.repository.CityRepository;
import com.ls.repository.CompanyRepository;
import com.ls.repository.DropDownRepository;
import com.ls.repository.JobScheduleConfigurationRepository;
import com.ls.repository.MenuRepository;
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
	
	@Autowired
	private MenuRepository menuRepository;
	
	@Autowired
	private JobScheduleConfigurationRepository jobScheduleConfigurationRepository;
	
	@Test
	public void testInitialConfiguration() {
		
		JobScheduleConfiguration jobScheduleConfiguration = new JobScheduleConfiguration();
		
		jobScheduleConfiguration.setFeUrlStartHour(20);
		jobScheduleConfiguration.setFeUrlStartMinute(10);
		jobScheduleConfiguration.setGjUrlStartHour(20);
		jobScheduleConfiguration.setGjUrlStartMinute(20);
		jobScheduleConfiguration.setOteUrlStartHour(20);
		jobScheduleConfiguration.setOteUrlStartMinute(30);
		
		
		jobScheduleConfiguration.setFeStartHour(2);
		jobScheduleConfiguration.setFeStartMinute(2);
		jobScheduleConfiguration.setGjStartHour(2);
		jobScheduleConfiguration.setGjStartMinute(2);
		jobScheduleConfiguration.setOteStartHour(2);
		jobScheduleConfiguration.setOteStartMinute(2);
		
		jobScheduleConfigurationRepository.save(jobScheduleConfiguration);
		
	}
	
	@Test
	public void testInitialMenus() {
		
		Menu menu1 = new Menu("�˿�����", "/ls/user/load.ls", "customerCenter");
		Menu menu2 = new Menu("�û�����", "/ls/admin/loadUser.ls", "userManager");
		Menu menu3 = new Menu("ϵͳ����", "/ls/admin/configuration.ls", "systemConfiguration");
		Menu menu4 = new Menu("���ݲɼ�", "/ls/grab/load.ls", "dataCollection");
		Menu menu7 = new Menu("��ҳץȡ", "/ls/grab/loadSingleGrab.ls", "singleGrab");
		Menu menu5 = new Menu("����ͻ�����", "/ls/wccheck/loadApproveCustomer.ls", "willingCustomerApprove");
		Menu menu6 = new Menu("���з���", "/ls/admin/userCityAssign.ls", "userCityAssign");
		Menu menu8 = new Menu("����ͳ��", "/ls/admin/loadStatistics.ls", "statistics");
		menuRepository.save(ImmutableList.of(menu1, menu2, menu6, menu3, menu4, menu7, menu5, menu8));
		
	}

	@Test
	public void testInitialCompanySteps() throws Exception {
		
		CustomerStatusEnum statusEnum[] = CustomerStatusEnum.values();
		for (CustomerStatusEnum customerStatusEnum : statusEnum) {
			Step step = new Step(customerStatusEnum.getId(), customerStatusEnum.getName(), customerStatusEnum.getOrderNumber());
			stepRepository.save(step);
		}

	}

	@Test
	public void testInitialApplyingCustomerStatus() throws Exception {
		
		ApplyingCustomerStatus statusEnum[] = ApplyingCustomerStatus.values();
		for (ApplyingCustomerStatus customerStatusEnum : statusEnum) {
			Dictionary dictionary = new Dictionary(XinXinConstants.APPLYING_CUSTOMER_STATUS, customerStatusEnum.getId().toString(), customerStatusEnum.getName(), "");
			dropDownRepository.save(dictionary);
		}

	}
	
//	/**
//	 * init company data
//	 * 
//	 * star : 0
//	 * status : no willing 
//	 * istracked : false
//	 * 
//	 * @throws Exception
//	 */
//	@Test
//	public void testInitialCompanyStatus() throws Exception {
//
//		List<Company> companies = companyRepository.findAll();
//		for (Company company : companies) {
//			company.setStatus(CustomerStatusEnum.NO_WILLING_CUSTOMER.getId());
//			company.setIsTracked(false);
//			company.setStar(0);
//			
//			companyRepository.save(company);
//		}
//		
//	}
//	
	@Test
	public void testInitialPhase() throws Exception {
		
		Phase firstPhase = new Phase(10, "һ��");
		Phase secondPhase = new Phase(10, "����");
		Phase thirdPhase = new Phase(10, "����");
	//	Phase fourthPhase = new Phase(10, "����");
		
		List<Phase> phases = ImmutableList.of(firstPhase, secondPhase, thirdPhase/*, fourthPhase*/);
		
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
	
//	@Test
//	public void removeBuxianCity() {
//		List<City> cities = cityRepository.findAll();
//		for (City city : cities) {
//			if (city.getName().contains("����")) {
//				cityRepository.delete(city);
//			}
//		}
//	}
//	
//	@Test
//	public void removeProblems() {
//		problemRepository.deleteAll();
//	}
//	
//	@Test
//	public void removeCompany() {
//		companyRepository.deleteAll();
//	}
	
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
		
		ProblemCategory customerProblemCategory = new ProblemCategory("�˿�����");
		ProblemCategory employeeProblemCategory = new ProblemCategory("Ա������");
		ProblemCategory otherCategory = new ProblemCategory("��������");
		
		List<ProblemCategory> categories = ImmutableList.of(customerProblemCategory, employeeProblemCategory, otherCategory);
		
		problemCategoryRepository.save(categories);
	}
	
	@Test
	public void testAddBirthdayType() {
		
		Dictionary bossBirthday = new Dictionary(XinXinConstants.BIRTHDAY_TYPE, "bossBirthday", "Ժ������", "");
		Dictionary bigKidBirthday = new Dictionary(XinXinConstants.BIRTHDAY_TYPE, "firstKidBirthday", "��������", "");
		
		//Dictionary secondKidBirthday = new Dictionary(XinXinConstants.BIRTHDAY_TYPE, "secondKidBirthday", "����������", "");
		//Dictionary thirdKidBirthday = new Dictionary(XinXinConstants.BIRTHDAY_TYPE, "thirdKidBirthday", "С��������", "");
		Dictionary husbandBirthday = new Dictionary(XinXinConstants.BIRTHDAY_TYPE, "loverBirthday", "��������", "");
		Dictionary merryAnniversary = new Dictionary(XinXinConstants.BIRTHDAY_TYPE, "merryAnniversary", "��������", "");
		
		Dictionary companyAnniversary = new Dictionary(XinXinConstants.BIRTHDAY_TYPE, "companyAnniversary", "��˾����", "");
		
		
		List<Dictionary> dictionaries = ImmutableList.of(bossBirthday, bigKidBirthday, /*, secondKidBirthday, thirdKidBirthday, */husbandBirthday, merryAnniversary, companyAnniversary);
		
		dropDownRepository.save(dictionaries);
	}
	
	@Test
	public void pureTest() {
		
	}
	
	@Test
	public void testCreateUserAndRoles() {
		
		User adminUser = new User("Jerry Jiang", "jerryjiang", getEncodedPassword("jerryjiang", "jerryjiang"), true);
		
		User bigAreaUser = new User("Allen Li", "allenli", getEncodedPassword("allenli", "allenli"), true);
		
		User huliu = new User("Hu Liu", "huliu",getEncodedPassword("huliu", "huliu"), true);
		
		User liuxiaoxue = new User("Liu JianXia","liujianxia", getEncodedPassword("liujianxia", "liujianxia"), true);
		
		Role superAdmin = new Role("ROLE_ADMIN", "ϵͳ������");
		
		Role bigAreaManager = new Role("ROLE_SALES_MANAGER", "��������");
		
		Role customerService = new Role("ROLE_CS_MANAGER", "�ͷ�����");
		
		Role newbite = new Role("ROLE_NEW_GUY", "����");
		
		Role willingCustomerApprover = new Role("ROLE_WC_APPROVER", "����ͻ����");
		
		adminUser.setRoles(ImmutableList.of(superAdmin));
		
		bigAreaUser.setRoles(ImmutableList.of(bigAreaManager));
		
		huliu.setRoles(ImmutableList.of(customerService, willingCustomerApprover));
		
		liuxiaoxue.setRoles(ImmutableList.of(newbite));
		
		List<Role> roles = ImmutableList.of(superAdmin, bigAreaManager, customerService, newbite, willingCustomerApprover);
		
		List<User> users = ImmutableList.of(adminUser, bigAreaUser, huliu, liuxiaoxue);
		
		roleRepository.save(roles);
		
		userRepository.save(users);
		
	}
	
	private String getEncodedPassword(String password, String username) {
		ShaPasswordEncoder shaPasswordEncoder = new ShaPasswordEncoder(256);
		return shaPasswordEncoder.encodePassword(password, username);
	}
	
	@Test
	public void testSaveTrainingStatus() {
		Dictionary appoint = new Dictionary(XinXinConstants.TRANING_STATUS, "appointed", "��ԤԼ", "");
		Dictionary inTraning = new Dictionary(XinXinConstants.TRANING_STATUS, "inTraning", "������ѵ", "");
		Dictionary trained = new Dictionary(XinXinConstants.TRANING_STATUS, "trained", "����ѵ", "");
		
		dropDownRepository.save(ImmutableList.of(appoint, inTraning, trained));
	}

	@Test
	public void testActiveUsers() {
		
		List<User> users = userRepository.findAll();
		for (User user : users) {
			user.setActive(true);
		}
		
		userRepository.save(users);
	}
	
	
	@Test
	public void testJson() {
		
		BarChartData barChartData = new BarChartData();
		
		List<String> labels = ImmutableList.of("Shanghai", "BeiJing");
		barChartData.setLabels(labels);
		
		Data data = new Data();
		data.setData(ImmutableList.of("1110", "233"));
		barChartData.setDatasets(ImmutableList.of(data));
		
		System.out.println(JSONObject.fromObject(barChartData));
	}
	
	/**
	 * var barChartData = {
			labels : [ "January", "February", "March", "April", "May", "June",
					"July" ],
			datasets : [
					{
						fillColor : "rgba(220,220,220,0.5)",
						strokeColor : "rgba(220,220,220,0.8)",
						highlightFill : "rgba(220,220,220,0.75)",
						highlightStroke : "rgba(220,220,220,1)",
						data : [ randomScalingFactor(), randomScalingFactor(),
								randomScalingFactor(), randomScalingFactor(),
								randomScalingFactor(), randomScalingFactor(),
								randomScalingFactor() ]
					},
					{
						fillColor : "rgba(151,187,205,0.5)",
						strokeColor : "rgba(151,187,205,0.8)",
						highlightFill : "rgba(151,187,205,0.75)",
						highlightStroke : "rgba(151,187,205,1)",
						data : [ randomScalingFactor(), randomScalingFactor(),
								randomScalingFactor(), randomScalingFactor(),
								randomScalingFactor(), randomScalingFactor(),
								randomScalingFactor() ]
					} ]

		}
	 *
	 */
	public class BarChartData {
		private List<String> labels;
		private List<Data> datasets;
		
		public List<String> getLabels() {
		
			return labels;
		}
		
		public void setLabels(List<String> labels) {
		
			this.labels = labels;
		}
		
		public List<Data> getDatasets() {
		
			return datasets;
		}
		
		public void setDatasets(List<Data> datasets) {
		
			this.datasets = datasets;
		}
		
	}
	
	
	public class Data {
		private String fillColor = "rgba(220,220,220,0.5)";
		private String strokeColor = "rgba(220,220,220,0.8)";
		private String highlightFill = "rgba(220,220,220,0.75)";
		private String highlightStroke = "rgba(220,220,220,1)";
		
		private List<String> data = new ArrayList<String>();
		
		public String getFillColor() {
		
			return fillColor;
		}
		
		public void setFillColor(String fillColor) {
		
			this.fillColor = fillColor;
		}
		
		public String getStrokeColor() {
		
			return strokeColor;
		}
		
		public void setStrokeColor(String strokeColor) {
		
			this.strokeColor = strokeColor;
		}
		
		public List<String> getData() {
		
			return data;
		}
		
		public void setData(List<String> data) {
		
			this.data = data;
		}

		
		public String getHighlightFill() {
		
			return highlightFill;
		}

		
		public void setHighlightFill(String highlightFill) {
		
			this.highlightFill = highlightFill;
		}

		
		public String getHighlightStroke() {
		
			return highlightStroke;
		}
		
		public void setHighlightStroke(String highlightStroke) {
		
			this.highlightStroke = highlightStroke;
		}
	}
	
}
