package com.ls.controller;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.ls.constants.XinXinConstants;
import com.ls.entity.User;
import com.ls.repository.UserRepository;
import com.ls.service.UserService;
import com.ls.util.XinXinUtils;
import com.ls.vo.ResponseVo;

@Component("userAction")
public class UserAction extends BaseAction {

	private static final long serialVersionUID = -3519886427026056067L;
	private String username;
	private String name;

	private List<User> users;

	private Set<String> usersList;

	private User user;

	@Resource(name = "userService")
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

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

}