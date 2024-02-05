package com.example.springboot.AddProjectCode;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.springboot.EmployeesDetails.Employee;

public interface ProjectNameAuthoritiesRepo extends JpaRepository<ProjectNameAuthorities,Integer>{
	
//	public ProjectNameAuthorities findByProject_id(int project_id);
	
	@Modifying
	@Query(value = "insert into project_name_authorities (project_id , employee_id) VALUES (:project_id , :employee_id)", nativeQuery = true)
	public void insertDataIntoProjectAuthorities(Integer project_id, Integer employee_id);
	
	
	@Query(nativeQuery = true, value="select project_id from project_name_authorities where employee_id=?1")
	 public List<Integer> findProjectIdsByEmployeeId(Integer employeeId);
	
	@Query(nativeQuery=true , value="SELECT  COUNT(*) from project_name_authorities where project_id=?1 AND employee_id=?2")
	public int CountProjectNameAuthorities(int projectId, int employeeId);
	
	@Query(value="select employee_id from ProjectNameAuthorities where project_id =?1")
	public List<String> getEmployeeIDListByProjectID(Integer projectname);
	
	@Query(nativeQuery = true, value="select * from project_name_authorities  ORDER BY project_id ASC limit 1")
	 public List<ProjectNameAuthorities> findProjectNameLimit1();
	
	@Query(nativeQuery = true, value="select employee_id from project_name_authorities where project_id=?1")
	 public List<Integer> findEmployeeIdByProjectIds(List<Integer> projectId);
}
