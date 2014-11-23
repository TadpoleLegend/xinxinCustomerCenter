package com.ls.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableList;
import com.ls.entity.Problem;
import com.ls.repository.ProblemRepository;
import com.ls.service.CompanyService;
import com.ls.util.XinXinUtils;
import com.ls.vo.ResponseVo;

@Component("configurationAction")
public class ConfigurationAction extends BaseAction {

	private static final long serialVersionUID = 1515418096891295143L;

	@Resource(name = "companyService")
	private CompanyService companyService;

	@Autowired
	private ProblemRepository problemRepository;


	private Problem problemOperating;

	private List<Problem> problems;

	public String saveProblem() {
		
		String problemJson = getParameter("problem");
		
		Problem problem = XinXinUtils.getJavaObjectFromJsonString(problemJson, Problem.class);
		
		problemOperating = problemRepository.saveAndFlush(problem);
		
		return SUCCESS;
	}

	public String deleteProblem() {
		
		try {
			
			String problemJson = getParameter("problem");
			
			Problem problem = XinXinUtils.getJavaObjectFromJsonString(problemJson, Problem.class);
			
			problemRepository.delete(problem);
			
		} catch (Exception e) {
			setResponse(ResponseVo.newSuccessMessage("200"));
		}
		
		setResponse(ResponseVo.newSuccessMessage(null));
		
		return SUCCESS;
	}
	
	public String getAllProblems() {
		
		Order order = new Order(Direction.ASC, "category");
		
		problems = problemRepository.findAll(new Sort(ImmutableList.of(order)));
		
		for (Problem problem : problems) {
			problem.setCompanies(null);
		}

		return SUCCESS;
	}

	public List<Problem> getProblems() {
		return problems;
	}

	public void setProblems(List<Problem> problems) {
		this.problems = problems;
	}

	public Problem getProblemOperating() {
		return problemOperating;
	}

	public void setProblemOperating(Problem problemOperating) {
		this.problemOperating = problemOperating;
	}


}