package com.ls.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.google.common.collect.Lists;
import com.ls.entity.Company;
import com.ls.entity.LearningHistory;

public interface LearningHistoryRepository extends JpaRepository<LearningHistory, Integer> , JpaSpecificationExecutor<LearningHistory>{
	List<LearningHistory> findByCompany(Company company);
}
