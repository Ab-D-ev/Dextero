package com.example.springboot.ProjectStatus;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ProjectStatusRepo extends JpaRepository<ProjectStatus , Integer> {
	
	@Query(nativeQuery = true, value = "SELECT * from  project_status "
			+ "WHERE dateyear >= DATE_SUB(CURRENT_DATE, INTERVAL DAYOFMONTH(CURRENT_DATE)-1 DAY)  ORDER BY currentdate ASC limit 1")
	public List<ProjectStatus> getProjectStatusByUserNameLimit1();
	
	@Query(nativeQuery = true, value = "select * from project_status where companyname=?1 AND pname=?2 AND pcode=?3")
	public List<ProjectStatus> FindProjectCodeByCompanyNameProjectName(String companyname, String projectname, String projectcode);


	@Query(nativeQuery = true, value = "Select firstname from employees where email IN"
			+ "(Select username from project_status where username= ?1)")
	public String getFirstName(String username);

	
}
