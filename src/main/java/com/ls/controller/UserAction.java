package com.ls.controller;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.ls.entity.User;
import com.ls.repository.UserRepository;
import com.ls.service.UserService;

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
			addActionMessage("锟斤拷没锟斤拷通锟斤拷锟斤拷证锟斤拷");
			System.out.println("user not found : " + username + " " + password);
			return INPUT;
			
		} else {
			user.getFunctions();
			user.getLocations();
			addActionMessage("锟斤拷陆锟缴癸拷");System.out.println(user.toString());
			
			setName(user.getName());
			setUsername(user.getUsername());
			
			User storedUserInSession = (User) getSession().get(user.getUsername().toString());
			if (storedUserInSession == null) {
				getSession().put(user.getUsername().toString(), user);
			}
			
			return SUCCESS;
		}
		
	}
	
	public String doLogoff() {
		setupSession();
		
		String username = getParameter("username");
		
		Object user = getSession().get(username);
		if (null == user) {
			getSession().remove(username);
		}
		
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
			users = userRepository.findAll(new Sort(Sort.Direction.ASC,"id"));
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

		return SUCCESS;
	}
	
	public String createUser() {
		String userName = getParameter("username");
		String name = getParameter("name");
		String password = getParameter("password");
		
		User userEntity = new User(name, userName, password);
		
		user = userRepository.save(userEntity);
		
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