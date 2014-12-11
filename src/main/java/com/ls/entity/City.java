package com.ls.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="ls_city")
public class City implements Serializable {

	private static final long serialVersionUID = 5299983057729736628L;
	
	@Id
	@GeneratedValue
	protected Integer id;
	protected String name;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="province_id")
	protected Province province;
	
	@OneToMany(mappedBy="city")
	protected List<CityURL> cityURLs;
	
	@ManyToMany(cascade = CascadeType.REFRESH, mappedBy = "cities", fetch = FetchType.LAZY)
	protected List<User> users;
	
	@Transient
	protected boolean selected;
	
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
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
	
	public Province getProvince() {
		return province;
	}
	public void setProvince(Province province) {
		this.province = province;
	}
	
	public List<CityURL> getCityURLs() {
		return cityURLs;
	}
	public void setCityURLs(List<CityURL> cityURLs) {
		this.cityURLs = cityURLs;
	}
	
	public List<User> getUsers() {
	
		return users;
	}
	
	public void setUsers(List<User> users) {
	
		this.users = users;
	}
}
