package com.example.springboot.AddTask;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.example.springboot.AddProjectCode.projectName;


@Service
public class TaskServiceImpl implements TaskService{
	
	@Autowired
	 private TaskRepository repo;

	@Override
	public List<AddTask> getAllTask() {
		
		return repo.findAll();
	}

	@Override
	public void saveAllTask(AddTask task) {
		this.repo.save(task);
		
	}

	@Override
	public AddTask getTaskById(Integer id) {
		java.util.Optional< AddTask> optional = repo.findById(id);
		 AddTask task=null;
		if(optional.isPresent()) {
			task=optional.get();
		}else {
			throw new RuntimeException(" id not found"+id);
		}
		return  task;
	}

	@Override
	public void deleteTaskById(Integer id) {
		this.repo.deleteById(id);
		
	}
	
	public List<AddTask> findTaskByCompanyIdProjectIdMilestoneId(@Param("company_name_id") Integer companyId, @Param("project_id") Integer projectId, @Param("milestone_id") Integer milestoneId){
		return repo.findTaskByCompanyIdProjectIdMilestoneId(companyId, projectId, milestoneId);
	}
	
	public List<AddTask> getTaskListByCurrentDate(String currentdate){
		return repo.getTaskListByCurrentDate(currentdate);
	}
	
	public List<String> findTaskByProjectName(String projectName){
		return repo.findTaskByProjectName(projectName);
	}

//	public List<String> findTaskByCompanyProjectNameMilestone(String company,String projectName, String milestone){
//		return repo.findTaskByCompanyProjectNameMilestone(company, projectName, milestone);
//	}
		
}
