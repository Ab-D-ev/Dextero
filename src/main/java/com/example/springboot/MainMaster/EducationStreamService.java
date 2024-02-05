package com.example.springboot.MainMaster;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EducationStreamService {
	
	@Autowired
	private EducationStreamRepo repo;
	
	public List<EducationStream> listAll() {
	      return repo.findAll();
	 }
	 
	 public EducationStream get(int id) {
		  return repo.findById(id).get();
	 }
	 
	 public void Save(EducationStream stream) {
	      repo.save(stream);
	 }
	 
	 public void delete(int id) {
		repo.deleteById(id);
	 }

	 public List<String> findCourse(String course){
		 return repo.findCourse(course);
	 }
}
