package com.example.springboot.TimeSheet;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface TimeSheetReportRepository extends  JpaRepository<TimeSheetReport,Integer>{

//	@Query(nativeQuery = true, value = "SELECT * "
//			+ "FROM time_sheet_report "
//			+ "WHERE MONTH (STR_TO_DATE(startdate,'%d-%m-%Y')) = ?1 AND  MONTH (STR_TO_DATE(enddate,'%d-%m-%Y')) = ?1 AND username=?2")
//	public TimeSheetReport getCurrentMonthData(String currentdate, String username);
	
	@Query("select c from TimeSheetReport c where c.employee_id LIKE ?1%")
	public List<TimeSheetReport> FilterByUserName(Integer employeeId);
	
//	@Query("SELECT t FROM TimeSheetReport t WHERE t.startdate BETWEEN ?1 AND ?1  OR t.enddate BETWEEN  ?2 AND ?2 ")
//	public List<TimeSheetReport> FilterByDates(String startdate, String enddate);
	
	@Query(nativeQuery = true, value ="select * from time_sheet_report where employee_id=?1 AND startdate=?2")
	public TimeSheetReport getusernameAndstartDate(Integer employeeId ,String startdate);
	
//	@Query(nativeQuery = true, value ="select * from time_sheet_report WHERE yeardate1 >= DATE_ADD(CURRENT_DATE, INTERVAL DAYOFMONTH(CURRENT_DATE)- 30 DAY) AND username=?1  ORDER BY startdate ASC ")
//	public List<TimeSheetReport> getChartData(String username);
//	
	
	@Query(nativeQuery = true, value =" select * from time_sheet_report where employee_id=?1 ")
	public List<TimeSheetReport> countTotalWorkingDays(Integer employeeId);
	
	
//	@Modifying
//    @Transactional
//    @Query(value = "INSERT INTO time_sheet_report_data_shifted_table (workedhours, remaininghours, overtime, employee_id, startdate, starttime, endtime, hours, minutes, start_date_year, company_id) " +
//            "SELECT workedhours, remaininghours, overtime, employee_id, startdate, starttime, endtime, hours, minutes, start_date_year, company_id " +
//            "FROM time_sheet_report " +
//            "WHERE STR_TO_DATE(start_date_year, '%Y-%m-%d') >= CURRENT_DATE - INTERVAL 7 DAY", nativeQuery = true)
//    public void moveLast7DaysDataToReportShiftedTable();
//
//    @Modifying
//    @Transactional
//    @Query(value = "DELETE FROM time_sheet_report WHERE STR_TO_DATE(start_date_year, '%Y-%m-%d') >= CURRENT_DATE - INTERVAL 7 DAY", nativeQuery = true)
//    public void deleteLast7DaysDataFromReport();
	
}
