package com.example.springboot.HelpDesk;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.swing.event.TableColumnModelListener;

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
import com.example.springboot.AddMilestone.Addmilestone;
import com.example.springboot.AddProjectCode.Category;
import com.example.springboot.AddProjectCode.CategoryService;
import com.example.springboot.AddProjectCode.ProjectNameAuthoritiesService;
import com.example.springboot.AddProjectCode.projectName;
import com.example.springboot.AddProjectCode.projectService;
import com.example.springboot.AddTask.AddTask;
import com.example.springboot.EmployeesDetails.Employee;
import com.example.springboot.EmployeesDetails.EmployeeService;
import com.example.springboot.Login.LoginUserAuthorityService;
import com.example.springboot.Login.LoginUserService;
import com.example.springboot.Login.Mainsidebarauthority;
import com.example.springboot.Login.MainsidebarauthorityService;
import com.example.springboot.ProjectStatus.ProjectStatus;
import com.example.springboot.ProjectStatus.ProjectStatusService;

@Controller
public class HelpDeskController {
	
	@Autowired
	private HelpDeskServiceImpl HelpDeskService;
	@Autowired
	private EmployeeService employeeService;
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
	private ProjectNameAuthoritiesService  projecNameAuthoritiesService;
	@Autowired
	private HelpDeskMapSevice helpDeskMapSevice;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ProjectNameAuthoritiesService projectNameAuthoritiesService;
	
	
	@GetMapping("/HelpDesk")
	public String HelpDesk(Model model,HttpServletRequest request) throws ParseException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		model.addAttribute("HelpDeskList", HelpDeskService.listAll());
		HelpDesk helpdesk = new HelpDesk();
		model.addAttribute("helpdesk",helpdesk);
		List<AddCustomer> c1 = customerService.getAllCustomer();
		model.addAttribute("CompanyList", c1);
		HashSet<projectName> t = new HashSet<projectName>();
		t.addAll(ProjectService.getAllprojectName());
		model.addAttribute("Projectlist", t);
		HashSet<Category> categories = new HashSet<Category>();
		categories.addAll(categoryService.listAll());
		model.addAttribute("Categorylist", categories);
		model.addAttribute("employee",employeeService.getAllEmployees());
		model.addAttribute("HelpDeskMap",helpDeskMapSevice.listAll());
//		model.addAttribute("UserName",employeeService.getEmployeeDataByEmailID(request.getUserPrincipal().getName()));
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "HelpDesk";
	}
	
	@PostMapping("/SaveHelpDesk")
	public String SaveHelpDesk(@ModelAttribute("helpdesk") HelpDesk helpdesk,@RequestParam(name="supportedBy",required=false)Integer[] empid, HttpServletRequest request) throws ParseException {
		int employee_id = employeeService.Update(request.getUserPrincipal().getName());
		List<HelpDesk> h =HelpDeskService.getPriorityLevel(helpdesk.getCompany_name_id(),helpdesk.getProject_id());
//		System.out.println("H : " + h.size());
		if(h.size()==0) {
			int n = 0;
			System.out.println("n : " + n);
			helpdesk.setPrioritylevel(n+1);
			helpdesk.setServiceRequestId(ProjectService.getProjectCode(helpdesk.getProject_id()) + "-00" + 001);
			LocalDateTime mydate = LocalDateTime.now();
			DateTimeFormatter myformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		    helpdesk.setCurrentdate(mydate.format(myformatter));
		    
		    SimpleDateFormat format_J = new SimpleDateFormat("dd-MM-yyyy");
			Date d1 = format_J.parse(helpdesk.getIssuedate());
			Date d2 = format_J.parse(helpdesk.getCurrentdate());
			long diff = (d2.getTime()-d1.getTime());
			long day1 = (diff / (24 * 60 * 60 * 1000));
			String Day = String.valueOf(day1) + "Days";
			helpdesk.setAgeing(Day);
			Employee c = employeeService.getEmployeeDataByEmailID(employee_id);
			helpdesk.setCompany_id(c.getCompany_id());
			// Check if completion is null before calling intValue()
			if (helpdesk.getCompletion() != null) {
			    helpdesk.setCompletionstatus("Completed"); // Assuming completion > 99 is considered "Completed"
			}
//			else if(helpdesk.getCompletion()>0 && helpdesk.getCompletion()<=99) {
//				helpdesk.setCompletionstatus("Inprogress");
//			}
			else {
			    helpdesk.setCompletion(0); // Set a default value, adjust as needed
			    helpdesk.setCompletionstatus("Yet to Start");
			}

			// Other code...

//			if(helpdesk.getCompletion()==0) {
//				helpdesk.setCompletionstatus("Yet to Start");
//			}
//			else if(helpdesk.getCompletion()>0 && helpdesk.getCompletion()<=99) {
//				helpdesk.setCompletionstatus("Inprogress");
//			}
//			else {
//				helpdesk.setCompletionstatus("Completed");
//			}
			HelpDeskService.save(helpdesk);
		}
		else {
		int n = h.get(h.size()-1).getPrioritylevel();
//		System.out.println("n : " + n);
		helpdesk.setPrioritylevel(n+1);
		int p= n+1;
		String n1 = String.valueOf(p);
//		System.out.println("n1 : " + n1);
		if(n1.length()==1) {
			helpdesk.setServiceRequestId(ProjectService.getProjectCode(helpdesk.getProject_id()) + "-00" + p);
		}
		if(n1.length()==2) {
			helpdesk.setServiceRequestId(ProjectService.getProjectCode(helpdesk.getProject_id()) + "-0" + p);
		}
		if(n1.length()==3) {
			helpdesk.setServiceRequestId(ProjectService.getProjectCode(helpdesk.getProject_id()) + "-" + p);
		}
		LocalDateTime mydate = LocalDateTime.now();
		DateTimeFormatter myformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");;
	    helpdesk.setCurrentdate(mydate.format(myformatter));
	    
	    SimpleDateFormat format_J = new SimpleDateFormat("dd-MM-yyyy");
		Date d1 = format_J.parse(helpdesk.getIssuedate());
		Date d2 = format_J.parse(helpdesk.getCurrentdate());
		long diff = (d2.getTime()-d1.getTime());
		long day1 = (diff / (24 * 60 * 60 * 1000));
		String Day = String.valueOf(day1) + "Days";
		helpdesk.setAgeing(Day);
		Employee c = employeeService.getEmployeeDataByEmailID(employee_id);
		helpdesk.setCompany_id(c.getCompany_id());
		if (helpdesk.getCategory1()!= null) {
			helpdesk.setCompletion(helpdesk.getCompletion());
	    } else {
			helpdesk.setCompletion(0);	
	    }
		if (helpdesk.getCompletion() != null) {
		    helpdesk.setCompletionstatus("Completed"); // Assuming completion > 99 is considered "Completed"
		}
//		else if(helpdesk.getCompletion()>0 && helpdesk.getCompletion()<=99) {
//			helpdesk.setCompletionstatus("Inprogress");
//		}
		else {
		    helpdesk.setCompletion(0); // Set a default value, adjust as needed
		    helpdesk.setCompletionstatus("Yet to Start");
		}
//		if(helpdesk.getCompletion()==0) {
//			helpdesk.setCompletionstatus("Yet to Start");
//		}
//		else if(helpdesk.getCompletion()>0 && helpdesk.getCompletion()<=99) {
//			helpdesk.setCompletionstatus("Inprogress");
//		}
//		else {
//			helpdesk.setCompletionstatus("Completed");
//		}
		HelpDeskService.save(helpdesk);
		}
		if (empid!= null) {
			for(Integer p:empid) {
//				System.out.println("empname" + p);
				helpDeskMapSevice.insertDataIntoHelpDeskMap(helpdesk.getId(), p);
			}
	    } else {
			helpDeskMapSevice.insertDataIntoHelpDeskMap(helpdesk.getId(), 0);	
	    }
		
		return "redirect:/HelpDesk";	
	}
	
	@PostMapping("/SaveEditHelpDesk")
	public String SaveEditHelpDesk(@ModelAttribute("helpdesk") HelpDesk helpdesk, HttpServletRequest request) throws ParseException {
		
		Employee c = employeeService.getEmployeeDataByEmailID(1);
		helpdesk.setCompany_id(c.getCompany_id());
		if (helpdesk.getCategory1()!= null) {
			helpdesk.setCompletion(helpdesk.getCompletion());
	    } else {
			helpdesk.setCompletion(0);	
	    }
		if(helpdesk.getCompletion()==0) {
			helpdesk.setCompletionstatus("Yet to Start");
		}
		else if(helpdesk.getCompletion()>0 && helpdesk.getCompletion()<=99) {
			helpdesk.setCompletionstatus("Inprogress");
		}
		else {
			helpdesk.setCompletionstatus("Completed");
		}
		HelpDeskService.save(helpdesk);
		return "redirect:/HelpDeskList";
		
	}
	
	@PostMapping("/SaveEditHelpDeskByAdmin")
	public String SaveEditHelpDeskByAdmin(@ModelAttribute("helpdesk") HelpDesk helpdesk,@RequestParam(name="supportedBy",required=false)Integer[] empid, HttpServletRequest request) throws ParseException {
		int employee_id = employeeService.Update(request.getUserPrincipal().getName());
		Employee c = employeeService.getEmployeeDataByEmailID(employee_id);
		helpdesk.setCompany_id(c.getCompany_id());
		if (helpdesk.getCategory1()!= null) {
			helpdesk.setCompletion(helpdesk.getCompletion());
	    } else {
			helpdesk.setCompletion(0);	
	    }
		if(helpdesk.getCompletion()==0) {
			helpdesk.setCompletionstatus("Yet to Start");
		}
		else if(helpdesk.getCompletion()>0 && helpdesk.getCompletion()<=99) {
			helpdesk.setCompletionstatus("Inprogress");
		}
		else {
			helpdesk.setCompletionstatus("Completed");
		}
		HelpDeskService.save(helpdesk);
		if (empid!= null) {
			for(Integer p1:empid) {
//				System.out.println("empname" + p1);
				helpDeskMapSevice.insertDataIntoHelpDeskMap(helpdesk.getId(), p1);
			}
	    } else {
			helpDeskMapSevice.insertDataIntoHelpDeskMap(helpdesk.getId(), 0);	
	    }
		return "redirect:/HelpDesk";
		
	}
	
	@GetMapping("/EditHelpDeskForm/{id}")
	public String EditHelpDeskForm(@PathVariable(value="id")int id,Model model,HttpServletRequest request) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		HelpDesk helpdesk = HelpDeskService.get(id);
//		helpdesk.setAssignedToEmail(request.getUserPrincipal().getName());
		model.addAttribute("helpdesk", helpdesk);
		List<AddCustomer> c1 = customerService.getAllCustomer();
		model.addAttribute("CompanyList", c1);
		HashSet<projectName> t = new HashSet<projectName>();
		t.addAll(ProjectService.getAllprojectName());
		model.addAttribute("Projectlist", t);
		HashSet<Category> categories = new HashSet<Category>();
		categories.addAll(categoryService.listAll());
		model.addAttribute("Categorylist", categories);
		model.addAttribute("employee",employeeService.getAllEmployees());
		model.addAttribute("HelpDeskMap",helpDeskMapSevice.listAll());
		return "EditHelpDesk"; 
	}
	
	@GetMapping("/EditHelpDeskByAdmin/{id}")
	public String EditHelpDeskFormByAdmin(@PathVariable(value="id")int id,Model model,HttpServletRequest request) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		HelpDesk helpdesk = HelpDeskService.get(id);
		model.addAttribute("helpdesk", helpdesk);
		List<AddCustomer> c1 = customerService.getAllCustomer();
		model.addAttribute("CompanyList", c1);
		HashSet<projectName> t = new HashSet<projectName>();
		t.addAll(ProjectService.getAllprojectName());
		model.addAttribute("Projectlist", t);
		HashSet<Category> categories = new HashSet<Category>();
		categories.addAll(categoryService.listAll());
		model.addAttribute("Categorylist", categories);
		List<Employee> e=employeeService.getAllEmployees();
		model.addAttribute("employee",e);
		model.addAttribute("HelpDeskMap",helpDeskMapSevice.listAll());
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "EditHelpDeskByAdmin"; 
	}
	
	
	@GetMapping("/deleteHelpDeskForm/{id}")
	public String deleteHelpDeskForm(@PathVariable(value="id")int id) {
		//call delete method
		this.HelpDeskService.deleteHelpDesk(id);
		return "redirect:/HelpDesk";
		      
	}  
	

	@GetMapping("/getRequestIDdropdown")
	public @ResponseBody List<String> ViewRequestIDdropdown(@RequestParam(name="name",required=false)String name,Model model) {
		return ProjectService.findprojectcode(name);				
	}
	
	@GetMapping("/getAssignToDropdown")
	public @ResponseBody List<Employee> ViewAssignToDropdown(@RequestParam(name="name1",required=false)Integer name1, Model model) {

		Set<Integer> employeeIds = new HashSet<>();
	    List<String> employeeIdStrings = projecNameAuthoritiesService.getEmployeeIDListByProjectID(name1);
	    for (String employeeIdString : employeeIdStrings) {
	        employeeIds.add(Integer.parseInt(employeeIdString));
	    }
	    List<String> employeeNames = employeeService.getEmployeeNameListByProjectID(employeeIds);
	    List<Employee> employees = new LinkedList<Employee>();
	    for (String employeeName : employeeNames) {
	        employees.add(employeeService.getEmployeeDataByEmailID(1));
	    }
//	    System.out.println("Employees: " + employees);
		return employees;
	}

//	@GetMapping("/getprojectNamedropdownByCompanyName")
//	public @ResponseBody HashSet<String> ViewProjectName(@RequestParam(name="name",required=false)String name,Model model) {
//		HashSet<String> p = new HashSet<String>();
//		p.addAll(ProjectService.findprojectNameByCompanyName(name));
//		System.out.println("P: " + p);
//		return p;				
//	}
//	
//	@GetMapping("/getProjectCodeByProjectName")
//	public @ResponseBody HashSet<String> View(@RequestParam(name="name",required=false)String name,Model model) {
//		HashSet<String> p = new HashSet<String>();
//		p.addAll(ProjectService.findprojectcodeByProjectName(name));
//		return p;				
//	}
	
	@GetMapping("/HelpDeskList")
	public String HelpDeskList(Model model,HttpServletRequest request) throws ParseException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		model.addAttribute("HelpDeskList", HelpDeskService.getListByEmployeeName(employeeService.Update(request.getUserPrincipal().getName())));
		model.addAttribute("username", request.getUserPrincipal().getName());
		List<HelpDesk> h1 = HelpDeskService.getListByEmployeeName(employeeService.Update(request.getUserPrincipal().getName()));
		for(HelpDesk h:h1) {
//			HelpDesk helpdesk1 = new HelpDesk();
			h.setId(h.getId());
			LocalDateTime mydate = LocalDateTime.now();
			DateTimeFormatter myformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
//		    h.setPreviousdate(h.getCurrentdate().formatted(myformatter));
		    h.setCurrentdate(mydate.format(myformatter));
		    
		    SimpleDateFormat format_J = new SimpleDateFormat("dd-MM-yyyy");
			Date d1 = format_J.parse(h.getIssuedate());
			Date d2 = format_J.parse(h.getCurrentdate());
			long diff = (d2.getTime()-d1.getTime());
			long day1 = (diff / (24 * 60 * 60 * 1000));
			String Day = String.valueOf(day1) + "Days";
//			System.out.println("Day : " + Day);
			h.setAgeing(Day.replaceAll("[^a-zA-Z0-9]",""));
			HelpDeskService.save(h);
		}
		List<AddCustomer> c1 = customerService.getAllCustomer();
		model.addAttribute("CompanyList", c1);
		HashSet<projectName> t = new HashSet<projectName>();
		t.addAll(ProjectService.getAllprojectName());
		model.addAttribute("Projectlist", t);
		HashSet<Category> categories = new HashSet<Category>();
		categories.addAll(categoryService.listAll());
		model.addAttribute("Categorylist", categories);
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		model.addAttribute("employee",employeeService.getAllEmployees());
		model.addAttribute("HelpDeskMap",helpDeskMapSevice.listAll());
		return "HelpDeskList";
		      
	}  
	
	@GetMapping("/HelpDeskReport")
	public String HelpDeskReport(Model model) throws ParseException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		model.addAttribute("HelpDeskList", HelpDeskService.listAll());
		List<AddCustomer> c1 = customerService.getAllCustomer();
		model.addAttribute("CompanyList", c1);
		HashSet<projectName> t = new HashSet<projectName>();
		t.addAll(ProjectService.getAllprojectName());
		model.addAttribute("Projectlist", t);
		HashSet<Category> categories = new HashSet<Category>();
		categories.addAll(categoryService.listAll());
		model.addAttribute("Categorylist", categories);
		model.addAttribute("HelpDeskMap",helpDeskMapSevice.listAll());
		model.addAttribute("employee",employeeService.getAllEmployees());
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		model.addAttribute("HelpDeskMap",helpDeskMapSevice.listAll());
		return "HelpDeskReport";
	}
}
