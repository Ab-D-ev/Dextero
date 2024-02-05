package com.example.springboot.TimeSheet;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TimeSheetReport")
public class TimeSheetReport {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String workedhours;
	private String remaininghours;
	private String overtime;
	private Integer employee_id;
	private String startdate;
	private String starttime;
	private String endtime;
	private Integer hours;
	private Integer minutes;
	private String startDateYear;
	private Integer company_id;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getWorkedhours() {
		return workedhours;
	}
	public void setWorkedhours(String workedhours) {
		this.workedhours = workedhours;
	}
	public String getRemaininghours() {
		return remaininghours;
	}
	public void setRemaininghours(String remaininghours) {
		this.remaininghours = remaininghours;
	}
	public String getOvertime() {
		return overtime;
	}
	public void setOvertime(String overtime) {
		this.overtime = overtime;
	}
	
	public Integer getEmployee_id() {
		return employee_id;
	}
	public void setEmployee_id(Integer employee_id) {
		this.employee_id = employee_id;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public Integer getHours() {
		return hours;
	}
	public void setHours(Integer hours) {
		this.hours = hours;
	}
	public Integer getMinutes() {
		return minutes;
	}
	public void setMinutes(Integer minutes) {
		this.minutes = minutes;
	}
	public String getStartDateYear() {
		return startDateYear;
	}
	public void setStartDateYear(String startDateYear) {
		this.startDateYear = startDateYear;
	}
	public Integer getCompany_id() {
		return company_id;
	}
	public void setCompany_id(Integer company_id) {
		this.company_id = company_id;
	}
	
}
