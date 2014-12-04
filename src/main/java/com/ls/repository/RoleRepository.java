package com.ls.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ls.entity.City;
import com.ls.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> , JpaSpecificationExecutor<Role>{
}
