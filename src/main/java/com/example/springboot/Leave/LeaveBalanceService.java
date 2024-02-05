package com.example.springboot.Leave;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.example.springboot.EmployeesDetails.Employee;

@Service
public class LeaveBalanceService {
	
	@Autowired
	private LeaveBalanceRepo repo;
	
	
	public List<LeaveBalance> listAll() {
	      return repo.findAll();
	 }
	 
	 public LeaveBalance get(int id) {
		  return repo.findById(id).get();
	 }
	 
	 public void Save(LeaveBalance leave) {
	      repo.save(leave);
	 }
	 
	 public void delete(int id) {
			repo.deleteById(id);
	 }

	 public LeaveBalance findByEmployeeId(@Param("employeeId") Integer employeeId , @Param("currentdate") String currentdate) {
		 return repo.findByEmployeeId(employeeId, currentdate);
	 }
	 
	 public LeaveBalance findByEmployeeIDAndYear(@Param("employeeId") Integer employeeId, @Param("year") int year) {
		 return repo.findByEmployeeIDAndYear(employeeId, year);
	 }
	 
	 public int getCarriedForwardLeave(Integer employeeId,int year) {
		 return repo.getCarriedForwardLeave(employeeId,year);
	 }
	 
	 public LeaveBalance getBalanceLeave(Integer employeeId , int year) {
		 return repo.getBalanceLeave(employeeId, year);
	 }
	 
//	 public LeaveBalance findByEmployeeAndYear(Employee employee, @Param("year") int year) {
//		 return repo.findByEmployeeAndYear(employee, year);
//	 }
}
