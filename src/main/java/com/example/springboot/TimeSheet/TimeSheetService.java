package com.example.springboot.TimeSheet;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

@Service
@Transactional
public interface TimeSheetService  {
	
	List<TimeSheet> getAllTimeSheet() ;
	void saveTime(TimeSheet timesheet);
	
	TimeSheet getTimeSheetById(long id);
	void deleteTimeSheetById(long id);
//	public List<TimeSheet> FilterByCompanyName(String username, String companyname, String projectCode);
	public List<TimeSheet> getListByCurrentDate(String currentdate);
	public TimeSheet selectDate(Integer employeeId, String currentdate);
	public int getUserName(Integer employeeId, String currentdate);
	public List<TimeSheet> getCurrentdateData(Integer employeeId, String currentdate);
	public List<TimeSheet> getCurrentMonthData(String currentdate, Integer employeeId);
	public String selectRemainingDuration(String currentdate , Integer employeeId);
//	public List<TimeSheet> FilterByCompanyNameProjectCode(String companyname, String projectCode);
	public int CountByUserName(Integer employeeId, String startdate);
	public String getRemainingDuration(String startdate , Integer employeeId);
}
