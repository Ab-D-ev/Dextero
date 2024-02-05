package com.example.springboot.Leave;

import java.time.Year;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;


@Service
public class LeaveServiceImpl implements LeaveService{
	
	@Autowired
	private LeaveRepositroy leaveRepository;
	

	@Override
	public List<Leave> getAllLeaves() {
		return leaveRepository.findAll();
	}

	@Override
	public void saveLeaves(Leave leave) {
		this.leaveRepository.save(leave);
		
	}

	@Override
	public Leave getLeavesById(long id) {
		java.util.Optional<Leave> optional = leaveRepository.findById(id);
		Leave leave=null;
		if(optional.isPresent()) {
			leave=optional.get();
		}else {
			throw new RuntimeException("id not found"+id);
		}
		return  leave;
		
	}

	@Override
	public void deleteLeavesById(long id) {
		this.leaveRepository.deleteById(id);
	}
	
	@Override
	 public List<Leave> findByEmployeeName(Integer employeeId){
		if (employeeId == null) {
			return leaveRepository.findAll();
            
        }
		return leaveRepository.findByEmployeeName(employeeId);
	}

	@Override
	public List<Leave> getLeaveListByOrder() {
		return leaveRepository.getLeaveListByOrder();
	}

	@Override
	public List<Leave> getLeaveListByOrder1() {
		return leaveRepository.getLeaveListByOrder1();
	}

	@Override
	public List<Leave> getListByUserNameANDCurrentdate(Integer employeeId, String currentdate) {
		return leaveRepository.getListByUserNameANDCurrentdate(employeeId, currentdate);
	}

	@Override
	public List<Leave> getListByUserName(Integer employeeId) {
		return leaveRepository.getListByUserName(employeeId);
	}

//	@Override
//	public int countNumberOfLeaves(String username) {
//		return leaveRepository.countNumberOfLeaves(username);
//	}

	@Override
	public Integer countNumberOfDays(Integer employeeId,String from_date, String to_date){
		return leaveRepository.countNumberOfDays(employeeId, from_date, to_date);
	}

	@Override
	public List<Leave> countNumberOfPaidOrSickLeave(Integer leavecategory,Integer employeeId){
		return leaveRepository.countNumberOfPaidOrSickLeave(leavecategory,employeeId);
	}

	@Override
	public List<Leave> getLeaveListByCurrentDate(String currentdate) {
		return leaveRepository.getLeaveListByCurrentDate(currentdate);
	}

	@Override
	public List<Leave> findLeaveHistoryByEmployeeIDAndYear(@Param("employeeId") Integer employeeId, @Param("year") int year){
		return leaveRepository.findLeaveHistoryByEmployeeIDAndYear(employeeId, year);
	}

	@Override
	public int getEmployeeLeaveMonthEmpID(@Param("month") int month, @Param("employeeId") Integer employeeId){
		return leaveRepository.getEmployeeLeaveMonthEmpID(month, employeeId);
	}

	@Override
	public Integer findLeaveEmployeeIDYear(@Param("year") int year, @Param("employeeId") Integer employeeId){
		return leaveRepository.findLeaveEmployeeIDYear(year, employeeId);
	}

}
