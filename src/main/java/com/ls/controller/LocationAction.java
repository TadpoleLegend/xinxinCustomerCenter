package com.ls.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ls.entity.City;
import com.ls.entity.Province;
import com.ls.repository.CityRepository;
import com.ls.repository.ProvinceRepository;

@Component("locationAction")
public class LocationAction extends BaseAction {
	
	private static final long serialVersionUID = 5470422430675388578L;

	private List<Province> provinces;
	private List<City> cities;
	
	@Autowired
	private CityRepository cityRepository;
	
	@Autowired
	private ProvinceRepository provinceRepository;
	
	public String getUserAssignedCities() {
		return SUCCESS;
	}
}