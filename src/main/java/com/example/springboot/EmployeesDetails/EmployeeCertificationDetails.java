package com.example.springboot.EmployeesDetails;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="employee_certification_details")
public class EmployeeCertificationDetails {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String certificationName;
	private String certificationCompletionID;
	private String certificationUrl;
	private String certificationValidity;
	private Integer company_id;
	private Integer employee_id;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCertificationName() {
		return certificationName;
	}
	public void setCertificationName(String certificationName) {
		this.certificationName = certificationName;
	}
	public String getCertificationCompletionID() {
		return certificationCompletionID;
	}
	public void setCertificationCompletionID(String certificationCompletionID) {
		this.certificationCompletionID = certificationCompletionID;
	}
	public String getCertificationUrl() {
		return certificationUrl;
	}
	public void setCertificationUrl(String certificationUrl) {
		this.certificationUrl = certificationUrl;
	}
	public String getCertificationValidity() {
		return certificationValidity;
	}
	public void setCertificationValidity(String certificationValidity) {
		this.certificationValidity = certificationValidity;
	}
	public Integer getCompany_id() {
		return company_id;
	}
	public void setCompany_id(Integer company_id) {
		this.company_id = company_id;
	}
	public Integer getEmployee_id() {
		return employee_id;
	}
	public void setEmployee_id(Integer employee_id) {
		this.employee_id = employee_id;
	}
	
	

}
