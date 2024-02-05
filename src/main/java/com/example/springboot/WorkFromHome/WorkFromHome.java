package com.example.springboot.WorkFromHome;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="work_from_home")
public class WorkFromHome {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String date_from;
	private String date_to;
	@Column(length = 2000)
	private String reason;
    private boolean accept_reject_flag;
    private boolean active;
	private Integer employee_id;
	private String current_date;
	private String to_date_year;
	private String from_date_year;
	private Integer n_days;
	private Integer company_id;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDate_from() {
		return date_from;
	}
	public void setDate_from(String date_from) {
		this.date_from = date_from;
	}
	public String getDate_to() {
		return date_to;
	}
	public void setDate_to(String date_to) {
		this.date_to = date_to;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public boolean isAccept_reject_flag() {
		return accept_reject_flag;
	}
	public void setAccept_reject_flag(boolean accept_reject_flag) {
		this.accept_reject_flag = accept_reject_flag;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public Integer getEmployee_id() {
		return employee_id;
	}
	public void setEmployee_id(Integer employee_id) {
		this.employee_id = employee_id;
	}
	public String getCurrent_date() {
		return current_date;
	}
	public void setCurrent_date(String current_date) {
		this.current_date = current_date;
	}
	public String getTo_date_year() {
		return to_date_year;
	}
	public void setTo_date_year(String to_date_year) {
		this.to_date_year = to_date_year;
	}
	public String getFrom_date_year() {
		return from_date_year;
	}
	public void setFrom_date_year(String from_date_year) {
		this.from_date_year = from_date_year;
	}
	public int getN_days() {
		return n_days;
	}
	public void setN_days(int n_days) {
		this.n_days = n_days;
	}
	public Integer getCompany_id() {
		return company_id;
	}
	public void setCompany_id(Integer company_id) {
		this.company_id = company_id;
	}
	

}
