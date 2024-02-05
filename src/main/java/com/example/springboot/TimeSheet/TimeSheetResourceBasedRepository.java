package com.example.springboot.TimeSheet;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface TimeSheetResourceBasedRepository extends  JpaRepository<TimeSheetResourceBased,Integer>{

	@Query(nativeQuery = true, value = "select * from time_sheet_resource_based where currentdate=?1")
	public List<TimeSheetResourceBased> getTimeSheetListByCurrentDate(String currentdate);
	
	@Query(nativeQuery = true, value = "select * from time_sheet_resource_based where employee_id=?1")
	public List<TimeSheetResourceBased> getTimeSheetListByEmployeeID(Integer employeeId);

}
