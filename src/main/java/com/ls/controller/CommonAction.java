package com.ls.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.ls.entity.City;
import com.ls.entity.Phase;
import com.ls.entity.Problem;
import com.ls.entity.Province;
import com.ls.entity.Step;
import com.ls.repository.PhaseRepository;
import com.ls.repository.ProblemRepository;
import com.ls.repository.ProvinceRepository;
import com.ls.repository.StepRepository;
import com.ls.service.CompanyService;

@Component("commonAction")
@Scope("prototype")
public class CommonAction extends BaseAction {
	
	private static final long serialVersionUID = 7274858323873739463L;
	
	@Resource(name = "companyService")
	private CompanyService companyService;
	
	@Autowired
	private ProblemRepository problemRepository;
	
	
	@Autowired
	private ProvinceRepository provinceRepository;
	
	@Autowired
	private StepRepository stepRepository;
	@Autowired
	private PhaseRepository phaseRepository;
	
	private List<Problem> problems;
	private List<Province> provinces;
	private List<City> cities;
	private List<Step> steps;
	private List<Phase> phases;
	
	public String findAllPhases() {
		
		
		phases = phaseRepository.findAll();
		
		if (phases != null) {
			for(Phase phase : phases) {
				phase.setLearningHistories(null);
			}
		}
		
		return SUCCESS;
	}
	
	/**
	 * static resources
	 */
	
	public String findAllProblems() {
		
		String type = getParameter("type");
		
		problems = problemRepository.findByCategory(type);
		
		for(Problem problem : problems) {
			problem.setCompanies(null);
		}
		
		return SUCCESS;
	}

	
	public String findAllProvinces() {
		provinces = provinceRepository.findAll();
		
		return SUCCESS;
	}
	
	public String findAllCities() {
		return SUCCESS;
	}

	public String findAllSteps() {

		steps = stepRepository.findAll(new Sort(Sort.Direction.ASC, "orderNumber"));
		
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


	public List<City> getCities() {
		return cities;
	}

	public void setCities(List<City> cities) {
		this.cities = cities;
	}
	
	public List<Step> getSteps() {
	
		return steps;
	}
	
	public void setSteps(List<Step> steps) {
	
		this.steps = steps;
	}

	
	public List<Phase> getPhases() {
	
		return phases;
	}

	
	public void setPhases(List<Phase> phases) {
	
		this.phases = phases;
	}
	
	
}