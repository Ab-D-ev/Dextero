package com.example.springboot.LeaveCategory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LeaveCategoryRepo extends JpaRepository<LeaveCategory, Integer>{
	
	@Query(nativeQuery = true, value = "SELECT id FROM leave_category WHERE lcategory = 'Paid Leave'")
	public Integer getLeaveCategoryID();


}
