package com.example.springboot.EmployeesDetails;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeEducationDetailsService {

	@Autowired
	private EmployeeEducationDetailsRepo repo;
	
	public List<EmployeeEducationDetails> listAll() {
	      return repo.findAll();
	 }
	 
	 public EmployeeEducationDetails get(int id) {
		  return repo.findById(id).get();
	 }
	 
	 public void Save(EmployeeEducationDetails education) {
	      repo.save(education);
	 }
	 
	 public void delete(int id) {
			repo.deleteById(id);
	 }

}
