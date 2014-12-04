package com.ls.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="ls_role")
public class Role {

	@Id
	@GeneratedValue
	protected Integer id;

	protected String name;
	
	@OneToMany(mappedBy="role")
	protected List<Function> functions;
	
	@ManyToMany(cascade = CascadeType.REFRESH, mappedBy = "roles", fetch = FetchType.LAZY)
	protected List<User> users;
	
	public Role() {

		super();
	}

	public Role( String name) {

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

	
	public List<Function> getFunctions() {
	
		return functions;
	}

	
	public void setFunctions(List<Function> functions) {
	
		this.functions = functions;
	}

	
	public List<User> getUsers() {
	
		return users;
	}

	
	public void setUsers(List<User> users) {
	
		this.users = users;
	}
	
	
}
