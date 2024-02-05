package com.example.springboot.Expense;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CreateExpenseReportService {
	
	@Autowired
	private CreateExpenseReportRepository repo;
	
	public List<CreateExpenseReport> listAll() {
	      return repo.findAll();
	 }
	 
	 public void save(CreateExpenseReport expreport) {
	      repo.save(expreport);
	 }
	 
	 public CreateExpenseReport get(int id) {
		  return repo.findById(id).get();
	 }
	 
	/* public void deleteCreateExpenseReport(int id) {
			repo.deleteById(id);
	 }*/
	
	 
	 public Integer getExpense(String ExpenseName,String EDate, String currentdate) {
		return repo.getExpense(ExpenseName, EDate, currentdate);
		 
	 }
	/* public List<CreateExpenseReport> getAllActiveExpense(){
		 return repo.getAllActiveExpense();
	 }*/
	 
	 public Integer getApprovedCount(Integer companyId) {
		 return repo.getApprovedCount(companyId);
	 }
	
	 public Integer getUnapprovedCount(Integer companyId) {
		 return repo.getUnapprovedCount(companyId);
	 }
	 
	 public Integer getPendingCount(Integer companyId) {
		 return repo.getPendingCount(companyId);
	 }
	 
	 public List<CreateExpenseReport> getListByUserNameANDCurrentdate(Integer employeeId, String currentdate) {
		 return repo.getListByUserNameANDCurrentdate(employeeId, currentdate);
	 }
	 
	 public List<CreateExpenseReport> getListByUserName(Integer employeeId){
		 return repo.getListByUserName(employeeId);
	 }
	 
	 public CreateExpenseReport getUsername(Integer employeeId){
		 return repo.getUsername(employeeId);
	 }
}
	
