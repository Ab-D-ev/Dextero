package com.example.springboot.Expense;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurrencyService {
	
	@Autowired
	private CurrencyRepo repo;
	
	public List<Currency> listAll() {
	      return repo.findAll();
	 }
	 
	 public Currency get(int id) {
		  return repo.findById(id).get();
	 }
	 
	 public void save(Currency currency) {
	      repo.save(currency);
	 }
	 
	 public void deleteCurrency(int id) {
			repo.deleteById(id);
	 }

}
