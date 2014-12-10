package com.ls.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ls.entity.Company;
import com.ls.entity.CompanyAdditional;

public interface CompanyAdditionalRepository extends JpaRepository<CompanyAdditional, Integer> , JpaSpecificationExecutor<CompanyAdditional>{
	CompanyAdditional findByCompany(Company company);
	List<CompanyAdditional> findByBossNameAndIdNot(String bossName, Integer id);
	List<CompanyAdditional> findByBossMobileAndIdNot(String bossMobile, Integer id);
}
