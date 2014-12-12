package com.ls.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ls.entity.User;
import com.ls.repository.UserRepository;
import com.ls.service.CommonService;
import com.ls.util.XinXinUtils;

@Service("commonService")
public class CommonServiceImpl implements CommonService {

	@Autowired
	private UserRepository userRepository;
	
	public User getCurrentLoggedInUser() {

		String username = XinXinUtils.getCurrentUserName();
		
		return userRepository.findByUsername(username);
		
	}

}
