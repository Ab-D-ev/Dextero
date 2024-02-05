package com.example.springboot.MainMaster;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {

	@Autowired
	private DepartmentRepo repo;
	
	public List<Department> listAll() {
	      return repo.findAll();
	 }
	 
	 public Department get(int id) {
		  return repo.findById(id).get();
	 }
	 
	 public void Save(Department department) {
	      repo.save(department);
	 }
	 
	 public void delete(int id) {
			repo.deleteById(id);
	 }
}
