package com.example.springboot.WorkSchedule;

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

import com.example.springboot.EmployeesDetails.EmployeeService;
import com.example.springboot.Login.LoginUserAuthorityService;
import com.example.springboot.Login.LoginUserService;
import com.example.springboot.Login.Mainsidebarauthority;
import com.example.springboot.Login.MainsidebarauthorityService;

@Controller
public class WorkScheduleController {
	
	@Autowired
	private MainsidebarauthorityService mainsidebarauthorityService;
	@Autowired
	private LoginUserAuthorityService loginuserauthorityService;
	@Autowired
	private LoginUserService loginuserService;
	@Autowired
	private WorkScheduleService workScheduleService;
	@Autowired
	private EmployeeService employeeService;
	
	
	@GetMapping("/work_schedule")
	public String WorkSchedule(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
		model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		WorkSchedule workSchedule = new WorkSchedule();
		model.addAttribute("workSchedule", workSchedule);		
		model.addAttribute("WorkScheduleList", workScheduleService.listAll());
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "workSchedule";
	}

	@PostMapping("/save_work_schedule")
	public String SaveWorkSchedule(@ModelAttribute("workSchedule") WorkSchedule workSchedule,HttpServletRequest request){
		//save to database
		int c = employeeService.getCompanyID(request.getUserPrincipal().getName());
		workSchedule.setCompany_id(c);
		workScheduleService.SaveWorkSchedule(workSchedule);
		return "redirect:/work_schedule";
	}   
	
	@GetMapping("/edit_work_schedule/{schedule_id}")
	public String EditWorkSchedule(@PathVariable(value="schedule_id")Integer id,Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
		model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		WorkSchedule workSchedule= workScheduleService.get(id);
		model.addAttribute("workSchedule", workSchedule);
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "EditWorkSchedule";	
	}
	
	@GetMapping("/delete_work_schedule/{schedule_id}")
	public String deleteWorkSchedule(@PathVariable(value="schedule_id")Integer id) {
		//call delete  method
		this.workScheduleService.delete(id);
		return "redirect:/work_schedule";
	
	}
}
