package com.example.springboot.DefineProjectPlan;

import java.util.List;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ViewProjectServiceImpl {
	
	@Autowired
	private ViewProjectRepository repo;
	
	 public List<ViewProject> listAll() {
	      return repo.findAll();
	 }
	 
	 public ViewProject get(int id) {
		  return repo.findById(id).get();
	 }
	 
	 public void save(ViewProject viewproject) {
	      repo.save(viewproject);
	 }
	 
	 public void deleteViewProject(int id) {
			repo.deleteById(id);
	 }
	  
	 public String selectDate() {
		 return repo.selectDate();
	 
	 }
	 public List<ViewProject> getListByCurrentDate(String currentdate){
		return repo.getListByCurrentDate(currentdate);
		 
	 }
//	 public List<ViewProject> FilterByCompanyName(String companyname,String projectcode){
//		 return repo.FilterByCompanyName(companyname, projectcode);
//	 }
//	 public List<ViewProject> FilterByCompanyANDEmployee( String companyname,String projectcode,String empname){
//		 return repo.FilterByCompanyANDEmployee(companyname, projectcode, empname);
//	 }
}
