package com.ls.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ls_menu")
public class Menu implements Serializable{
	
	private static final long serialVersionUID = -1682642301978517925L;
	
	@Id
	@GeneratedValue
	protected Integer id;
	protected String title;
	protected String url;
	protected String elementId;
	
	public Menu() {
		super();
	}

	public Menu(String title, String url) {

		super();
		this.title = title;
		this.url = url;
	}
	
	public Menu(String title, String url, String elementId) {

		super();
		this.title = title;
		this.url = url;
		this.elementId = elementId;
	}

	public String getElementId() {
	
		return elementId;
	}
	
	public void setElementId(String elementId) {
	
		this.elementId = elementId;
	}

	public Integer getId() {
	
		return id;
	}
	
	public void setId(Integer id) {
	
		this.id = id;
	}
	
	public String getTitle() {
	
		return title;
	}
	
	public void setTitle(String title) {
	
		this.title = title;
	}
	
	public String getUrl() {
	
		return url;
	}
	
	public void setUrl(String url) {
	
		this.url = url;
	}
}
