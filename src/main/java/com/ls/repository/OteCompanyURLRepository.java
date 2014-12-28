package com.ls.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ls.entity.OteCompanyURL;

public interface OteCompanyURLRepository extends JpaRepository<OteCompanyURL, Integer> , JpaSpecificationExecutor<OteCompanyURL>{
	@Query(value="SELECT lc.* FROM ls_ote_companyurl lc where lc.hasGet=0 and lc.cityId = :cityId and lc.companyId = :companyId", nativeQuery=true)
	OteCompanyURL findCompany(@Param("cityId") Integer cityId,@Param("companyId") String companyId);
	
	List<OteCompanyURL> findTop20ByCityIdInOrderByIdAsc(List<Integer> cityIds);
	@Query(value="SELECT lc.* FROM ls_ote_companyurl lc where lc.hasGet=0 and lc.cityId = :cityId", nativeQuery=true)
	List<OteCompanyURL> findByCityId(@Param("cityId") Integer cityId);

	OteCompanyURL findByCompanyId(String resourceId);

	List<OteCompanyURL> findByCityIdInAndSavedCompanyIsNullAndStatusIsNullOrderByIdDesc(List<Integer> userCityIds);
}
