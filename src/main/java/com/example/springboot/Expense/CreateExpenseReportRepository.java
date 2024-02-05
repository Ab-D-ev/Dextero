package com.example.springboot.Expense;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.springboot.EmployeesDetails.Employee;


@Repository
public interface CreateExpenseReportRepository extends JpaRepository< CreateExpenseReport,Integer >{
	
	@Query( value = "select c.id from CreateExpenseReport c where c.expenseName =?1 AND c.edate =?2 AND c.currentdate=?3")
	   public Integer getExpense(String ExpenseName,String EDate, String currentdate);

	//@Query(nativeQuery = true, value = "select * from create_expense_report where active=true")
	//   public List<CreateExpenseReport> getAllActiveExpense();

//	@Query(nativeQuery = true, value = "select count(case when approved_reject_flag='1' then 1 end) as approved ,"
//			+ " count(case when approved_reject_flag='0' then 1 end) as unapproved from create_expense_report")
//	   public Integer getApprovedUnapprovedCount();
//	
	@Query("select count(*) from CreateExpenseReport where active=1 AND approved_reject_flag=1 AND company_id=?1")
	public Integer getApprovedCount(Integer companyId);
	
	@Query("select count(*) from CreateExpenseReport where  active=1 AND approved_reject_flag=0 AND company_id=?1")
	public Integer getUnapprovedCount(Integer companyId);
	
	@Query("select count(*) from CreateExpenseReport where  active=0 AND approved_reject_flag=0 AND company_id=?1")
	public Integer getPendingCount(Integer companyId);
	
	
	@Query(nativeQuery = true, value =" select * from create_expense_report where employee_id= ?1 AND currentdate=?2 ")
	public List<CreateExpenseReport> getListByUserNameANDCurrentdate(Integer employeeId, String currentdate);
	
	@Query(nativeQuery = true, value =" select * from create_expense_report where employee_id= ?1")
	public List<CreateExpenseReport> getListByUserName(Integer employeeId);
	
	@Query(nativeQuery=true , value="select * from create_expense_report  where employee_id= ?1")
	public CreateExpenseReport getUsername(Integer employeeId);
}
