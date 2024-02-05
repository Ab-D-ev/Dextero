package com.example.springboot.EmployeesDetails;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeAddressDetailsService {
	
	@Autowired 
	private EmployeeAddressDetailsRepo repo;
	
	public List<EmployeeAddressDetails> listAll() {
	      return repo.findAll();
	 }
	 
	 public EmployeeAddressDetails get(int id) {
		  return repo.findById(id).get();
	 }
	 
	 public void Save(EmployeeAddressDetails address) {
	      repo.save(address);
	 }
	 
	 public void delete(int id) {
			repo.deleteById(id);
	 }

}
