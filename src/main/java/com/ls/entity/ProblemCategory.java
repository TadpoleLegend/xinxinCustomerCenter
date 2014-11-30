package com.ls.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="ls_problem_category")
public class ProblemCategory {
	@Id
	@GeneratedValue
	protected Integer id;

	protected String name;
	
	@OneToMany(mappedBy="problemCategory")
	protected List<Problem> problem;

	public ProblemCategory() {
		super();
	}

	public ProblemCategory(String name) {
		super();
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Problem> getProblem() {
		return problem;
	}

	public void setProblem(List<Problem> problem) {
		this.problem = problem;
	}

	
}
