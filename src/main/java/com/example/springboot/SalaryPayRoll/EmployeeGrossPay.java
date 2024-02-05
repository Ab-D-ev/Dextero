package com.example.springboot.SalaryPayRoll;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="employee_gross_pay")
public class EmployeeGrossPay {
	
	@Id
	private Integer empId;
	private double basic;
	private double da;
	private double hra;
	private double travel_allowance;
	private double special_allowance;
	private double project_allowance;
	private double project_travel;
	private double conveyance_allowance;
	private double medical_allowance;
	private double dearness_allowance;
	private double other_allowance;
	private double grossSalary;
	
	private double Employee_PF;
	private double Employee_Professional_Tax;
	private double Employee_TDS;
	private double Employee_ESI;
	private double Employee_Gratuity;
	private double Employee_Loan_Recovery;
	private double Employer_PF;
	private double Employer_Professional_Tax;
	private double Employer_TDS;
	private double Employer_ESI;
	private double Employer_Gratuity;
	private double Employer_Loan_Recovery;
	private double total_Deduction;
	private Integer company_id;
	
	public Integer getEmpId() {
		return empId;
	}
	public void setEmpId(Integer empId) {
		this.empId = empId;
	}
	
	public double getBasic() {
		return basic;
	}
	public void setBasic(double basic) {
		this.basic = basic;
	}
	public double getDa() {
		return da;
	}
	public void setDa(double da) {
		this.da = da;
	}
	public double getHra() {
		return hra;
	}
	public void setHra(double hra) {
		this.hra = hra;
	}
	public double getTravel_allowance() {
		return travel_allowance;
	}
	public void setTravel_allowance(double travel_allowance) {
		this.travel_allowance = travel_allowance;
	}
	public double getSpecial_allowance() {
		return special_allowance;
	}
	public void setSpecial_allowance(double special_allowance) {
		this.special_allowance = special_allowance;
	}
	public double getProject_allowance() {
		return project_allowance;
	}
	public void setProject_allowance(double project_allowance) {
		this.project_allowance = project_allowance;
	}
	public double getProject_travel() {
		return project_travel;
	}
	public void setProject_travel(double project_travel) {
		this.project_travel = project_travel;
	}
	public double getConveyance_allowance() {
		return conveyance_allowance;
	}
	public void setConveyance_allowance(double conveyance_allowance) {
		this.conveyance_allowance = conveyance_allowance;
	}
	public double getMedical_allowance() {
		return medical_allowance;
	}
	public void setMedical_allowance(double medical_allowance) {
		this.medical_allowance = medical_allowance;
	}
	public double getDearness_allowance() {
		return dearness_allowance;
	}
	public void setDearness_allowance(double dearness_allowance) {
		this.dearness_allowance = dearness_allowance;
	}
	public double getOther_allowance() {
		return other_allowance;
	}
	public void setOther_allowance(double other_allowance) {
		this.other_allowance = other_allowance;
	}
	public double getGrossSalary() {
		return grossSalary;
	}
	public void setGrossSalary(double grossSalary) {
		this.grossSalary = grossSalary;
	}
	public double getEmployee_PF() {
		return Employee_PF;
	}
	public void setEmployee_PF(double employee_PF) {
		Employee_PF = employee_PF;
	}
	public double getEmployee_Professional_Tax() {
		return Employee_Professional_Tax;
	}
	public void setEmployee_Professional_Tax(double employee_Professional_Tax) {
		Employee_Professional_Tax = employee_Professional_Tax;
	}
	public double getEmployee_TDS() {
		return Employee_TDS;
	}
	public void setEmployee_TDS(double employee_TDS) {
		Employee_TDS = employee_TDS;
	}
	public double getEmployee_ESI() {
		return Employee_ESI;
	}
	public void setEmployee_ESI(double employee_ESI) {
		Employee_ESI = employee_ESI;
	}
	public double getEmployee_Gratuity() {
		return Employee_Gratuity;
	}
	public void setEmployee_Gratuity(double employee_Gratuity) {
		Employee_Gratuity = employee_Gratuity;
	}
	public double getEmployee_Loan_Recovery() {
		return Employee_Loan_Recovery;
	}
	public void setEmployee_Loan_Recovery(double employee_Loan_Recovery) {
		Employee_Loan_Recovery = employee_Loan_Recovery;
	}
	public double getEmployer_PF() {
		return Employer_PF;
	}
	public void setEmployer_PF(double employer_PF) {
		Employer_PF = employer_PF;
	}
	public double getEmployer_Professional_Tax() {
		return Employer_Professional_Tax;
	}
	public void setEmployer_Professional_Tax(double employer_Professional_Tax) {
		Employer_Professional_Tax = employer_Professional_Tax;
	}
	public double getEmployer_TDS() {
		return Employer_TDS;
	}
	public void setEmployer_TDS(double employer_TDS) {
		Employer_TDS = employer_TDS;
	}
	public double getEmployer_ESI() {
		return Employer_ESI;
	}
	public void setEmployer_ESI(double employer_ESI) {
		Employer_ESI = employer_ESI;
	}
	public double getEmployer_Gratuity() {
		return Employer_Gratuity;
	}
	public void setEmployer_Gratuity(double employer_Gratuity) {
		Employer_Gratuity = employer_Gratuity;
	}
	public double getEmployer_Loan_Recovery() {
		return Employer_Loan_Recovery;
	}
	public void setEmployer_Loan_Recovery(double employer_Loan_Recovery) {
		Employer_Loan_Recovery = employer_Loan_Recovery;
	}
	
	public double getTotal_Deduction() {
		return total_Deduction;
	}
	public void setTotal_Deduction(double total_Deduction) {
		this.total_Deduction = total_Deduction;
	}
	public Integer getCompany_id() {
		return company_id;
	}
	public void setCompany_id(Integer company_id) {
		this.company_id = company_id;
	}
	
}
