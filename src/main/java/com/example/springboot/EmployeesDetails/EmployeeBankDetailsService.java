package com.example.springboot.EmployeesDetails;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeBankDetailsService {

	@Autowired
	private EmployeeBankDetailsRepo repo;
	
	public List<EmployeeBankDetails> listAll() {
	      return repo.findAll();
	 }
	 
	 public EmployeeBankDetails get(int id) {
		  return repo.findById(id).get();
	 }
	 
	 public void Save(EmployeeBankDetails bankDetails) {
	      repo.save(bankDetails);
	 }
	 
	 public void delete(int id) {
			repo.deleteById(id);
	 }
}
