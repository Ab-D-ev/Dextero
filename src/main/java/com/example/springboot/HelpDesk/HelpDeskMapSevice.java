package com.example.springboot.HelpDesk;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class HelpDeskMapSevice {
	
	@Autowired
	private HelpDeskMapRepo repo;
	
	public List<HelpdeskMap> listAll() {
	      return repo.findAll();
	 }
	 
	 public HelpdeskMap get(int id) {
		  return repo.findById(id).get();
	 }
	 
	 public void save(HelpdeskMap helpdesk) {
	      repo.save(helpdesk);
	 }
	 
	 public void delete(int id) {
			repo.deleteById(id);
	 }

	 public void insertDataIntoHelpDeskMap(Integer helpdesk_id, Integer employee_id) {
		 repo.insertDataIntoHelpDeskMap(helpdesk_id, employee_id);
	 }
}
