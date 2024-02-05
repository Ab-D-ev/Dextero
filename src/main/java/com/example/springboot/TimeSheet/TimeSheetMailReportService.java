package com.example.springboot.TimeSheet;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TimeSheetMailReportService {

	@Autowired
	private TimeSheetMailReportRepo repo;
	
	public List<TimeSheetMailReport> listAll() {
		return repo.findAll();
	}
	
	public void save(TimeSheetMailReport report) {
		repo.save(report);	
	}
	
	public List<TimeSheetMailReport> getMonthData(){
		return repo.getMonthData();
	}
	public List<TimeSheetMailReport> getListByEmployeeName(Integer employeeId){
		return repo.getListByEmployeeName(employeeId);
	}
	
	public List<TimeSheetMailReport> getMonthDataGroupByDate(){
		return repo.getMonthDataGroupByDate();
	}

	public int CountByEmployeeName(Integer employeeId) {
		return repo.CountByEmployeeName(employeeId);
	}
	
	public List<TimeSheetMailReport> getLimit1(){
		return repo.getLimit1();
	}
	
	public List<TimeSheetMailReport> getUserListLimit1(Integer employeeId){
		return repo.getUserListLimit1(employeeId);
	}
	
	public List<TimeSheetMailReport> getUserMonthDataGroupByDate(Integer employeeId){
		return repo.getUserMonthDataGroupByDate(employeeId);
	}
	
	public List<Integer> getEmployeeIdByCompletionStatus(){
		return repo.getEmployeeIdByCompletionStatus();
	}
	
}
