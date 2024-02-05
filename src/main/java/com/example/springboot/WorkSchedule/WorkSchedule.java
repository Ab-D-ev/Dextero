package com.example.springboot.WorkSchedule;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="work_schedule")
public class WorkSchedule {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer schedule_id;
	private String work_schedule_name;
	private Integer hours_per_day;
	private Integer company_id;
	
	
	public Integer getSchedule_id() {
		return schedule_id;
	}
	public void setSchedule_id(Integer schedule_id) {
		this.schedule_id = schedule_id;
	}
	public String getWork_schedule_name() {
		return work_schedule_name;
	}
	public void setWork_schedule_name(String work_schedule_name) {
		this.work_schedule_name = work_schedule_name;
	}
	
	public Integer getHours_per_day() {
		return hours_per_day;
	}
	public void setHours_per_day(Integer hours_per_day) {
		this.hours_per_day = hours_per_day;
	}
	public Integer getCompany_id() {
		return company_id;
	}
	public void setCompany_id(Integer company_id) {
		this.company_id = company_id;
	}
	
	
}
