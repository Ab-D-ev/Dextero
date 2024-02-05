package com.example.springboot.AddMilestone;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="milestone")
public class Addmilestone {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String add_milestone;
	private String currentdate;
	private Integer company_id;
	private Integer company_name_id;
	private Integer project_id;

	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getAdd_milestone() {
		return add_milestone;
	}
	public void setAdd_milestone(String add_milestone) {
		this.add_milestone = add_milestone;
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

	
	
}
