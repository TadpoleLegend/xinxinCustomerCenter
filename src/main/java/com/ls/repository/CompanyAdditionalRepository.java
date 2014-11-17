package com.ls.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ls.entity.CompanyAdditional;

public interface CompanyAdditionalRepository extends JpaRepository<CompanyAdditional, Integer> , JpaSpecificationExecutor<CompanyAdditional>{
}
