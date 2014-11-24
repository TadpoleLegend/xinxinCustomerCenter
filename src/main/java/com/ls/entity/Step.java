package com.ls.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ls_step")
public class Step implements Serializable {


	private static final long serialVersionUID = -2775462549176475484L;

	@Id
	protected Integer id;

	protected String name;
	
	protected Integer orderNumber;

	
	public Step() {

		super();
	}


	public Step(Integer id, String name, Integer orderNumber) {

		super();
		this.id = id;
		this.name = name;
		this.orderNumber = orderNumber;
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


	
	public Integer getOrderNumber() {
	
		return orderNumber;
	}


	
	public void setOrderNumber(Integer orderNumber) {
	
		this.orderNumber = orderNumber;
	}


	
	
}
