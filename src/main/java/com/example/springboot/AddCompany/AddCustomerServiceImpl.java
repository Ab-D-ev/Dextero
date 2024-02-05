package com.example.springboot.AddCompany;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddCustomerServiceImpl implements AddCustomerService {
	
	@Autowired
	private AddCustomerRepository addcustomerRepository;

	@Override
	public List<AddCustomer> getAllCustomer() {
		return addcustomerRepository.findAll();
		 
	}

	@Override
	public void saveAllCustomer(AddCustomer customer) {
		this.addcustomerRepository.save(customer);
		
		
	}

	@Override
	public AddCustomer getCustomerById(long id) {
		java.util.Optional<AddCustomer> optional = addcustomerRepository.findById(id);
		AddCustomer customer=null;
		if(optional.isPresent()) {
			customer=optional.get();
		}else {
			throw new RuntimeException("customer id not found"+id);
		}
		return  customer;
		
	}

	@Override
	public void deleteCustomerById(long id) {
		this.addcustomerRepository.deleteById(id);
		
	}
	
	public List<AddCustomer> getCustomerListByOrder(String CompanyName){
		return addcustomerRepository.getCustomerListByOrder(CompanyName);
		
	}
	
	public List<AddCustomer> getListByCurrentDate(String currentdate){
		return addcustomerRepository.getListByCurrentDate(currentdate);
	}
	
	public int findByCompanyName1(String companyname) {
		return addcustomerRepository.findByCompanyName1(companyname);
	}
	
	@Override
	public String findCompanyNameById(Long id){
		 return addcustomerRepository.findCompanyNameById(id);
	 }

	@Override
	public Long findCompanyIDByCompanyName(String companyname) {
		return addcustomerRepository.findCompanyIDByCompanyName(companyname);
	}

	
}
