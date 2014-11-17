package com.ls.dao;

import org.springframework.jdbc.core.JdbcTemplate;

public class CompanyDao extends JdbcTemplate{
	public boolean  checkCompanyExistByResourceId(String resourceId){
	String sql = "select count(*) from ls_company_resource where type='"+ resourceId+"'";
    try {
    	Integer rs = this.queryForInt(sql);
    	if(rs==null){
    		return false;
    	}else{
    		return rs.intValue()==0?false:true;
    	}
    } catch (RuntimeException re) {
        logger.error("find company resource failed with resourceId "+resourceId, re);
        throw re;
    }
	}
}
