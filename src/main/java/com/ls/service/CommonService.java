package com.ls.service;

import java.util.List;

import com.ls.entity.Province;
import com.ls.entity.User;


public interface CommonService {

	User getCurrentLoggedInUser();
	
	List<Province> findUserAssignedCities();
	
	boolean checkUserNotHasRole(String username, String roleName);
}
