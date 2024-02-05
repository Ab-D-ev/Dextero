package com.example.springboot.PostJob;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserJobApplyService {
	
	@Autowired
	private UserJobApplyRepo repo;
	
	public List<UserJobApply> listAll() {
		return repo.findAll();
	}
	
	public UserJobApply get(int id) {
		return repo.findById(id).get();
	}
	
	public void save(UserJobApply uApply) {
		repo.save(uApply);	
	}
	
	public void delete(int id) {
		repo.deleteById(id);
	}

	public int GetJobID(int job_id){
		 return repo.GetJobID(job_id);
	 }
	
	public List<UserJobApply> findByJobId(int jobId){
		return repo.findByJobId(jobId);
	}
}
