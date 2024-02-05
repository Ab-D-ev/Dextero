package com.example.springboot.PostJob;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@Entity
@Table(name="JobPost")
public class JobPost {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private Integer Designation;
	private Integer EmploymentType;
	private String JobDescription; 
	@Column(length = 2000)
	private String KeySkills;
	private Integer MinWorkExperience;
	private Integer MaxWorkExperience;     
	private Integer MinAnnualSalary;
	private Integer MaxAnnualSalary;
	private Integer Location; 
	private Integer Industry;
	private Integer PreferredIndustry; 
	private Integer FunctionalArea;
	private String ReferenceCode;
	private Integer NumberofVacancies;
	private Integer EducationalQualification;
	private String CompanyName; 
	@Column(length = 2000)
	private String CompanyDetails;
	private Integer WorkMode;
	private Integer Role;
	@Column(length = 2000)
	private String Responsibilities;
	@Column(length = 2000)
	private String DesiredCandidateProfile;
	private String currentdate;
	private Integer response;
	private String postedDate;
	private String ageing;
	private Integer company_id;
	private Integer employee_id;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getDesignation() {
		return Designation;
	}
	public void setDesignation(Integer designation) {
		Designation = designation;
	}
	public Integer getEmploymentType() {
		return EmploymentType;
	}
	public void setEmploymentType(Integer employmentType) {
		EmploymentType = employmentType;
	}
	public String getJobDescription() {
		return JobDescription;
	}
	public void setJobDescription(String jobDescription) {
		JobDescription = jobDescription;
	}
	public String getKeySkills() {
		return KeySkills;
	}
	public void setKeySkills(String keySkills) {
		KeySkills = keySkills;
	}
	
	public Integer getMinWorkExperience() {
		return MinWorkExperience;
	}
	public void setMinWorkExperience(Integer minWorkExperience) {
		MinWorkExperience = minWorkExperience;
	}
	public Integer getMaxWorkExperience() {
		return MaxWorkExperience;
	}
	public void setMaxWorkExperience(Integer maxWorkExperience) {
		MaxWorkExperience = maxWorkExperience;
	}
	public Integer getMinAnnualSalary() {
		return MinAnnualSalary;
	}
	public void setMinAnnualSalary(Integer minAnnualSalary) {
		MinAnnualSalary = minAnnualSalary;
	}
	public Integer getMaxAnnualSalary() {
		return MaxAnnualSalary;
	}
	public void setMaxAnnualSalary(Integer maxAnnualSalary) {
		MaxAnnualSalary = maxAnnualSalary;
	}
	public Integer getLocation() {
		return Location;
	}
	public void setLocation(Integer location) {
		Location = location;
	}
	public Integer getIndustry() {
		return Industry;
	}
	public void setIndustry(Integer industry) {
		Industry = industry;
	}
	public Integer getPreferredIndustry() {
		return PreferredIndustry;
	}
	public void setPreferredIndustry(Integer preferredIndustry) {
		PreferredIndustry = preferredIndustry;
	}
	public Integer getFunctionalArea() {
		return FunctionalArea;
	}
	public void setFunctionalArea(Integer functionalArea) {
		FunctionalArea = functionalArea;
	}
	public String getReferenceCode() {
		return ReferenceCode;
	}
	public void setReferenceCode(String referenceCode) {
		ReferenceCode = referenceCode;
	}
	public Integer getNumberofVacancies() {
		return NumberofVacancies;
	}
	public void setNumberofVacancies(Integer numberofVacancies) {
		NumberofVacancies = numberofVacancies;
	}
	public Integer getEducationalQualification() {
		return EducationalQualification;
	}
	public void setEducationalQualification(Integer educationalQualification) {
		EducationalQualification = educationalQualification;
	}
	public String getCompanyName() {
		return CompanyName;
	}
	public void setCompanyName(String companyName) {
		CompanyName = companyName;
	}
	public String getCompanyDetails() {
		return CompanyDetails;
	}
	public void setCompanyDetails(String companyDetails) {
		CompanyDetails = companyDetails;
	}
	public Integer getWorkMode() {
		return WorkMode;
	}
	public void setWorkMode(Integer workMode) {
		WorkMode = workMode;
	}
	public Integer getRole() {
		return Role;
	}
	public void setRole(Integer role) {
		Role = role;
	}
	public String getResponsibilities() {
		return Responsibilities;
	}
	public void setResponsibilities(String responsibilities) {
		Responsibilities = responsibilities;
	}
	public String getDesiredCandidateProfile() {
		return DesiredCandidateProfile;
	}
	public void setDesiredCandidateProfile(String desiredCandidateProfile) {
		DesiredCandidateProfile = desiredCandidateProfile;
	}
	public String getCurrentdate() {
		return currentdate;
	}
	public void setCurrentdate(String currentdate) {
		this.currentdate = currentdate;
	}
	public Integer getResponse() {
		return response;
	}
	public void setResponse(Integer response) {
		this.response = response;
	}
	public String getPostedDate() {
		return postedDate;
	}
	public void setPostedDate(String postedDate) {
		this.postedDate = postedDate;
	}
	public String getAgeing() {
		return ageing;
	}
	public void setAgeing(String ageing) {
		this.ageing = ageing;
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
