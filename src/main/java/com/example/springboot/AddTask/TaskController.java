package com.example.springboot.AddTask;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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
import com.example.springboot.AddProjectCode.ProjectNameAuthorities;
import com.example.springboot.AddProjectCode.ProjectNameAuthoritiesService;
import com.example.springboot.AddProjectCode.projectName;
import com.example.springboot.AddProjectCode.projectService;
import com.example.springboot.EmployeesDetails.Employee;
import com.example.springboot.EmployeesDetails.EmployeeService;
import com.example.springboot.Login.LoginUserAuthorityService;
import com.example.springboot.Login.LoginUserService;
import com.example.springboot.Login.Mainsidebarauthority;
import com.example.springboot.Login.MainsidebarauthorityService;

@Controller
public class TaskController {

	@Autowired
	private TaskService taskService;
	@Autowired
	private AddmilestoneService  milestoneService;
	@Autowired
	private projectService ProjectService;
	@Autowired
	private AddCustomerService customerService;
	@Autowired
	private TaskServiceImpl taskServiceImpl;
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
	@Autowired
	private ProjectNameAuthoritiesService projectNameAuthoritiesService;
	@Autowired
	private AddmilestoneServiceImpl addmilestoneServiceImpl;
	
	
	@GetMapping("/addtask")
	public String showTask(Model model) {
		//create model attribute to bind from data
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		model.addAttribute("tasklist",taskServiceImpl.getAllTask());
		AddTask task=new AddTask();
		model.addAttribute("task", task);
		List<AddCustomer> cc=customerService.getAllCustomer();
		model.addAttribute("Customerlist", cc);
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
        model.addAttribute("milestonelist", m1);
        model.addAttribute("milestonelist1", milestoneService.getAllmilestone());
		return "addtask";
	}
	
	@PostMapping("/saveTask")
	public String saveTask(@ModelAttribute("task") AddTask  task, HttpServletRequest request) {
		//save to database
		LocalDateTime mydate = LocalDateTime.now();
		DateTimeFormatter myformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		task.setCurrentdate(mydate.format(myformatter));
		Employee c = employeeService.getEmployeeDataByEmailID(1);
		task.setCompany_id(c.getCompany_id());
		taskService.saveAllTask(task);
		return "redirect:/addtask";
		
	}   
	
	@GetMapping("/TaskEditForm/{id}")
	public String TaskEditForm(@PathVariable(value="id")Integer id,Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		AddTask task=taskService.getTaskById(id);
		model.addAttribute("task", task);
		List<AddCustomer> cc=customerService.getAllCustomer();
		model.addAttribute("Customerlist", cc);
		List<projectName> p= ProjectService.getAllprojectName();
		model.addAttribute("Projectlist", p);
		List<Addmilestone> m= milestoneService.getAllmilestone();
		model.addAttribute("list", m);
		List<Category> c1 = categoryService.listAll();
		model.addAttribute("Categorylist", c1);
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "editTask";

	}
	
	@GetMapping("/deleteTask/{id}")
	public String deleteTask(@PathVariable(value="id")Integer id) {
		//call delete  method
		this.taskService.deleteTaskById(id);
		return "redirect:/addtask";
		
	}
	@GetMapping("/getprojectNamedropdown")
	public @ResponseBody HashSet<projectName> Viewprojectname(HttpServletRequest request, @RequestParam(name="name",required=false)Long name, @RequestParam(name="name1",required=false)String name1) {
	    // code to fetch the project names based on the selected company name and category
		HashSet<projectName> p = new HashSet<projectName>();
		p.addAll(ProjectService.findProjectNameByCompanyNameCategoryName(name, name1));
//		System.out.println("P : " + p);
//		System.out.println(" P : " + p);
//		Employee employee = employeeService.getEmployeeDataByEmailID(request.getUserPrincipal().getName());
////		List<Integer> a = projectNameAuthoritiesService.findProjectIdsByEmployeeId(employee.getId());
//		for(projectName pn:ProjectService.findProjectNameByCompanyNameCategoryName(name, name1)) {
//			for(Integer i : a) {
//				if(pn.getId() == i) {
//					p.add(pn);
//				}
//			}
//		}
	    return p;
	}
	
	
	@GetMapping("/getdropdown")
	public @ResponseBody HashSet<projectName> Viewprojectcodepage(@RequestParam(name="name",required=false)String name,@RequestParam(name="name1",required=false)Integer name1, Model model) {
		HashSet<projectName> p = new HashSet<projectName>();
		p.addAll(ProjectService.findprojectcodeByCategoryProjectName(name, name1));
//		System.out.println("P : " + p);
	return p;				
	}
	
	@GetMapping("/getTaskDropDown")
	public @ResponseBody HashSet<AddTask> ViewTask(@RequestParam(name="name",required=false)Integer name,@RequestParam(name="name1",required=false)Integer name1,@RequestParam(name="name2",required=false)Integer name2,Model model) {
		HashSet<AddTask> p = new HashSet<AddTask>();
		p.addAll(taskServiceImpl.findTaskByCompanyIdProjectIdMilestoneId(name, name1, name2));
	return p;				
	}
	
	@GetMapping("/getMileStonedropdown")
	public @ResponseBody HashSet<Addmilestone> ViewlevelofMileStone(@RequestParam(name="name",required=false)Integer name,@RequestParam(name="name1",required=false)Integer name1, Model model) {
		HashSet<Addmilestone> p = new HashSet<Addmilestone>();
		p.addAll(addmilestoneServiceImpl.findMilestone(name, name1));
	return  p;				
 
	}

}
