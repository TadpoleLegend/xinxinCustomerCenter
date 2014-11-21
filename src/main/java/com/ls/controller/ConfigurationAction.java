package com.ls.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ls.entity.Problem;
import com.ls.repository.ProblemRepository;
import com.ls.service.CompanyService;

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
		String id = getParameter("id");
		String name = getParameter("name");
		String category = getParameter("category");

		Problem problem = new Problem();

		// update
		if (null != id) {
			problem.setId(Integer.valueOf(id));
			problem.setName(name);
			//problem.setCategory(category);
			problemOperating = companyService.saveProblem(problem);

		} else {

			Problem checkDB = problemRepository.findByName(name);
			if (null != checkDB) {
				// throw exception

			} else {
				problem.setName(name);
				//problem.setCategory(category);
				problemOperating = companyService.saveProblem(problem);
			}

		}

		return SUCCESS;
	}

	public String saveStep() {
	
		return SUCCESS;
	}

	public String getAllSteps() {

	

		return SUCCESS;
	}

	public String getAllProblems() {

		problems = problemRepository.findAll();

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