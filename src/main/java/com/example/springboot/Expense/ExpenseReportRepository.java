package com.example.springboot.Expense;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseReportRepository extends JpaRepository<ExpenseReport,Integer> {
	
	@Query(nativeQuery = true, value = "select * from expense_report where reportid =?1")
	   public List<ExpenseReport> getAllExpense(Integer reportid);
	
}
