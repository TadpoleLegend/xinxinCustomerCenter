package com.ls.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ls.entity.City;

public interface CityRepository extends JpaRepository<City, Integer> , JpaSpecificationExecutor<City>{
}
