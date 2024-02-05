package com.example.springboot.PostJob;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="JobResponse")
public class JobResponse {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private Integer jobTitle;
	private Integer response;
	private Integer postedBy;
	private String postedDate;
	private Integer company_id;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(Integer jobTitle) {
		this.jobTitle = jobTitle;
	}
	public Integer getResponse() {
		return response;
	}
	public void setResponse(Integer response) {
		this.response = response;
	}
	public Integer getPostedBy() {
		return postedBy;
	}
	public void setPostedBy(Integer postedBy) {
		this.postedBy = postedBy;
	}
	public String getPostedDate() {
		return postedDate;
	}
	public void setPostedDate(String postedDate) {
		this.postedDate = postedDate;
	}
	public Integer getCompany_id() {
		return company_id;
	}
	public void setCompany_id(Integer company_id) {
		this.company_id = company_id;
	}
	
}
