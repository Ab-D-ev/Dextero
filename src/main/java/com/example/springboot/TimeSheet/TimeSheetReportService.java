package com.example.springboot.TimeSheet;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TimeSheetReportService {

	@Autowired
	private TimeSheetReportRepository repo;
	
	public List<TimeSheetReport> listAll() {
	      return repo.findAll();
	 }
	 
	 public TimeSheetReport get(int id) {
		  return repo.findById(id).get();
	 }
	 
	 public void SaveTimeSheetReport(TimeSheetReport tReport) {
	      repo.save(tReport);
	 }
	 
	 public void deleteTimeSheetReport(int id) {
			repo.deleteById(id);
	 }
//	 public TimeSheetReport getCurrentMonthData(String currentdate, String username) {
//		 return repo.getCurrentMonthData(currentdate, username);
//	 }
	 
	 public List<TimeSheetReport> FilterByUserName(Integer employeeId){
		 return repo.FilterByUserName(employeeId);
	 }
//	 public List<TimeSheetReport> FilterByDates(String startdate, String enddate){
//		 return repo.FilterByDates(startdate, enddate);
//	 }
	 public TimeSheetReport getusernameAndstartDate(Integer employeeId,String startdate) {
		 return repo.getusernameAndstartDate(employeeId, startdate);
	 }
	 
//	 public List<TimeSheetReport> getChartData(String username){
//		 return repo.getChartData(username);
//	 }
	 
	 public List<TimeSheetReport> countTotalWorkingDays(Integer employeeId){
		 return repo.countTotalWorkingDays(employeeId);
	 }
	 
}
