package com.example.springboot.CompanyProfile;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="company_details")
public class CompanyDetails {

	@Column(name="COMPANY_ID")
	public Integer company_id;
	
	@Column(name="COMPANY_NAME")
	public String company_name;
	
	@Column(name="EMAIL_ID")
	public String email_id;
	
//	@Pattern(regexp="[A-Z]{5}[0-9]{4}[A-Z]{1}",message="The last digit should be a capital letter")
//	@Pattern(regexp="[A-Z]{5}[0-9]{4}[A-Z]{1}",message="The next four should be a number")
//	@Pattern(regexp="[A-Z]{5}[0-9]{4}[A-Z]{1}",message="The first five characters should be capital letters")
//	@Size(min=10,max=10  ,message = "It should be exact 10 characters.")
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
    
    @Column(name="NUMBER_OF_EMPLOYEE")
    public Integer nemployee;
    
    @Column(name="GROUP_ID")
    public Integer group_id;
    public String companyprefix;
    public boolean status;
    

    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getCompany_id() {
		return company_id;
	}

	public void setCompany_id(Integer company_id) {
		this.company_id = company_id;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
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

	public String getEmail_id() {
		return email_id;
	}

	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}

	public Integer getNemployee() {
		return nemployee;
	}

	public void setNemployee(Integer nemployee) {
		this.nemployee = nemployee;
	}

	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getGroup_id() {
		return group_id;
	}

	public void setGroup_id(Integer group_id) {
		this.group_id = group_id;
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
