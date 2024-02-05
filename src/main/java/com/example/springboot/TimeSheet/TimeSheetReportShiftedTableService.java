package com.example.springboot.TimeSheet;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

@Service
public class TimeSheetReportShiftedTableService {

	@Autowired
	private TimeSheetReportShiftedTableRepo repo;
	
	public List<TimeSheetReportShiftedTable> listAll() {
	      return repo.findAll();
	 }
	 
	 public TimeSheetReportShiftedTable get(int id) {
		  return repo.findById(id).get();
	 }
	 
	 public void Save(TimeSheetReportShiftedTable tReport) {
	      repo.save(tReport);
	 }
	 
	 public void deleteTimeSheetReportShiftedTable(int id) {
			repo.deleteById(id);
	 }
	 
	 public List<TimeSheetReportShiftedTable> FilterByUserName(Integer employeeId){
		 return repo.FilterByUserName(employeeId);
	 }
	 
	 public TimeSheetReportShiftedTable getusernameAndstartDate(Integer employeeId ,String startdate) {
		 return repo.getusernameAndstartDate(employeeId, startdate);
	 }
	 
	 public List<TimeSheetReportShiftedTable> countTotalWorkingDays(Integer employeeId){
		 return repo.countTotalWorkingDays(employeeId);
	 }
	 
	 public Integer getExistingId(@Param("id") Integer id) {
		 return repo.getExistingId(id);
	 }
}
