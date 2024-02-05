package com.example.springboot.Login;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class AuthorityService {
	@Autowired
	 private AuthorityRepository repo;
	 
	 public List<Authority> listAll() {
	      return repo.findAll();
	 }
	 
	 public Authority get(int authority_id) {
		  return repo.findById(authority_id).get();
	 }
	 
	 public void save(Authority std) {
	      repo.save(std);
	 }
	 
	 public void deleteAuthority(int authority_id) {
			repo.deleteById(authority_id);
	 }
}
