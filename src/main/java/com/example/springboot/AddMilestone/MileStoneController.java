package com.example.springboot.AddMilestone;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

import com.example.springboot.AddCompany.AddCustomer;
import com.example.springboot.AddCompany.AddCustomerService;
import com.example.springboot.AddProjectCode.Category;
import com.example.springboot.AddProjectCode.CategoryService;
import com.example.springboot.AddProjectCode.projectName;
import com.example.springboot.AddProjectCode.projectService;
import com.example.springboot.EmployeesDetails.Employee;
import com.example.springboot.EmployeesDetails.EmployeeService;
import com.example.springboot.Login.LoginUserAuthorityService;
import com.example.springboot.Login.LoginUserService;
import com.example.springboot.Login.Mainsidebarauthority;
import com.example.springboot.Login.MainsidebarauthorityService;


@Controller
public class MileStoneController {
	
	@Autowired
	private AddmilestoneService  milestoneService;
	@Autowired
	private projectService ProjectService;
	@Autowired
	private AddCustomerService customerService;
	@Autowired
	private AddmilestoneServiceImpl milestoneserviceImpl;
	@Autowired
	private MainsidebarauthorityService mainsidebarauthorityService;
	@Autowired
	private LoginUserAuthorityService loginuserauthorityService;
	@Autowired
	private LoginUserService loginuserService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private CategoryService categoryService;
	
	
	@GetMapping("/addmilestone")
	public String showMilestoneForm(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		//create model attribute to bind from data
		model.addAttribute("milestonelist",milestoneserviceImpl.getAllmilestone());
		Addmilestone milestone=new Addmilestone();
		model.addAttribute("milestone", milestone);
		List<AddCustomer> cc=customerService.getAllCustomer();
		model.addAttribute("Customerlist", cc);
		List<projectName> p= ProjectService.getAllprojectName();
		model.addAttribute("Projectlist", p);
		List<Category> c1 = categoryService.listAll();
		model.addAttribute("Categorylist", c1);
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "addmilestone";
	}
	
	@PostMapping("/saveMilestone")
	public String saveMilestone(@ModelAttribute("milestone") Addmilestone  milestone, HttpServletRequest request) {
		//save project name to database
		LocalDateTime mydate = LocalDateTime.now();
		DateTimeFormatter myformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		milestone.setCurrentdate(mydate.format(myformatter));
		Employee c = employeeService.getEmployeeDataByEmailID(1);
		milestone.setCompany_id(c.getCompany_id());
		milestoneService.saveAllmilestone(milestone);
		return "redirect:/addmilestone";
		
	}   
	
	@GetMapping("/showMilestoneEditForm/{id}")
	public String showFormForEdit(@PathVariable(value="id")Integer id,Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		Addmilestone milestone=milestoneService.getMileStoneById(id);
		model.addAttribute("milestone", milestone);
		List<AddCustomer> cc=customerService.getAllCustomer();
		model.addAttribute("Customerlist", cc);
		List<projectName> p= ProjectService.getAllprojectName();
		model.addAttribute("Projectlist", p);
		List<Category> c1 = categoryService.listAll();
		model.addAttribute("Categorylist", c1);
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "edit_milestone";
	}
	
//	@GetMapping("/EditMilestone/{id}")
//	public String EditMilestone(@PathVariable(value="id")Integer id,Model model) {
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
//    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
//		Addmilestone milestone=milestoneService.getMileStoneById(id);
//		model.addAttribute("milestone", milestone);
////		List<projectName> p= ProjectService.getAllprojectName();
////		model.addAttribute("Projectlist", p);
////		List<AddCustomer> cc=customerService.getAllCustomer();
////		model.addAttribute("Customerlist", cc);
////		List<Category> c1 = categoryService.listAll();
////		model.addAttribute("Categorylist", c1);
//		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
//		return "EditMilestone";
//	}
	
	@GetMapping("/deleteMilestone/{id}")
	public String deleteMilestone(@PathVariable(value="id")Integer id) {
		//call delete  method
		this.milestoneService.deleteMilestoneById(id);
		return "redirect:/addmilestone";
		
	}
	
}
