package com.ls.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Sort;
import org.springframework.security.config.authentication.PasswordEncoderParser;
import org.springframework.stereotype.Component;

import com.ls.entity.City;
import com.ls.entity.Dictionary;
import com.ls.entity.Menu;
import com.ls.entity.Phase;
import com.ls.entity.Problem;
import com.ls.entity.ProblemCategory;
import com.ls.entity.Province;
import com.ls.entity.Role;
import com.ls.entity.Step;
import com.ls.entity.User;
import com.ls.repository.CityRepository;
import com.ls.repository.DropDownRepository;
import com.ls.repository.MenuRepository;
import com.ls.repository.PhaseRepository;
import com.ls.repository.ProblemCategoryRepository;
import com.ls.repository.ProblemRepository;
import com.ls.repository.ProvinceRepository;
import com.ls.repository.StepRepository;
import com.ls.repository.UserRepository;
import com.ls.service.CommonService;
import com.ls.service.CompanyService;
import com.ls.util.XinXinUtils;
import com.ls.vo.ResponseVo;

@Component("commonAction")
@Scope("prototype")
public class CommonAction extends BaseAction {

	private static final long serialVersionUID = 7274858323873739463L;

	@Resource(name = "companyService")
	private CompanyService companyService;

	@Resource(name = "commonService")
	private CommonService commonService;

	@Autowired
	private ProblemRepository problemRepository;

	@Autowired
	private ProvinceRepository provinceRepository;

	@Autowired
	private StepRepository stepRepository;
	@Autowired
	private PhaseRepository phaseRepository;

	@Autowired
	private DropDownRepository dropDownRepository;

	@Autowired
	private ProblemCategoryRepository problemCategoryRepository;

	@Autowired
	private MenuRepository menuRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CityRepository cityRepository;

	private List<Dictionary> companyTypes;

	private List<Problem> problems;
	private List<Province> provinces;
	private List<City> cities;
	private List<Step> steps;
	private List<Phase> phases;
	private List<ProblemCategory> problemCategories;
	private List<Menu> menus;
	private User user;

	public String findAllPhases() {

		phases = phaseRepository.findAll();

		if (phases != null) {
			for (Phase phase : phases) {
				phase.setLearningHistories(null);
			}
		}

		return SUCCESS;
	}

	/**
	 * static resources
	 */

	public String findAllProblems() {

		String type = getParameter("type");

		problems = problemRepository.findByCategory(type);

		for (Problem problem : problems) {
			problem.setCompanies(null);
		}

		return SUCCESS;
	}

	public String loadMe() {

		String username = XinXinUtils.getCurrentUserName();

		user = userRepository.findByUsername(username);
		
		user.setCities(null);
		user.setApplyingWillingCustomers(null);
		user.setPhoneCallHistory(null);
		
		List<Role> roles = user.getRoles();
		for (Role role : roles) {
			role.setUsers(null);
		}
		return SUCCESS;

	}

	public String findAllProvinces() {

		String dataRange = getParameter("dataRange");

		if (StringUtils.isNotBlank(dataRange) && dataRange.equals("all")) {

			provinces = provinceRepository.findAll();

			for (Province province : provinces) {

				List<City> cities = province.getCitys();

				for (City city : cities) {
					city.setProvince(null);
					city.setCityURLs(null);
					city.setUsers(null);
				}
			}

		} else {
			provinces = commonService.findUserAssignedCities();
		}

		return SUCCESS;
	}

	public String findAllCities() {

		return SUCCESS;
	}

	public String resetMyPassword() {

		String oldPassword = getParameter("oldPassword");
		String newPassword = getParameter("newPassword");
		
		User currentLoggedInUser = commonService.getCurrentLoggedInUser();
		
		String encodedPassword = XinXinUtils.getEncodedPassword(oldPassword, currentLoggedInUser.getUsername());
		if (encodedPassword.equals(currentLoggedInUser.getPassword())) {
			
			String newEncodedPassword = XinXinUtils.getEncodedPassword(newPassword, currentLoggedInUser.getUsername());
			currentLoggedInUser.setPassword(newEncodedPassword);
			
			userRepository.saveAndFlush(currentLoggedInUser);
			
			setResponse(XinXinUtils.makeGeneralSuccessResponse());
		} else {
			
			setResponse(ResponseVo.newFailMessage("你输入的旧密码不正确。"));
			
		}

		return SUCCESS;
	}

	public String findAllSteps() {

		steps = stepRepository.findAll(new Sort(Sort.Direction.ASC, "orderNumber"));

		return SUCCESS;
	}

	public String findDropDownDataSouce() {

		String identityType = getParameter("identityType");

		companyTypes = dropDownRepository.findByIdentity(identityType);

		return SUCCESS;
	}

	public String findProblemCategories() {

		problemCategories = problemCategoryRepository.findAll();

		return SUCCESS;
	}

	public String findAllMenus() {

		menus = menuRepository.findAll();

		return SUCCESS;
	}

	public List<Menu> getMenus() {

		return menus;
	}

	public User getUser() {

		return user;
	}

	public void setUser(User user) {

		this.user = user;
	}

	public void setMenus(List<Menu> menus) {

		this.menus = menus;
	}

	public List<Province> getProvinces() {

		return provinces;
	}

	public void setProvinces(List<Province> provinces) {

		this.provinces = provinces;
	}

	public List<Problem> getProblems() {

		return problems;
	}

	public void setProblems(List<Problem> problems) {

		this.problems = problems;
	}

	public List<City> getCities() {

		return cities;
	}

	public void setCities(List<City> cities) {

		this.cities = cities;
	}

	public List<Step> getSteps() {

		return steps;
	}

	public void setSteps(List<Step> steps) {

		this.steps = steps;
	}

	public List<ProblemCategory> getProblemCategories() {

		return problemCategories;
	}

	public void setProblemCategories(List<ProblemCategory> problemCategories) {

		this.problemCategories = problemCategories;
	}

	public List<Phase> getPhases() {

		return phases;
	}

	public void setPhases(List<Phase> phases) {

		this.phases = phases;
	}

	public List<Dictionary> getCompanyTypes() {

		return companyTypes;
	}

	public void setCompanyTypes(List<Dictionary> companyTypes) {

		this.companyTypes = companyTypes;
	}

}