package com.example.springboot.WorkSchedule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkScheduleService {

	@Autowired
	private WorkScheduleRepo repo;
	
	public List<WorkSchedule> listAll() {
	      return repo.findAll();
	 }
	 
	 public WorkSchedule get(int id) {
		  return repo.findById(id).get();
	 }
	 
	 public void SaveWorkSchedule(WorkSchedule schedule) {
	      repo.save(schedule);
	 }
	 
	 public void delete(int id) {
			repo.deleteById(id);
	 }
}
