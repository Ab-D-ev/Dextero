package com.example.springboot.TimeSheet;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TimeSheetServiceImpl implements TimeSheetService {
	
	@Autowired
	private TimeSheetRepository timesheetRepository;

	@Override
	public List<TimeSheet> getAllTimeSheet() {
		return timesheetRepository.findAll();
	}

	@Override
	public void saveTime(TimeSheet timesheet) {
		this.timesheetRepository.save(timesheet);
		
	}

	@Override
	public TimeSheet getTimeSheetById(long id) {
		java.util.Optional<TimeSheet> optional = timesheetRepository.findById(id);
		TimeSheet  timesheet=null;
		if(optional.isPresent()) {
			timesheet=optional.get();
		}else {
			throw new RuntimeException("id not found"+id);
		}
		return  timesheet;
	}

	@Override
	public void deleteTimeSheetById(long id) {
		this.timesheetRepository.deleteById(id);
		
		
	}

//	@Override
//	public List<TimeSheet> FilterByCompanyName(String username, String companyname, String projectCode) {
//		if (companyname == null && projectCode==null) {
//			return timesheetRepository.findAll();
//		}
//		return timesheetRepository.FilterByCompanyName(username,companyname,projectCode);
//		
//	}
	@Override
	public List<TimeSheet> getListByCurrentDate(String currentdate){
		return timesheetRepository.getListByCurrentDate(currentdate);
	}

	@Override
	public TimeSheet selectDate(Integer employeeId, String currentdate) {
		return timesheetRepository.selectDate(employeeId, currentdate);
	}

	@Override
	public int getUserName(Integer employeeId, String currentdate) {
		return timesheetRepository.getUserName(employeeId, currentdate);
	}

	@Override
	public List<TimeSheet> getCurrentdateData(Integer employeeId, String currentdate) {
		return timesheetRepository.getCurrentdateData(employeeId, currentdate);
	}

	@Override
	public List<TimeSheet> getCurrentMonthData(String currentdate, Integer employeeId) {
		return timesheetRepository.getCurrentMonthData(currentdate, employeeId);
	}

	@Override
	public String selectRemainingDuration(String currentdate , Integer employeeId){
		return timesheetRepository.selectRemainingDuration(currentdate, employeeId);
	}

//	@Override
//	public List<TimeSheet> FilterByCompanyNameProjectCode(String companyname, String projectCode) {
//		return timesheetRepository.FilterByCompanyNameProjectCode(companyname, projectCode);
//	}

	@Override
	public int CountByUserName(Integer employeeId, String startdate) {
		return timesheetRepository.CountByUserName(employeeId, startdate);
	}

	@Override
	public String getRemainingDuration(String startdate, Integer employeeId) {
		return timesheetRepository.getRemainingDuration(startdate, employeeId);
	}

}
