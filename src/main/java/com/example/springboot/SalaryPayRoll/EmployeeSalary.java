package com.example.springboot.SalaryPayRoll;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="employee_salary")
public class EmployeeSalary {
	
	  @Id
	  private Integer employeeId;
	  private Integer user_id;
	  private String grossSalary;
	  private String netSalary;
	  private String currentdate;
	  private String workingDays;
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
	  private String Employee_PF;
	  private String Employee_Professional_Tax;
	  private String Employee_TDS;
	  private String Employee_ESI;
	  private String Employee_Gratuity;
	  private String Employee_Loan_Recovery;
	  private String totalDeduction;
	  private String numberOfSundays;
	  private String numberOfDaysInMonth;
	  private String unpaidLeaves;
	  private String paidLeaves;
	  private String fromDate;
	  private String toDate;
	  private Integer company_id;
	  
	public Integer getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public String getGrossSalary() {
		return grossSalary;
	}
	public void setGrossSalary(String grossSalary) {
		this.grossSalary = grossSalary;
	}
	public String getNetSalary() {
		return netSalary;
	}
	public void setNetSalary(String netSalary) {
		this.netSalary = netSalary;
	}
	public String getCurrentdate() {
		return currentdate;
	}
	public void setCurrentdate(String currentdate) {
		this.currentdate = currentdate;
	}
	public String getWorkingDays() {
		return workingDays;
	}
	public void setWorkingDays(String workingDays) {
		this.workingDays = workingDays;
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
	public String getTotalDeduction() {
		return totalDeduction;
	}
	public void setTotalDeduction(String totalDeduction) {
		this.totalDeduction = totalDeduction;
	}
	public String getNumberOfSundays() {
		return numberOfSundays;
	}
	public void setNumberOfSundays(String numberOfSundays) {
		this.numberOfSundays = numberOfSundays;
	}
	public String getNumberOfDaysInMonth() {
		return numberOfDaysInMonth;
	}
	public void setNumberOfDaysInMonth(String numberOfDaysInMonth) {
		this.numberOfDaysInMonth = numberOfDaysInMonth;
	}
	public String getUnpaidLeaves() {
		return unpaidLeaves;
	}
	public void setUnpaidLeaves(String unpaidLeaves) {
		this.unpaidLeaves = unpaidLeaves;
	}
	public String getPaidLeaves() {
		return paidLeaves;
	}
	public void setPaidLeaves(String paidLeaves) {
		this.paidLeaves = paidLeaves;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public Integer getCompany_id() {
		return company_id;
	}
	public void setCompany_id(Integer company_id) {
		this.company_id = company_id;
	}  
		
}
