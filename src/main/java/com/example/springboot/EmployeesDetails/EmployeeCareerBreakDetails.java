package com.example.springboot.EmployeesDetails;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="employee_career_break_details")
public class EmployeeCareerBreakDetails {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String reasonOfBreak;
	private String breakStartFrom;
	private String breakEnd;
	private Integer company_id;
	private Integer employee_id;
	

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getReasonOfBreak() {
		return reasonOfBreak;
	}
	public void setReasonOfBreak(String reasonOfBreak) {
		this.reasonOfBreak = reasonOfBreak;
	}
	public String getBreakStartFrom() {
		return breakStartFrom;
	}
	public void setBreakStartFrom(String breakStartFrom) {
		this.breakStartFrom = breakStartFrom;
	}
	public String getBreakEnd() {
		return breakEnd;
	}
	public void setBreakEnd(String breakEnd) {
		this.breakEnd = breakEnd;
	}
	public Integer getCompany_id() {
		return company_id;
	}
	public void setCompany_id(Integer company_id) {
		this.company_id = company_id;
	}
	public Integer getEmployee_id() {
		return employee_id;
	}
	public void setEmployee_id(Integer employee_id) {
		this.employee_id = employee_id;
	}
	
}
