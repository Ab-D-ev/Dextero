package com.example.springboot.AdhocTask;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="assignTask")
public class AssignTask {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private Integer company_name_id;
	private Integer project_id;
	private Integer milestone_id;
	private Integer task_id;
	private Integer employee_id;
	private Integer company_id;
	@Column(length = 2000)
	private String TaskDescription;
	private String Priority;
	private String StartDate;
	private String EndDate;
	private String StartYear;
	private String EndYear;
	private String status;
	private Integer createdby;
	private Integer authority_id;
	private Integer number_of_days;
	private String updated_start_date;
	private String updated_end_date;
	private Integer updated_number_of_days;
	private String t_changes;
	private String t_change_description;
	
	
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
	public Integer getMilestone_id() {
		return milestone_id;
	}
	public void setMilestone_id(Integer milestone_id) {
		this.milestone_id = milestone_id;
	}
	public Integer getTask_id() {
		return task_id;
	}
	public void setTask_id(Integer task_id) {
		this.task_id = task_id;
	}
	public Integer getEmployee_id() {
		return employee_id;
	}
	public void setEmployee_id(Integer employee_id) {
		this.employee_id = employee_id;
	}
	public Integer getCompany_id() {
		return company_id;
	}
	public void setCompany_id(Integer company_id) {
		this.company_id = company_id;
	}
	public String getTaskDescription() {
		return TaskDescription;
	}
	public void setTaskDescription(String taskDescription) {
		TaskDescription = taskDescription;
	}
	public String getPriority() {
		return Priority;
	}
	public void setPriority(String priority) {
		Priority = priority;
	}
	public String getStartDate() {
		return StartDate;
	}
	public void setStartDate(String startDate) {
		StartDate = startDate;
	}
	public String getEndDate() {
		return EndDate;
	}
	public void setEndDate(String endDate) {
		EndDate = endDate;
	}
	public String getStartYear() {
		return StartYear;
	}
	public void setStartYear(String startYear) {
		StartYear = startYear;
	}
	public String getEndYear() {
		return EndYear;
	}
	public void setEndYear(String endYear) {
		EndYear = endYear;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getCreatedby() {
		return createdby;
	}
	public void setCreatedby(Integer createdby) {
		this.createdby = createdby;
	}
	public Integer getAuthority_id() {
		return authority_id;
	}
	public void setAuthority_id(Integer authority_id) {
		this.authority_id = authority_id;
	}
	public Integer getNumber_of_days() {
		return number_of_days;
	}
	public void setNumber_of_days(Integer number_of_days) {
		this.number_of_days = number_of_days;
	}
	public String getUpdated_start_date() {
		return updated_start_date;
	}
	public void setUpdated_start_date(String updated_start_date) {
		this.updated_start_date = updated_start_date;
	}
	public String getUpdated_end_date() {
		return updated_end_date;
	}
	public void setUpdated_end_date(String updated_end_date) {
		this.updated_end_date = updated_end_date;
	}
	public Integer getUpdated_number_of_days() {
		return updated_number_of_days;
	}
	public void setUpdated_number_of_days(Integer updated_number_of_days) {
		this.updated_number_of_days = updated_number_of_days;
	}
	public String getT_changes() {
		return t_changes;
	}
	public void setT_changes(String t_changes) {
		this.t_changes = t_changes;
	}
	public String getT_change_description() {
		return t_change_description;
	}
	public void setT_change_description(String t_change_description) {
		this.t_change_description = t_change_description;
	}
	
}
