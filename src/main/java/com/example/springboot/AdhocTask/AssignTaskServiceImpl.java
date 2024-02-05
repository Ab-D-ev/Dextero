package com.example.springboot.AdhocTask;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AssignTaskServiceImpl {
	
	@Autowired
	 private  AssignTaskRepository  repo;
	 
	 public List<AssignTask> listAll() {
	      return repo.findAll();
	 }
	 
	 public AssignTask get(int id) {
		  return repo.findById(id).get();
	 }
	 
	 public void save(AssignTask assigntask) {
	      repo.save(assigntask);
	 }
	 
	 public void deleteAssignTask(int id) {
			repo.deleteById(id);
	 }
	 
//	 public List<AssignTask> getListByCurrentDate(String currentdate){
//		 return repo.getListByCurrentDate(currentdate);
//	 }
//	 
//	 public List<AssignTask> FilterByCompanyName( String companyname, String projectCode, String empname){
//		 return repo.FilterByCompanyName(companyname, projectCode, empname);
//	 }

	 public List<AssignTask> getTaskListLimit1(){
		 return repo.getTaskListLimit1();
	 }
	 
	 public List<AssignTask> getTaskList(){
		 return repo.getTaskList();
	 }
	 
}



