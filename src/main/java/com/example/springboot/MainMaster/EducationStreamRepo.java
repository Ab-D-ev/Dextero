package com.example.springboot.MainMaster;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EducationStreamRepo extends JpaRepository<EducationStream, Integer>{
	
	@Query(value="select stream from EducationStream where equalification=?1")
	public List<String> findCourse(String course);

}
