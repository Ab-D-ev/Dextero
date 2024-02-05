package com.example.springboot.TimeSheet;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TimeSheetReportShiftedTableRepo extends JpaRepository<TimeSheetReportShiftedTable, Integer>{
	
	
	//	@Query(nativeQuery = true, value = "SELECT * "
	//	+ "FROM time_sheet_report "
	//	+ "WHERE MONTH (STR_TO_DATE(startdate,'%d-%m-%Y')) = ?1 AND  MONTH (STR_TO_DATE(enddate,'%d-%m-%Y')) = ?1 AND username=?2")
	//public TimeSheetReport getCurrentMonthData(String currentdate, String username);
	
	@Query("select c from TimeSheetReportShiftedTable c where c.employee_id LIKE ?1%")
	public List<TimeSheetReportShiftedTable> FilterByUserName(Integer employeeId);
	
	//@Query("SELECT t FROM TimeSheetReport t WHERE t.startdate BETWEEN ?1 AND ?1  OR t.enddate BETWEEN  ?2 AND ?2 ")
	//public List<TimeSheetReport> FilterByDates(String startdate, String enddate);
	
	@Query(nativeQuery = true, value ="select * from time_sheet_report_data_shifted_table where employee_id=?1 AND startdate=?2")
	public TimeSheetReportShiftedTable getusernameAndstartDate(Integer employeeId ,String startdate);
	
	//@Query(nativeQuery = true, value ="select * from time_sheet_report WHERE yeardate1 >= DATE_ADD(CURRENT_DATE, INTERVAL DAYOFMONTH(CURRENT_DATE)- 30 DAY) AND username=?1  ORDER BY startdate ASC ")
	//public List<TimeSheetReport> getChartData(String username);
	//
	
	@Query(nativeQuery = true, value =" select * from time_sheet_report_data_shifted_table where employee_id=?1 ")
	public List<TimeSheetReportShiftedTable> countTotalWorkingDays(Integer employeeId);


	@Query("SELECT COUNT(t) FROM TimeSheetReportShiftedTable t WHERE t.id = :id")
    public Integer getExistingId(@Param("id") Integer id);
	
}
