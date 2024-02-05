package com.example.springboot.MainMaster;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnnualSalaryService {

	@Autowired
	private AnnualSalaryRepo repo;
	
	public List<AnnualSalary> listAll() {
	      return repo.findAll();
	 }
	 
	 public AnnualSalary get(int id) {
		  return repo.findById(id).get();
	 }
	 
	 public void Save(AnnualSalary salary) {
	      repo.save(salary);
	 }
	 
	 public void delete(int id) {
			repo.deleteById(id);
	 }
}
