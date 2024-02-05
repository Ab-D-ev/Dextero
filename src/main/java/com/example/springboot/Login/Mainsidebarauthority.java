package com.example.springboot.Login;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="system_main_sidebar_authority")
public class Mainsidebarauthority {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer id;
	public Integer authorityid;
	public Integer mainsidebarid;
	public String status;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getAuthorityid() {
		return authorityid;
	}
	public void setAuthorityid(Integer authorityid) {
		this.authorityid = authorityid;
	}
	public Integer getMainsidebarid() {
		return mainsidebarid;
	}
	public void setMainsidebarid(Integer mainsidebarid) {
		this.mainsidebarid = mainsidebarid;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
