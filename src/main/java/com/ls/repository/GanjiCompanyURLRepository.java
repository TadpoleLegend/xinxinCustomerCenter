package com.ls.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ls.entity.GanjiCompanyURL;

public interface GanjiCompanyURLRepository extends JpaRepository<GanjiCompanyURL, Integer> , JpaSpecificationExecutor<GanjiCompanyURL>{
	@Query(value="SELECT lc.* FROM ls_ganji_companyurl lc where lc.hasGet=0 and lc.cityId = :cityId and lc.companyId = :companyId", nativeQuery=true)
	GanjiCompanyURL findCompany(@Param("cityId") Integer cityId,@Param("companyId") String companyId);
	
	@Query(value="SELECT lc.* FROM ls_ganji_companyurl lc where lc.hasGet=0 and lc.cityId = :cityId", nativeQuery=true)
	List<GanjiCompanyURL> findByCityId(@Param("cityId") Integer cityId);

	List<GanjiCompanyURL> findTop20ByCityIdInOrderByIdAsc(List<Integer> userCityIds);

	GanjiCompanyURL findByCompanyId(String resourceId);

	List<GanjiCompanyURL> findByCityIdInAndSavedCompanyIsNullOrderByIdDesc(List<Integer> userCityIds);

	List<GanjiCompanyURL> findByCityIdInAndSavedCompanyIsNullAndStatusNotInOrderByIdDesc(List<Integer> userCityIds, List<String> queryUrlDatasourceExcludeList);

	List<GanjiCompanyURL> findByCityIdInAndSavedCompanyIsNullAndStatusIsNullOrderByIdDesc(List<Integer> userCityIds);
}
