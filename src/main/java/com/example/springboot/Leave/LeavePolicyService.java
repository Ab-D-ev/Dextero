package com.example.springboot.Leave;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

@Service
public class LeavePolicyService {

	@Autowired
	private LeavePolicyRepo repo;
	
	public List<LeavePolicy> listAll() {
	      return repo.findAll();
	 }
	 
	 public LeavePolicy get(int id) {
		  return repo.findById(id).get();
	 }
	 
	 public void Save(LeavePolicy leave) {
	      repo.save(leave);
	 }
	 
	 public void delete(int id) {
			repo.deleteById(id);
	 }
	
	 public LeavePolicy findLeavePolicyByYearAndCompanyId(@Param("year") int year, @Param("companyId") int companyId) {
		 return repo.findLeavePolicyByYearAndCompanyId(year, companyId);
	 }
	 
	 public Integer findLeaveCarriedForward(Integer company_id, Integer year) {
		 return repo.findLeaveCarriedForward(company_id, year);
	 }
	 
	 public int findMonthlyPaidLeaveCount(Integer company_id, Integer year) {
		 return repo.findMonthlyPaidLeaveCount(company_id, year);
	 }
}
