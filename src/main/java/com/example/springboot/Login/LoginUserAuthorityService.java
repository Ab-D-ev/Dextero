package com.example.springboot.Login;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LoginUserAuthorityService {
	
	@Autowired
	 private LoginUserAuthorityRepository repo;
	 
	 public Integer GetAuthorityID(Integer user_id) {
	      return repo.GetAuthorityID(user_id);
	 }
	 
	 public Integer GetCountID(Integer authority_id) {
	      return repo.GetCountID(authority_id);
	 }
	 
//	 public Integer GetUserID(Integer authority_id) {
//	      if(repo.GetUserID(authority_id) == null) {
//				return 0;
//			}
//			else {
//				return repo.GetUserID(authority_id);
//			}
//	 }
	 
	 public List<LoginUserAuthority> listAll() {
	      return repo.findAll();
	 }
	 
	 public LoginUserAuthority get(int user_id) {
		  return repo.findById(user_id).get();
	 }
	 
	 public void save(LoginUserAuthority std) {
	      repo.save(std);
	 }
	 
	 public void deleteLoginUserAuthority(int user_id) {
			repo.deleteById(user_id);
		}
	 
//	 public void deleteLoginUserAuthorityID(Integer authority_id) {
//			repo.DeleteLoginUserAuthority(authority_id);
//		}

	 public void saveAll(List<LoginUserAuthority> loginUserAuthorities) {
	       repo.saveAll(loginUserAuthorities);
	    }

	 public LoginUserAuthority findByUserID(Integer user_id) {
		 return repo.findByUserID(user_id);
	 }
}
