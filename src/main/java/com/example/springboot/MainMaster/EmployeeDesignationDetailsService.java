package com.example.springboot.MainMaster;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeDesignationDetailsService {

	@Autowired
	private EmployeeDesignationDetailsRepo repo;
	
	public List<EmployeeDesignationDetails> listAll() {
	      return repo.findAll();
	 }
	 
	 public EmployeeDesignationDetails get(int id) {
		  return repo.findById(id).get();
	 }
	 
	 public void Save(EmployeeDesignationDetails designationDetails) {
	      repo.save(designationDetails);
	 }
	 
	 public void delete(int id) {
			repo.deleteById(id);
	 }
	 
	 public Integer getDesignationIDAsIntern(Integer companyId){
		 return repo.getDesignationIDAsIntern(companyId);
	 }
	 
	 public Integer getDesignationIDAsJrDeveloper(Integer companyId) {
		 return repo.getDesignationIDAsJrDeveloper(companyId);
	 }
	 
	 public Integer getDesignationIDAsSrDeveloper(Integer companyId) {
		 return repo.getDesignationIDAsSrDeveloper(companyId);
	 }
	 
	 public Integer getDesignationIDAsDeveloper(Integer companyId) {
		return repo.getDesignationIDAsDeveloper(companyId);
	 }
	 
	 public Integer getDesignationIDAsProjectManager(Integer companyId) {
		 return repo.getDesignationIDAsProjectManager(companyId);
	 }
	 
	 public Integer getDesignationIDAsConsultant(Integer companyId) {
		 return repo.getDesignationIDAsConsultant(companyId);
	 }
}
