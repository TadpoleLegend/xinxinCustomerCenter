package com.ls.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ls.entity.CompanyResource;


public interface CompanyResourceRepository extends JpaRepository<CompanyResource, Integer> , JpaSpecificationExecutor<CompanyResource>{
}
