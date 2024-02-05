package com.example.springboot.Login;


import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


@Entity
@Table(name="system_login_user")
public class LoginUser {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="LOGIN_USER_ID")
	public Integer login_user_id;
	
	@Column(name="USERNAME")
	public String username;
	
	@Column(name="PASSWORD")
	public String password;
	
//	@Column(name="STATUS")
	public boolean status;
	

	public LoginUser() {
		
	}

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="system_login_user_authorities", joinColumns = @JoinColumn(name="USER_ID"), inverseJoinColumns = @JoinColumn(name="AUTHORITY_ID"))
	
	public Set<Authority> authority;
	
	public Integer getLogin_user_id() {
		return login_user_id;
	}

	public void setLogin_user_id(Integer login_user_id) {
		this.login_user_id = login_user_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Set<Authority> getAuthority() {
		return authority;
	}

	public void setAuthority(Set<Authority> authority) {
		this.authority = authority;
	}
	
	@Override
	public String toString() {
		return "LoginUser [login_user_id=" + login_user_id + ", username=" + username + ", password=" + password + ", status=" + status + "]";
	}
	
	
}

