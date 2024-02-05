package com.example.springboot.Leave;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="leave_manage")
public class Leave {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private Integer leavecategory_id;
	private String dateFrom;
	private String dateTo;
	private String leaveType;
	@Column(length = 2000)
	private String reason;
	@Column(name = "accept_reject_flag")
    private boolean acceptRejectFlag;
	@Column(name = "active")
    private boolean active;
	private Integer employee_id;
	private String currentdate;
	private String toDateYear;
	private String fromDateYear;
	private int nDays;
	private Integer company_id;
	private String type;
	
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Integer getLeavecategory_id() {
		return leavecategory_id;
	}
	public void setLeavecategory_id(Integer leavecategory_id) {
		this.leavecategory_id = leavecategory_id;
	}
	public String getDateFrom() {
		return dateFrom;
	}
	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}
	public String getDateTo() {
		return dateTo;
	}
	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}
	public String getLeaveType() {
		return leaveType;
	}
	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public boolean isAcceptRejectFlag() {
		return acceptRejectFlag;
	}
	public void setAcceptRejectFlag(boolean acceptRejectFlag) {
		this.acceptRejectFlag = acceptRejectFlag;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public Integer getEmployee_id() {
		return employee_id;
	}
	public void setEmployee_id(Integer employee_id) {
		this.employee_id = employee_id;
	}
	public String getCurrentdate() {
		return currentdate;
	}
	public void setCurrentdate(String currentdate) {
		this.currentdate = currentdate;
	}
	public String getToDateYear() {
		return toDateYear;
	}
	public void setToDateYear(String toDateYear) {
		this.toDateYear = toDateYear;
	}
	public String getFromDateYear() {
		return fromDateYear;
	}
	public void setFromDateYear(String fromDateYear) {
		this.fromDateYear = fromDateYear;
	}
	
	public int getnDays() {
		return nDays;
	}
	public void setnDays(int nDays) {
		this.nDays = nDays;
	}
	public Integer getCompany_id() {
		return company_id;
	}
	public void setCompany_id(Integer company_id) {
		this.company_id = company_id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
