package com.ls.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ls.entity.City;
import com.ls.entity.Problem;
import com.ls.entity.Province;
import com.ls.entity.Step;
import com.ls.repository.ProblemRepository;
import com.ls.repository.ProvinceRepository;
import com.ls.repository.StepRepository;
import com.ls.service.CompanyService;

@Component("commonAction")
public class CommonAction extends BaseAction {
	
	private static final long serialVersionUID = 7274858323873739463L;
	
	@Resource(name = "companyService")
	private CompanyService companyService;
	
	@Autowired
	private ProblemRepository problemRepository;
	
	@Autowired
	private StepRepository stepRepository;
	
	@Autowired
	private ProvinceRepository provinceRepository;
	
	private List<Problem> problems;
	private List<Step> steps;
	private List<Province> provinces;
	private List<City> cities;
	
	/**
	 * static resources
	 */
	
	public String findAllProblems() {
		problems = problemRepository.findAll();
		
		return SUCCESS;
	}
	
	public String findAllSteps() {
		
		steps = stepRepository.findAll();
		
		return SUCCESS;
	}
	
	public String findAllProvinces() {
		provinces = provinceRepository.findAll();
		
		return SUCCESS;
	}
	
	public String findAllCities() {
		return SUCCESS;
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

	public List<Step> getSteps() {
		return steps;
	}

	public void setSteps(List<Step> steps) {
		this.steps = steps;
	}

	public List<City> getCities() {
		return cities;
	}

	public void setCities(List<City> cities) {
		this.cities = cities;
	}
	
}