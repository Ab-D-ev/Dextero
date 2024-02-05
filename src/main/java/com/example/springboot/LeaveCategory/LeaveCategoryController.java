package com.example.springboot.LeaveCategory;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.springboot.CompanyProfile.CompanyDetails;
import com.example.springboot.CompanyProfile.CompanyDetailsService;
import com.example.springboot.EmployeesDetails.Employee;
import com.example.springboot.EmployeesDetails.EmployeeService;
import com.example.springboot.Holidays.HolidayController;
import com.example.springboot.Login.LoginUserAuthorityService;
import com.example.springboot.Login.LoginUserService;
import com.example.springboot.Login.Mainsidebarauthority;
import com.example.springboot.Login.MainsidebarauthorityService;

@Controller
public class LeaveCategoryController {
	
	@Autowired
	private LeaveCategoryService leaveCategoryService;
	@Autowired
	private MainsidebarauthorityService mainsidebarauthorityService;
	@Autowired
	private LoginUserAuthorityService loginuserauthorityService;
	@Autowired
	private LoginUserService loginuserService;
	@Autowired
	private CompanyDetailsService companyDetailsService;
	@Autowired
	private EmployeeService employeeService;
		
	
	@GetMapping("/leaveCategory")
	public String LeaveCategory(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		model.addAttribute("LeaveCategoryList",leaveCategoryService.listAll());
		LeaveCategory category = new LeaveCategory();
		model.addAttribute("leavecategory", category);
		model.addAttribute("CompanyID",companyDetailsService.getCompanyID(auth.getName()));
		return "leaveCategory";
	}
	
	@PostMapping("/saveleavecategory")
	public String saveleavecategory(@ModelAttribute("category") LeaveCategory category,HttpServletRequest request){
		//save to database
		Employee c = employeeService.getEmployeeDataByEmailID(1);
		category.setCompany_id(c.getCompany_id());
		leaveCategoryService.Save(category);
		return "redirect:/leaveCategory";
	    
	} 
	
	@GetMapping("/editleavecategory/{id}")
	public String EditLeaveCategory(@PathVariable(value="id")Integer id,Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		LeaveCategory category = leaveCategoryService.get(id);
		model.addAttribute("leavecategory", category);
		return "EditLeaveCategory";

	}
	@GetMapping("/deleteleavecategory/{id}")
	public String DeleteLeaveCategory(@PathVariable(value="id")Integer id) {
		//call delete method
		this.leaveCategoryService.delete(id);
		return "redirect:/leaveCategory";
		
	}
}
