package com.example.springboot.DefineProjectPlan;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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
import com.example.springboot.AddMilestone.Addmilestone;
import com.example.springboot.AddMilestone.AddmilestoneService;
import com.example.springboot.AddMilestone.AddmilestoneServiceImpl;
import com.example.springboot.AddProjectCode.Category;
import com.example.springboot.AddProjectCode.CategoryService;
import com.example.springboot.AddProjectCode.projectName;
import com.example.springboot.AddProjectCode.projectService;
import com.example.springboot.AddTask.AddTask;
import com.example.springboot.AddTask.TaskService;
import com.example.springboot.AddTask.TaskServiceImpl;
import com.example.springboot.CompanyProfile.CompanyDetails;
import com.example.springboot.CompanyProfile.CompanyDetailsService;
import com.example.springboot.EmployeesDetails.Employee;
import com.example.springboot.EmployeesDetails.EmployeeService;
import com.example.springboot.Login.LoginUserAuthorityService;
import com.example.springboot.Login.LoginUserService;
import com.example.springboot.Login.Mainsidebarauthority;
import com.example.springboot.Login.MainsidebarauthorityService;

@Controller
public class ViewProjectController {
	
	@Autowired
	private ViewProjectServiceImpl viewprojectservice;
	@Autowired
	private AddmilestoneService  milestoneService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private TaskServiceImpl taskServiceImpl;
	@Autowired
	private  AddmilestoneServiceImpl  milestoneServiceImpl;
	@Autowired
	private AddCustomerService customerService;
	@Autowired
	private projectService ProjectService;
	@Autowired
	private MainsidebarauthorityService mainsidebarauthorityService;
	@Autowired
	private LoginUserAuthorityService loginuserauthorityService;
	@Autowired
	private LoginUserService loginuserService;
	@Autowired
	private CategoryService categoryService;
	
	
	
	@GetMapping("/ViewProject")
	public String ViewProject(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		List<ViewProject> ViewProjectList = viewprojectservice.listAll();
		model.addAttribute("ViewProjectList", ViewProjectList);
		ViewProject viewproject = new ViewProject();
		model.addAttribute("viewproject", viewproject);
		List<AddCustomer> c1 = customerService.getAllCustomer();
		model.addAttribute("CompanyList", c1);
		HashSet<projectName> t = new HashSet<projectName>();
		t.addAll(ProjectService.getAllprojectName());
		model.addAttribute("Projectlist", t);
		HashSet<Category> categories = new HashSet<Category>();
		categories.addAll(categoryService.listAll());
		model.addAttribute("Categorylist", categories);
		List<Employee> e=employeeService.getAllEmployees();
		model.addAttribute("employeeList", e);
		List<Addmilestone> m= milestoneService.getAllmilestone();
		model.addAttribute("list", m);
		List<AddTask> ta=taskService.getAllTask();
		model.addAttribute("tasklist", ta);
		//System.out.println("EndDate" + viewprojectservice.selectDate());
		model.addAttribute("EndDate",viewprojectservice.selectDate());
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "ViewProject";
	}
	
	@PostMapping("/save")
	public String saveViewProject(@ModelAttribute("viewproject") ViewProject viewproject,@RequestParam("startdate")String startdate
			, HttpServletRequest request) {
		//save to database
		LocalDateTime mydate = LocalDateTime.now();
		DateTimeFormatter myformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		viewproject.setCurrentdate(mydate.format(myformatter));
		viewproject.setStartdate(startdate);
		Employee c = employeeService.getEmployeeDataByEmailID(1);
		viewproject.setCompany_id(c.getCompany_id());
		if (viewproject.getTask_id()!= null) {
			viewproject.setTask_id(viewproject.getTask_id());
	    } else {
			viewproject.setTask_id(0);
	    }
		viewprojectservice.save(viewproject);
		return "redirect:/ViewProject";
	}
	
	@PostMapping("/SaveEditViewProject")
	public String saveViewProject(@ModelAttribute("viewproject") ViewProject viewproject,HttpServletRequest request) {
		//save to database
//		Employee e1=employeeService.get(Integer.parseInt(empname));
//		System.out.println("name : " + e1.getFirstname());
//		viewproject.setEmpname(e1.getFirstname());
//		viewproject.setEmployeeEmail(e1.getEmail());
//		Employee e1=employeeService.getEmployeeById(employeeService.Update(viewproject.getEmployeeEmail()));
//  	System.out.println("name : " + e1.getFirstname());
//		viewproject.setEmpname(empname);
		LocalDateTime mydate = LocalDateTime.now();
		DateTimeFormatter myformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		viewproject.setCurrentdate(mydate.format(myformatter));
		Employee c = employeeService.getEmployeeDataByEmailID(1);
		viewproject.setCompany_id(c.getCompany_id());
		viewprojectservice.save(viewproject);
		return "redirect:/ViewProject";
	}
	
	@GetMapping("/EditViewProject/{id}")
	public String EditForm(@PathVariable(value="id")int id,Model model,HttpServletRequest request) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		ViewProject viewproject = viewprojectservice.get(id);
		model.addAttribute("viewproject", viewproject);
		List<AddCustomer> c1 = customerService.getAllCustomer();
		model.addAttribute("CompanyList", c1);
		HashSet<projectName> t = new HashSet<projectName>();
		t.addAll(ProjectService.getAllprojectName());
		model.addAttribute("Projectlist", t);
		HashSet<Category> categories = new HashSet<Category>();
		categories.addAll(categoryService.listAll());
		model.addAttribute("Categorylist", categories);
		List<Employee> e=employeeService.getAllEmployees();
		model.addAttribute("employeeList", e);
		List<Addmilestone> m= milestoneService.getAllmilestone();
		model.addAttribute("list", m);
		List<AddTask> ta=taskService.getAllTask();
		model.addAttribute("tasklist", ta);
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "EditViewProject";		 
	}
	
	@GetMapping("/deleteViewProject/{id}")
	public String deleteForm(@PathVariable(value="id")int id) {
		//call delete method
		this.viewprojectservice.deleteViewProject(id);
		return "redirect:/ViewProject";	      
	}  


	@GetMapping("/listofProject")
	public String AllDefineProjectPlan(Model model,@Param("companyname") String companyname, @Param("projectcode") String projectcode,
			@Param("empname") String empname, @Param("fromdate") String fromdate, @Param("todate") String todate) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
    	LocalDateTime mydate = LocalDateTime.now();
		DateTimeFormatter myformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    	int year= mydate.getYear();
		int month= mydate.getMonthValue();
		Calendar calendar = Calendar.getInstance();
	    calendar.set(1, month - 1, year);
//    	System.out.println("fromdate : " + "01-0" + month + "-" + year);
//    	System.out.println("todate" + mydate.format(myformatter));
    	if(month <= 9) {
    		if(fromdate == null || todate == null) {
    			List<ViewProject> ViewProjectList1 = viewprojectservice.listAll();
    			model.addAttribute("ViewProjectList",ViewProjectList1);
    			model.addAttribute("companyname",companyname);
    			model.addAttribute("projectcode",projectcode);
    			model.addAttribute("empname",empname);
    			model.addAttribute("fromdate", "01-0" + month + "-" + year);
    			model.addAttribute("todate",mydate.format(myformatter));	
    		}
    		else {
    			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    			LocalDate FromDate = LocalDate.parse(fromdate, formatter);
    			LocalDate ToDate = LocalDate.parse(todate, formatter);
    			List<ViewProject> ViewProjectList = new LinkedList<>();
    			List<ViewProject> ViewProjectList1 = viewprojectservice.listAll();
    			for(ViewProject v:ViewProjectList1) {
    				LocalDate date1 = LocalDate.parse(v.getStartdate(), formatter);
    				LocalDate date2 = LocalDate.parse(v.getEnddate(), formatter);
    				if(date1.compareTo(FromDate)>=0 && date2.compareTo(ToDate)<=0) {
    					ViewProjectList.add(v);
    				}
    			}
    			model.addAttribute("ViewProjectList",ViewProjectList);
    			model.addAttribute("companyname",companyname);
    			model.addAttribute("projectcode",projectcode);
    			model.addAttribute("empname",empname);
    			model.addAttribute("fromdate",fromdate);
    			model.addAttribute("todate",todate);
    		}
    	}
    	else {
    		if(fromdate == null || todate == null) {
    			List<ViewProject> ViewProjectList1 = viewprojectservice.listAll();
    			model.addAttribute("ViewProjectList",ViewProjectList1);
    			model.addAttribute("companyname",companyname);
    			model.addAttribute("projectcode",projectcode);
    			model.addAttribute("empname",empname);
    			model.addAttribute("fromdate", "01-" + month + "-" + year);
    			model.addAttribute("todate",mydate.format(myformatter));	
    		}
    		else {
    			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    			LocalDate FromDate = LocalDate.parse(fromdate, formatter);
    			LocalDate ToDate = LocalDate.parse(todate, formatter);
    			List<ViewProject> ViewProjectList = new LinkedList<>();
    			List<ViewProject> ViewProjectList1 = viewprojectservice.listAll();
    			for(ViewProject v:ViewProjectList1) {
    				LocalDate date1 = LocalDate.parse(v.getStartdate(), formatter);
    				LocalDate date2 = LocalDate.parse(v.getEnddate(), formatter);
    				if(date1.compareTo(FromDate)>=0 && date2.compareTo(ToDate)<=0) {
    					ViewProjectList.add(v);
    				}
    			}
    			model.addAttribute("ViewProjectList",ViewProjectList);
    			model.addAttribute("companyname",companyname);
    			model.addAttribute("projectcode",projectcode);
    			model.addAttribute("empname",empname);
    			model.addAttribute("fromdate",fromdate);
    			model.addAttribute("todate",todate);
    		}
    	}
    	List<AddCustomer> c1 = customerService.getAllCustomer();
		model.addAttribute("CompanyList", c1);
		HashSet<projectName> t = new HashSet<projectName>();
		t.addAll(ProjectService.getAllprojectName());
		model.addAttribute("Projectlist", t);
		HashSet<Category> categories = new HashSet<Category>();
		categories.addAll(categoryService.listAll());
		model.addAttribute("Categorylist", categories);
		List<Employee> e=employeeService.getAllEmployees();
		model.addAttribute("employeeList", e);
		List<Addmilestone> m= milestoneService.getAllmilestone();
		model.addAttribute("list", m);
		List<AddTask> ta=taskService.getAllTask();
		model.addAttribute("tasklist", ta);
    	model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "AllDefineProjectPlan";
	}

	@GetMapping("/getprojectnamedropdown2")
	public @ResponseBody HashSet<String> Viewprojectnamepage(@RequestParam(name="name",required=false)String name,Model model) {
		HashSet<String> p = new HashSet<String>();
		p.addAll(ProjectService.findprojectNameByCompanyName(name));
	return p;				
	}
	
	@GetMapping("/getprojectCodedropdown2")
	public @ResponseBody HashSet<String> Viewprojectcodepage(@RequestParam(name="name",required=false)String name,Model model) {
		HashSet<String> p = new HashSet<String>();
		p.addAll(ProjectService.findprojectcodeByProjectName(name));
		return p;				
 
	}

//	@GetMapping("/getTaskdropdown2")
//	public @ResponseBody List<String> ViewTaskpage(@RequestParam(name="name",required=false)String name,
//			@RequestParam(name="name1",required=false)String name1,@RequestParam(name="name2",required=false)String name2,Model model) {
//	return taskServiceImpl.findTaskByCompanyProjectNameMilestone(name, name1, name2);				
//	}		

}
