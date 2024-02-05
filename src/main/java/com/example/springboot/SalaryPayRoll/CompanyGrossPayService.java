package com.example.springboot.SalaryPayRoll;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyGrossPayService {
	
	@Autowired
	private CompanyGrossPayRepo repo;
	
	public List<CompanyGrossPay> listAll() {
	      return repo.findAll();
	 }
	 
	 public CompanyGrossPay get(int id) {
		  return repo.findById(id).get();
	 }
	 
	 public void Save(CompanyGrossPay cGrossPay) {
	      repo.save(cGrossPay);
	 }
	 
	 public void delete(int id) {
			repo.deleteById(id);
	 }

}
