package com.example.springboot.EmployeesDetails;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeCareerBreakDetailsService {

	@Autowired
	private EmployeeCareerBreakDetailsRepo repo;
	
	public List<EmployeeCareerBreakDetails> listAll() {
	      return repo.findAll();
	 }
	 
	 public EmployeeCareerBreakDetails get(int id) {
		  return repo.findById(id).get();
	 }
	 
	 public void Save(EmployeeCareerBreakDetails careerBreakDetails) {
	      repo.save(careerBreakDetails);
	 }
	 
	 public void delete(int id) {
			repo.deleteById(id);
	 }
}
