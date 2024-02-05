package com.example.springboot.Leave;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveRepositroy extends JpaRepository<Leave,Long>{
	
//    @Query(nativeQuery = true, value = "select * from leave_manage where employeeName=? order by id desc")
//    public List<Leave> getAllLeavesOfUser(String employeeName);

    @Query(nativeQuery = true, value = "SELECT * from  leave_manage "
    		+ "WHERE from_date_year >= DATE_ADD(CURRENT_DATE, INTERVAL DAYOFMONTH(CURRENT_DATE)- 30 DAY) "
    		+ "AND accept_reject_flag=1 AND active=1 AND to_date_year >= CURRENT_DATE ORDER BY date_from ASC limit 1")
	   public List<Leave> getLeaveListByOrder();
    
	@Query("select e from Leave e where e.employee_id LIKE %?1%")
		public List<Leave> findByEmployeeName(Integer employeeId);
	    
	@Query(nativeQuery = true, value = "SELECT * from  leave_manage "
			+ "WHERE from_date_year >= DATE_ADD(CURRENT_DATE, INTERVAL DAYOFMONTH(CURRENT_DATE)- 30 DAY) "
			+ "AND accept_reject_flag=1 AND active=1 AND to_date_year >= CURRENT_DATE ORDER BY date_from ASC")
	   public List<Leave> getLeaveListByOrder1();
	
	@Query(nativeQuery = true, value =" select * from leave_manage where employee_id= ?1 AND currentdate=?2 ")
	public List<Leave> getListByUserNameANDCurrentdate(Integer employeeId, String currentdate);
	
	@Query(nativeQuery = true, value =" select * from leave_manage where employee_id= ?1")
	public List<Leave> getListByUserName(Integer employeeId);
	
//	@Query(nativeQuery = true, value =" select count(*) from leave_manage where leavecategory='Unpaid Leave' AND username=?1 ")
//	public int countNumberOfLeaves(String username);
	
	@Query(nativeQuery = true, value =" select datediff(to_date_year, from_date_year) + 1 AS n_days FROM leave_manage where employee_id=?1 AND "
			+ "from_date_year=?2 AND to_date_year=?3 ")
	public Integer countNumberOfDays(Integer employeeId,String from_date, String to_date);
	
	
	@Query(nativeQuery = true, value =" SELECT * from leave_manage WHERE leavecategory_id=?1 AND employee_id=?2 AND "
			+ "(leavecategory_id=?1 AND employee_id=?2) OR (leavecategory_id=?1 AND employee_id=?2)AND "
			+ "accept_reject_flag=1 AND active=1")
	public List<Leave> countNumberOfPaidOrSickLeave(Integer leavecategory,Integer employeeId);
	
	@Query(nativeQuery = true, value = "select * from leave_manage where currentdate=?1")
	public List<Leave> getLeaveListByCurrentDate(String currentdate);
	
	@Query("SELECT COUNT(l) FROM Leave l WHERE l.employee_id = :employee_id")
    public int countLeavesByEmployee(@Param("employee_id")Integer employeeId);
	
	@Query("SELECT lh FROM Leave lh WHERE lh.employee_id = :employeeId AND EXTRACT(YEAR FROM lh.fromDateYear) = :year")
    public List<Leave> findLeaveHistoryByEmployeeIDAndYear(@Param("employeeId") Integer employeeId, @Param("year") int year);

	
	@Query(nativeQuery = true, value = "select count(*) from leave_manage where  accept_reject_flag = 1 AND active = 1 AND type='Paid Leave'"
			+ " AND EXTRACT(MONTH FROM from_date_year) =:month  AND employee_id =:employeeId")
	public int getEmployeeLeaveMonthEmpID(@Param("month") int month, @Param("employeeId") Integer employeeId);
	
	@Query(nativeQuery = true, value = "select count(*) from leave_manage where  accept_reject_flag = 1 AND active = 1 AND type='Paid Leave' "
			+ " AND n_days = 1 AND EXTRACT(Year FROM from_date_year) =:year  AND employee_id =:employeeId")
	public Integer findLeaveEmployeeIDYear(@Param("year") int year, @Param("employeeId") Integer employeeId);
	
	
}
