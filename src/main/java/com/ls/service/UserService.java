package com.ls.service;

import java.util.List;
import java.util.Set;

import com.ls.entity.City;
import com.ls.entity.User;

public interface UserService {
	List<User> findUserByName(String name);
	Set<String> findAllAccounts();
	
	User findUser(String username, String password);
	
	List<City> getCitiesFromSpans(Object[] spanList);
	
	void updateUserCityAssignments(User userToAssign, Object[] cityArray);
}
