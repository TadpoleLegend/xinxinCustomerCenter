package com.ls.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ls.entity.Problem;

public interface ProblemRepository extends JpaRepository<Problem, Integer> , JpaSpecificationExecutor<Problem>{
	
	Problem findByName(String name);
}
