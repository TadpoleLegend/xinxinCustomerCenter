package com.ls.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ls.entity.Company;


public interface CompanyRepository extends JpaRepository<Company, Integer> , JpaSpecificationExecutor<Company>{
	
	List<Company> findByNameAndFEurl(String name, String fEurl);
	
	List<Company> findByNameAndContactorAndArea(String name, String contactor, String area);
	
	@Query(value="SELECT lc.* FROM ls_company lc where lc.cityId = :cityId and (lc.oTEresourceId = :oTEresourceId or lc.name = :name)", nativeQuery=true)
	Company findCompanyFor138GrabJob(@Param("cityId") Integer cityId,@Param("oTEresourceId") String oTEresourceId,@Param("name") String name);
	
	@Query(value="SELECT lc.* FROM ls_company lc where lc.cityId = :cityId and (lc.ganjiresourceId = :ganjiresourceId or lc.name = :name) ", nativeQuery=true)
	Company findCompanyForGanjiGrabJob(@Param("cityId") Integer cityId,@Param("ganjiresourceId") String ganjiresourceId,@Param("name") String name);
	
	@Query(value="SELECT lc.* FROM ls_company lc where lc.cityId = :cityId and (lc.fEresourceId = :fEresourceId or lc.name = :name)", nativeQuery=true)
	Company findCompanyFor58GrabJob(@Param("cityId") Integer cityId,@Param("fEresourceId") String fEresourceId,@Param("name") String name);
}
