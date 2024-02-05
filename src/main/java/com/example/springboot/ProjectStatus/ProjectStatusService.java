package com.example.springboot.ProjectStatus;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springboot.TimeSheet.TimeSheetMailReport;

@Service
@Transactional
public class ProjectStatusService {

	@Autowired
	private ProjectStatusRepo repo;
	
	public List<ProjectStatus> listAll() {
	      return repo.findAll();
	 }
	 
	 public ProjectStatus get(int id) {
		  return repo.findById(id).get();
	 }
	 
	 public void Save(ProjectStatus project) {
	      repo.save(project);
	 }
	 
	 public void delete(int id) {
			repo.deleteById(id);
	 }
	 public List<ProjectStatus> getProjectStatusByUserNameLimit1(){
		 return repo.getProjectStatusByUserNameLimit1();
	 }
	 
	 public List<ProjectStatus> FindProjectCodeByCompanyNameProjectName(String companyname, String projectname, String projectcode){
		 return repo.FindProjectCodeByCompanyNameProjectName(companyname, projectname, projectcode);
	 }
	 
	 public String getFirstName(String username){
		 return repo.getFirstName(username);
	 }
}
