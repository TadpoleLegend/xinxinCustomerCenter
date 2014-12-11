package com.ls.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.tags.Span;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.NodeVisitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ls.entity.City;
import com.ls.entity.User;
import com.ls.repository.CityRepository;
import com.ls.repository.UserRepository;
import com.ls.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CityRepository cityRepository;
	
	public List<User> findUserByName(String name) {
		return userRepository.findByName(name);
	}

	public Set<String> findAllAccounts() {
		List<User> users = userRepository.findAll();
		Set<String> userAccounts = new TreeSet<String>();
		for (User user : users) {
			userAccounts.add(user.getName());
		}
		return userAccounts;
	}

	public User findUser(final String username, final String password) {
		List<User> users = userRepository.findByUsernameAndPassword(username, password);
		
		if (users.isEmpty()) {
			return null;
		}
		
		if (users.size() != 1) {
			
		}

		return users.get(0);
	}

	public List<City> getCitiesFromSpans(Object[] spanList) {
		
		final List<City> cities = new ArrayList<City>();
		Parser htmlParser = new Parser();
		
		for (Object span : spanList) {
			
			if (null == span) {
				
				continue;
			}
			try {
				htmlParser.setInputHTML(span.toString());
				NodeVisitor nodeVisitor = new NodeVisitor(){

					@Override
					public void visitTag(Tag tag) {

						super.visitTag(tag);
						
						if (tag instanceof Span) {
							
							Span citySpan =  (Span) tag;
							String idString = citySpan.getAttribute("id");
							String dataType = citySpan.getAttribute("datatype");
							
							if (StringUtils.isNotBlank(idString) && StringUtils.isNotBlank(dataType) && dataType.equals("city")) {
								
								City singleCity = cityRepository.findOne(Integer.valueOf(idString));
								cities.add(singleCity);
							}
						}
					}
					
				};
				
				htmlParser.visitAllNodesWith(nodeVisitor);
				
			} catch (ParserException e) {
				
			}
			
		}
		
		return cities;
	}

	@Transactional
	@Secured("ROLE_ADMIN")
	public void updateUserCityAssignments(User userToAssign, Object[] cityArray) {

		User freshUser = userRepository.findOne(userToAssign.getId());
		freshUser.setCities(getCitiesFromSpans(cityArray));
		
		userRepository.save(freshUser);
		
	}

}
