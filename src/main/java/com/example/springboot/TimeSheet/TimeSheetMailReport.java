package com.example.springboot.TimeSheet;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TimeSheetMailReport")
public class TimeSheetMailReport {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private Integer employee_id;
	private String sdate;
	private String completionstatus;
	private String dateyear;
	private Integer company_id;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getEmployee_id() {
		return employee_id;
	}
	public void setEmployee_id(Integer employee_id) {
		this.employee_id = employee_id;
	}
	public String getSdate() {
		return sdate;
	}
	public void setSdate(String sdate) {
		this.sdate = sdate;
	}
	public String getCompletionstatus() {
		return completionstatus;
	}
	public void setCompletionstatus(String completionstatus) {
		this.completionstatus = completionstatus;
	}
	public String getDateyear() {
		return dateyear;
	}
	public void setDateyear(String dateyear) {
		this.dateyear = dateyear;
	}
	public Integer getCompany_id() {
		return company_id;
	}
	public void setCompany_id(Integer company_id) {
		this.company_id = company_id;
	}
	
}
