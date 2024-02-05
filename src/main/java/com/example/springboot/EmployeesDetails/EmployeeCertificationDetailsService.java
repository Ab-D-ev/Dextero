package com.example.springboot.EmployeesDetails;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeCertificationDetailsService {

	@Autowired
	private EmployeeCertificationDetailsRepo repo;
	
	public List<EmployeeCertificationDetails> listAll() {
	      return repo.findAll();
	 }
	 
	 public EmployeeCertificationDetails get(int id) {
		  return repo.findById(id).get();
	 }
	 
	 public void Save(EmployeeCertificationDetails certificationDetails) {
	      repo.save(certificationDetails);
	 }
	 
	 public void delete(int id) {
			repo.deleteById(id);
	 }
}
