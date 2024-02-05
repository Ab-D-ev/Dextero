package com.example.springboot.TimeSheet;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="time_sheet")
public class TimeSheet {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private Long company_name_id;
	private Long project_id;
	private Long milestone_id;
	private Integer task_id;
	private String StartDate;
	private String start_time;
	private String end_time;
	private String currentdate;
	private String taskstatus;
	private Integer employee_id;
	@Column(length = 1000)
	private String remarks;
	private String duration;
	private String remainingduration;
	private String overtime;
	private Integer company_id;
	private String milestoneStatus;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Long getCompany_name_id() {
		return company_name_id;
	}
	public void setCompany_name_id(Long company_name_id) {
		this.company_name_id = company_name_id;
	}
	public Long getProject_id() {
		return project_id;
	}
	public void setProject_id(Long project_id) {
		this.project_id = project_id;
	}
	public Long getMilestone_id() {
		return milestone_id;
	}
	public void setMilestone_id(Long milestone_id) {
		this.milestone_id = milestone_id;
	}
	public Integer getTask_id() {
		return task_id;
	}
	public void setTask_id(Integer task_id) {
		this.task_id = task_id;
	}
	public String getStartDate() {
		return StartDate;
	}
	public void setStartDate(String startDate) {
		StartDate = startDate;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public String getCurrentdate() {
		return currentdate;
	}
	public void setCurrentdate(String currentdate) {
		this.currentdate = currentdate;
	}
	public String getTaskstatus() {
		return taskstatus;
	}
	public void setTaskstatus(String taskstatus) {
		this.taskstatus = taskstatus;
	}
	
	public Integer getEmployee_id() {
		return employee_id;
	}
	public void setEmployee_id(Integer employee_id) {
		this.employee_id = employee_id;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getRemainingduration() {
		return remainingduration;
	}
	public void setRemainingduration(String remainingduration) {
		this.remainingduration = remainingduration;
	}
	public String getOvertime() {
		return overtime;
	}
	public void setOvertime(String overtime) {
		this.overtime = overtime;
	}
	public Integer getCompany_id() {
		return company_id;
	}
	public void setCompany_id(Integer company_id) {
		this.company_id = company_id;
	}
	public String getMilestoneStatus() {
		return milestoneStatus;
	}
	public void setMilestoneStatus(String milestoneStatus) {
		this.milestoneStatus = milestoneStatus;
	}
//	public String getCategory() {
//		return Category;
//	}
//	public void setCategory(String category) {
//		Category = category;
//	}
	
}
