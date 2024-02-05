package com.example.springboot.PostJob;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class JobPostService {

	@Autowired
	private JobPostRepo repo;
	
	public List<JobPost> listAll() {
	      return repo.findAll();
	 }
	 
	 public JobPost get(int id) {
		  return repo.findById(id).get();
	 }
	 
	 public void Save(JobPost jpost) {
	      repo.save(jpost);
	 }
	 
	 public void delete(int id) {
			repo.deleteById(id);
	 }
	 
	 public int getID(Integer Id) {
		 return repo.getID(Id);
	 }
	 public JobPost getJobById(Integer id) {
		 return repo.getJobById(id);
	 }
}
