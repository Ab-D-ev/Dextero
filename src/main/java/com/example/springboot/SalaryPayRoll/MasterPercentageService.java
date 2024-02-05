package com.example.springboot.SalaryPayRoll;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MasterPercentageService {
	
	@Autowired
	private MasterPercentageRepo repo;
	
	public List<MasterPercentage> listAll() {
	      return repo.findAll();
	 }
	 
	 public MasterPercentage get(int id) {
		  return repo.findById(id).get();
	 }
	 
	 public void Save(MasterPercentage deductions) {
	      repo.save(deductions);
	 }
	 
	 public void delete(int id) {
			repo.deleteById(id);
	 }
	 
	 public String findEmployee_PFlessEqual15000(Integer companyid) {
		 return repo.findEmployee_PFlessEqual15000(companyid);
	 }
	 
	 public String findEmployee_PFGrater15000(Integer companyid) {
		 return repo.findEmployee_PFGrater15000(companyid);
	 }
	 
	 public String findEmployer_PFlessEqual15000(Integer companyid) {
		 return repo.findEmployer_PFlessEqual15000(companyid);
	 }
	 
	 public String findEmployer_PFGrater15000(Integer companyid) {
		 return repo.findEmployer_PFGrater15000(companyid);
	 }
	 
	 public String findEmployee_ESIlessthan21000(Integer employeeId ,Integer companyid) {
		 return repo.findEmployee_ESIlessthan21000(employeeId,companyid);
	 }
	 
	 public String findEmployee_ESILessEqual25000(Integer employeeId,Integer companyid) {
		 return repo.findEmployee_ESILessEqual25000(employeeId,companyid);
	 }
	 
	 public String findEmployee_ESIGraterEqual25000(Integer employeeId, Integer companyid) {
		 return repo.findEmployee_ESIGraterEqual25000(employeeId, companyid);
	 }
	 
	 public String findEmployee_ESIGrater21000(Integer employeeId ,Integer companyid) {
		 return repo.findEmployee_ESIGrater21000(employeeId, companyid);
	 }
	 
	 public String findEmployer_ESIlessthan21000(Integer employeeId,Integer companyid) {
		 return repo.findEmployer_ESIlessthan21000(employeeId, companyid);
	 }
	
	 public String findEmployer_ESILessEqual25000(Integer employeeId, Integer companyid) {
		 return repo.findEmployer_ESILessEqual25000(employeeId, companyid);
	 }
	 public String findEmployer_ESIGraterEqual25000(Integer employeeId ,Integer companyid) {
		 return repo.findEmployer_ESIGraterEqual25000(employeeId, companyid);
	 }
	 public String findEmployer_ESIGrater21000(Integer employeeId,Integer companyid) {
		 return repo.findEmployer_ESIGrater21000(employeeId, companyid);
	 }
	 
	 
	 public String findEmployeePTaxLessEqual10000(Integer employeeId, Integer companyId) {
		 return repo.findEmployeePTaxLessEqual10000(employeeId, companyId);
	 }
	 
	 public String findEmployee_PTaxGrater10000(Integer employeeId, Integer companyId){
		 return repo.findEmployee_PTaxGrater10000(employeeId, companyId);
	 }
	 
	 public String findEmployee_PTaxGrater10(Integer employeeId,Integer companyid){
		 return repo.findEmployee_PTaxGrater10(employeeId, companyid);
	 }
	 
	 public String findEmployee_PTaxlessthan10(Integer companyid) {
		 return repo.findEmployee_PTaxlessthan10(companyid);
	 }
	 
	 public String findEmployee_Gratuity(Integer companyid) {
		 return repo.findEmployee_Gratuity(companyid);
	 }
	 
	 public String findEmployee_GratuityForMale(Integer companyid) {
		 return repo.findEmployee_GratuityForMale(companyid);
	 }
	 
	 public String findEmployee_Gratuityless10000(Integer companyid) {
		 return repo.findEmployee_Gratuityless10000(companyid);
	 }
}

