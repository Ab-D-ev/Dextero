package com.example.springboot.AddCompany;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface AddCustomerService {
	
	List<AddCustomer> getAllCustomer();
	void saveAllCustomer(AddCustomer customer);
	
	AddCustomer getCustomerById(long id);
	void deleteCustomerById(long id);
	
	public String findCompanyNameById(Long id);
	public Long findCompanyIDByCompanyName(String companyname);
	

}
