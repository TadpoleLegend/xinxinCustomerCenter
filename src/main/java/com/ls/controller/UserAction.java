package com.ls.controller;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableList;
import com.ls.constants.XinXinConstants;
import com.ls.entity.City;
import com.ls.entity.Province;
import com.ls.entity.Role;
import com.ls.entity.User;
import com.ls.exception.ApplicationException;
import com.ls.repository.CityRepository;
import com.ls.repository.ProvinceRepository;
import com.ls.repository.RoleRepository;
import com.ls.repository.UserRepository;
import com.ls.service.UserService;
import com.ls.util.XinXinUtils;
import com.ls.vo.JsTreeOptions;
import com.ls.vo.ResponseVo;

@Component("userAction")
@Scope("prototype")
public class UserAction extends BaseAction {

	private static final long serialVersionUID = -3519886427026056067L;
	private String username;
	private String name;
	
	@Autowired
	private RoleRepository roleRepository;

	private List<User> users;

	private Set<String> usersList;

	private User user;

	@Resource(name = "userService")
	private UserService userService;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CityRepository cityRepository;
	@Autowired
	private ProvinceRepository provinceRepository;
	
	private List<Role> roles;
	
	private List<City> chinaCities;
	private List<Province> chinaProvinces;

	public void setUsername(String username) {

		this.username = username;
	}

	public String doLogin() {

		setupSession();

		String username = getParameter("username");
		String password = getParameter("password");

		User user = userService.findUser(username, password);

		if (null == user) {
			addActionMessage("User not found.");
			System.out.println("user not found : " + username + " " + password);
			return INPUT;

		} else {

			User storedUserInSession = (User) getSession().get(XinXinConstants.CURRENT_USER);
			
			if (storedUserInSession == null) {
				getSession().put(XinXinConstants.CURRENT_USER, user);
			}

			return SUCCESS;
		}

	}

	public String doLogoff() {

		setupSession();

		session.clear();

		return SUCCESS;

	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public String loadGrabPage() {

		return SUCCESS;
	}

	public String loadAssignLocationToUser() {

		return SUCCESS;
	}

	public String ajaxFindUser() {

		String name = getParameter("userName");

		if (StringUtils.isEmpty(name)) {
			users = userRepository.findAll(new Sort(Sort.Direction.ASC, "id"));
		} else {
			users = userService.findUserByName(name);
		}

		return SUCCESS;
	}

	/**
	 * for auto-complete plugin
	 * 
	 * @return
	 */
	public String getAllUserAccounts() {

		usersList = userService.findAllAccounts();

		return SUCCESS;
	}

	public String loadAllUsers() {

		users = userRepository.findAll(new Sort(Sort.Direction.ASC, "id"));
		for (User singleUser : users) {
			singleUser.setCities(null);
			//singleUser.setPassword(null);
			singleUser.setPhoneCallHistory(null);
		}

		return SUCCESS;
	}

	public String createUser() {

		try {
			String userJson = getParameter("userJson");
			
			if (StringUtils.isEmpty(userJson)) {
				setResponse(XinXinUtils.makeGeneralErrorResponse(null));
				return SUCCESS;
			}
			
			User userEntity = XinXinUtils.getJavaObjectFromJsonString(userJson, User.class);
			
			//new user
			if (userEntity.getId() == null) {
				
				User userInDb = userRepository.findByUsername(userEntity.getUsername());
				
				if (userInDb == null) {
					
					String password = userEntity.getPassword();
					userEntity.setPassword(XinXinUtils.getEncodedPassword(password, userEntity.getUsername()));
					userEntity.setActive(true);
					
				} else {
					
					setResponse(ResponseVo.newFailMessage(userEntity.getUsername() + "已经存在！"));
					
					return SUCCESS;
				}
				
			}

			userRepository.save(userEntity);
			
		} catch (Exception e) {
			setResponse(XinXinUtils.makeGeneralErrorResponse(e));
			return SUCCESS;
		}
		setResponse(XinXinUtils.makeGeneralSuccessResponse());
		
		return SUCCESS;
	}

	public String disactiveUser() {
		try {
			String userJson = getParameter("userJson");
			
			if (StringUtils.isEmpty(userJson)) {
				setResponse(XinXinUtils.makeGeneralErrorResponse(null));
				return SUCCESS;
			}
			
			User userEntity = XinXinUtils.getJavaObjectFromJsonString(userJson, User.class);
			
			userEntity.setActive(false);
			userEntity.setRoles(null);
			
			userRepository.save(userEntity);
		} catch (Exception e) {
			setResponse(XinXinUtils.makeGeneralErrorResponse(e));
			return SUCCESS;
		}
		
		setResponse(XinXinUtils.makeGeneralSuccessResponse());
		
		return SUCCESS;
	}
	
	public String updateUserRole() {
		
		try {
			String userJson = getParameter("userJson");
			String roleJson = getParameter("roleJson");
			String checkedOrNot = getParameter("checkedOrNot");
			
			User userFromClient = XinXinUtils.getJavaObjectFromJsonString(userJson, User.class);
			Role roleFromClient = XinXinUtils.getJavaObjectFromJsonString(roleJson, Role.class);
			
			User freshUser = userRepository.findOne(userFromClient.getId());
			Role freshRole = roleRepository.findOne(roleFromClient.getId());
			
			if (StringUtils.isNotBlank(checkedOrNot) && checkedOrNot.equals("true")) {
				
				freshUser.getRoles().add(freshRole);
				userRepository.save(freshUser);
				
			} else {
				
				freshUser.getRoles().remove(freshRole);
				userRepository.save(freshUser);
			}
			
		} catch (Exception e) {
			setResponse(XinXinUtils.makeGeneralErrorResponse(e));
			return SUCCESS;
		}
		
		setResponse(XinXinUtils.makeGeneralSuccessResponse());
		return SUCCESS;
	}
	public String resetPassword() {
		
		try {
			String userJson = getParameter("userJson");
			String newPasswordToReset = getParameter("newPasswordToReset");
			
			if (StringUtils.isEmpty(userJson)) {
				setResponse(XinXinUtils.makeGeneralErrorResponse(null));
				return SUCCESS;
			}
			
			User userEntity = XinXinUtils.getJavaObjectFromJsonString(userJson, User.class);
			
			User freshUserInDb = userRepository.findOne(userEntity.getId());
			
			freshUserInDb.setPassword(XinXinUtils.getEncodedPassword(newPasswordToReset, freshUserInDb.getUsername()));
			
			userRepository.save(freshUserInDb);
			
		} catch (Exception e) {
			setResponse(XinXinUtils.makeGeneralErrorResponse(e));
			return SUCCESS;
		}
		
		setResponse(XinXinUtils.makeGeneralSuccessResponse());
		
		return SUCCESS;
	}
	
	public String getAllRoles() {
		
		roles  = roleRepository.findAll();
		
		return SUCCESS;
	}
	
	public String updateUserCity() {
		
		try {
			String selectedCities = getParameter("selectedCities");
			
			User userToAssign = null;
			String targetUser = getParameter("userJson");
			if (StringUtils.isBlank(targetUser)) {
				setResponse(XinXinUtils.makeGeneralErrorResponse(new ApplicationException("未选择用户")));
				return SUCCESS;
			} else {
				userToAssign = XinXinUtils.getJavaObjectFromJsonString(targetUser, User.class);
			}
			
			Object[] cityArray = JSONArray.fromObject(selectedCities).toArray();
			
			userService.updateUserCityAssignments(userToAssign, cityArray);
			
		} catch (Exception e) {
			
			setResponse(XinXinUtils.makeGeneralErrorResponse(e));
			return SUCCESS;
		}
		
		setResponse(XinXinUtils.makeGeneralSuccessResponse());
		
		return SUCCESS;
	}
	
	public String showAssignedCities() {
		
		String userId = getParameter("userId");
		
		if (StringUtils.isBlank(userId)) {
			setResponse(XinXinUtils.makeGeneralErrorResponse(new ApplicationException("没有找到用户!")));
			
			return SUCCESS;
		}
		
		User targetUser = userRepository.findOne(Integer.valueOf(userId));
		
		chinaProvinces = provinceRepository.findAll();
		List<City> userCities = cityRepository.findByUsers(ImmutableList.of(targetUser));
		
		for (Province province : chinaProvinces) {
			List<City> citiesInTheProvince = province.getCitys();
			
			for (City singleCityInChina : citiesInTheProvince) {
				
				singleCityInChina.setProvince(null);
				singleCityInChina.setUsers(null);
				singleCityInChina.setCityURLs(null);
				if (userCities.contains(singleCityInChina)) {
					JsTreeOptions jsTreeOptions = new JsTreeOptions(true);
					
					singleCityInChina.setJsTreeJsonValue(JSONObject.fromObject(jsTreeOptions).toString().replace("\"", "\\\""));
				} else {
					JsTreeOptions jsTreeOptions = new JsTreeOptions(false);
					singleCityInChina.setJsTreeJsonValue(JSONObject.fromObject(jsTreeOptions).toString());
				}
			}
		}
		
		return SUCCESS;
	}
	
	public List<City> getChinaCities() {
	
		return chinaCities;
	}
	
	public void setChinaCities(List<City> chinaCities) {
	
		this.chinaCities = chinaCities;
	}

	public String getUsername() {

		return username;
	}

	public List<User> getUsers() {

		return users;
	}

	public void setUsers(List<User> users) {

		this.users = users;
	}

	public Set<String> getUsersList() {

		return usersList;
	}

	public void setUsersList(Set<String> usersList) {

		this.usersList = usersList;
	}

	public User getUser() {

		return user;
	}

	public void setUser(User user) {

		this.user = user;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	public List<Province> getChinaProvinces() {
	
		return chinaProvinces;
	}
	
	public void setChinaProvinces(List<Province> chinaProvinces) {
	
		this.chinaProvinces = chinaProvinces;
	}

}