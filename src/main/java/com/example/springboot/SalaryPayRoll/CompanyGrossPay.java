package com.example.springboot.SalaryPayRoll;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="company_gross_pay")
public class CompanyGrossPay {
	
	@Id
	private Integer employee_id;
	private String basic;
	private String da;
	private String hra;
	private String travel_allowance;
	private String special_allowance;
	private String project_allowance;
	private String project_travel;
	private String conveyance_allowance;
	private String medical_allowance;
	private String dearness_allowance;
	private String other_allowance;
	private Integer company_id;
	
	
	public Integer getEmployee_id() {
		return employee_id;
	}
	public void setEmployee_id(Integer employee_id) {
		this.employee_id = employee_id;
	}
	public String getBasic() {
		return basic;
	}
	public void setBasic(String basic) {
		this.basic = basic;
	}
	public String getDa() {
		return da;
	}
	public void setDa(String da) {
		this.da = da;
	}
	public String getHra() {
		return hra;
	}
	public void setHra(String hra) {
		this.hra = hra;
	}
	public String getTravel_allowance() {
		return travel_allowance;
	}
	public void setTravel_allowance(String travel_allowance) {
		this.travel_allowance = travel_allowance;
	}
	public String getSpecial_allowance() {
		return special_allowance;
	}
	public void setSpecial_allowance(String special_allowance) {
		this.special_allowance = special_allowance;
	}
	public String getProject_allowance() {
		return project_allowance;
	}
	public void setProject_allowance(String project_allowance) {
		this.project_allowance = project_allowance;
	}
	public String getProject_travel() {
		return project_travel;
	}
	public void setProject_travel(String project_travel) {
		this.project_travel = project_travel;
	}
	public String getConveyance_allowance() {
		return conveyance_allowance;
	}
	public void setConveyance_allowance(String conveyance_allowance) {
		this.conveyance_allowance = conveyance_allowance;
	}
	public String getMedical_allowance() {
		return medical_allowance;
	}
	public void setMedical_allowance(String medical_allowance) {
		this.medical_allowance = medical_allowance;
	}
	public String getDearness_allowance() {
		return dearness_allowance;
	}
	public void setDearness_allowance(String dearness_allowance) {
		this.dearness_allowance = dearness_allowance;
	}
	public String getOther_allowance() {
		return other_allowance;
	}
	public void setOther_allowance(String other_allowance) {
		this.other_allowance = other_allowance;
	}
	public Integer getCompany_id() {
		return company_id;
	}
	public void setCompany_id(Integer company_id) {
		this.company_id = company_id;
	}
		
}
