package com.example.springboot.AddCompany;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface AddCustomerRepository extends JpaRepository<AddCustomer,Long> {
	
	@Query("FROM AddCustomer ORDER BY CompanyName DESC")
	   public List<AddCustomer> getCustomerListByOrder(String CompanyName);
	    
	@Query(nativeQuery = true, value = "select * from addcustomer where currentdate=?1")
	public List<AddCustomer> getListByCurrentDate(String currentdate);
	
	@Query(nativeQuery = true, value ="select count(*) from addcustomer where company_name = ?1")
	 public int findByCompanyName1(String companyname);
	
	@Query(nativeQuery = true,value="select company_name from addcustomer where id=?1")
	public String findCompanyNameById(Long id);
	
	@Query(nativeQuery = true,value="select id from addcustomer where company_name=?1")
	public Long findCompanyIDByCompanyName(String companyname);
	
 
}
