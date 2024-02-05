package com.example.springboot.AddTask;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Task")
public class AddTask {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
//	private String companyname;
//	private String projectname;
//	private String addprojectcode;
//	private String addMilestone; 
	private String addTask;
	private String currentdate;
	private Integer company_id;
	private Integer company_name_id;
	private Integer project_id;
	private Integer milestone_id;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAddTask() {
		return addTask;
	}
	public void setAddTask(String addTask) {
		this.addTask = addTask;
	}
	public String getCurrentdate() {
		return currentdate;
	}
	public void setCurrentdate(String currentdate) {
		this.currentdate = currentdate;
	}
	public Integer getCompany_id() {
		return company_id;
	}
	public void setCompany_id(Integer company_id) {
		this.company_id = company_id;
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
	
	
}
