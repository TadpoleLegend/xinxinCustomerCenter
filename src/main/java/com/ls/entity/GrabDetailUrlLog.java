package com.ls.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ls_grab_detail_url_log")
public class GrabDetailUrlLog implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6934730715429994339L;

	@Id
	@GeneratedValue
	private Long id;
	
	private String type;
	private String message;
	private Date createDate;
	
	public GrabDetailUrlLog() {
		super();
	}
	public GrabDetailUrlLog(String type, String message, Date createDate) {
		super();
		this.type = type;
		this.message = message;
		this.createDate = createDate;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
