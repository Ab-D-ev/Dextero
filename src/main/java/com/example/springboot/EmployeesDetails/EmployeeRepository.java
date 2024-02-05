package com.example.springboot.EmployeesDetails;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer>{
	
	// Method 1: returns Employee object directly
    public Employee findByEmail(String email);
    
    // Method 2: returns Optional<Employee> object
    public Optional<Employee> findByEmailIgnoreCase(String email);
	
	@Query(" FROM Employee ORDER BY id ")
	   public List<Employee> getEmployeeListByOrder(int id);
	    
	@Query(" select firstname , age FROM Employee")
	   public List<Employee> getNameANDAge(String firstname,String age);

	@Query(" select id FROM Employee where email=?1")
		public Integer Update(String emailid);
	
//	@Query(" select id FROM Employee where id=?1")
//	public Integer Update1(Integer id);
//	
	@Query(nativeQuery=true , 
			value= "SELECT * from  employees"
			+ " WHERE  DATE_ADD(birthyear, INTERVAL YEAR(CURDATE())-YEAR(birthyear) + IF(DAYOFYEAR(CURDATE()) > "
			+ " DAYOFYEAR(birthyear),1,0)YEAR) BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 30 DAY) ORDER BY dateof_birth Desc")
		public List<Employee> getBirthDate();

	@Query(nativeQuery=true , 
			value= "SELECT * from  employees"
			+ " WHERE  DATE_ADD(joiningdate_year, INTERVAL YEAR(CURDATE())-YEAR(joiningdate_year) + IF(DAYOFYEAR(CURDATE()) > "
			+ " DAYOFYEAR(joiningdate_year),1,0)YEAR) BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 30 DAY) ORDER BY joiningdate Desc")
		public List<Employee> getWorkAnniversaryDate();

	@Query(nativeQuery=true , value="SELECT  COUNT(*) AS 'Total employees' FROM employees where company_id=?1")
	public int TotalNumberOfEmployee(Integer companyId);
	
	@Query(nativeQuery=true , 
			value= "SELECT * from  employees"
			+ " WHERE  DATE_ADD(birthyear, INTERVAL YEAR(CURDATE())-YEAR(birthyear) + IF(DAYOFYEAR(CURDATE()) > "
			+ " DAYOFYEAR(birthyear),1,0)YEAR) BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 30 DAY) ORDER BY dateof_birth Desc limit 1")
		public List<Employee> getBirthDateLimit1();

	@Query("select count(*) from Employee where gender='Male' AND company_id=?1")
	public Integer getMaleCount(Integer companyId);
	
	@Query("select count(*) from Employee where gender='Female' AND company_id=?1")
	public Integer getFemaleCount(Integer companyId);
	
	@Query(nativeQuery=true , value="select * from employees where email=?1")
	public Employee getUsername(String username);
	
	@Query("select count(e) from Employee e where e.email = ?1")
	 public int findByEmailID(String username);
	
	@Query("select count(e) from Employee e where e.company_id = ?1")
	 public Integer CountByCompanyId(Integer companyid);
	
//	@Query("select e from Employee e where e.firstname LIKE ?1%  OR e.middlename LIKE ?2% OR e.lastname LIKE ?3%")
//	public List<Employee> FilterByEmployeeName(String firstname,String middlename, String lastname);

	
	@Query(nativeQuery=true , value="Select * from employees where email IN"
			+ "(Select username from system_login_user where login_user_id IN"
			+ "(SELECT user_id FROM system_login_user_authorities where department=?1 AND authority_id= 5));")
	public List<Employee> getEmployeeManager(String department);
	
	@Query(value="select e from Employee e where e.department=?1 AND authority_id= 5 AND company_id=?2")
	public List<Employee> findEmployeeManagerName(String department, Integer companyId);
	
	@Query(nativeQuery=true , value="select count(*) from employees where company_id IN"
			+ "(SELECT company_id from company_details where company_id= ?1)")
	public String countPrefixValue(Integer companyId);
	
	@Query(nativeQuery=true , value="SELECT companyprefix FROM company_details where company_id= ?1")
	public String getCompanyPrefix(Integer companyId);
	
	@Query(nativeQuery = true, value = "select sufix from employees where company_id IN"
			+ "(SELECT company_id from company_details where company_id= ?1) ORDER BY id DESC LIMIT 1;")
	public String getPreviousSufixValue(Integer companyId);
	
	@Query("select e from Employee e where e.authority_id= 5 AND e.id=?1 OR e.authority_id= 6 AND e.reporting_manager=?1 OR e.authority_id= 5 AND e.reporting_manager=?1")
	public List<Employee> FilterByReportingManager(Integer id);

	@Query(nativeQuery = true, value = "SELECT id from employees where email= ?1")
	public Integer getEmployeeID(String email);
	
	
	@Query(value="select e from Employee e where authority_id= 5")
	public List<Employee> findTeamLeaderName();
	
	@Query(nativeQuery = true, value = "SELECT company_id from employees where email= ?1")
	public Integer getCompanyID(String emailid);
	
	@Query(nativeQuery = true, value = "select * from employees where id=?1")
	public Employee getEmployeeDataByEmailID(Integer id);
	
	@Query(nativeQuery=true , value="select id from employees where email=?1")
	public Employee getEmpID(String username);
	
	@Query(value="select email from Employee where id=?1")
	public List<String> getEmployeeNameListByProjectID(Set<Integer> id);

	@Query(value =" select disability FROM Employee where id=?1")
	public String getEmployeeDisability(Integer employeeId);
	
	@Query("select e from Employee e where e.company_id=?1")
	public List<Employee> findByCompanyId(Integer company_id);
	
	@Query(nativeQuery=true , value="select * from employees where authority_id= 5 OR authority_id= 6")
	public List<Employee> getEmployeeList();
	
	@Query(value =" select firstname , middlename, lastname FROM Employee where id=?1")
	public String getEmployeeNameByID(Integer employeeId);
	
	@Query(" FROM Employee ORDER BY id DESC ")
	public List<Employee> getEmployeeListByDescOrder();
	
	@Query(" select authority_id FROM Employee where email=?1")
	public Integer getAuthorityID(String emailid);
}
