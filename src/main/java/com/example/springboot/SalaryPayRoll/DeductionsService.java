package com.example.springboot.SalaryPayRoll;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeductionsService {
	
	@Autowired
	private DeductionsRepo repo;
	
	public List<Deductions> listAll() {
	      return repo.findAll();
	 }
	 
	 public Deductions get(int id) {
		  return repo.findById(id).get();
	 }
	 
	 public void Save(Deductions deductions) {
	      repo.save(deductions);
	 }
	 
	 public void delete(int id) {
			repo.deleteById(id);
	 }

}
