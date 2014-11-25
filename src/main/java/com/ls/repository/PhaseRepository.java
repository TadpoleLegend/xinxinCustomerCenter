package com.ls.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ls.entity.Phase;

public interface PhaseRepository extends JpaRepository<Phase, Integer> , JpaSpecificationExecutor<Phase>{
}
