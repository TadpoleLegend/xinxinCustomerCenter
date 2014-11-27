package com.ls.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ls.entity.NegativeCompany;

public interface NegativeCompanyRepository extends JpaRepository<NegativeCompany, Integer> , JpaSpecificationExecutor<NegativeCompany>{

}
