package com.example.springboot.SalaryPayRoll;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="master_percentage_deduction")
public class MasterPercentage {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String employee_PF;
	private String employee_Professional_Tax;
	private String employee_TDS;
	private String employee_ESI;
	private String employee_Gratuity;
	private String employee_Loan_Recovery;
	private String employer_PF;
	private String employer_Professional_Tax;
	private String employer_TDS;
	private String employer_ESI;
	private String employer_Gratuity;
	private String employer_Loan_Recovery;
	private String ptCondition;
	private String gender;
	private String disability;
	private Integer company_id;
	
	
	public Integer getCompany_id() {
		return company_id;
	}
	public void setCompany_id(Integer company_id) {
		this.company_id = company_id;
	}
	public String getEmployee_PF() {
		return employee_PF;
	}
	public void setEmployee_PF(String employee_PF) {
		this.employee_PF = employee_PF;
	}
	public String getEmployee_Professional_Tax() {
		return employee_Professional_Tax;
	}
	public void setEmployee_Professional_Tax(String employee_Professional_Tax) {
		this.employee_Professional_Tax = employee_Professional_Tax;
	}
	public String getEmployee_TDS() {
		return employee_TDS;
	}
	public void setEmployee_TDS(String employee_TDS) {
		this.employee_TDS = employee_TDS;
	}
	public String getEmployee_ESI() {
		return employee_ESI;
	}
	public void setEmployee_ESI(String employee_ESI) {
		this.employee_ESI = employee_ESI;
	}
	public String getEmployee_Gratuity() {
		return employee_Gratuity;
	}
	public void setEmployee_Gratuity(String employee_Gratuity) {
		this.employee_Gratuity = employee_Gratuity;
	}
	public String getEmployee_Loan_Recovery() {
		return employee_Loan_Recovery;
	}
	public void setEmployee_Loan_Recovery(String employee_Loan_Recovery) {
		this.employee_Loan_Recovery = employee_Loan_Recovery;
	}
	public String getEmployer_PF() {
		return employer_PF;
	}
	public void setEmployer_PF(String employer_PF) {
		this.employer_PF = employer_PF;
	}
	public String getEmployer_Professional_Tax() {
		return employer_Professional_Tax;
	}
	public void setEmployer_Professional_Tax(String employer_Professional_Tax) {
		this.employer_Professional_Tax = employer_Professional_Tax;
	}
	public String getEmployer_TDS() {
		return employer_TDS;
	}
	public void setEmployer_TDS(String employer_TDS) {
		this.employer_TDS = employer_TDS;
	}
	public String getEmployer_ESI() {
		return employer_ESI;
	}
	public void setEmployer_ESI(String employer_ESI) {
		this.employer_ESI = employer_ESI;
	}
	public String getEmployer_Gratuity() {
		return employer_Gratuity;
	}
	public void setEmployer_Gratuity(String employer_Gratuity) {
		this.employer_Gratuity = employer_Gratuity;
	}
	public String getEmployer_Loan_Recovery() {
		return employer_Loan_Recovery;
	}
	public void setEmployer_Loan_Recovery(String employer_Loan_Recovery) {
		this.employer_Loan_Recovery = employer_Loan_Recovery;
	}
	public String getPtCondition() {
		return ptCondition;
	}
	public void setPtCondition(String ptCondition) {
		this.ptCondition = ptCondition;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getDisability() {
		return disability;
	}
	public void setDisability(String disability) {
		this.disability = disability;
	}
	
	
}
