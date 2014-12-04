package com.ls.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ls_user")
public class User implements Serializable {

	private static final long serialVersionUID = -9150498313341113868L;
	@Id
	@GeneratedValue
	protected Integer id;
	protected String name;
	protected String username;
	protected String password;

	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinTable(name = "ls_user_city", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "city_id"))
	protected List<City> cities;

	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinTable(name = "ls_user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	protected List<Role> roles;
	
	@OneToOne(mappedBy="user")
	protected PhoneCallHistory phoneCallHistory;

	public User() {
		super();
	}

	public User(String name, String username, String password) {
		super();
		this.name = name;
		this.username = username;
		this.password = password;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public List<Role> getRoles() {
	
		return roles;
	}
	
	public void setRoles(List<Role> roles) {
	
		this.roles = roles;
	}
	
	public List<City> getCities() {
	
		return cities;
	}
	
	public void setCities(List<City> cities) {
	
		this.cities = cities;
	}

	public PhoneCallHistory getPhoneCallHistory() {
		return phoneCallHistory;
	}

	public void setPhoneCallHistory(PhoneCallHistory phoneCallHistory) {
		this.phoneCallHistory = phoneCallHistory;
	}
	
}
