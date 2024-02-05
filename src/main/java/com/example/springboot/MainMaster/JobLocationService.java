package com.example.springboot.MainMaster;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class JobLocationService {

	@Autowired
	private JobLocationRepo repo;
	
	public List<JobLocation> listAll() {
		return repo.findAll();
	}
	
	public JobLocation get(int id) {
		return repo.findById(id).get();
	}
	
	public void save(JobLocation location) {
		repo.save(location);	
	}
	
	public void delete(int id) {
		repo.deleteById(id);
	}
}
