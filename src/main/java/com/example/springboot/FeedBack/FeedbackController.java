package com.example.springboot.FeedBack;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
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

import com.example.springboot.AddProjectCode.projectName;
import com.example.springboot.CompanyProfile.CompanyDetails;
import com.example.springboot.CompanyProfile.CompanyDetailsService;
import com.example.springboot.EmployeesDetails.Employee;
import com.example.springboot.EmployeesDetails.EmployeeService;
import com.example.springboot.Login.LoginUserAuthorityService;
import com.example.springboot.Login.LoginUserService;
import com.example.springboot.Login.Mainsidebarauthority;
import com.example.springboot.Login.MainsidebarauthorityService;

@Controller
public class FeedbackController {
	
	@Autowired
	private MainsidebarauthorityService mainsidebarauthorityService;
	@Autowired
	private LoginUserAuthorityService loginuserauthorityService;
	@Autowired
	private LoginUserService loginuserService;
	@Autowired
	private FeedbackService feedbackService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private CompanyDetailsService companyDetailsService;

	
	
	@GetMapping("/FeedbackForm")
	public String UserApplyForm(Model model, HttpServletRequest request){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
		model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		Feedback feedback = new Feedback();
		model.addAttribute("feedback", feedback);
		model.addAttribute("FeedbackList", feedbackService.listAll());
		model.addAttribute("UserName", employeeService.Update(request.getUserPrincipal().getName()));

    	int n = loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName()));
    	String status = "false";
    	if(n==5 || n==6) {
    		status = "true";
    	}
    	model.addAttribute("status", status);
    	model.addAttribute("ListUserName", employeeService.getAllEmployees());
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "FeedbackForm";
	}
	
	@GetMapping("/FeedbackList")
	public String ApplyFormList(Model model){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
		model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		model.addAttribute("FeedbackList", feedbackService.listAll());
		model.addAttribute("CompanyNameList" , companyDetailsService.listAll());
		model.addAttribute("ListUserName", employeeService.getAllEmployees());
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "FeedbackList";
	}
	
	@PostMapping("/SaveFeedbackForm")
	public String SaveFeedbackForm(@ModelAttribute("feedback") Feedback feedback,HttpServletRequest request){
		int employee_id = employeeService.Update(request.getUserPrincipal().getName());
		LocalDateTime mydate = LocalDateTime.now();
		DateTimeFormatter myformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy  hh:mm:ss");
		feedback.setCurrentdate(mydate.format(myformatter));
		feedback.setEmployee_id(employeeService.Update(request.getUserPrincipal().getName()));
		Employee c = employeeService.getEmployeeDataByEmailID(employee_id);
		feedback.setCompany_id(c.getCompany_id());
		feedbackService.Save(feedback);
		return "redirect:/FeedbackForm";
	}
	
	@PostMapping("/SaveEditFeedbackForm")
	public String SaveEditFeedbackForm(@ModelAttribute("feedback") Feedback feedback,HttpServletRequest request){
		feedbackService.Save(feedback);
		return "redirect:/FeedbackList";
	}
	@GetMapping("/edit-feedback-master-admin/{id}")
	public String EditFeedbackFormByMasterAdmin(@PathVariable(value="id")int id,Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
		model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		Feedback feedback = feedbackService.get(id);
		model.addAttribute("feedback", feedback);
		model.addAttribute("AuthList" , loginuserauthorityService.listAll());
		return "EditFeedbackByMasterAdmin";
	}
	
	@GetMapping("/EditFeedback/{id}")
	public String EditFeedbackForm(@PathVariable(value="id")int id,Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
		model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		Feedback feedback = feedbackService.get(id);
		model.addAttribute("feedback", feedback);
		model.addAttribute("AuthList" , loginuserauthorityService.listAll());
		return "EditFeedback";
	}
	
	@GetMapping("/DeleteFeedback/{id}")
	public String DeleteFeedbackForm(@PathVariable(value="id")int id,Model model) {
		this.feedbackService.delete(id);
		return "redirect:/FeedbackList";
	}

}
