package com.example.springboot.WorkFromHome;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springboot.WorkSchedule.WorkSchedule;
import com.example.springboot.WorkSchedule.WorkScheduleRepo;

@Service
public class WorkFromHomeService {

	@Autowired
	private WorkFromHomeRepo repo;

	
	public List<WorkFromHome> listAll() {
	      return repo.findAll();
	 }
	 
	 public WorkFromHome get(int id) {
		  return repo.findById(id).get();
	 }
	 
	 public void SaveWorkFromHome(WorkFromHome schedule) {
	      repo.save(schedule);
	 }
	 
	 public void delete(int id) {
			repo.deleteById(id);
	 }
}
