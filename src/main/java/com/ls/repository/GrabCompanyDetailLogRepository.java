package com.ls.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ls.entity.GrabCompanyDetailLog;

public interface GrabCompanyDetailLogRepository extends JpaRepository<GrabCompanyDetailLog, Integer> , JpaSpecificationExecutor<GrabCompanyDetailLog>{

}
