package com.example.springboot.Expense;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class AllExpensesService {
	
	@Autowired
	private AllExpensesRepository repo;
	
	public List<AllExpenses> listAll() {
	      return repo.findAll();
	 }
	 
	 public AllExpenses get(int id) {
		  return repo.findById(id).get();
	 }
	 
	 public void save(AllExpenses expense) {
	      repo.save(expense);
	 }
	 
	 public void deleteAllExpenses(int id) {
			repo.deleteById(id);
	 }
	 
}
