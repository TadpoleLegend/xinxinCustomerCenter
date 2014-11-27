package com.ls.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ls.entity.NegativeCompany;

public interface NegativeCompanyRepository extends JpaRepository<NegativeCompany, Integer> , JpaSpecificationExecutor<NegativeCompany>{
	@Query(value="SELECT lc.* FROM ls_negativecompany lc where lc.cityId = :cityId and lc.resourceId = :resourceId and lc.sourceType = :sourceType", nativeQuery=true)
	NegativeCompany findNegativeCompany(@Param("cityId") Integer cityId,@Param("resourceId") String resourceId,@Param("sourceType") String sourceType);

}
