package com.ls.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ls.entity.ApplyingWillingCustomer;
import com.ls.entity.User;

public interface ApplyingWillingCustomerRepository extends JpaRepository<ApplyingWillingCustomer, Integer>, JpaSpecificationExecutor<ApplyingWillingCustomer> {

	ApplyingWillingCustomer findByCompanyIdAndUser(Integer companyId, User user);

	List<ApplyingWillingCustomer> findByStatus(Integer status);

	ApplyingWillingCustomer findByCompanyIdAndUserAndStatus(Integer valueOf, User currentUser, Integer id);
}
