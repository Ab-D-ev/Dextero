package com.example.springboot.Expense;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Expenses")
public class AllExpenses {
	
	private Integer id;
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
	public String getReceipt() {
		return Receipt;
	}
	public void setReceipt(String receipt) {
		Receipt = receipt;
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
