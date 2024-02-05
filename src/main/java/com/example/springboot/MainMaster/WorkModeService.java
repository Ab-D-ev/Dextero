package com.example.springboot.MainMaster;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class WorkModeService {
	
	@Autowired
	private WorkModeRepo repo;
	
	public List<WorkMode> listAll() {
	      return repo.findAll();
	 }
	 
	 public WorkMode get(int id) {
		  return repo.findById(id).get();
	 }
	 
	 public void Save(WorkMode mode) {
	      repo.save(mode);
	 }
	 
	 public void delete(int id) {
			repo.deleteById(id);
	 }

}
