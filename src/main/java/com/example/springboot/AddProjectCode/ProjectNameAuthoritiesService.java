package com.example.springboot.AddProjectCode;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class ProjectNameAuthoritiesService {
	
	@Autowired
	private ProjectNameAuthoritiesRepo repo;
	
	 public List<ProjectNameAuthorities> listAll() {
	      return repo.findAll();
	 }
	 
	 public ProjectNameAuthorities get(int projectid) {
		  return repo.findById(projectid).get();
	 }
	 
	 public void save(ProjectNameAuthorities std) {
	      repo.save(std);
	 }
	 
	 public void insertDataIntoProjectAuthorities(Integer project_id, Integer employee_id) {
		 repo.insertDataIntoProjectAuthorities(project_id, employee_id);
	 }
	 
	 public List<Integer> findProjectIdsByEmployeeId(Integer employeeId){
		 return repo.findProjectIdsByEmployeeId(employeeId);
	 }
	 
	 public int CountProjectNameAuthorities(int projectId, int employeeId) {
		return repo.CountProjectNameAuthorities(projectId, employeeId);
	 }
	 
	 public ProjectNameAuthorities getProjectAuthoritiesBYId(Integer id) {
		    return repo.findById(id).orElse(null);
		}
	 
	 public List<String> getEmployeeIDListByProjectID(Integer projectname){
	        List<String> employeeIds = repo.getEmployeeIDListByProjectID(projectname);
	        return employeeIds;
	    }
	 
	 public List<ProjectNameAuthorities> findProjectNameLimit1(){
		 return repo.findProjectNameLimit1();
	 }
	 
	 public List<Integer> findEmployeeIdByProjectIds(List<Integer> assignedProjectIds){
		 return repo.findEmployeeIdByProjectIds(assignedProjectIds);
	 }
}
