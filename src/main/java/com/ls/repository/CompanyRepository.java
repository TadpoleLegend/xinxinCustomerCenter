package com.ls.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.ls.entity.Company;
import com.ls.vo.BarData;

public interface CompanyRepository extends JpaRepository<Company, Integer>, JpaSpecificationExecutor<Company> {

	List<Company> findByNameAndFEurl(String name, String fEurl);

	List<Company> findByNameAndContactorAndArea(String name, String contactor, String area);

	List<Company> findByNameAndNameNot(String name, String myName);

	Company findByFEresourceId(String fEresourceId);

	Company findByGanjiresourceId(String ganjiresourceId);
	
	Company findByOTEresourceId(String oteResourceId);

	Company findByCityIdAndName(Integer cityId, String name);
	
	@Query(value="SELECT b.name cityName, count(cityId) count FROM ls.ls_ganji_companyurl a left join ls_city b on a.cityId = b.id group by cityId order by count desc limit 20", nativeQuery=true)
	List<?> findTop10CityCompanyCount(); 
}
