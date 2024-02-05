package com.example.springboot.MainMaster;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class EmploymentTypeService {

	@Autowired
	private EmploymentTypeRepo repo;
	
	public List<EmploymentType> listAll() {
	      return repo.findAll();
	 }
	 
	 public EmploymentType get(int id) {
		  return repo.findById(id).get();
	 }
	 
	 public void Save(EmploymentType etype) {
	      repo.save(etype);
	 }
	 
	 public void delete(int id) {
			repo.deleteById(id);
	 }
	
}
