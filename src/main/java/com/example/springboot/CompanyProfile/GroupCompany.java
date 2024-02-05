package com.example.springboot.CompanyProfile;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="group_company")
public class GroupCompany {
	
	@Column(name="COMPANY_ID")
	public Integer groupcompany_id;
	
	@Column(name="COMPANY_NAME")
	public String company_name;
	
	@Column(name="EMAIL_ID")
	public String email_id;
	
	@Column(name="PAN_NUMBER")
	public String pan_number;
	
	@Column(name="GSTN_NUMBER")
	public String gstn_number;
	
	@Column(name="ADDRESS_LINE1")
	public String address_line1;
	
	@Column(name="ADDRESS_LINE2")
	public String address_line2; 
	
	@Column(name="PINCODE")
	public String pincode;
	
	@Column(name="CITY")
	public String city;
	
	@Column(name="STATE")
	public String state;
	
	@Column(name="COUNTRY")
	public String country;
    
    @Column(name="TOTAL_NUMBER_OF_EMPLOYEE")
    public Integer nemployee;
    public String companyprefix;
    public boolean status;

    
    @Id
   	@GeneratedValue(strategy=GenerationType.IDENTITY)
    public Integer getGroupcompany_id() {
		return groupcompany_id;
	}

	public void setGroupcompany_id(Integer groupcompany_id) {
		this.groupcompany_id = groupcompany_id;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getEmail_id() {
		return email_id;
	}

	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}

	public String getPan_number() {
		return pan_number;
	}

	public void setPan_number(String pan_number) {
		this.pan_number = pan_number;
	}

	public String getGstn_number() {
		return gstn_number;
	}

	public void setGstn_number(String gstn_number) {
		this.gstn_number = gstn_number;
	}

	public String getAddress_line1() {
		return address_line1;
	}

	public void setAddress_line1(String address_line1) {
		this.address_line1 = address_line1;
	}

	public String getAddress_line2() {
		return address_line2;
	}

	public void setAddress_line2(String address_line2) {
		this.address_line2 = address_line2;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
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

	public Integer getNemployee() {
		return nemployee;
	}

	public void setNemployee(Integer nemployee) {
		this.nemployee = nemployee;
	}

	public String getCompanyprefix() {
		return companyprefix;
	}

	public void setCompanyprefix(String companyprefix) {
		this.companyprefix = companyprefix;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	

}
