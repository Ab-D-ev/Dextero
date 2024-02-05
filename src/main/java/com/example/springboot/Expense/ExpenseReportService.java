package com.example.springboot.Expense;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ExpenseReportService {
	
	@Autowired
	private ExpenseReportRepository repo;
	
	public List<ExpenseReport> listAll(){
		return repo.findAll();
	}
	
	public ExpenseReport get(int id) {
		  return repo.findById(id).get();
	 }
	
	public void save(ExpenseReport report) {
		 repo.save(report);
	}
	
	public List<ExpenseReport> getAllExpense(Integer reportid){
		return repo.getAllExpense(reportid);
	}
	
}
