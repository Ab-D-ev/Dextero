package com.example.springboot.MainMaster;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeDesignationDetailsRepo extends JpaRepository<EmployeeDesignationDetails, Integer>{

	@Query(nativeQuery=true , value="select id from employee_designation where designation='Intern' AND company_id=?1")
	public Integer getDesignationIDAsIntern(Integer companyId);
	
	@Query(nativeQuery=true , value="select id from employee_designation where designation='Jr.Developer' AND company_id=?1")
	public Integer getDesignationIDAsJrDeveloper(Integer companyId);
	
	@Query(nativeQuery=true , value="select id from employee_designation where designation='Sr.Developer' AND company_id=?1")
	public Integer getDesignationIDAsSrDeveloper(Integer companyId);
	
	@Query(nativeQuery=true , value="select id from employee_designation where designation='Developer'  AND company_id=?1")
	public Integer getDesignationIDAsDeveloper(Integer companyId);
	
	@Query(nativeQuery=true , value="select id from employee_designation where designation='Project Manager' AND company_id=?1")
	public Integer getDesignationIDAsProjectManager(Integer companyId);
	
	@Query(nativeQuery=true , value="select id from employee_designation where designation='Consultant' AND company_id=?1")
	public Integer getDesignationIDAsConsultant(Integer companyId);
}
