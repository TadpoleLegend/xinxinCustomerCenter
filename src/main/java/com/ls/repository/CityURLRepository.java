package com.ls.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ls.entity.CityURL;

public interface CityURLRepository extends JpaRepository<CityURL, Integer> , JpaSpecificationExecutor<CityURL>{
	List<CityURL> findByResourceType(String resourceType);
	
	CityURL findByUrlLike(String url);
}
