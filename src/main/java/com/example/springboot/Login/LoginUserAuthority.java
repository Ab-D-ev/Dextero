package com.example.springboot.Login;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="system_login_user_authorities")
public class LoginUserAuthority {

	@Id
	@Column(name="user_id")
	public Integer user_id;
	@Column(name="authority_id")
	public Integer authority_id;
	
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public Integer getAuthority_id() {
		return authority_id;
	}
	public void setAuthority_id(Integer authority_id) {
		this.authority_id = authority_id;
	}
	
}
