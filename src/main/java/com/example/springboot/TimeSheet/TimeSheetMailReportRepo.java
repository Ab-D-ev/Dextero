package com.example.springboot.TimeSheet;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface TimeSheetMailReportRepo extends JpaRepository< TimeSheetMailReport , Integer> {

	@Query(nativeQuery=true , 
			value= "SELECT * from time_sheet_mail_report"
					+ " WHERE dateyear >= DATE_SUB(CURRENT_DATE, INTERVAL DAYOFMONTH(CURRENT_DATE)-1 DAY) ORDER BY sdate ASC")
		public List<TimeSheetMailReport> getMonthData();
	
//	@Query(nativeQuery = true, value = "SELECT id,company_id,dateyear, sdate, completionstatus,employee_id "
//	        + "FROM time_sheet_mail_report "
//	        + "WHERE (completionstatus = 'Not Filled' OR completionstatus = 'Partial Filled') "
//	        + "AND dateyear BETWEEN DATE_SUB(CURRENT_DATE, INTERVAL DAYOFYEAR(CURRENT_DATE)-1 DAY) AND CURRENT_DATE "
//	        + "GROUP BY sdate, completionstatus "
//	        + "ORDER BY sdate DESC")
//	@Query(nativeQuery = true, value = "SELECT id, company_id, dateyear, sdate, completionstatus from time_sheet_mail_report "
//			+ "where (completionstatus = 'Not Filled' OR completionstatus = 'Partial Filled') AND "
//			+ "dateyear between DATE_SUB(CURRENT_DATE, INTERVAL DAYOFYEAR(CURRENT_DATE)-1 DAY) "
//			+ "AND CURRENT_DATE GROUP BY id, company_id, dateyear, sdate, completionstatus ORDER BY id DESC")
	@Query(nativeQuery = true, value = "SELECT id,company_id, sdate, completionstatus from time_sheet_mail_report "
			+ "where (completionstatus = 'Not Filled' OR completionstatus = 'Partial Filled') AND "
			+ "dateyear between DATE_SUB(CURRENT_DATE, INTERVAL DAYOFYEAR(CURRENT_DATE)-1 DAY) "
			+ "AND CURRENT_DATE GROUP BY id,company_id, sdate, completionstatus ORDER BY sdate DESC")
	public List<TimeSheetMailReport> getMonthDataGroupByDate();

	
	@Query("select c from TimeSheetMailReport c where c.employee_id=?1")
	public List<TimeSheetMailReport> getListByEmployeeName(Integer employeeId);
	
	@Query("select count(*) from TimeSheetMailReport c where c.employee_id=?1")
	public int CountByEmployeeName(Integer employeeId);
	
//	@Query(value = "SELECT id, company_id, dateyear, sdate, completionstatus,  GROUP_CONCAT(CAST(employee_id AS CHAR) SEPARATOR ', ') AS employee_id from time_sheet_mail_report "
//			+ "where (completionstatus = 'Not Filled' OR completionstatus = 'Partial Filled') AND dateyear between DATE_SUB(CURRENT_DATE, INTERVAL DAYOFMONTH(CURRENT_DATE)-1 DAY) AND CURRENT_DATE "
//			+ "GROUP BY sdate, completionstatus ORDER BY sdate DESC limit 1", nativeQuery = true)
//	@Query(value = "SELECT id, company_id, dateyear, sdate, completionstatus, employee_id from time_sheet_mail_report "
//			+ "where (completionstatus = 'Not Filled' OR completionstatus = 'Partial Filled') AND dateyear "
//			+ "between DATE_SUB(CURRENT_DATE, INTERVAL DAYOFMONTH(CURRENT_DATE)-1 DAY) AND CURRENT_DATE ", nativeQuery = true)
	@Query(value = "SELECT id, company_id, dateyear, sdate, completionstatus from time_sheet_mail_report "
			+ "where (completionstatus = 'Not Filled' OR completionstatus = 'Partial Filled') AND "
			+ "dateyear between DATE_SUB(CURRENT_DATE, INTERVAL DAYOFMONTH(CURRENT_DATE)-1 DAY) AND CURRENT_DATE "
			+ "GROUP BY id, company_id, dateyear, sdate, completionstatus ORDER BY id DESC limit 1", nativeQuery = true)
	public List<TimeSheetMailReport> getLimit1();
	
	@Query(nativeQuery = true, value = "SELECT id,company_id,dateyear, sdate, completionstatus, employee_id from  time_sheet_mail_report "
			+ "WHERE  dateyear >= DATE_SUB(CURRENT_DATE, INTERVAL DAYOFMONTH(CURRENT_DATE)-1 DAY) AND employee_id=?1 ORDER BY sdate DESC Limit 1")
	public List<TimeSheetMailReport> getUserListLimit1(Integer employeeId);
	
	@Query(nativeQuery = true, value = "SELECT id,company_id,dateyear, sdate, completionstatus, employee_id from  time_sheet_mail_report "
			+ "	WHERE  dateyear >= DATE_SUB(CURRENT_DATE, INTERVAL DAYOFYEAR(CURRENT_DATE)-1 DAY) AND employee_id=?1 ORDER BY sdate ASC")
	public List<TimeSheetMailReport> getUserMonthDataGroupByDate(Integer employeeId);
	
	@Query(nativeQuery = true, value = "SELECT employee_id "
	        + "FROM time_sheet_mail_report "
	        + "WHERE completionstatus = 'Not Filled' OR completionstatus = 'Partial Filled'")
	public List<Integer> getEmployeeIdByCompletionStatus();
}
