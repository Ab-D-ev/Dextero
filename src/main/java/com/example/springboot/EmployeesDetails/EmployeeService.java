package com.example.springboot.EmployeesDetails;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public interface EmployeeService {
	
	List<Employee> getAllEmployees();
	void saveEmployee(Employee employee);
	
	Employee getEmployeeById(Integer id);
	void deleteEmployeeById(Integer id);
	List<Employee> getNameANDAge(String firstname, String age);
	public Integer Update(String emailid);
	public List<Employee> getBirthDate();
	public List<Employee> getWorkAnniversaryDate();
	public int TotalNumberOfEmployee(Integer companyId);
	public List<Employee> getBirthDateLimit1();
	public Integer getMaleCount(Integer companyId);
	public Integer getFemaleCount(Integer companyId);
	public Employee getUsername(String username);
	
//	public void Excelsave(MultipartFile file);
	
	public int findByEmailID(String username);
	public Integer CountByCompanyId(Integer companyid);
//	public List<Employee> FilterByEmployeeName(String firstname,String middlename, String lastname);
	
	public List<Employee> getEmployeeManager(String department);
	public List<Employee> findEmployeeManagerName(String department, Integer companyId);
	public String countPrefixValue(Integer companyId);
	public String getCompanyPrefix(Integer companyId);
	public String getPreviousSufixValue(Integer companyId);
	public List<Employee> FilterByReportingManager(Integer id);
	public Integer getEmployeeID(String email);
	Employee get(int user_id);
	public List<Employee> findTeamLeaderName();
	public Integer getCompanyID(String emailid);
	public Employee getEmployeeDataByEmailID(Integer id);
	public Employee getEmpID(String username); 
	public List<String> getEmployeeNameListByProjectID(Set<Integer> employeeIds);
	public String getEmployeeDisability(Integer employeeId);
	
	public List<Employee> findByCompanyId(Integer company_id);
	public List<Employee> getEmployeeList();
	public String getEmployeeNameByID(Integer employeeId);
	public List<Employee> getEmployeeListByDescOrder();
	
	public Integer getAuthorityID(String emailid);
}
