package com.example.springboot.AdhocTask;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
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
import com.example.springboot.AddMilestone.Addmilestone;
import com.example.springboot.AddMilestone.AddmilestoneService;
import com.example.springboot.AddMilestone.AddmilestoneServiceImpl;
import com.example.springboot.AddProjectCode.Category;
import com.example.springboot.AddProjectCode.CategoryService;
import com.example.springboot.AddProjectCode.ProjectNameAuthoritiesService;
import com.example.springboot.AddProjectCode.projectName;
import com.example.springboot.AddProjectCode.projectService;
import com.example.springboot.AddTask.AddTask;
import com.example.springboot.AddTask.TaskService;
import com.example.springboot.EmployeesDetails.Employee;
import com.example.springboot.EmployeesDetails.EmployeeService;
import com.example.springboot.Login.LoginUserAuthorityService;
import com.example.springboot.Login.LoginUserService;
import com.example.springboot.Login.Mainsidebarauthority;
import com.example.springboot.Login.MainsidebarauthorityService;


@Controller
public class AssignTaskController {
	
	@Autowired
	private  AssignTaskServiceImpl assignTaskService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private MainsidebarauthorityService mainsidebarauthorityService;
	@Autowired
	private LoginUserAuthorityService loginuserauthorityService;
	@Autowired
	private LoginUserService loginuserService;
	@Autowired
	private AddCustomerService customerService;
	@Autowired
	private ProjectNameAuthoritiesService projectNameAuthoritiesService;
	@Autowired
	private projectService ProjectService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private AddmilestoneService  milestoneService;
	@Autowired
	private AddmilestoneServiceImpl addmilestoneServiceImpl;
	
	@GetMapping("/assignTask")
	public String AssignTask(Model model, HttpServletRequest request) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		model.addAttribute("AssignTaskList",assignTaskService.listAll());
		AssignTask assigntask = new AssignTask();
		model.addAttribute("assigntask", assigntask);
		List<AddCustomer> c=customerService.getAllCustomer();
		model.addAttribute("Customerlist", c);
		List<projectName> p= ProjectService.getAllprojectName();
		model.addAttribute("Projectlist", p);
		List<Category> c1 = categoryService.listAll();
		model.addAttribute("Categorylist", c1);
//		remove duplicate records
		HashMap<String, Addmilestone> uniqueMap = new HashMap<>();
        for (Addmilestone m : milestoneService.getAllmilestone()) {
            uniqueMap.putIfAbsent(m.getAdd_milestone(), m);
        }
        List<Addmilestone> m1 = new ArrayList<>(uniqueMap.values());
        model.addAttribute("list", m1);
        model.addAttribute("Milestonelist", milestoneService.getAllmilestone());
        model.addAttribute("Tasklist", taskService.getAllTask());
		List<Employee> e=employeeService.getAllEmployees();
		model.addAttribute("employeeList", e);
		int n = loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName()));
    	String status = "false";
    	if(n==3) {
    		status = "true";
    	}
    	model.addAttribute("status", status);
    	model.addAttribute("UserName",employeeService.Update(request.getUserPrincipal().getName()));
    	model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "assignTask";	
	}
	
	@PostMapping("/saveAssignTask")
	public String saveAssignTask(@ModelAttribute("assigntask") AssignTask assigntask, HttpServletRequest request) throws ParseException {
		//save to database
		SimpleDateFormat myformatter = new SimpleDateFormat("yyyy-MM-dd");
    	SimpleDateFormat myformatter2 = new SimpleDateFormat("dd-MM-yyyy");
    	Date d1 = myformatter2.parse(assigntask.getStartDate());
    	Date d2 = myformatter2.parse(assigntask.getEndDate());
    	assigntask.setStartYear(myformatter.format(d1));
    	assigntask.setEndYear(myformatter.format(d2));
    	LocalDate startDate = d1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    	LocalDate endDate = d2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    	// Calculate the difference in days
    	long daysDifference = ChronoUnit.DAYS.between(startDate, endDate);
    	// Set the number of days in AssignTask object
    	assigntask.setNumber_of_days((int) daysDifference);
		assigntask.setUpdated_start_date("");
		assigntask.setUpdated_end_date("");
    	assigntask.setUpdated_number_of_days(0);
    	assigntask.setT_changes("");
    	assigntask.setT_change_description("");
    	Employee c = employeeService.getEmployeeDataByEmailID(employeeService.Update(request.getUserPrincipal().getName()));
    	assigntask.setCompany_id(c.getCompany_id());
    	assigntask.setAuthority_id(c.getAuthority_id());
    	assigntask.setCreatedby(c.getId());
		assignTaskService.save(assigntask);
		return "redirect:/assignTask";	
	}
	
	@PostMapping("/SaveEditAssignTask")
	public String EditAssignTask(@ModelAttribute("assigntask") AssignTask assigntask,HttpServletRequest request) throws ParseException {
		//save to database
		if(assigntask.getId() == null) {
			SimpleDateFormat myformatter = new SimpleDateFormat("yyyy-MM-dd");
	    	SimpleDateFormat myformatter2 = new SimpleDateFormat("dd-MM-yyyy");
	    	Date d1 = myformatter2.parse(assigntask.getStartDate());
	    	Date d2 = myformatter2.parse(assigntask.getEndDate());
	    	assigntask.setStartYear(myformatter.format(d1));
	    	assigntask.setEndYear(myformatter.format(d2));
	    	LocalDate startDate = d1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	    	LocalDate endDate = d2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	    	// Calculate the difference in days
	    	long daysDifference = ChronoUnit.DAYS.between(startDate, endDate);
	    	// Set the number of days in AssignTask object
	    	assigntask.setNumber_of_days((int) daysDifference);
			assigntask.setUpdated_start_date("");
			assigntask.setUpdated_end_date("");
	    	assigntask.setUpdated_number_of_days(0);
	    	assigntask.setT_changes("");
	    	assigntask.setT_change_description("");
	    	Employee c = employeeService.getEmployeeDataByEmailID(employeeService.Update(request.getUserPrincipal().getName()));
	    	assigntask.setCompany_id(c.getCompany_id());
	    	assigntask.setAuthority_id(c.getAuthority_id());
	    	assigntask.setCreatedby(c.getId());
		}
		else {
			SimpleDateFormat myformatter = new SimpleDateFormat("yyyy-MM-dd");
	    	SimpleDateFormat myformatter2 = new SimpleDateFormat("dd-MM-yyyy");
	    	Date d1 = myformatter2.parse(assigntask.getStartDate());
	    	Date d2 = myformatter2.parse(assigntask.getEndDate());
	    	assigntask.setStartYear(myformatter.format(d1));
	    	assigntask.setEndYear(myformatter.format(d2));
	    	LocalDate startDate = d1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	    	LocalDate endDate = d2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	    	// Calculate the difference in days
	    	long daysDifference = ChronoUnit.DAYS.between(startDate, endDate);
	    	// Set the number of days in AssignTask object
	    	assigntask.setNumber_of_days((int) daysDifference);
			assigntask.setUpdated_start_date(assigntask.getUpdated_start_date());
			assigntask.setUpdated_end_date(assigntask.getUpdated_end_date());
	    	assigntask.setUpdated_number_of_days(assigntask.getNumber_of_days());
	    	assigntask.setT_changes(assigntask.getT_changes());
	    	assigntask.setT_change_description(assigntask.getT_change_description());
	    	Employee c = employeeService.getEmployeeDataByEmailID(employeeService.Update(request.getUserPrincipal().getName()));
	    	assigntask.setCompany_id(c.getCompany_id());
	    	assigntask.setAuthority_id(c.getAuthority_id());
	    	assigntask.setCreatedby(c.getId());
		}
		
		assignTaskService.save(assigntask);
		return "redirect:/assignTask";	
	}
	
	@GetMapping("/EditAssignForm/{id}")
	public String EditForm(@PathVariable(value="id")int id,Model model, HttpServletRequest request) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		AssignTask assigntask=assignTaskService.get(id);
		model.addAttribute("assigntask", assigntask);
		List<AddCustomer> c=customerService.getAllCustomer();
		model.addAttribute("Customerlist", c);
		List<projectName> p= ProjectService.getAllprojectName();
		model.addAttribute("Projectlist", p);
		List<Category> c1 = categoryService.listAll();
		model.addAttribute("Categorylist", c1);
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
//		remove duplicate records
		HashMap<String, Addmilestone> uniqueMap = new HashMap<>();
        for (Addmilestone m : milestoneService.getAllmilestone()) {
            uniqueMap.putIfAbsent(m.getAdd_milestone(), m);
        }
        List<Addmilestone> m1 = new ArrayList<>(uniqueMap.values());
        model.addAttribute("list", m1);
        model.addAttribute("Milestonelist", milestoneService.getAllmilestone());
        model.addAttribute("Tasklist", taskService.getAllTask());
		List<Employee> e=employeeService.getAllEmployees();
		model.addAttribute("employeeList", e);
    	model.addAttribute("UserName",employeeService.Update(request.getUserPrincipal().getName()));
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		model.addAttribute("Authority",employeeService.getAuthorityID(request.getUserPrincipal().getName()));
		return "EditAssignTask";
	}
	
	@PostMapping("/saveEmployeeAssignTask")
	public String saveEmployeeAssignTask(@ModelAttribute("assigntask") AssignTask assigntask, HttpServletRequest request) throws ParseException {
		//save to database
		SimpleDateFormat myformatter = new SimpleDateFormat("yyyy-MM-dd");
    	SimpleDateFormat myformatter2 = new SimpleDateFormat("dd-MM-yyyy");
    	Date d1 = myformatter2.parse(assigntask.getStartDate());
    	Date d2 = myformatter2.parse(assigntask.getEndDate());
    	assigntask.setStartYear(myformatter.format(d1));
    	assigntask.setEndYear(myformatter.format(d2));
    	LocalDate startDate = d1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    	LocalDate endDate = d2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    	// Calculate the difference in days
    	long daysDifference = ChronoUnit.DAYS.between(startDate, endDate);
    	// Set the number of days in AssignTask object
    	assigntask.setNumber_of_days((int) daysDifference);
		assigntask.setUpdated_start_date("");
		assigntask.setUpdated_end_date("");
    	assigntask.setUpdated_number_of_days(0);
    	assigntask.setT_changes("");
    	assigntask.setT_change_description("");
    	Employee c = employeeService.getEmployeeDataByEmailID(employeeService.Update(request.getUserPrincipal().getName()));
    	assigntask.setCompany_id(c.getCompany_id());
    	assigntask.setAuthority_id(c.getAuthority_id());
    	assigntask.setCreatedby(c.getId());
    	assignTaskService.save(assigntask);
		return "redirect:/TodayTask";	
	}
	
	@PostMapping("/SaveEmployeeEditAssignTask")
	public String EditEmployeeAssignTask(@ModelAttribute("assigntask") AssignTask assigntask,HttpServletRequest request) throws ParseException {
		//save to database
//		 AssignTask existingTask = assignTaskService.get(assigntask.getId());
//		    // Update existing data with new values
//		 SimpleDateFormat myformatter = new SimpleDateFormat("yyyy-MM-dd");
//	     SimpleDateFormat myformatter2 = new SimpleDateFormat("dd-MM-yyyy");
//	     existingTask.setUpdated_start_date(assigntask.getUpdated_start_date());
//	     existingTask.setUpdated_end_date(assigntask.getUpdated_end_date());
//	     existingTask.setUpdated_number_of_days(assigntask.getUpdated_number_of_days());
//	     existingTask.setT_changes(assigntask.getT_changes());
//	     existingTask.setT_change_description(assigntask.getT_change_description());
//	     Date d1 = myformatter2.parse(assigntask.getUpdated_start_date());
//	     Date d2 = myformatter2.parse(assigntask.getUpdated_end_date());
//	     LocalDate startDate = d1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//	     LocalDate endDate = d2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//	     // Calculate the difference in days
//	     long daysDifference = ChronoUnit.DAYS.between(startDate, endDate);
//	     // Set the number of days in AssignTask object
//	     existingTask.setUpdated_number_of_days((int) daysDifference);
//		 assignTaskService.save(existingTask);
		SimpleDateFormat myformatter = new SimpleDateFormat("yyyy-MM-dd");
    	SimpleDateFormat myformatter2 = new SimpleDateFormat("dd-MM-yyyy");
    	Date d1 = myformatter2.parse(assigntask.getStartDate());
    	Date d2 = myformatter2.parse(assigntask.getEndDate());
    	assigntask.setStartYear(myformatter.format(d1));
    	assigntask.setEndYear(myformatter.format(d2));
    	LocalDate startDate = d1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    	LocalDate endDate = d2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    	// Calculate the difference in days
    	long daysDifference = ChronoUnit.DAYS.between(startDate, endDate);
    	// Set the number of days in AssignTask object
    	assigntask.setNumber_of_days((int) daysDifference);
		assigntask.setUpdated_start_date("");
		assigntask.setUpdated_end_date("");
    	assigntask.setUpdated_number_of_days(0);
    	assigntask.setT_changes("");
    	assigntask.setT_change_description("");
    	Employee c = employeeService.getEmployeeDataByEmailID(employeeService.Update(request.getUserPrincipal().getName()));
    	assigntask.setEmployee_id(c.getId());
    	assigntask.setCompany_id(c.getCompany_id());
    	assigntask.setAuthority_id(c.getAuthority_id());
    	assigntask.setCreatedby(c.getId());
    	assignTaskService.save(assigntask);
		return "redirect:/TodayTask";	
	}
	
	@GetMapping("/EmployeeEditForm/{id}")
	public String EmployeeEditForm(@PathVariable(value="id")int id,Model model, HttpServletRequest request) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		AssignTask assigntask=assignTaskService.get(id);
		model.addAttribute("assigntask", assigntask);
		List<AddCustomer> c=customerService.getAllCustomer();
		model.addAttribute("Customerlist", c);
		List<projectName> p= ProjectService.getAllprojectName();
		model.addAttribute("Projectlist", p);
		List<Category> c1 = categoryService.listAll();
		model.addAttribute("Categorylist", c1);
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
//		remove duplicate records
		HashMap<String, Addmilestone> uniqueMap = new HashMap<>();
        for (Addmilestone m : milestoneService.getAllmilestone()) {
            uniqueMap.putIfAbsent(m.getAdd_milestone(), m);
        }
        List<Addmilestone> m1 = new ArrayList<>(uniqueMap.values());
        model.addAttribute("list", m1);
        model.addAttribute("Milestonelist", milestoneService.getAllmilestone());
        model.addAttribute("Tasklist", taskService.getAllTask());
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		List<Employee> e=employeeService.getAllEmployees();
		model.addAttribute("employeeList", e);
		Employee emp = employeeService.getEmpID(request.getUserPrincipal().getName());
        Integer employeeID = emp.getId();
        List<Integer> pName = projectNameAuthoritiesService.findProjectIdsByEmployeeId(employeeID);
        List<AddCustomer> c2 = new LinkedList<AddCustomer>();
        HashSet<Integer> companyid = new HashSet<Integer>();
        for (Integer projectId : pName) {
            Integer companyId = ProjectService.findCompanyNameIdByProjectIds(projectId);
            companyid.add(companyId);
        }
        List<AddCustomer> customers = customerService.getAllCustomer();
        for (AddCustomer customer : customers) {
            for (Integer cid : companyid) {
                if (customer.getId().intValue() == cid) {
                    c2.add(customer);
                }
            }
        }
        model.addAttribute("Company", c2);
		return "EditAssignTaskByEmployee";
	}
	
	
	
	@PostMapping("/SaveEditAssignTask_2")
	public String EditEmployeeAssignTask2(@ModelAttribute("assigntask") AssignTask assigntask,HttpServletRequest request) throws ParseException {
		//save to database
		 AssignTask existingTask = assignTaskService.get(assigntask.getId());
		    // Update existing data with new values
		 SimpleDateFormat myformatter = new SimpleDateFormat("yyyy-MM-dd");
	     SimpleDateFormat myformatter2 = new SimpleDateFormat("dd-MM-yyyy");
	     existingTask.setUpdated_start_date(assigntask.getUpdated_start_date());
	     existingTask.setUpdated_end_date(assigntask.getUpdated_end_date());
	     existingTask.setUpdated_number_of_days(assigntask.getUpdated_number_of_days());
	     existingTask.setT_changes(assigntask.getT_changes());
	     existingTask.setT_change_description(assigntask.getT_change_description());
	     
	    	Date d1 = myformatter2.parse(assigntask.getUpdated_start_date());
	    	Date d2 = myformatter2.parse(assigntask.getUpdated_end_date());
	    	LocalDate startDate = d1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	    	LocalDate endDate = d2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	    	// Calculate the difference in days
	    	long daysDifference = ChronoUnit.DAYS.between(startDate, endDate);
	    	// Set the number of days in AssignTask object
	    	existingTask.setUpdated_number_of_days((int) daysDifference);
		 assignTaskService.save(existingTask);
		return "redirect:/TodayTask";	
	}
	
	@GetMapping("/EmployeeEditAssignTask_2/{id}")
	public String EmployeeEditTask(@PathVariable(value="id")int id,Model model, HttpServletRequest request) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		AssignTask assigntask=assignTaskService.get(id);
		model.addAttribute("assigntask", assigntask);
		List<AddCustomer> c=customerService.getAllCustomer();
		model.addAttribute("Customerlist", c);
		List<projectName> p= ProjectService.getAllprojectName();
		model.addAttribute("Projectlist", p);
		List<Category> c1 = categoryService.listAll();
		model.addAttribute("Categorylist", c1);
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
//		remove duplicate records
		HashMap<String, Addmilestone> uniqueMap = new HashMap<>();
        for (Addmilestone m : milestoneService.getAllmilestone()) {
            uniqueMap.putIfAbsent(m.getAdd_milestone(), m);
        }
        List<Addmilestone> m1 = new ArrayList<>(uniqueMap.values());
        model.addAttribute("list", m1);
        model.addAttribute("Milestonelist", milestoneService.getAllmilestone());
        model.addAttribute("Tasklist", taskService.getAllTask());
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		List<Employee> e=employeeService.getAllEmployees();
		model.addAttribute("employeeList", e);
		Employee emp = employeeService.getEmpID(request.getUserPrincipal().getName());
        Integer employeeID = emp.getId();
        List<Integer> pName = projectNameAuthoritiesService.findProjectIdsByEmployeeId(employeeID);
        List<AddCustomer> c2 = new LinkedList<AddCustomer>();
        HashSet<Integer> companyid = new HashSet<Integer>();
        for (Integer projectId : pName) {
            Integer companyId = ProjectService.findCompanyNameIdByProjectIds(projectId);
            companyid.add(companyId);
        }
        List<AddCustomer> customers = customerService.getAllCustomer();
        for (AddCustomer customer : customers) {
            for (Integer cid : companyid) {
                if (customer.getId().intValue() == cid) {
                    c2.add(customer);
                }
            }
        }
        model.addAttribute("Company", c2);
		return "EditAssignTaskByEmployee2";
	}
	
	
	
	
	
	@GetMapping("/deleteAssignForm/{id}")
	public String deleteForm(@PathVariable(value="id")int id) {
		//call delete method
		this.assignTaskService.deleteAssignTask(id);
		return "redirect:/assignTask";	      
	}  
	
//	@GetMapping("/MyAssignedTasks")
//	public String MyAssignedTasks(Model model,@Param("companyname") String companyname, @Param("projectCode") String projectCode,
//		@Param("empname") String empname, @Param("fromdate") String fromdate, @Param("todate") String todate, HttpServletRequest request) {
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
//    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
//    	List<Mainsidebar> listmainsiderbar = mainsidebarService.listAll();
//    	model.addAttribute("listMainsiderbar", listmainsiderbar);
//		if(fromdate == null || todate == null) {
//			List<AssignTask> AssignTaskList1 = assignTaskService.listAll();
//			model.addAttribute("AssignTaskList",AssignTaskList1);
//			model.addAttribute("companyname",companyname);
//			model.addAttribute("projectCode",projectCode);
//			model.addAttribute("empname",empname);	
//			model.addAttribute("fromdate",fromdate);
//			model.addAttribute("todate",todate);	
//		}
//		else {
//			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//			LocalDate FromDate = LocalDate.parse(fromdate, formatter);
//			LocalDate ToDate = LocalDate.parse(todate, formatter);
//			List<AssignTask> AssignTaskList = new LinkedList<>();
//			List<AssignTask> AssignTaskList1 = assignTaskService.FilterByCompanyName(companyname, projectCode, empname);
//			for(AssignTask t:AssignTaskList1) {
//				LocalDate date1 = LocalDate.parse(t.getDate(), formatter);
//				LocalDate date2 = LocalDate.parse(t.getDate(), formatter);
//				if(date1.compareTo(FromDate)>=0 && date2.compareTo(ToDate)<=0) {
//					AssignTaskList.add(t);
//				}
//			}
//			model.addAttribute("AssignTaskList",AssignTaskList1);
//			model.addAttribute("companyname",companyname);
//			model.addAttribute("projectCode",projectCode);
//			model.addAttribute("empname",empname);	
//			model.addAttribute("fromdate",fromdate);
//			model.addAttribute("todate",todate);
//		}
//		model.addAttribute("username", request.getUserPrincipal().getName());
//		return "MyAssignedTasks";
//	}
//	
	
//	@GetMapping("/getcomprojectdropdown3")
//	public @ResponseBody List<String> Viewprojectcodepage(@RequestParam(name="name",required=false)String name,Model model) {
//		//System.out.println("ViewPortloading:"+ name);
//	return projectServiceImpl.findprojectcode(name);				
// 
//	}
//	
//	@GetMapping("/getlevelofmilestonedropdown3")
//	public @ResponseBody List<String> ViewlevelofMileStone(@RequestParam(name="name",required=false)String name,Model model) {
//		//System.out.println("Milestone:"+ name);
//	return  milestoneServiceImpl.findlevelofMilestone(name);				
// 
//	}
//	
//	@GetMapping("/getmilestonedropdown3")
//	public @ResponseBody List<String> ViewMileStone(@RequestParam(name="name",required=false)String name,Model model) {
//		//System.out.println("Milestone:"+ name);
//	return  addmilestoneServiceImpl.findMilestone(name, name, name);				
// 
//	}
//	
//	@GetMapping("/getlevelofTaskdropdown3")
//	public @ResponseBody List<String> ViewlevelofTaskpage(@RequestParam(name="name",required=false)String name,Model model) {
//		//System.out.println("Milestone:"+ name);
//	return taskServiceImpl.findlevelofTask(name);				
// 
//	}		
//	
//	@GetMapping("/getTaskdropdown3")
//	public @ResponseBody List<String> ViewTaskpage(@RequestParam(name="name",required=false)String name,Model model) {
//		//System.out.println("Milestone:"+ name);
//	return taskServiceImpl.findTask(name);				
// 
//	}		

//	@GetMapping("/getTaskByProjectName")
//	public @ResponseBody HashSet<String> TaskByProjectName(@RequestParam(name="name",required=false)String name,Model model) {
//		HashSet<String> p = new HashSet<String>();
//		p.addAll(taskServiceImpl.findTaskByProjectName(name));
//	return  p;				
// 
//	}
	
	@GetMapping("/TodayTask")
	public String TodayTask(Model model, HttpServletRequest request) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
    	model.addAttribute("AssignTaskList",assignTaskService.listAll());
    	AssignTask assigntask = new AssignTask();
		model.addAttribute("assigntask", assigntask);
    	int n = loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName()));
    	String status = "false";
    	if(n==6) {
    		status = "true";
    	}
    	model.addAttribute("status", status);
    	
    	HashSet<Employee> employees = new HashSet<Employee>();
    	
    	List<Employee> employees1 = employeeService.FilterByReportingManager(employeeService.Update(request.getUserPrincipal().getName()));
    	for(Employee e:employees1) {
    		employees.add(e);
    		List<Employee> employees2 = employeeService.FilterByReportingManager(e.getId());
    		for(Employee e1:employees2) {
    			employees.add(e1);
    			List<Employee> employees3 = employeeService.FilterByReportingManager(e.getId());
    			for(Employee e2:employees3) {
    				employees.add(e2);
    				List<Employee> employees4 = employeeService.FilterByReportingManager(e.getId());
    				for(Employee e3:employees4) {
    					employees.add(e3);
    				}
    			}
    		}
    	}
    	model.addAttribute("ListUserName", employees);
    	List<AddCustomer> c=customerService.getAllCustomer();
		model.addAttribute("Customerlist", c);
		List<projectName> p= ProjectService.getAllprojectName();
		model.addAttribute("Projectlist", p);
		List<Category> c1 = categoryService.listAll();
		model.addAttribute("Categorylist", c1);
//		remove duplicate records
		HashMap<String, Addmilestone> uniqueMap = new HashMap<>();
        for (Addmilestone m : milestoneService.getAllmilestone()) {
            uniqueMap.putIfAbsent(m.getAdd_milestone(), m);
        }
        List<Addmilestone> m1 = new ArrayList<>(uniqueMap.values());
        model.addAttribute("list", m1);
        model.addAttribute("Milestonelist", milestoneService.getAllmilestone());
        model.addAttribute("Tasklist", taskService.getAllTask());
		List<Employee> e=employeeService.getAllEmployees();
		model.addAttribute("employeeList", e);
    	model.addAttribute("UserName",employeeService.Update(request.getUserPrincipal().getName()));
    	model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
//    	List<AddCustomer> c=customerService.getAllCustomer();
//		model.addAttribute("Customerlist", c);
//    	model.addAttribute("EmployeeList", employeeService.getAllEmployees());
//    	model.addAttribute("UserName",employeeService.Update(request.getUserPrincipal().getName()));
//    	model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
    	Employee emp = employeeService.getEmpID(request.getUserPrincipal().getName());
        Integer employeeID = emp.getId();
        List<Integer> pName = projectNameAuthoritiesService.findProjectIdsByEmployeeId(employeeID);
        List<AddCustomer> c2 = new LinkedList<AddCustomer>();
        HashSet<Integer> companyid = new HashSet<Integer>();
        for (Integer projectId : pName) {
            Integer companyId = ProjectService.findCompanyNameIdByProjectIds(projectId);
            companyid.add(companyId);
        }
        List<AddCustomer> customers = customerService.getAllCustomer();
        for (AddCustomer customer : customers) {
            for (Integer cid : companyid) {
                if (customer.getId() != null && cid != null && customer.getId().intValue() == cid) {
                    c2.add(customer);
                }
            }
        }

        model.addAttribute("Company", c2);
        model.addAttribute("Authority",employeeService.getAuthorityID(request.getUserPrincipal().getName()));
		return "TodayTask";	
	}
	
	
	@GetMapping("/work_planner_report")
	public String WorkPlannerReport(Model model, HttpServletRequest request) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
    	model.addAttribute("AssignTaskList",assignTaskService.listAll());
    	AssignTask assigntask = new AssignTask();
		model.addAttribute("assigntask", assigntask);
    	List<AddCustomer> c=customerService.getAllCustomer();
		model.addAttribute("Customerlist", c);
		List<projectName> p= ProjectService.getAllprojectName();
		model.addAttribute("Projectlist", p);
		List<Category> c1 = categoryService.listAll();
		model.addAttribute("Categorylist", c1);
//		remove duplicate records
		HashMap<String, Addmilestone> uniqueMap = new HashMap<>();
        for (Addmilestone m : milestoneService.getAllmilestone()) {
            uniqueMap.putIfAbsent(m.getAdd_milestone(), m);
        }
        List<Addmilestone> m1 = new ArrayList<>(uniqueMap.values());
        model.addAttribute("list", m1);
        model.addAttribute("Milestonelist", milestoneService.getAllmilestone());
        model.addAttribute("Tasklist", taskService.getAllTask());
		List<Employee> e=employeeService.getAllEmployees();
		model.addAttribute("employeeList", e);
    	model.addAttribute("UserName",employeeService.Update(request.getUserPrincipal().getName()));
    	model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "work_planner_report";
	}
	
	@GetMapping("/day_planner_report")
	public String DayPlannerReport(Model model, HttpServletRequest request) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
    	model.addAttribute("AssignTaskList",assignTaskService.listAll());
    	AssignTask assigntask = new AssignTask();
		model.addAttribute("assigntask", assigntask);
		List<AddCustomer> c=customerService.getAllCustomer();
		model.addAttribute("Customerlist", c);
    	model.addAttribute("employeeList", employeeService.getAllEmployees());
    	model.addAttribute("UserName",employeeService.Update(request.getUserPrincipal().getName()));
    	model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "day_planner_report";
	}
	
	
	@GetMapping("/show_assign_task")
	public String ShowAssignTask(Model model, HttpServletRequest request) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
//		LocalDateTime mydate = LocalDateTime.now();
//		DateTimeFormatter myformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		model.addAttribute("AssignTaskList",assignTaskService.listAll());
		AssignTask assigntask = new AssignTask();
		model.addAttribute("assigntask", assigntask);
		List<AddCustomer> c=customerService.getAllCustomer();
		model.addAttribute("Customerlist", c);
		List<projectName> p= ProjectService.getAllprojectName();
		model.addAttribute("Projectlist", p);
		List<Category> c1 = categoryService.listAll();
		model.addAttribute("Categorylist", c1);
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
//		remove duplicate records
		HashMap<String, Addmilestone> uniqueMap = new HashMap<>();
        for (Addmilestone m : milestoneService.getAllmilestone()) {
            uniqueMap.putIfAbsent(m.getAdd_milestone(), m);
        }
        List<Addmilestone> m1 = new ArrayList<>(uniqueMap.values());
        model.addAttribute("list", m1);
        model.addAttribute("Milestonelist", milestoneService.getAllmilestone());
        model.addAttribute("Tasklist", taskService.getAllTask());
		List<Employee> e=employeeService.getAllEmployees();
		model.addAttribute("employeeList", e);
		int n = loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName()));
    	String status = "false";
    	if(n==3) {
    		status = "true";
    	}
    	model.addAttribute("status", status);
    	model.addAttribute("UserName",employeeService.Update(request.getUserPrincipal().getName()));
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		
		return "show_task";	
	}
	
	@GetMapping("/assign_task1/{username}")
	public String AssignTaskTest(@PathVariable("username") String username,Model model, HttpServletRequest request) {
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
//    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		model.addAttribute("AssignTaskList",assignTaskService.listAll());
		AssignTask assigntask = new AssignTask();
		model.addAttribute("assigntask", assigntask);
		List<AddCustomer> c=customerService.getAllCustomer();
		model.addAttribute("Customerlist", c);
		List<projectName> p= ProjectService.getAllprojectName();
		model.addAttribute("Projectlist", p);
		List<Category> c1 = categoryService.listAll();
		model.addAttribute("Categorylist", c1);
		model.addAttribute("CompanyID",employeeService.getCompanyID(username));
//		remove duplicate records
		HashMap<String, Addmilestone> uniqueMap = new HashMap<>();
        for (Addmilestone m : milestoneService.getAllmilestone()) {
            uniqueMap.putIfAbsent(m.getAdd_milestone(), m);
        }
        List<Addmilestone> m1 = new ArrayList<>(uniqueMap.values());
        model.addAttribute("list", m1);
        model.addAttribute("Milestonelist", milestoneService.getAllmilestone());
        model.addAttribute("Tasklist", taskService.getAllTask());
		List<Employee> e=employeeService.getAllEmployees();
		model.addAttribute("employeeList", e);
//		int n = loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName()));
//    	String status = "false";
//    	if(n==3) {
//    		status = "true";
//    	}
//    	model.addAttribute("status", status);
//    	model.addAttribute("UserName",employeeService.Update(request.getUserPrincipal().getName()));
//		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		
		return "AssignTask1";	
	}
	
}