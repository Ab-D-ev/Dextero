package com.example.springboot.CompanyProfile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CompanyDetailsRepo extends JpaRepository<CompanyDetails, Integer>{
	
	@Query(nativeQuery = true, value = "select * from company_details where email_id=?1")
	public CompanyDetails getDataByEmailID(String email_id);
	
	@Query(nativeQuery = true, value = "SELECT company_id from company_details where email_id= ?1")
	public Integer getCompanyID(String email_id);
	
	@Query(nativeQuery = true, value = "select * from company_details where company_id=?1")
	public CompanyDetails getDataByID(Integer companyId);
	
	@Query(nativeQuery = true, value = "select company_name from company_details where company_id=?1")
	public String getCompanyNameByID(Integer companyId);
	
}
