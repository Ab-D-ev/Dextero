package com.example.springboot.HelpDesk;

import java.util.List;

import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

@Service
@Transactional
public class HelpDeskServiceImpl {
	
	@Autowired
	private HelpDeskRepository repo;
	
	 public List<HelpDesk> listAll() {
	      return repo.findAll();
	 }
	 
	 public HelpDesk get(int id) {
		  return repo.findById(id).get();
	 }
	 
	 public void save(HelpDesk helpdesk) {
	      repo.save(helpdesk);
	 }
	 
	 public void deleteHelpDesk(int id) {
			repo.deleteById(id);
	 }
	 public List<HelpDesk> getPriorityLevel(Integer companyId, Integer projectcode){
		 return repo.getPriorityLevel(companyId, projectcode);
	 }

	 public List<HelpDesk> getListByEmployeeName(Integer employee_id){
		 return repo.getListByEmployeeName(employee_id);
	 }
}
