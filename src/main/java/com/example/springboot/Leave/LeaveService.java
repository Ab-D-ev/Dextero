package com.example.springboot.Leave;

import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

@Service
public interface LeaveService {
	
	List<Leave> getAllLeaves() ;
	void saveLeaves(Leave leave);
	
	Leave getLeavesById(long id);
	void deleteLeavesById(long id);
	public List<Leave> findByEmployeeName(Integer employeeId);
	public List<Leave> getLeaveListByOrder();
	public List<Leave> getLeaveListByOrder1();
	public List<Leave> getListByUserNameANDCurrentdate(Integer employeeId, String currentdate);
	public List<Leave> getListByUserName(Integer employeeId);
//	public int countNumberOfLeaves(String username);
	public Integer countNumberOfDays(Integer employeeId,String from_date, String to_date);
	
	public List<Leave> countNumberOfPaidOrSickLeave(Integer leavecategory,Integer employeeId);
	public List<Leave> getLeaveListByCurrentDate(String currentdate);
	public List<Leave> findLeaveHistoryByEmployeeIDAndYear(@Param("employeeId") Integer employeeId, @Param("year") int year);
	public int getEmployeeLeaveMonthEmpID(@Param("month") int month, @Param("employeeId") Integer employeeId);
	public Integer findLeaveEmployeeIDYear(@Param("year") int year, @Param("employeeId") Integer employeeId);
}
