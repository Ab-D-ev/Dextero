package com.example.springboot.Login;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class MainsidebarauthorityService {
	@Autowired
	 private MainsidebarauthorityRepository repo;
	 
	 public List<Mainsidebarauthority> listAll() {
	      return repo.findAll();
	 }
	 
	 public List<Mainsidebarauthority> Mainsidebarauthoritybyauthid(Integer authorityid) {
	      return repo.Mainsidebarauthoritybyauthid(authorityid);
	 }
	 
	 public void DeleteMainsidebarauthority(Integer authorityid) {
	      repo.DeleteMainsidebarauthority(authorityid);
	 }
	 
	 public void save(Mainsidebarauthority std) {
	      repo.save(std);
	 }
	 
	 public void deleteMainsidebarAuthority(int id) {
			repo.deleteById(id);
	 }
	 
}