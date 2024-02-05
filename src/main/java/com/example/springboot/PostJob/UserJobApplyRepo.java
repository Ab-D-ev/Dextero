package com.example.springboot.PostJob;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserJobApplyRepo extends JpaRepository<UserJobApply, Integer>{

	@Query("select count(*) from UserJobApply u where u.job_id=?1")
	 public int GetJobID(int job_id);
	
	@Query("select u from UserJobApply u where u.job_id=?1")
	public List<UserJobApply> findByJobId(int jobId);
	
}
