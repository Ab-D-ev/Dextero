package com.example.springboot.HelpDesk;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="helpdesk")
public class HelpDesk {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private Integer company_name_id;
	private Integer project_id;
	private Integer employee_id;
	private String issuedate;
	private String criticality;
	private Integer prioritylevel;
	private String status;
	private String type;
	private String serviceRequestId;
	private String serviceRequest;
	@Column(length = 2000)
	private String description;
	private String responsibility;
	private String clientstakeholder;
	private String plannedstartdate;
	private String plannedcloseddate;
	private String actualstartdate;
	private String actualcloseddate;
	private String ageing;
	@Column(length = 2000)
	private String rootcause;
	@Column(length = 2000)
	private String actionplan;
	@Column(length = 2000)
	private String currentstatus;
	@Column(length = 2000)
	private String nextstep;
	@Column(length = 2000)
	private String remarks;
	private Integer completion;
	private String currentdate;
	private String category1;
	private String category2;
	private String category3;
	private Integer company_id;
	private String completionstatus;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getCompany_name_id() {
		return company_name_id;
	}
	public void setCompany_name_id(Integer company_name_id) {
		this.company_name_id = company_name_id;
	}
	
	public Integer getProject_id() {
		return project_id;
	}
	public void setProject_id(Integer project_id) {
		this.project_id = project_id;
	}
	public Integer getEmployee_id() {
		return employee_id;
	}
	public void setEmployee_id(Integer employee_id) {
		this.employee_id = employee_id;
	}
	public String getIssuedate() {
		return issuedate;
	}
	public void setIssuedate(String issuedate) {
		this.issuedate = issuedate;
	}
	public String getCriticality() {
		return criticality;
	}
	public void setCriticality(String criticality) {
		this.criticality = criticality;
	}
	public Integer getPrioritylevel() {
		return prioritylevel;
	}
	public void setPrioritylevel(Integer prioritylevel) {
		this.prioritylevel = prioritylevel;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getServiceRequestId() {
		return serviceRequestId;
	}
	public void setServiceRequestId(String serviceRequestId) {
		this.serviceRequestId = serviceRequestId;
	}
	public String getServiceRequest() {
		return serviceRequest;
	}
	public void setServiceRequest(String serviceRequest) {
		this.serviceRequest = serviceRequest;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getResponsibility() {
		return responsibility;
	}
	public void setResponsibility(String responsibility) {
		this.responsibility = responsibility;
	}
	public String getClientstakeholder() {
		return clientstakeholder;
	}
	public void setClientstakeholder(String clientstakeholder) {
		this.clientstakeholder = clientstakeholder;
	}
	public String getPlannedstartdate() {
		return plannedstartdate;
	}
	public void setPlannedstartdate(String plannedstartdate) {
		this.plannedstartdate = plannedstartdate;
	}
	public String getPlannedcloseddate() {
		return plannedcloseddate;
	}
	public void setPlannedcloseddate(String plannedcloseddate) {
		this.plannedcloseddate = plannedcloseddate;
	}
	public String getActualstartdate() {
		return actualstartdate;
	}
	public void setActualstartdate(String actualstartdate) {
		this.actualstartdate = actualstartdate;
	}
	public String getActualcloseddate() {
		return actualcloseddate;
	}
	public void setActualcloseddate(String actualcloseddate) {
		this.actualcloseddate = actualcloseddate;
	}
	public String getAgeing() {
		return ageing;
	}
	public void setAgeing(String ageing) {
		this.ageing = ageing;
	}
	public String getRootcause() {
		return rootcause;
	}
	public void setRootcause(String rootcause) {
		this.rootcause = rootcause;
	}
	public String getActionplan() {
		return actionplan;
	}
	public void setActionplan(String actionplan) {
		this.actionplan = actionplan;
	}
	public String getCurrentstatus() {
		return currentstatus;
	}
	public void setCurrentstatus(String currentstatus) {
		this.currentstatus = currentstatus;
	}
	public String getNextstep() {
		return nextstep;
	}
	public void setNextstep(String nextstep) {
		this.nextstep = nextstep;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Integer getCompletion() {
		return completion;
	}
	public void setCompletion(Integer completion) {
		this.completion = completion;
	}
	public String getCurrentdate() {
		return currentdate;
	}
	public void setCurrentdate(String currentdate) {
		this.currentdate = currentdate;
	}
	
	public String getCategory1() {
		return category1;
	}
	public void setCategory1(String category1) {
		this.category1 = category1;
	}
	public String getCategory2() {
		return category2;
	}
	public void setCategory2(String category2) {
		this.category2 = category2;
	}
	public String getCategory3() {
		return category3;
	}
	public void setCategory3(String category3) {
		this.category3 = category3;
	}
	public Integer getCompany_id() {
		return company_id;
	}
	public void setCompany_id(Integer company_id) {
		this.company_id = company_id;
	}
	public String getCompletionstatus() {
		return completionstatus;
	}
	public void setCompletionstatus(String completionstatus) {
		this.completionstatus = completionstatus;
	}
	
}
