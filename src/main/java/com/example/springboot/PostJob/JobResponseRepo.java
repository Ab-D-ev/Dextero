package com.example.springboot.PostJob;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JobResponseRepo  extends JpaRepository<JobResponse, Integer>{
	
	@Query(nativeQuery=true , value="select * from job_response ORDER BY id ASC Limit 1")
	public List<JobResponse> getJobResponseList();

}
