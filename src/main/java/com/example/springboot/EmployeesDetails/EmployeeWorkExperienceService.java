package com.example.springboot.EmployeesDetails;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeWorkExperienceService {
	
	@Autowired
	private EmployeeWorkExperienceRepo repo;
	
	public List<EmployeeWorkExperience> listAll() {
	      return repo.findAll();
	 }
	 
	 public EmployeeWorkExperience get(int id) {
		  return repo.findById(id).get();
	 }
	 
	 public void Save(EmployeeWorkExperience employeeWorkExperience) {
	      repo.save(employeeWorkExperience);
	 }
	 
	 public void delete(int id) {
			repo.deleteById(id);
	 }

}
