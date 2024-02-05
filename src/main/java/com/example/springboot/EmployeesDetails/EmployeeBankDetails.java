package com.example.springboot.EmployeesDetails;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="employee_bank_details")
public class EmployeeBankDetails {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer employee_id;
	private String accountholdername;
	private String bankname;
	private String branch;
	private String accountnumber;
	private String IFSC_Code;
	private String UAN_Number;
	private String PF_Number;
	private String ESI_Number;
	private Integer company_id;
	
	public Integer getEmployee_id() {
		return employee_id;
	}
	public void setEmployee_id(Integer employee_id) {
		this.employee_id = employee_id;
	}
	public String getAccountholdername() {
		return accountholdername;
	}
	public void setAccountholdername(String accountholdername) {
		this.accountholdername = accountholdername;
	}
	public String getBankname() {
		return bankname;
	}
	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getAccountnumber() {
		return accountnumber;
	}
	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}
	public String getIFSC_Code() {
		return IFSC_Code;
	}
	public void setIFSC_Code(String iFSC_Code) {
		IFSC_Code = iFSC_Code;
	}
	public String getUAN_Number() {
		return UAN_Number;
	}
	public void setUAN_Number(String uAN_Number) {
		UAN_Number = uAN_Number;
	}
	public String getPF_Number() {
		return PF_Number;
	}
	public void setPF_Number(String pF_Number) {
		PF_Number = pF_Number;
	}
	public String getESI_Number() {
		return ESI_Number;
	}
	public void setESI_Number(String eSI_Number) {
		ESI_Number = eSI_Number;
	}
	public Integer getCompany_id() {
		return company_id;
	}
	public void setCompany_id(Integer company_id) {
		this.company_id = company_id;
	}
	
}
