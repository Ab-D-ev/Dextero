package com.example.springboot.Leave;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.springboot.EmployeesDetails.Employee;

public interface LeaveBalanceRepo extends JpaRepository<LeaveBalance, Integer>{
	
	 @Query("SELECT lb FROM LeaveBalance lb WHERE lb.employee_id = :employeeId AND lb.currentdate = :currentdate")
	   public LeaveBalance findByEmployeeId(@Param("employeeId") Integer employeeId , @Param("currentdate") String currentdate);
	 
	 @Query("SELECT lb FROM LeaveBalance lb WHERE lb.employee_id = :employeeId AND  EXTRACT(YEAR FROM currentdate) = :year")
	 public LeaveBalance findByEmployeeIDAndYear(@Param("employeeId") Integer employeeId, @Param("year") int year);

	 @Query(nativeQuery = true, value = "select carried_forward_leave from leave_balance where employee_id = ?1 AND EXTRACT(YEAR FROM current_date_year) =?2")
	 public int getCarriedForwardLeave(Integer employeeId , int year);
	 
	 @Query(nativeQuery = true, value = "select * from leave_balance where employee_id = ?1 AND EXTRACT(YEAR FROM current_date_year) =?2")
	 public LeaveBalance getBalanceLeave(Integer employeeId , int year);

//	// Custom query method to find LeaveBalance by employee and year
//	 @Query(nativeQuery = true, value = "SELECT * FROM leave_balance WHERE employee_id = ?1 AND EXTRACT(YEAR FROM currentdate) = :year")
//	 public LeaveBalance findByEmployeeAndYear(Employee employee, @Param("year") int year);
}
