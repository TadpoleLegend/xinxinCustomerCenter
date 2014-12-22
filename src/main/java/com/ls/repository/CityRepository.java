package com.ls.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ls.entity.City;
import com.ls.entity.User;

public interface CityRepository extends JpaRepository<City, Integer> , JpaSpecificationExecutor<City>{
	
	public List<City> findByUsers(List<User> users);
	
	public City findByName(String name);
}
