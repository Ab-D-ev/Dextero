package com.example.springboot.Expense;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ExpenseReport")
public class ExpenseReport {
	
	private Integer id;
	private Integer reportid;
	private String Category;
	private String FromLocation;
	private String ToLocation;
	private String Location;
	private String FromDate;
	private String ToDate;
	private String ModeOfTravel;
	private String Vendor;
	private String Amount;
	private String Receipt;
	private boolean approvedRejectFlag;
    private boolean active;
    private String approvedamount;
    @Column(length = 1000)
    private String reason;
    private Integer employee_id;
    private String currency;
    private Integer company_id;
	
    
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCategory() {
		return Category;
	}
	public void setCategory(String category) {
		Category = category;
	}
	public String getFromLocation() {
		return FromLocation;
	}
	public void setFromLocation(String fromLocation) {
		FromLocation = fromLocation;
	}
	public String getToLocation() {
		return ToLocation;
	}
	public void setToLocation(String toLocation) {
		ToLocation = toLocation;
	}
	public String getLocation() {
		return Location;
	}
	public void setLocation(String location) {
		Location = location;
	}
	public String getFromDate() {
		return FromDate;
	}
	public void setFromDate(String fromDate) {
		FromDate = fromDate;
	}
	public String getToDate() {
		return ToDate;
	}
	public void setToDate(String toDate) {
		ToDate = toDate;
	}
	public String getModeOfTravel() {
		return ModeOfTravel;
	}
	public void setModeOfTravel(String modeOfTravel) {
		ModeOfTravel = modeOfTravel;
	}
	public String getVendor() {
		return Vendor;
	}
	public void setVendor(String vendor) {
		Vendor = vendor;
	}
	public String getAmount() {
		return Amount;
	}
	public void setAmount(String amount) {
		Amount = amount;
	}
	public Integer getReportid() {
		return reportid;
	}
	public void setReportid(Integer reportid) {
		this.reportid = reportid;
	}
	public String getReceipt() {
		return Receipt;
	}
	public void setReceipt(String receipt) {
		Receipt = receipt;
	}
	public boolean isApprovedRejectFlag() {
		return approvedRejectFlag;
	}
	public void setApprovedRejectFlag(boolean approvedRejectFlag) {
		this.approvedRejectFlag = approvedRejectFlag;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getApprovedamount() {
		return approvedamount;
	}
	public void setApprovedamount(String approvedamount) {
		this.approvedamount = approvedamount;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Integer getEmployee_id() {
		return employee_id;
	}
	public void setEmployee_id(Integer employee_id) {
		this.employee_id = employee_id;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public Integer getCompany_id() {
		return company_id;
	}
	public void setCompany_id(Integer company_id) {
		this.company_id = company_id;
	}
	
}
