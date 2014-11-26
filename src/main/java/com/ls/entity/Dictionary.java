package com.ls.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * data source for configurable drop-downs
 * 
 * identity is the one to identity which the drop-down that the data source belongs to.
 *  
 * @author jjiang
 *
 */
@Entity
@Table(name="ls_dictionary")
public class Dictionary implements Serializable{
	
	private static final long serialVersionUID = -8091870035671834689L;
	@Id
	@GeneratedValue
	protected Integer id;
	protected String identity;
	protected String optionText;
	protected String optionValue;
	protected String description;
	
	
	public Dictionary() {

		super();
	}


	
	public Dictionary(String identity,  String optionValue, String optionText, String description) {

		super();
		this.identity = identity;
		this.optionText = optionText;
		this.optionValue = optionValue;
		this.description = description;
	}



	public Integer getId() {
	
		return id;
	}


	
	public void setId(Integer id) {
	
		this.id = id;
	}


	
	public String getIdentity() {
	
		return identity;
	}


	
	public void setIdentity(String identity) {
	
		this.identity = identity;
	}


	
	public String getOptionText() {
	
		return optionText;
	}


	
	public void setOptionText(String optionText) {
	
		this.optionText = optionText;
	}


	
	public String getOptionValue() {
	
		return optionValue;
	}


	
	public void setOptionValue(String optionValue) {
	
		this.optionValue = optionValue;
	}


	
	public String getDescription() {
	
		return description;
	}


	
	public void setDescription(String description) {
	
		this.description = description;
	}


}
