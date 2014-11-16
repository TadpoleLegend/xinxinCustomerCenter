package com.ls.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ls_city_url")
public class CityURL implements Serializable {

	private static final long serialVersionUID = 6824380215762097280L;

	@Id
	@GeneratedValue
	protected Integer id;
	protected String url;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="city_id")
	protected City city;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "source_type_id")
	protected SourceType sourceType;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public SourceType getSourceType() {
		return sourceType;
	}

	public void setSourceType(SourceType sourceType) {
		this.sourceType = sourceType;
	}

}
