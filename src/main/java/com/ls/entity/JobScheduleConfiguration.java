package com.ls.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ls_job_schedule")
public class JobScheduleConfiguration implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	protected Integer id;

	protected Integer feStartHour;
	protected Integer feStartMinute;
	
	protected Integer feUrlStartHour;
	protected Integer feUrlStartMinute;

	protected Integer gjStartHour;
	protected Integer gjStartMinute;
	
	protected Integer gjUrlStartHour;
	protected Integer gjUrlStartMinute;

	protected Integer oteStartHour;
	protected Integer oteStartMinute;
	
	protected Integer oteUrlStartHour;
	protected Integer oteUrlStartMinute;
	
	protected Integer sleepMiliSeconds;

	public Integer getId() {

		return id;
	}

	public void setId(Integer id) {

		this.id = id;
	}

	public Integer getFeStartHour() {

		return feStartHour;
	}

	public void setFeStartHour(Integer feStartHour) {

		this.feStartHour = feStartHour;
	}

	public Integer getFeStartMinute() {

		return feStartMinute;
	}

	public void setFeStartMinute(Integer feStartMinute) {

		this.feStartMinute = feStartMinute;
	}

	public Integer getGjStartHour() {

		return gjStartHour;
	}

	public void setGjStartHour(Integer gjStartHour) {

		this.gjStartHour = gjStartHour;
	}

	public Integer getGjStartMinute() {

		return gjStartMinute;
	}

	public void setGjStartMinute(Integer gjStartMinute) {

		this.gjStartMinute = gjStartMinute;
	}

	public Integer getOteStartHour() {

		return oteStartHour;
	}

	public void setOteStartHour(Integer oteStartHour) {

		this.oteStartHour = oteStartHour;
	}

	public Integer getOteStartMinute() {

		return oteStartMinute;
	}

	public void setOteStartMinute(Integer oteStartMinute) {

		this.oteStartMinute = oteStartMinute;
	}

	
	public Integer getSleepMiliSeconds() {
	
		return sleepMiliSeconds;
	}

	
	public void setSleepMiliSeconds(Integer sleepMiliSeconds) {
	
		this.sleepMiliSeconds = sleepMiliSeconds;
	}

	
	public Integer getFeUrlStartHour() {
	
		return feUrlStartHour;
	}

	
	public void setFeUrlStartHour(Integer feUrlStartHour) {
	
		this.feUrlStartHour = feUrlStartHour;
	}

	
	public Integer getFeUrlStartMinute() {
	
		return feUrlStartMinute;
	}

	
	public void setFeUrlStartMinute(Integer feUrlStartMinute) {
	
		this.feUrlStartMinute = feUrlStartMinute;
	}

	
	public Integer getGjUrlStartHour() {
	
		return gjUrlStartHour;
	}

	
	public void setGjUrlStartHour(Integer gjUrlStartHour) {
	
		this.gjUrlStartHour = gjUrlStartHour;
	}

	
	public Integer getGjUrlStartMinute() {
	
		return gjUrlStartMinute;
	}

	
	public void setGjUrlStartMinute(Integer gjUrlStartMinute) {
	
		this.gjUrlStartMinute = gjUrlStartMinute;
	}

	
	public Integer getOteUrlStartHour() {
	
		return oteUrlStartHour;
	}

	
	public void setOteUrlStartHour(Integer oteUrlStartHour) {
	
		this.oteUrlStartHour = oteUrlStartHour;
	}

	
	public Integer getOteUrlStartMinute() {
	
		return oteUrlStartMinute;
	}

	
	public void setOteUrlStartMinute(Integer oteUrlStartMinute) {
	
		this.oteUrlStartMinute = oteUrlStartMinute;
	}
	
	
}
