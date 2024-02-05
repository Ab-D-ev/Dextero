package com.example.springboot.SalaryPayRoll;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface EmployeeGrossPayRepo extends JpaRepository<EmployeeGrossPay, Integer>{
	
	@Query("select g from EmployeeGrossPay g where g.empId=?1")
	public List<EmployeeGrossPay> getEmployeeNameByID(int empId);

}
