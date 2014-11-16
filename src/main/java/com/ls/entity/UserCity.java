package com.ls.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
@Entity
@Table(name="ls_user_city")
public class UserCity implements Serializable{
	
	@Id
	@GeneratedValue
	protected Integer id;
	
	@OneToMany(mappedBy="userCity")
	protected List<City> cities;
	
	@OneToMany(mappedBy="userCity")
	protected List<User> users;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<City> getCities() {
		return cities;
	}

	public void setCities(List<City> cities) {
		this.cities = cities;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
}
