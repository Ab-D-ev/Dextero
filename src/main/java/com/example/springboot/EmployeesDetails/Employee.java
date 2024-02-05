package com.example.springboot.EmployeesDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="employees")
public class Employee {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String firstname;
	private String middlename;
	private String lastname;
	@Column(unique=true,length=200)
	private String email;
	private String gender; 
	private Integer age;
	private String DateofBirth;
	private String MobileNumber1;
	private String MobileNumber2;
	private String TelephoneNumber1;
	private String TelephoneNumber2;
	private String joiningdate;
	private String bloodgroup;
	private String mcode;
	private String tcode;
	private String birthyear;
	private String joiningdateYear;
	private String maritalStatus;
	private String disability;
	private String disabilityType;
	private Integer group_id;
	private Integer company_id;
	private Integer authority_id;
	private String department;
	private Integer reporting_manager;
	private String prefix;
	private String sufix;
	private Integer designation_id;
	private boolean status;
	private boolean email_status;
	
	
	public Integer getGroup_id() {
		return group_id;
	}
	public void setGroup_id(Integer group_id) {
		this.group_id = group_id;
	}
	public Integer getCompany_id() {
		return company_id;
	}
	public void setCompany_id(Integer company_id) {
		this.company_id = company_id;
	}
	public Integer getAuthority_id() {
		return authority_id;
	}
	public void setAuthority_id(Integer authority_id) {
		this.authority_id = authority_id;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getMiddlename() {
		return middlename;
	}
	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getDateofBirth() {
		return DateofBirth;
	}
	public void setDateofBirth(String dateofBirth) {
		DateofBirth = dateofBirth;
	}
	public String getMobileNumber1() {
		return MobileNumber1;
	}
	public void setMobileNumber1(String mobileNumber1) {
		MobileNumber1 = mobileNumber1;
	}
	public String getMobileNumber2() {
		return MobileNumber2;
	}
	public void setMobileNumber2(String mobileNumber2) {
		MobileNumber2 = mobileNumber2;
	}
	
	public String getTelephoneNumber1() {
		return TelephoneNumber1;
	}
	public void setTelephoneNumber1(String telephoneNumber1) {
		TelephoneNumber1 = telephoneNumber1;
	}
	public String getTelephoneNumber2() {
		return TelephoneNumber2;
	}
	public void setTelephoneNumber2(String telephoneNumber2) {
		TelephoneNumber2 = telephoneNumber2;
	}
	public String getMcode() {
		return mcode;
	}
	public void setMcode(String mcode) {
		this.mcode = mcode;
	}
	public String getTcode() {
		return tcode;
	}
	public void setTcode(String tcode) {
		this.tcode = tcode;
	}
	public String getBirthyear() {
		return birthyear;
	}
	public void setBirthyear(String birthyear) {
		this.birthyear = birthyear;
	}
	public String getJoiningdateYear() {
		return joiningdateYear;
	}
	public void setJoiningdateYear(String joiningdateYear) {
		this.joiningdateYear = joiningdateYear;
	}
	

	public String getJoiningdate() {
		return joiningdate;
	}
	public void setJoiningdate(String joiningdate) {
		this.joiningdate = joiningdate;
	}
	
	public String getBloodgroup() {
		return bloodgroup;
	}
	public void setBloodgroup(String bloodgroup) {
		this.bloodgroup = bloodgroup;
	}
	public String getMaritalStatus() {
		return maritalStatus;
	}
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	
	public String getDisability() {
		return disability;
	}
	public void setDisability(String disability) {
		this.disability = disability;
	}
	public String getDisabilityType() {
		return disabilityType;
	}
	public void setDisabilityType(String disabilityType) {
		this.disabilityType = disabilityType;
	}
	
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	
	public Integer getReporting_manager() {
		return reporting_manager;
	}
	public void setReporting_manager(Integer reporting_manager) {
		this.reporting_manager = reporting_manager;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public String getSufix() {
		return sufix;
	}
	public void setSufix(String sufix) {
		this.sufix = sufix;
	}
	
	public Integer getDesignation_id() {
		return designation_id;
	}
	public void setDesignation_id(Integer designation_id) {
		this.designation_id = designation_id;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public boolean isEmail_status() {
		return email_status;
	}
	public void setEmail_status(boolean email_status) {
		this.email_status = email_status;
	}
	
	
}
