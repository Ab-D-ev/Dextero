package com.example.springboot.Login;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.springboot.EmployeesDetails.Employee;
import com.example.springboot.ExcelDataUpload.ExcelHelper;
import com.example.springboot.ExcelDataUpload.LoginExcelHelper;



@Service
@Transactional
public class LoginUserService implements UserDetailsService{

	@Autowired
	public LoginUserRepository repo;
	
	@Autowired
	private LoginUserAuthorityService loginuserauthorityService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		LoginUser appUser = repo.findByUsername(username)
				.orElseThrow(()-> new UsernameNotFoundException("Not Exists User"));
		
		return new User(appUser.getUsername(), appUser.getPassword(), getAuthorities(appUser));
	}
		private static Collection<? extends GrantedAuthority> getAuthorities(LoginUser appUser) {
	        String[] userRoles = appUser.getAuthority().stream().map((authority) -> authority.getAuthority()).toArray(String[]::new);
	        Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(userRoles);
	        return authorities;
	    }
		
		
		public Integer GetAuthorityID(String username) {
		      return repo.GetAuthorityID(username);
		 }
		
		public List<LoginUser> listAll() {
		      return repo.findAll();
		 }
		 
		 public LoginUser get(int login_user_id) {
			  return repo.findById(login_user_id).get();
		 }
		 
		 public void save(LoginUser std) {
		      repo.save(std);
		 }
		 
		 public void deleteLoginUser(int login_user_id) {
				repo.deleteById(login_user_id);
			}
		 void UpdatePasswordByUsername(String username, String password) {
			  repo.UpdatePasswordByUsername(username, password);
		 }
		 
		 public List<LoginUser> getUserName(String username){
			 return repo.getUserName(username);
		 }
		
		 public int findByUser(String username) {
			 return repo.findByUser(username);
		 }
		 
		 public LoginUser findByUserName(String username) {
			 return repo.findByUserName(username);
		 }
		 
		 public String getStatusByUsernamePassword(String username) {
			 return repo.getStatusByUsernamePassword(username);
		 }
		 
		 public void LoginExcelsave(MultipartFile file) {
			 LoginUser aform= new LoginUser();
			 int username1 = repo.findByUser(aform.getUsername());
			 if(username1 == 0){
			    try {
			    	String name=file.getOriginalFilename();
			    	String fileName = name.replaceFirst("[.][^.]+$", "");
			    	System.out.println("Original File Name : "+ fileName);
			      List<LoginUser> tutorials = LoginExcelHelper.excelToTutorials(file.getInputStream());
//			      repo.saveAll(tutorials);
			      for(LoginUser user:tutorials) {
			    	  if( repo.findByUser(user.getUsername()) == 0) {
			    		  repo.save(user);
			    		  LoginUserAuthority e = new LoginUserAuthority();
				    	  e.setAuthority_id(6);
				    	  e.setUser_id(repo.GetAuthorityID(user.getUsername()));
				    	  loginuserauthorityService.save(e);
			    	  }
			      }
			    } catch (IOException e) {
			      throw new RuntimeException("fail to store excel data: " + e.getMessage());
			    }
			  }
			 else {
			    	String error="true";
			    	System.out.println("E-mail address already exists.  : "+ error);
			    }
		 }
		 
		 public List<LoginUser> getUsernamePassword(String username, String password){
			 return repo.getUsernamePassword(username, password);
		 }
		 
		 public void saveAll(List<LoginUser> loginUsers) {
		      repo.saveAll(loginUsers);
		 }
		 
		
}
