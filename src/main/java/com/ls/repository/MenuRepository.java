package com.ls.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ls.entity.Menu;

public interface MenuRepository extends JpaRepository<Menu, Integer> , JpaSpecificationExecutor<Menu>{
}
