package com.ls.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ls.entity.GrabDetailUrlLog;

public interface GrabDetailUrlLogRepository extends JpaRepository<GrabDetailUrlLog, Long> , JpaSpecificationExecutor<GrabDetailUrlLog>{
}
