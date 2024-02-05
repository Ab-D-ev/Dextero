package com.example.springboot.Leave;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="leave_balance")
public class LeaveBalance {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
    private int carried_forward_leave;
    private Integer employee_id;
    private Integer company_id;
    private String currentdate;
    private String current_date_year;
    
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public int getCarried_forward_leave() {
		return carried_forward_leave;
	}
	public void setCarried_forward_leave(int carried_forward_leave) {
		this.carried_forward_leave = carried_forward_leave;
	}
	public Integer getEmployee_id() {
		return employee_id;
	}
	public void setEmployee_id(Integer employee_id) {
		this.employee_id = employee_id;
	}
	public Integer getCompany_id() {
		return company_id;
	}
	public void setCompany_id(Integer company_id) {
		this.company_id = company_id;
	}
	
	public String getCurrentdate() {
		return currentdate;
	}
	public void setCurrentdate(String currentdate) {
		this.currentdate = currentdate;
	}
	public String getCurrent_date_year() {
		return current_date_year;
	}
	public void setCurrent_date_year(String current_date_year) {
		this.current_date_year = current_date_year;
	}
	
	
}
