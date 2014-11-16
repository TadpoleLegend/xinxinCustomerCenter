package com.ls.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ls.entity.CityURL;

public interface CityURLRepository extends JpaRepository<CityURL, Integer> , JpaSpecificationExecutor<CityURL>{
	
}
