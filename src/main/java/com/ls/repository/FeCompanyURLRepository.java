package com.ls.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ls.entity.FeCompanyURL;

public interface FeCompanyURLRepository  extends JpaRepository<FeCompanyURL, Integer> , JpaSpecificationExecutor<FeCompanyURL>{
	@Query(value="SELECT lc.* FROM ls_fe_companyurl lc where lc.hasGet=0 and lc.cityId = :cityId and lc.companyId = :companyId", nativeQuery=true)
	FeCompanyURL findCompany(@Param("cityId") Integer cityId,@Param("companyId") String companyId);
}