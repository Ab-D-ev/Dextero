package com.example.springboot.SalaryPayRoll;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="deduction")
public class Deductions {
	
	@Id
	private Integer id;
	private String Employee_PF;
	private String Employee_Professional_Tax;
	private String Employee_TDS;
	private String Employee_ESI;
	private String Employee_Gratuity;
	private String Employee_Loan_Recovery;
	private String Employer_PF;
	private String Employer_Professional_Tax;
	private String Employer_TDS;
	private String Employer_ESI;
	private String Employer_Gratuity;
	private String Employer_Loan_Recovery;
	private Integer company_id;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getEmployee_PF() {
		return Employee_PF;
	}
	public void setEmployee_PF(String employee_PF) {
		Employee_PF = employee_PF;
	}
	public String getEmployee_Professional_Tax() {
		return Employee_Professional_Tax;
	}
	public void setEmployee_Professional_Tax(String employee_Professional_Tax) {
		Employee_Professional_Tax = employee_Professional_Tax;
	}
	public String getEmployee_TDS() {
		return Employee_TDS;
	}
	public void setEmployee_TDS(String employee_TDS) {
		Employee_TDS = employee_TDS;
	}
	public String getEmployee_ESI() {
		return Employee_ESI;
	}
	public void setEmployee_ESI(String employee_ESI) {
		Employee_ESI = employee_ESI;
	}
	public String getEmployee_Gratuity() {
		return Employee_Gratuity;
	}
	public void setEmployee_Gratuity(String employee_Gratuity) {
		Employee_Gratuity = employee_Gratuity;
	}
	public String getEmployee_Loan_Recovery() {
		return Employee_Loan_Recovery;
	}
	public void setEmployee_Loan_Recovery(String employee_Loan_Recovery) {
		Employee_Loan_Recovery = employee_Loan_Recovery;
	}
	public String getEmployer_PF() {
		return Employer_PF;
	}
	public void setEmployer_PF(String employer_PF) {
		Employer_PF = employer_PF;
	}
	public String getEmployer_Professional_Tax() {
		return Employer_Professional_Tax;
	}
	public void setEmployer_Professional_Tax(String employer_Professional_Tax) {
		Employer_Professional_Tax = employer_Professional_Tax;
	}
	public String getEmployer_TDS() {
		return Employer_TDS;
	}
	public void setEmployer_TDS(String employer_TDS) {
		Employer_TDS = employer_TDS;
	}
	public String getEmployer_ESI() {
		return Employer_ESI;
	}
	public void setEmployer_ESI(String employer_ESI) {
		Employer_ESI = employer_ESI;
	}
	public String getEmployer_Gratuity() {
		return Employer_Gratuity;
	}
	public void setEmployer_Gratuity(String employer_Gratuity) {
		Employer_Gratuity = employer_Gratuity;
	}
	public String getEmployer_Loan_Recovery() {
		return Employer_Loan_Recovery;
	}
	public void setEmployer_Loan_Recovery(String employer_Loan_Recovery) {
		Employer_Loan_Recovery = employer_Loan_Recovery;
	}
	public Integer getCompany_id() {
		return company_id;
	}
	public void setCompany_id(Integer company_id) {
		this.company_id = company_id;
	}
	
}
