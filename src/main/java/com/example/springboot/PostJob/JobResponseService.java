package com.example.springboot.PostJob;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobResponseService {

	@Autowired
	private JobResponseRepo repo;
	
	public List<JobResponse> listAll() {
	      return repo.findAll();
	 }
	 
	 public JobResponse get(int id) {
		  return repo.findById(id).get();
	 }
	 
	 public void Save(JobResponse response) {
	      repo.save(response);
	 }
	 
	 public List<JobResponse> getJobResponseList(){
		 return repo.getJobResponseList();
	 }
	
}
