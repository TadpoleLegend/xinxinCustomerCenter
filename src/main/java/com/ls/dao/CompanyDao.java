package com.ls.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import com.ls.entity.Company;
@Component
public class CompanyDao extends BaseDao{
	
	
	
	public Company  checkCompanyExistByResourceId(Company company){
	/*String sql = "select * from ls_company where cityId='"+company.getCityId()+
			"' and name ='"+company.getName().trim()+"'" ;*/
		String sql = "select * from ls_company where id="+company.getId();
    try {
//    	company = this.jdbcTemplate.queryForObject(sql, Company.class);
    	List<Company> list = this.jdbcTemplate.query(sql,compayMapper);
    	return list.get(0);
//    	return company;
    } catch (RuntimeException re) {
    	re.printStackTrace();
    }
    return null;
	}
	
	private static ResultSetExtractor compayMapper = new ResultSetExtractor(){
		public List<Company> extractData(ResultSet rs) throws SQLException,DataAccessException 
		{
			List<Company> companys= new ArrayList<Company>();
			while(rs.next())
			{
				Company company = new Company();
				company.setName(rs.getString("name"));
				companys.add(company);
			}
			return companys;
		}
	};
	
}
