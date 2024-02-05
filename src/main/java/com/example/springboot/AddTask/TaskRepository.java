package com.example.springboot.AddTask;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.springboot.AddProjectCode.projectName;

@Repository
public interface TaskRepository extends JpaRepository<AddTask,Integer>{ 
	
	@Query("select t from AddTask t where t.company_name_id=?1 AND t.project_id=?2 AND milestone_id=?3")
	public List<AddTask> findTaskByCompanyIdProjectIdMilestoneId(Integer companyId, Integer projectId,Integer milestoneId);
	
//	public List<String> findTask(String mile_stone);
	
	@Query(value="select addTask from AddTask where projectname=?1")
	public List<String> findTaskByProjectName(String projectName);
	
	@Query(nativeQuery = true, value = "select * from Task where currentdate=?1")
	public List<AddTask> getTaskListByCurrentDate(String currentdate);
	
	
//	@Query(value="select addTask from AddTask where company_name_id IN(select id from AddCustomer where CompanyName=?1) "
//			+ "AND project_id IN(select id from projectName where projectName=?2)"
//			+ "AND milestone_id IN(select id from Addmilestone where add_milestone=?3)")
//	public List<String> findTaskByCompanyProjectNameMilestone(String company,String projectName, String milestone);

}
