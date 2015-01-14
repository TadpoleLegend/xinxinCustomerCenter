package com.ls.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ls.entity.Dictionary;

public interface DropDownRepository extends JpaRepository<Dictionary, Integer> , JpaSpecificationExecutor<Dictionary>{
	
	List<Dictionary> findByIdentity(String identity);
	
	Dictionary findByIdentityAndOptionValue(String identity, String optionValue);
	
}
