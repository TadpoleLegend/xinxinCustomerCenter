package com.ls.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ls.entity.City;

public interface CityRepository extends JpaRepository<City, Integer> , JpaSpecificationExecutor<City>{
	List<City> findByUrl(String url);
}
