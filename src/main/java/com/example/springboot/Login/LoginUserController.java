package com.example.springboot.Login;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.springboot.AddCompany.AddCustomer;
import com.example.springboot.AddCompany.AddCustomerService;
import com.example.springboot.AddProjectCode.projectService;
import com.example.springboot.AdhocTask.AssignTaskServiceImpl;
import com.example.springboot.EmployeesDetails.Employee;
import com.example.springboot.EmployeesDetails.EmployeeService;
import com.example.springboot.HelpDesk.HelpDeskServiceImpl;
import com.example.springboot.Leave.Leave;
import com.example.springboot.Leave.LeaveService;
import com.example.springboot.MainMaster.PostName;
import com.example.springboot.MainMaster.PostNameService;
import com.example.springboot.PostJob.JobPostService;
import com.example.springboot.PostJob.JobResponseService;
import com.example.springboot.ProjectStatus.ProjectStatusService;
import com.example.springboot.TimeSheet.TimeSheetMailReport;
import com.example.springboot.TimeSheet.TimeSheetMailReportService;
import com.example.springboot.TimeSheet.TimeSheetReportService;


@Controller
public class LoginUserController {

	@Autowired
	private MainsidebarauthorityService mainsidebarauthorityService;
	@Autowired
	private LoginUserAuthorityService loginuserauthorityService;
	@Autowired
	private LoginUserService loginuserService;
	@Autowired
	private projectService ProjectService;
	@Autowired
	private EmailForgetPasswordService emailforgetpasswordservice;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private TimeSheetMailReportService MailReportService;
	@Autowired
	private  AssignTaskServiceImpl assignTaskService;
	@Autowired
	private LeaveService leaveService;
	@Autowired
	private ProjectStatusService pstatusService;
	@Autowired
	private TimeSheetReportService timesheetReportService;
//	@Autowired
//	private HelpDeskServiceImpl HelpDeskService;
	@Autowired
	private JobResponseService jResponseService;
	@Autowired
	private AddCustomerService customerService;
	@Autowired
	private PostNameService postNameService;

	
	@RequestMapping({"/","/login"})
	public String LoginPage(Model model) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(4);
//		System.out.println("Manager : " + bCryptPasswordEncoder.encode("admin"));
//		System.out.println("Mail" + bCryptPasswordEncoder.encode("123456"));
		return "login";
	}
	
	@GetMapping("/checkusername")
	public @ResponseBody String CheckUsername(@RequestParam(name="name") String name) {
		if(loginuserService.findByUser(name) == 0) {
			return "true";
		}
		else {
			return "false";
		}
	}
	
	@RequestMapping("/home")
	public String MainPage() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		int n = loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName()));
		if(n==1|| n==2|| n==3 || n==4) {
			
			return "redirect:/AdminHome";
		}
		else {
			
			return "redirect:/UserHome";
		}
		
	}
	
	@RequestMapping("/AdminHome")
	public String home(Model model,HttpServletRequest request) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
    	model.addAttribute("EmployeesList",employeeService.getBirthDateLimit1());
    	model.addAttribute("TotalEmployee",employeeService.TotalNumberOfEmployee(employeeService.getCompanyID(auth.getName())));
    	model.addAttribute("TotalProject", ProjectService.TotalNumberOfProject());
		model.addAttribute("ReportList",MailReportService.listAll());
		List<TimeSheetMailReport> mailReports = MailReportService.getLimit1();
        model.addAttribute("ReportListDate", mailReports);
//        model.addAttribute("ReportList_Date", mailReports.get(0).getSdate());
//        model.addAttribute("ReportList_Status", mailReports.get(0).getCompletionstatus());
        model.addAttribute("EmployeeNameList", employeeService.getAllEmployees());
//        System.out.println("Employee Name : " + employeeService.getAllEmployees());
		model.addAttribute("CompletionStatus", MailReportService.getEmployeeIdByCompletionStatus());
//		System.out.println("Completion Status : " + MailReportService.getEmployeeIdByCompletionStatus());
    
    	int n = loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName()));
    	String status = "false";
    	if(n==1 || n==2 || n==3 || n==4) {
    		status = "true";
    	}
    	model.addAttribute("status", status);
    	model.addAttribute("username", request.getUserPrincipal().getName());
    	model.addAttribute("UserName", employeeService.Update(request.getUserPrincipal().getName()));
		model.addAttribute("leaveList",leaveService.getLeaveListByOrder());
		model.addAttribute("allResponse", jResponseService.getJobResponseList());
		model.addAttribute("allResponse", jResponseService.listAll());
		model.addAttribute("EmployerName",employeeService.getAllEmployees());
		model.addAttribute("PostNameList", postNameService.listAll());
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "home";
	}
	
	
	@RequestMapping("/UserHome")
	public String home1(Model model,HttpServletRequest request) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
    	model.addAttribute("EmployeesList",employeeService.getBirthDate());
    	model.addAttribute("TotalEmployee",employeeService.TotalNumberOfEmployee(employeeService.getCompanyID(auth.getName())));
    	model.addAttribute("TotalProject", ProjectService.TotalNumberOfProject());
    	model.addAttribute("EmployeesList1",employeeService.getBirthDateLimit1());
		model.addAttribute("ReportList",MailReportService.getUserListLimit1(employeeService.Update(request.getUserPrincipal().getName())));
    	int n = loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName()));
    	String status = "false";
    	if(n==5 || n==6) {
    		status = "true";
    	}
    	model.addAttribute("status", status);
    	model.addAttribute("username",employeeService.Update(request.getUserPrincipal().getName()));
    	List<AddCustomer> c=customerService.getAllCustomer();
		model.addAttribute("Customerlist", c);
		model.addAttribute("EmployeeNameList", employeeService.getAllEmployees());
//    	model.addAttribute("AssignTaskList",assignTaskService.getTaskListLimit1());
    	model.addAttribute("UserName", employeeService.Update(request.getUserPrincipal().getName()));
    	model.addAttribute("leaveList",leaveService.getLeaveListByOrder());
		model.addAttribute("ProjectStatusList", pstatusService.getProjectStatusByUserNameLimit1());
		model.addAttribute("tReportList", timesheetReportService.listAll());
    	model.addAttribute("loginuser", loginuserService.getUserName(request.getUserPrincipal().getName()));
    	model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "home1";
	}
	
	@Value("${spring.mail.username}") 
	private String mail_username;
	
	@RequestMapping("/forgetpasswordpage")
	public String EmailForgetPasswordpage(Model model) {
		return "forgetpasswordpage";
	}
	
	@GetMapping("/checkusernamepassword")
	public @ResponseBody String viewStatusByUsernamePassword(@RequestParam(name="user",required=false)String user) {
	return (loginuserService.getStatusByUsernamePassword(user));
	}


	@RequestMapping(value ="/forgetpassword",method = RequestMethod.POST)
	public String Forgetpassword(@RequestParam("username") String username,RedirectAttributes redirectAttributes) {
		Random rand = new Random();
		int password = rand.nextInt(1000000);
		String error="false";
		try {
			String text="Hi " + username + ", Your One Time Password (OTP) is " + password + " for Online HR Portal. Please do not share password with anyone.";
			Mail mail = new Mail();
	        mail.setFrom(mail_username);
	        mail.setMailTo(username);
	        mail.setSubject("Forget Password");
	        emailforgetpasswordservice.sendforgetpasswordEmail(mail, text);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		redirectAttributes.addAttribute("password", password);
		redirectAttributes.addAttribute("error", error);
		return "redirect:/otp";
	}
	
	@RequestMapping("/otp")
	public String Otp(@RequestParam("password") String password,@RequestParam("error") String error,Model model) {
		model.addAttribute("password" , password);
		model.addAttribute("error" , error);
		return "otp";
	}
	
	@RequestMapping(value ="/checkotp",method = RequestMethod.POST)
	public String Checkotp(@RequestParam("password") String password,@RequestParam("otp") String otp,RedirectAttributes redirectAttributes) {
		if(password.equals(otp)) {
			return "changepassword";
		}
		else {
			String error="true";
			redirectAttributes.addAttribute("password", password);
			redirectAttributes.addAttribute("error", error);
			return "redirect:/otp";
		}
	}
	
	@RequestMapping(value ="/savechangepassword",method = RequestMethod.POST)
	public String Savechangepassword(@RequestParam("username") String username,@RequestParam("password") String password,@RequestParam("repassword") String repassword) {
		if(password.equals(repassword)) {
			BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(4);
			loginuserService.UpdatePasswordByUsername(username, bCryptPasswordEncoder.encode(password));
			return "login";
		}
		else {
			return "changepassword";
		}
	}
}
