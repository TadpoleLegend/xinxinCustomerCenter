package com.ls.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ls_function")
public class Function implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7972306768352381380L;

	@Id
	@GeneratedValue
	protected Integer id;

	protected Integer title;

	protected Integer url;

	protected Integer description;
	
	protected String actionName;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="role_id")
	protected Role role;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTitle() {
		return title;
	}

	public void setTitle(Integer title) {
		this.title = title;
	}

	public Integer getUrl() {
		return url;
	}

	public void setUrl(Integer url) {
		this.url = url;
	}

	public Integer getDescription() {
		return description;
	}

	public void setDescription(Integer description) {
		this.description = description;
	}
	
	public Role getRole() {
	
		return role;
	}
	
	public void setRole(Role role) {
	
		this.role = role;
	}

	public String getActionName() {
	
		return actionName;
	}
	
	public void setActionName(String actionName) {
	
		this.actionName = actionName;
	}

}
