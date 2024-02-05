package com.example.springboot.EmployeesDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService{
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Override
	public List<Employee> getAllEmployees() {
		return  employeeRepository.findAll();
		
	}

	@Override
	public void saveEmployee(Employee employee) {
		this.employeeRepository.save(employee);
		
	}
	
	@Override
	public Employee get(int user_id) {
		  return employeeRepository.findById(user_id).get();
	 }
	
	@Override
	public Employee getEmployeeById(Integer id) {
		java.util.Optional<Employee> optional = employeeRepository.findById(id);
		Employee employee=null;
		if(optional.isPresent()) {
			employee=optional.get();
		}else {
			throw new RuntimeException("Employee id not found"+id);
		}
		return employee;
		
	}

	@Override
	public void deleteEmployeeById(Integer id) {
		this.employeeRepository.deleteById(id);
		
	}
	    
		public List<Employee> getEmployeeListByOrder(int id){
			return employeeRepository.getEmployeeListByOrder(id);
		}
		@Override
		public List<Employee> getNameANDAge(String firstname,String age){
			return employeeRepository.getNameANDAge(firstname, age);
			
		}

		@Override
		public Integer Update(String emailid) {
			return employeeRepository.Update(emailid);
		}
		
		@Override
		public List<Employee> getBirthDate(){
			return employeeRepository.getBirthDate();
		}

		@Override
		public List<Employee> getWorkAnniversaryDate() {
			return employeeRepository.getWorkAnniversaryDate();
		}

		@Override
		public int TotalNumberOfEmployee(Integer companyId) {
			return employeeRepository.TotalNumberOfEmployee(companyId);
		}

		@Override
		public List<Employee> getBirthDateLimit1() {
			return employeeRepository.getBirthDateLimit1();
		}

		@Override
		public Integer getMaleCount(Integer companyId) {
			return employeeRepository.getMaleCount(companyId);
		}

		@Override
		public Integer getFemaleCount(Integer companyId) {
			return employeeRepository.getFemaleCount(companyId);
		}

		@Override
		public Employee getUsername(String username) {
			return employeeRepository.getUsername(username);
		}
		
//		@Override
//		public void Excelsave(MultipartFile file) {
//		    try {
//		    	System.out.println(file.getName());
//		    	System.out.println(file.getName());
//		    	String name=file.getOriginalFilename();
//		    	String fileName = name.replaceFirst("[.][^.]+$", "");
//		    	System.out.println("Original File Name : "+ fileName);
//		      List<Employee> tutorials = ExcelHelper.excelToTutorials(file.getInputStream());
//		      employeeRepository.saveAll(tutorials);
//		      System.out.println("Employee Service : " + tutorials);
//		    } catch (IOException e) {
//		      throw new RuntimeException("fail to store excel data: " + e.getMessage());
//		    }
//		  }

		@Override
		public int findByEmailID(String username) {
			return employeeRepository.findByEmailID(username);
		}

		@Override
		public Integer CountByCompanyId(Integer companyid) {
			return employeeRepository.CountByCompanyId(companyid);
		}

//		@Override
//		public List<Employee> FilterByEmployeeName(String firstname,String middlename, String lastname){
//			return employeeRepository.FilterByEmployeeName(firstname, middlename, lastname);
//		}

		@Override
		public List<Employee> getEmployeeManager(String department) {
			return employeeRepository.getEmployeeManager(department);
		}

		@Override
		public List<Employee> findEmployeeManagerName(String department, Integer companyId) {
			return employeeRepository.findEmployeeManagerName(department,companyId);
		}

		@Override
		public String countPrefixValue(Integer companyId) {
			return employeeRepository.countPrefixValue(companyId);
		}

		@Override
		public String getCompanyPrefix(Integer companyId) {
			return employeeRepository.getCompanyPrefix(companyId);
		}

		@Override
		public String getPreviousSufixValue(Integer companyId) {
			return employeeRepository.getPreviousSufixValue(companyId);
		}

		@Override
		public List<Employee> FilterByReportingManager(Integer id) {
			return employeeRepository.FilterByReportingManager(id);
		}

		@Override
		public Integer getEmployeeID(String email) {
			return employeeRepository.getEmployeeID(email);
		}

		@Override
		public List<Employee> findTeamLeaderName() {
			return employeeRepository.findTeamLeaderName();
		}

		@Override
		public Integer getCompanyID(String emailid) {
			return employeeRepository.getCompanyID(emailid);
		}

		@Override
		public Employee getEmployeeDataByEmailID(Integer id){
			return employeeRepository.getEmployeeDataByEmailID(id);
		}

		@Override
		public Employee getEmpID(String username) {
	        return employeeRepository.findByEmail(username);
	    }
		
		@Override
	    public List<String> getEmployeeNameListByProjectID(Set<Integer> employeeIds) {
	        List<Employee> employees = employeeRepository.findAllById(employeeIds);
	        List<String> employeeNames = new ArrayList<>();
	        for (Employee employee : employees) {
	            employeeNames.add(employee.getEmail());
	        }
	        return employeeNames;
	    }

		@Override
		public String getEmployeeDisability(Integer employeeId) {
			return employeeRepository.getEmployeeDisability(employeeId);
		}

		@Override
		public List<Employee> findByCompanyId(Integer company_id) {
			return employeeRepository.findByCompanyId(company_id);
		}

		@Override
		public List<Employee> getEmployeeList() {
			return employeeRepository.getEmployeeList();
		}

		@Override
		public String getEmployeeNameByID(Integer employeeId) {
			return employeeRepository.getEmployeeNameByID(employeeId);
		}

		@Override
		public List<Employee> getEmployeeListByDescOrder() {
			return employeeRepository.getEmployeeListByDescOrder();
		}

		@Override
		public Integer getAuthorityID(String emailid) {
			return employeeRepository.getAuthorityID(emailid);
		}
}
