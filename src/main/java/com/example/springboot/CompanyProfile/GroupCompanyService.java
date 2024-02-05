package com.example.springboot.CompanyProfile;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupCompanyService {
	
	@Autowired
	private GroupCompanyRepo repo;
	
	public List<GroupCompany> listAll(){
		return repo.findAll();
	}

	public void Save(GroupCompany groupCompany) {
		repo.save(groupCompany);
	}
	
	public GroupCompany get(int groupcompany_id) {
		return repo.findById(groupcompany_id).get();
	}
	
	public void delete(int groupcompany_id) {
		repo.deleteById(groupcompany_id);
	}
	
	public Integer getgroupcompany_id(String email_id) {
		return repo.getgroupcompany_id(email_id);
	}

	public Integer getcountgroupcompany_id(String email_id) {
		return repo.getcountgroupcompany_id(email_id);
	}
	
	public GroupCompany getDataByEmailId(String email_id) {
		return repo.getDataByEmailId(email_id);
	}
}
