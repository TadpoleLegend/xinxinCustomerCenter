package com.ls.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ls.entity.Problem;
import com.ls.entity.ProblemCategory;

public interface ProblemRepository extends JpaRepository<Problem, Integer> , JpaSpecificationExecutor<Problem>{
	
	Problem findByName(String name);
	
	List<Problem> findByProblemCategory(ProblemCategory problemCategory);
	
	@Query("SELECT p FROM Problem p WHERE p.category = :category")
	List<Problem> findByCategory(@Param("category") String category);
	
	@Query(value="SELECT p.* FROM ls_problem p, ls_company_problem cp where p.id = cp.problem_id and cp.company_id = :companyId", nativeQuery=true)
	List<Problem> findByCompanyId(@Param("companyId") Integer companyId);
}
