package com.example.springboot.SalaryPayRoll;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MasterPercentageRepo extends JpaRepository<MasterPercentage, Integer> {

	@Query(value="select employee_PF from MasterPercentage where company_id =?1 AND ptCondition= '<=15000'")
	public String findEmployee_PFlessEqual15000(Integer companyid);
	
	@Query(value="select employee_PF from MasterPercentage where company_id =?1 AND ptCondition= '>15000' ")
	public String findEmployee_PFGrater15000(Integer companyid);
	
	@Query(value="select employer_PF from MasterPercentage where company_id =?1 AND ptCondition='<=15000' ")
	public String findEmployer_PFlessEqual15000(Integer companyid);
	
	@Query(value="select employer_PF from MasterPercentage where company_id =?1 AND ptCondition='>15000' ")
	public String findEmployer_PFGrater15000(Integer companyid);
	
	@Query(value="SELECT mp.employee_ESI FROM MasterPercentage mp " +
            "JOIN Employee emp ON emp.id = ?1 " +
            "JOIN EmployeeGrossPay gross ON gross.empId = emp.id " +
            "WHERE mp.company_id = ?2 AND mp.ptCondition = '<=21000' AND emp.disability = mp.disability")
	public String findEmployee_ESIlessthan21000(Integer employeeId,Integer companyid);
	
	@Query(value="SELECT mp.employee_ESI FROM MasterPercentage mp " +
            "JOIN Employee emp ON emp.id = ?1 " +
            "JOIN EmployeeGrossPay gross ON gross.empId = emp.id " +
            "WHERE mp.company_id = ?2 AND mp.ptCondition = '<=25000' AND emp.disability = mp.disability")
	public String findEmployee_ESILessEqual25000(Integer employeeId, Integer companyid);
	
	@Query(value="SELECT mp.employee_ESI FROM MasterPercentage mp " +
            "JOIN Employee emp ON emp.id = ?1 " +
            "JOIN EmployeeGrossPay gross ON gross.empId = emp.id " +
            "WHERE mp.company_id = ?2 AND mp.ptCondition = '>25000' AND emp.disability = mp.disability")
	public String findEmployee_ESIGraterEqual25000(Integer employeeId ,Integer companyid);
	
	@Query(value="SELECT mp.employee_ESI FROM MasterPercentage mp " +
            "JOIN Employee emp ON emp.id = ?1 " +
            "JOIN EmployeeGrossPay gross ON gross.empId = emp.id " +
            "WHERE mp.company_id = ?2 AND mp.ptCondition = '>21000' AND emp.disability = mp.disability")
	public String findEmployee_ESIGrater21000(Integer employeeId,Integer companyid);
	
	
	
	@Query(value="SELECT mp.employer_ESI FROM MasterPercentage mp " +
            "JOIN Employee emp ON emp.id = ?1 " +
            "JOIN EmployeeGrossPay gross ON gross.empId = emp.id " +
            "WHERE mp.company_id = ?2 AND mp.ptCondition = '<=21000' AND emp.disability = mp.disability")
	public String findEmployer_ESIlessthan21000(Integer employeeId,Integer companyid);
	
	@Query(value="SELECT mp.employer_ESI FROM MasterPercentage mp " +
            "JOIN Employee emp ON emp.id = ?1 " +
            "JOIN EmployeeGrossPay gross ON gross.empId = emp.id " +
            "WHERE mp.company_id = ?2 AND mp.ptCondition = '<=25000' AND emp.disability = mp.disability")
	public String findEmployer_ESILessEqual25000(Integer employeeId, Integer companyid);
	
	@Query(value="SELECT mp.employer_ESI FROM MasterPercentage mp " +
            "JOIN Employee emp ON emp.id = ?1 " +
            "JOIN EmployeeGrossPay gross ON gross.empId = emp.id " +
            "WHERE mp.company_id = ?2 AND mp.ptCondition = '>25000' AND emp.disability = mp.disability")
	public String findEmployer_ESIGraterEqual25000(Integer employeeId ,Integer companyid);
	
	@Query(value="SELECT mp.employer_ESI FROM MasterPercentage mp " +
            "JOIN Employee emp ON emp.id = ?1 " +
            "JOIN EmployeeGrossPay gross ON gross.empId = emp.id " +
            "WHERE mp.company_id = ?2 AND mp.ptCondition = '>21000' AND emp.disability = mp.disability")
	public String findEmployer_ESIGrater21000(Integer employeeId,Integer companyid);
	
	
	
	
	
//	@Query(value="select employer_ESI from MasterPercentage where company_id =?1 AND ptCondition='<=21000' ")
//	public String findEmployer_ESIlessthan21000(Integer companyid);
//	
//	@Query(value="SELECT mp.employer_ESI FROM MasterPercentage mp " +
//            "JOIN Employee emp ON emp.id = ?1 " +
//            "JOIN EmployeeGrossPay gross ON gross.empId = emp.id " +
//            "WHERE mp.company_id = ?2 AND mp.ptCondition = '<=25000' AND emp.disability = mp.disability")
//	public String findEmployer_ESILessEqual25000(Integer employeeId , Integer companyid);
//	
//	
//	@Query(value="select employer_ESI from MasterPercentage where company_id =?1 AND ptCondition='>21000' ")
//	public String findEmployer_ESIGrater21000(Integer companyid);
	
//	@Query(value="select employee_Professional_Tax from MasterPercentage where company_id =?1 AND ptCondition='<=10000' AND gender='Male'")
//	public String findEmployee_PTaxLessEqual10000(Integer companyid);
	@Query(value="SELECT mp.employee_Professional_Tax FROM MasterPercentage mp " +
            "JOIN Employee emp ON emp.id = ?1 " +
            "JOIN EmployeeGrossPay gross ON gross.empId = emp.id " +
            "WHERE mp.company_id = ?2 AND mp.ptCondition = '<=10000' AND emp.gender = mp.gender")
	public String findEmployeePTaxLessEqual10000(Integer employeeId, Integer companyId);

	@Query(value="SELECT mp.employee_Professional_Tax FROM MasterPercentage mp " +
            "JOIN Employee emp ON emp.id = ?1 " +
            "JOIN EmployeeGrossPay gross ON gross.empId = emp.id " +
            "WHERE mp.company_id = ?2 AND mp.ptCondition = '<=10000' AND emp.gender = mp.gender")
	public String findEmployee_PTaxGrater10000(Integer employeeId, Integer companyId);
	
	@Query(value="SELECT mp.employee_Professional_Tax FROM MasterPercentage mp " +
            "JOIN Employee emp ON emp.id = ?1 " +
            "JOIN EmployeeGrossPay gross ON gross.empId = emp.id " +
            "WHERE mp.company_id = ?2 AND mp.ptCondition = '>10000' ")
	public String findEmployee_PTaxGrater10(Integer employeeId,Integer companyid);
	
	@Query(value="select employee_Professional_Tax from MasterPercentage where company_id =?1 AND ptCondition='<10000' ")
	public String findEmployee_PTaxlessthan10(Integer companyid);
	
	@Query(value="select employee_Gratuity from MasterPercentage where company_id =?1 AND ptCondition='>10000'")
	public String findEmployee_Gratuity(Integer companyid);
	
	@Query(value="select employee_Gratuity from MasterPercentage where company_id =?1 AND ptCondition='<=10000' AND gender='Male'")
	public String findEmployee_GratuityForMale(Integer companyid);
	
	@Query(value="select employee_Gratuity from MasterPercentage where company_id =?1 AND ptCondition='<10000'")
	public String findEmployee_Gratuityless10000(Integer companyid);
}
