package com.example.springboot.CompanyProfile;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyDetailsService {
	
	@Autowired
	private CompanyDetailsRepo repo;
	
	public List<CompanyDetails> listAll(){
		return repo.findAll();
	}

	public void Save(CompanyDetails companyDetails) {
		repo.save(companyDetails);
	}
	
	public CompanyDetails get(int company_id) {
		return repo.findById(company_id).get();
	}
	
	public void delete(int company_id) {
		repo.deleteById(company_id);
	}
	
	public CompanyDetails getDataByEmailID(String email_id) {
		return repo.getDataByEmailID(email_id);
	}
	
	public Integer getCompanyID(String email_id) {
		return repo.getCompanyID(email_id);
	}
	public CompanyDetails getDataByID(Integer companyId) {
		return repo.getById(companyId);
	}
	
	public String getCompanyNameByID(Integer companyId) {
		return repo.getCompanyNameByID(companyId);
	}
}
