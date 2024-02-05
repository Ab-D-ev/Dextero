package com.example.springboot.SalaryPayRoll;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface EmployeeSalaryRepo extends JpaRepository<EmployeeSalary, Integer>{
	
	@Query("select u from EmployeeSalary u where u.employeeId=?1")
	public List<EmployeeSalary> findSalarySlipByEmployeeId(int empid);

}
