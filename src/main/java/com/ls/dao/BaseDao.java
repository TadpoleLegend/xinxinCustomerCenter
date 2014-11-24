package com.ls.dao;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;

public class BaseDao {
	@Resource(name = "jdbcTemplate") 
	public JdbcTemplate jdbcTemplate;
	
	
	
}
