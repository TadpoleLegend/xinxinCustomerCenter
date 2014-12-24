package com.ls.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ls.entity.JobScheduleConfiguration;

public interface JobScheduleConfigurationRepository extends JpaRepository<JobScheduleConfiguration, Integer> , JpaSpecificationExecutor<JobScheduleConfiguration>{
}
