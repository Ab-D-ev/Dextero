package com.example.springboot.MainMaster;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class IndustryService {
	
	@Autowired
	private IndustryRepo repo;
	
	public List<Industry> listAll() {
		return repo.findAll();
	}
	
	public Industry get(int id) {
		return repo.findById(id).get();
	}
	
	public void save(Industry industry) {
		repo.save(industry);	
	}
	
	public void delete(int id) {
		repo.deleteById(id);
	}
	
}
