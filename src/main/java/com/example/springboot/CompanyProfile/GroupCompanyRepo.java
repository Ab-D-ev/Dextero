package com.example.springboot.CompanyProfile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GroupCompanyRepo extends JpaRepository<GroupCompany, Integer>{
	
	@Query(nativeQuery = true, value = "select groupcompany_id from group_company where email_id=?1")
	public Integer getgroupcompany_id(String email_id);
	
	@Query(nativeQuery = true, value = "select count(groupcompany_id) from group_company where email_id=?1")
	public Integer getcountgroupcompany_id(String email_id);
	
	@Query(nativeQuery = true, value = "select * from group_company where email_id=?1")
	public GroupCompany getDataByEmailId(String email_id);
}
