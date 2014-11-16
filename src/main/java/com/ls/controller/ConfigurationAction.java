package com.ls.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ls.entity.Problem;
import com.ls.entity.Step;
import com.ls.repository.ProblemRepository;
import com.ls.repository.StepRepository;
import com.ls.service.CompanyService;

@Component("configurationAction")
public class ConfigurationAction extends BaseAction {

	private static final long serialVersionUID = 1515418096891295143L;

	@Resource(name = "companyService")
	private CompanyService companyService;

	@Autowired
	private ProblemRepository problemRepository;

	@Autowired
	private StepRepository stepRepository;

	private Problem problemOperating;

	private List<Problem> problems;

	private Step stepOperating;

	private List<Step> steps;

	public String saveProblem() {
		String id = getParameter("id");
		String name = getParameter("name");

		Problem problem = new Problem();

		// update
		if (null != id) {
			problem.setId(Integer.valueOf(id));
			problem.setName(name);
			problemOperating = companyService.saveProblem(problem);

		} else {

			Problem checkDB = problemRepository.findByName(name);
			if (null != checkDB) {
				// throw exception

			} else {
				problem.setName(name);
				problemOperating = companyService.saveProblem(problem);
			}

		}

		return SUCCESS;
	}

	public String saveStep() {
		String id = getParameter("id");
		String name = getParameter("name");

		Step step = new Step();

		// update
		if (null != id) {
			step.setId(Integer.valueOf(id));
			step.setName(name);
			stepOperating = companyService.saveStep(step);

		} else {

			Step checkDB = stepRepository.findByName(name);
			if (null != checkDB) {
				// throw exception

			} else {
				step.setName(name);
				stepOperating = companyService.saveStep(step);
			}

		}

		return SUCCESS;
	}

	public String getAllSteps() {

		steps = stepRepository.findAll();

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

	public Step getStepOperating() {
		return stepOperating;
	}

	public void setStepOperating(Step stepOperating) {
		this.stepOperating = stepOperating;
	}

	public List<Step> getSteps() {
		return steps;
	}

	public void setSteps(List<Step> steps) {
		this.steps = steps;
	}

}