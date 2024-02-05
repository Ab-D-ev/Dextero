package com.example.springboot.SalaryPayRoll;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeGrossPayService {
	
	@Autowired
	private EmployeeGrossPayRepo repo;
	
	
	public List<EmployeeGrossPay> listAll() {
	      return repo.findAll();
	 }
	 
	 public EmployeeGrossPay get(int id) {
		  return repo.findById(id).get();
	 }
	 
	 public void Save(EmployeeGrossPay eGrossPay) {
	      repo.save(eGrossPay);
	 }
	 
	 public void delete(int id) {
			repo.deleteById(id);
	 }

	 public List<EmployeeGrossPay> getEmployeeNameByID(int empId){
		 return repo.getEmployeeNameByID(empId);
	 }

}
