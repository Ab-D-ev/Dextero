package com.example.springboot.EmployeesDetails;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="employee_address_details")
public class EmployeeAddressDetails {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer employee_id;
	private String PresentAddress1;
	private String PresentAddress2;
	private String city;
	private String state;
	private String country;
	private String pinCode;
	private Integer company_id;
	
	
	public Integer getEmployee_id() {
		return employee_id;
	}
	public void setEmployee_id(Integer employee_id) {
		this.employee_id = employee_id;
	}
	public String getPresentAddress1() {
		return PresentAddress1;
	}
	public void setPresentAddress1(String presentAddress1) {
		PresentAddress1 = presentAddress1;
	}
	public String getPresentAddress2() {
		return PresentAddress2;
	}
	public void setPresentAddress2(String presentAddress2) {
		PresentAddress2 = presentAddress2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getPinCode() {
		return pinCode;
	}
	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}
	public Integer getCompany_id() {
		return company_id;
	}
	public void setCompany_id(Integer company_id) {
		this.company_id = company_id;
	}
	
	
}
