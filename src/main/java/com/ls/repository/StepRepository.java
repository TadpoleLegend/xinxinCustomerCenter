package com.ls.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ls.entity.Step;

public interface StepRepository extends JpaRepository<Step, Integer> , JpaSpecificationExecutor<Step>{
	
	Step findByName(String name);
}
