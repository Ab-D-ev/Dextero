package com.example.springboot.HelpDesk;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="HelpDeskMap")
public class HelpdeskMap {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	@Column(name = "helpdesk_id")
	private Integer helpdesk_id;
	@Column(name = "employee_id")
	private Integer employee_id;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getHelpdesk_id() {
		return helpdesk_id;
	}
	public void setHelpdesk_id(Integer helpdesk_id) {
		this.helpdesk_id = helpdesk_id;
	}
	public Integer getEmployee_id() {
		return employee_id;
	}
	public void setEmployee_id(Integer employee_id) {
		this.employee_id = employee_id;
	}
	
}
