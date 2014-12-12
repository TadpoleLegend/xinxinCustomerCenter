package com.ls.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;
import com.ls.entity.City;
import com.ls.entity.Province;
import com.ls.entity.User;
import com.ls.repository.CityRepository;
import com.ls.repository.UserRepository;
import com.ls.service.CommonService;
import com.ls.util.XinXinUtils;

@Service("commonService")
public class CommonServiceImpl implements CommonService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CityRepository cityRepository;
	
	public User getCurrentLoggedInUser() {

		String username = XinXinUtils.getCurrentUserName();
		
		return userRepository.findByUsername(username);
		
	}

	public List<Province> findUserAssignedCities() {
		
		List<City> userCities = cityRepository.findByUsers(ImmutableList.of(getCurrentLoggedInUser()));
		
		List<Province> provinces = new ArrayList<Province>();
		
		for (City singleCity : userCities) {
			Province singleProvince = singleCity.getProvince();
			
			if (!provinces.contains(singleProvince)) {
				provinces.add(singleProvince);
			}
		}
		
		for (Province province : provinces) {
			
			List<City> cities = province.getCitys();
			List<City> assignedCities = new ArrayList<City>();
			for (City city : cities) {
				if (checkExistsInCityList(city, userCities)) {
					assignedCities.add(city);
				}
				city.setCityURLs(null);
				city.setUsers(null);
			}
			province.setCitys(assignedCities);
		}
		
		return provinces;
	}
	
	private boolean checkExistsInCityList(City city, List<City> cities) {
		for (City singleCity : cities) {
			if (singleCity.getId() == city.getId()) {
				return true;
			}
		}
		
		return false;
	}
}
