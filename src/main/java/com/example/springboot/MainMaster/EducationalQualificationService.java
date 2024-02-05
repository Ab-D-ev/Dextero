package com.example.springboot.MainMaster;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EducationalQualificationService {

	@Autowired
	private EducationalQualificationRepo repo;
	 
	 public List<EducationalQualification> listAll(){
		 return repo.findAll();
	 }
	 public EducationalQualification get(int id) {
		 return repo.findById(id).get();
	 }
	 public void Save(EducationalQualification qualification) {
		 repo.save(qualification);
	 }
	 public void delete(int id) {
		 repo.deleteById(id);
	 }
}
