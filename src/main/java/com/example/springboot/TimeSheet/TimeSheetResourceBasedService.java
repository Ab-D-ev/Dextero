package com.example.springboot.TimeSheet;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TimeSheetResourceBasedService {

	@Autowired
	private TimeSheetResourceBasedRepository repo;
	
	public List<TimeSheetResourceBased> listAll() {
	      return repo.findAll();
	 }
	 
	 public TimeSheetResourceBased get(int id) {
		  return repo.findById(id).get();
	 }
	 
	 public void SaveTimeSheetResourceBased(TimeSheetResourceBased time1) {
	      repo.save(time1);
	 }
	 
	 public void deleteTimeSheetResourceBased(int id) {
			repo.deleteById(id);
	 }
	 
	 public List<TimeSheetResourceBased> getTimeSheetListByCurrentDate(String currentdate){
		 return repo.getTimeSheetListByCurrentDate(currentdate);
	 }
	 
	 public List<TimeSheetResourceBased> getTimeSheetListByEmployeeID(Integer employeeId){
		 return repo.getTimeSheetListByEmployeeID(employeeId);
	 }
}
