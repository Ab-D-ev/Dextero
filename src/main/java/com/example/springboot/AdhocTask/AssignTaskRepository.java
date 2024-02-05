package com.example.springboot.AdhocTask;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.springboot.Holidays.AddHolidays;

@Repository
public interface AssignTaskRepository extends  JpaRepository< AssignTask , Integer >{
	
//	@Query(nativeQuery = true, value = "select * from assign_task where currentdate=?1")
//	public List<AssignTask> getListByCurrentDate(String currentdate);
//	
//	@Query("select c from AssignTask c where c.companyname LIKE %?1% AND c.projectCode LIKE %?2% AND c.empname LIKE %?3%")
//	public List<AssignTask> FilterByCompanyName( String companyname, String projectCode, String empname);

	@Query(nativeQuery = true, value =" SELECT *  from  assign_task "
			+ "WHERE  DATE_ADD(end_year, "
			+ "                INTERVAL YEAR(curdate())-YEAR(end_year)"
			+ "                         + IF(DAYOFYEAR(curdate()) > DAYOFYEAR(end_year),1,0)YEAR) "
			+ "            BETWEEN curdate() AND DATE_ADD(curdate(), INTERVAL 30 DAY) ORDER BY end_date ASC limit 1")
	   public List<AssignTask> getTaskListLimit1(); 
	
	@Query(nativeQuery = true, value =" SELECT *  from  assign_task "
			+ "WHERE  DATE_ADD(end_year, "
			+ "                INTERVAL YEAR(curdate())-YEAR(end_year)"
			+ "                         + IF(DAYOFYEAR(curdate()) > DAYOFYEAR(end_year),1,0)YEAR) "
			+ "            BETWEEN curdate() AND DATE_ADD(curdate(), INTERVAL 30 DAY) ORDER BY end_date ASC limit 1")
	   public List<AssignTask> getTaskList(); 
	

}
