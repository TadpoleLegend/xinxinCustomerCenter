package com.ls.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ls_problem")
public class Problem implements Serializable {

	private static final long serialVersionUID = -5939656727237197540L;

	@Id
	@GeneratedValue
	protected Integer id;

	protected String name;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="problem_category_id")
	protected ProblemCategory problemCategory;

	@ManyToMany(mappedBy = "problems", fetch = FetchType.LAZY)
	protected List<Company> companies;

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

	public List<Company> getCompanies() {
		return companies;
	}

	public void setCompanies(List<Company> companies) {
		this.companies = companies;
	}
	


	public ProblemCategory getProblemCategory() {
		return problemCategory;
	}

	public void setProblemCategory(ProblemCategory problemCategory) {
		this.problemCategory = problemCategory;
	}

	@Override
	public String toString() {
		return "Problem [id=" + id + ", name=" + name + ", companies=" + companies + "]";
	}
}
