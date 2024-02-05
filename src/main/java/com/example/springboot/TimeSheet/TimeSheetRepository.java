package com.example.springboot.TimeSheet;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.springboot.AddCompany.AddCustomer;

@Repository
public interface TimeSheetRepository extends JpaRepository<TimeSheet,Long>{
	
	@Query(nativeQuery = true, value = "select * from time_sheet where currentdate=?1")
	public List<TimeSheet> getListByCurrentDate(String currentdate);

	@Query(nativeQuery = true, value = "select * from time_sheet where employee_id=?1 AND start_date=?2 ORDER BY id DESC LIMIT 1 ")
	public TimeSheet selectDate(Integer employeeId, String currentdate);
	
	@Query(nativeQuery = true, value = "select COUNT(*) from time_sheet where employee_id=?1 AND start_date=?2")
	public int getUserName(Integer employeeId, String currentdate);
	
	@Query(nativeQuery = true, value = "select * from time_sheet where employee_id=?1 AND start_date=?2")
	public List<TimeSheet> getCurrentdateData(Integer employeeId, String currentdate);
	
	@Query(nativeQuery = true, value = "SELECT * "
			+ "FROM time_sheet "
			+ "WHERE MONTH (STR_TO_DATE(currentdate,'%d-%m-%Y')) = ?1 AND employee_id=?2")
	public List<TimeSheet> getCurrentMonthData(String currentdate, Integer employeeId);
	
	@Query(nativeQuery = true, value = "select remainingduration from time_sheet where start_date = ?1 AND employee_id = ?2 ORDER BY id DESC LIMIT 1 ")
	public String selectRemainingDuration(String currentdate , Integer employeeId);
	
	@Query(nativeQuery = true, value =" select COUNT(*) from time_sheet where employee_id= ?1 AND start_date=?2 ")
	public int CountByUserName(Integer employeeId, String startdate);
	
	@Query(nativeQuery = true, value = "select remainingduration from time_sheet where start_date = ?1 AND employee_id = ?2 ORDER BY id DESC LIMIT 1 ")
	public String getRemainingDuration(String startdate , Integer employeeId);
	
}















