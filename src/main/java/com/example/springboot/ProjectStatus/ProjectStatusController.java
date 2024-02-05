package com.example.springboot.ProjectStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.springboot.AddCompany.AddCustomer;
import com.example.springboot.AddCompany.AddCustomerService;
import com.example.springboot.AddProjectCode.projectName;
import com.example.springboot.AddProjectCode.projectService;
import com.example.springboot.CompanyProfile.CompanyDetails;
import com.example.springboot.CompanyProfile.CompanyDetailsService;
import com.example.springboot.EmployeesDetails.Employee;
import com.example.springboot.EmployeesDetails.EmployeeService;
import com.example.springboot.Login.LoginUserAuthorityService;
import com.example.springboot.Login.LoginUserService;
import com.example.springboot.Login.Mainsidebarauthority;
import com.example.springboot.Login.MainsidebarauthorityService;

@Controller
public class ProjectStatusController {

	@Autowired
	private MainsidebarauthorityService mainsidebarauthorityService;
	@Autowired
	private LoginUserAuthorityService loginuserauthorityService;
	@Autowired
	private LoginUserService loginuserService;
	@Autowired
	private ProjectStatusService pstatusService;
	@Autowired
	private projectService ProjectService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private AddCustomerService customerService;
	
	
	@GetMapping("/ProjectStatus")
	public String ShowProjectStatus(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
    	ProjectStatus project = new ProjectStatus();
		model.addAttribute("project", project);		
		model.addAttribute("ProjectStatusList", pstatusService.listAll());
		List<AddCustomer> cc= customerService.getAllCustomer();
		model.addAttribute("Customerlist", cc);
		List<projectName> pname = ProjectService.getAllprojectName();
		model.addAttribute("ProjectList", pname);
		List<Employee> empname = employeeService.getAllEmployees();
		model.addAttribute("EmployeeList", empname);
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "ProjectStatus";
	}
	
	@PostMapping("/SaveProjectStatus")
	public String SaveEmploymentType(@ModelAttribute("project") ProjectStatus project,@RequestParam("empname") String empname, HttpServletRequest request) throws ParseException{
		//save to database
		int employee_id = employeeService.Update(request.getUserPrincipal().getName());
		Employee e1=employeeService.get(Integer.parseInt(empname));
		System.out.println("name : " + e1.getFirstname());
		project.setEname(e1.getFirstname()+ " " + e1.getLastname());
		project.setUsername(e1.getEmail());
		LocalDateTime mydate = LocalDateTime.now();
		DateTimeFormatter myformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		project.setCurrentdate(mydate.format(myformatter));
		SimpleDateFormat myformatter1 = new SimpleDateFormat("yyyy-MM-dd");
    	SimpleDateFormat myformatter2 = new SimpleDateFormat("dd-MM-yyyy");
    	Date d1 = myformatter2.parse(project.getCurrentdate());
		project.setDateyear(myformatter1.format(d1));
		Employee c = employeeService.getEmployeeDataByEmailID(employee_id);
		project.setCompany_id(c.getCompany_id());
		pstatusService.Save(project);
		return "redirect:/ProjectStatus";
	}   
	
	@GetMapping("/EditProjectStatus/{id}")
	public String EditProjectStatus(@PathVariable(value="id")Integer id,Model model) throws ParseException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
    	ProjectStatus project = pstatusService.get(id);
    	LocalDateTime mydate = LocalDateTime.now();
		DateTimeFormatter myformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		project.setCurrentdate(mydate.format(myformatter));
		SimpleDateFormat myformatter1 = new SimpleDateFormat("yyyy-MM-dd");
    	SimpleDateFormat myformatter2 = new SimpleDateFormat("dd-MM-yyyy");
    	Date d1 = myformatter2.parse(project.getCurrentdate());
		project.setDateyear(myformatter1.format(d1));
    	model.addAttribute("project", project);
    	List<AddCustomer> cc= customerService.getAllCustomer();
		model.addAttribute("Customerlist", cc);
		List<projectName> pname = ProjectService.getAllprojectName();
		model.addAttribute("ProjectList", pname);
		List<Employee> empname = employeeService.getAllEmployees();
		model.addAttribute("EmployeeList", empname);
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "EditProjectStatus";	
	}
	
	@GetMapping("/deleteProjectStatus/{id}")
	public String deleteProjectStatus(@PathVariable(value="id")Integer id) {
		//call delete  method
		this.pstatusService.delete(id);
		return "redirect:/ProjectStatus";
		
	}
	
	@GetMapping("/getProjectName")
	public @ResponseBody HashSet<String> ProjectName(@RequestParam(name="name",required=false)String name,Model model) {
		HashSet<String> p = new HashSet<String>();
		p.addAll(ProjectService.findprojectcode(name));
		return p;				
	}
	
	@GetMapping("/ProjectStatusReport")
	public String ShowProjectStatusReport(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		model.addAttribute("ProjectStatusList", pstatusService.listAll());
		List<Employee> empname = employeeService.getAllEmployees();
		model.addAttribute("EmployeeList", empname);
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "ProjectStatusReport";
	}
}
