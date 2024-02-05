package com.example.springboot.MainMaster;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolesService {

	@Autowired
	private RolesRepo repo;
	
	public List<Roles> listAll(){
		return repo.findAll();
	}
	
	public void Save(Roles roles) {
		repo.save(roles);
	}
	
	public Roles get(int id) {
		return repo.findById(id).get();
	}
	
	public void delete(int id) {
		repo.deleteById(id);
	}
}
