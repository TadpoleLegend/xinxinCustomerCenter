package com.ls.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ls.entity.ApplyingWillingCustomer;
import com.ls.entity.User;

public interface ApplyingWillingCustomerRepository extends JpaRepository<ApplyingWillingCustomer, Integer> , JpaSpecificationExecutor<ApplyingWillingCustomer>{
	
	public ApplyingWillingCustomer findByCompanyIdAndUser(Integer companyId, User user);
}
