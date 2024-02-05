package com.example.springboot.MainMaster;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class FunctionalAreaService {

	@Autowired
	private FunctionalAreaRepo repo;
	
	public List<FunctionalArea> listAll() {
	      return repo.findAll();
	 }
	 
	 public FunctionalArea get(int id) {
		  return repo.findById(id).get();
	 }
	 
	 public void Save(FunctionalArea farea) {
	      repo.save(farea);
	 }
	 
	 public void delete(int id) {
			repo.deleteById(id);
	 }
}
