package com.ls.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ls.entity.Company;
import com.ls.entity.PhoneCallHistory;

public interface PhoneCallHistoryRepository extends JpaRepository<PhoneCallHistory, Integer> , JpaSpecificationExecutor<PhoneCallHistory>{
	List<PhoneCallHistory> findByCompany(Company company);
}
