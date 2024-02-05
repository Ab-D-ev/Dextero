package com.example.springboot.PostJob;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JobPostRepo extends JpaRepository<JobPost , Integer>{

	@Query(nativeQuery = true, value = "select count(*) from job_post where id=?1 order by id")
	public int getID(Integer Id);
	
	@Query(nativeQuery = true, value = "select * from job_post where id=?1")
	public JobPost getJobById(Integer id);
	
	
	
}
