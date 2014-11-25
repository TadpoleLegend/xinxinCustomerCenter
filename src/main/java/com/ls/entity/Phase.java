package com.ls.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ls_phase")
public class Phase {

	@Id
	@GeneratedValue
	protected Integer id;

	protected String name;

	@OneToMany(mappedBy = "phase")
	protected List<LearningHistory> learningHistories;

	public Phase() {

		super();
	}

	public Phase(Integer id, String name) {

		super();
		this.id = id;
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

	public List<LearningHistory> getLearningHistories() {

		return learningHistories;
	}

	public void setLearningHistories(List<LearningHistory> learningHistories) {

		this.learningHistories = learningHistories;
	}

}
