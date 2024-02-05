package com.example.springboot.LeaveCategory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeaveCategoryService {

	@Autowired
	private LeaveCategoryRepo repo;
	
	public List<LeaveCategory> listAll() {
	      return repo.findAll();
	 }
	 
	 public LeaveCategory get(int id) {
		  return repo.findById(id).get();
	 }
	 
	 public void Save(LeaveCategory category) {
	      repo.save(category);
	 }
	 
	 public void delete(int id) {
			repo.deleteById(id);
	 }

	 public Integer getLeaveCategoryID() {
		 return repo.getLeaveCategoryID();
	 }
}
