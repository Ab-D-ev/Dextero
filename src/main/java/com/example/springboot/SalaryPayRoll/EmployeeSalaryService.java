package com.example.springboot.SalaryPayRoll;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeSalaryService {
	
	
	@Autowired
	private EmployeeSalaryRepo repo;
	
	public List<EmployeeSalary> listAll() {
	      return repo.findAll();
	 }
	 
	 public EmployeeSalary get(int id) {
		  return repo.findById(id).get();
	 }
	 
	 public void Save(EmployeeSalary employeeSalary) {
	      repo.save(employeeSalary);
	 }
	 
	 public List<EmployeeSalary> findSalarySlipByEmployeeId(int empid){
		 return repo.findSalarySlipByEmployeeId(empid);
	 }

}
