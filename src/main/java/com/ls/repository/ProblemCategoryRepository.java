package com.ls.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ls.entity.ProblemCategory;

public interface ProblemCategoryRepository extends JpaRepository<ProblemCategory, Integer> , JpaSpecificationExecutor<ProblemCategory>{
}
