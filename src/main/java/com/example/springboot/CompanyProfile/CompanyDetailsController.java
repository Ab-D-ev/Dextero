package com.example.springboot.CompanyProfile;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cassandra.CassandraProperties.Request;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.springboot.EmployeesDetails.Employee;
import com.example.springboot.EmployeesDetails.EmployeeService;
import com.example.springboot.Login.EmailSendLoginPasswordUserService;
import com.example.springboot.Login.LoginUser;
import com.example.springboot.Login.LoginUserAuthority;
import com.example.springboot.Login.LoginUserAuthorityService;
import com.example.springboot.Login.LoginUserService;
import com.example.springboot.Login.Mail;
import com.example.springboot.Login.Mainsidebarauthority;
import com.example.springboot.Login.MainsidebarauthorityService;
import com.example.springboot.MainMaster.Department;
import com.example.springboot.MainMaster.EducationalQualification;
import com.example.springboot.MainMaster.EmployeeDesignationDetails;
import com.example.springboot.SalaryPayRoll.CompanyGrossPay;
import com.example.springboot.SalaryPayRoll.CompanyGrossPayService;
import com.example.springboot.SalaryPayRoll.Deductions;
import com.example.springboot.SalaryPayRoll.DeductionsService;
import com.example.springboot.SalaryPayRoll.EmployeeGrossPay;
import com.example.springboot.SalaryPayRoll.EmployeeGrossPayService;
import com.example.springboot.SalaryPayRoll.MasterPercentageService;
import com.example.springboot.TimeSheet.TimeSheetReport;

@Controller
public class CompanyDetailsController {
	
	@Autowired
	private CompanyDetailsService companyDetailsService;
	@Autowired
	private MainsidebarauthorityService mainsidebarauthorityService;
	@Autowired
	private LoginUserAuthorityService loginuserauthorityService;
	@Autowired
	private LoginUserService loginuserService;
	@Autowired
	private GroupCompanyService groupCompanyService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private EmployeeGrossPayService employeeGrossPayService;
	@Autowired
	private DeductionsService deductionsService;
	@Autowired
	private CompanyGrossPayService companyGrossPayService;
	@Autowired
	private EmailSendLoginPasswordUserService emailSendLoginPasswordUserService;
	
	
	@Value("${spring.mail.username}") 
	private String mail_username;
	
		@GetMapping("/companydetailsList")
		public String CompanyDetailsList(Model model) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
	    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
			model.addAttribute("CompanyDetailsList",companyDetailsService.listAll());
			return "companydetailsList";
		}  
		
		@GetMapping("/companydetails")
		public String CompanyDetails(Model model ,HttpServletRequest request) {
			//create model attribute to bind from data
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
	    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
			CompanyDetails companyDetails = new CompanyDetails();
			model.addAttribute("companyDetails",companyDetails);
			return "companydetails";
		}
		
		@GetMapping("/getCountEmployeeStatus")
		public @ResponseBody String getCountEmployeeStatus(@RequestParam(name="name",required=false)String name,HttpServletRequest r) {
			String msgString = "";
			if(groupCompanyService.getcountgroupcompany_id(r.getUserPrincipal().getName()) > 0) {
				GroupCompany g = groupCompanyService.get(groupCompanyService.getgroupcompany_id(r.getUserPrincipal().getName()));
				Integer n = g.getNemployee() - Integer.parseInt(name);
				if(n >= 0) {
					msgString = "Remaining Number of Employees : " + n;
				}
				else {
					msgString = "Employee Count Exceed Limit";
				}
			}
			else {
				msgString = "true";
			}
			return msgString;	
	   	}
		
		@PostMapping("/savecompanydetails")
		public String SaveCompanyDetails(@ModelAttribute("companyDetails") CompanyDetails companyDetails,HttpServletRequest request) throws MessagingException, IOException {
			//save employee to database
		    	
				if(groupCompanyService.getcountgroupcompany_id(request.getUserPrincipal().getName()) > 0) {
					companyDetails.setGroup_id(groupCompanyService.getgroupcompany_id(request.getUserPrincipal().getName()));
				}
				else {
					companyDetails.setGroup_id(0);
				}
//				String str1 = companyDetails.getCompanyprefix().substring(0, 5) ;
//				companyDetails.setCompanyprefix(str1);
				companyDetails.setStatus(true);
				companyDetailsService.Save(companyDetails);
				
				BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(4);	
				LoginUser m = new LoginUser();
				m.setUsername(companyDetails.getEmail_id());
				m.setPassword(bCryptPasswordEncoder.encode("admin"));
				m.setStatus(true);
				loginuserService.save(m);
				LoginUserAuthority a = new LoginUserAuthority();
				a.setAuthority_id(3);
				a.setUser_id(loginuserService.GetAuthorityID(companyDetails.getEmail_id()));
				loginuserauthorityService.save(a);
				
				Employee employee = new Employee();
				employee.setEmail(companyDetails.getEmail_id());
				CompanyDetails c = companyDetailsService.getDataByEmailID(companyDetails.getEmail_id());
		    	employee.setGroup_id(c.getGroup_id());
		    	employee.setCompany_id(c.getCompany_id());
		    	employee.setAuthority_id(a.getAuthority_id());
	    		employee.setPrefix(companyDetails.getCompanyprefix());
	    		employee.setSufix("000001");
	    		employee.setAge(0);
	    		employee.setDepartment("-");
	    		employee.setReporting_manager(0);
	    		employee.setStatus(true);
				employeeService.saveEmployee(employee);
				
				int e = employeeService.getEmployeeID(employee.getEmail());
				CompanyGrossPay cg = new CompanyGrossPay();
				cg.setEmployee_id(e);
				cg.setBasic("true");
				cg.setDa("false");
				cg.setHra("false");
				cg.setConveyance_allowance("false");
				cg.setDearness_allowance("false");
				cg.setMedical_allowance("false");
				cg.setOther_allowance("false");
				cg.setProject_allowance("false");
				cg.setProject_travel("false");
				cg.setSpecial_allowance("false");
				cg.setTravel_allowance("false");
				cg.setCompany_id(c.getCompany_id());
				companyGrossPayService.Save(cg);
				
				EmployeeGrossPay g = new EmployeeGrossPay();
				g.setEmpId(e);
				g.setBasic(0);
				g.setDa(0);
				g.setHra(0);
				g.setConveyance_allowance(0);
				g.setDearness_allowance(0);
				g.setMedical_allowance(0);
				g.setOther_allowance(0);
				g.setProject_allowance(0);
				g.setProject_travel(0);
				g.setSpecial_allowance(0);
				g.setTravel_allowance(0);
				g.setEmployee_ESI(0);
				g.setEmployee_Gratuity(0);
				g.setEmployee_Loan_Recovery(0);
				g.setEmployee_PF(0);
				g.setEmployee_Professional_Tax(0);
				g.setEmployee_TDS(0);
				g.setEmployer_ESI(0);
				g.setEmployer_Gratuity(0);
				g.setEmployer_Loan_Recovery(0);
				g.setEmployer_PF(0);
				g.setEmployer_Professional_Tax(0);
				g.setEmployer_TDS(0);
				g.setCompany_id(c.getCompany_id());
				employeeGrossPayService.Save(g);
				
				Deductions deductions = new Deductions();
				deductions.setId(e);
				deductions.setEmployee_ESI("false");
				deductions.setEmployee_Gratuity("false");
				deductions.setEmployee_Loan_Recovery("false");
				deductions.setEmployee_PF("false");
				deductions.setEmployee_Professional_Tax("false");
				deductions.setEmployee_TDS("false");
				deductions.setEmployer_ESI("false");
				deductions.setEmployer_Gratuity("false");
				deductions.setEmployer_Loan_Recovery("false");
				deductions.setEmployer_PF("false");
				deductions.setEmployer_Professional_Tax("false");
				deductions.setEmployer_TDS("false");
				deductions.setCompany_id(c.getCompany_id());
				deductionsService.Save(deductions);
				
//				String text="Welcome " +  ","  +  "\n"  +  "This is Your Login Username :  "  +  m.getUsername() + " and Password : "  +  "admin"  + " for Online HR Portal."  +  "\n"  +  " Please do not share password with anyone.";
//				Mail mail = new Mail();
//			    mail.setFrom(mail_username);
//			    mail.setMailTo(m.getUsername());
//			    mail.setSubject("Welcome To Online HR Portal");
//			    emailSendLoginPasswordUserService.senduserloginpasswordEmail(mail, text);
				
			return "redirect:/companydetailsList";
		}
		
		@PostMapping("/saveUpdatecompanydetails")
		public String EditSaveCompanyDetails(@ModelAttribute("companyDetails") CompanyDetails companyDetails,HttpServletRequest request) {
			companyDetailsService.Save(companyDetails);
				
			return "redirect:/SavePage";
		}
		
		@GetMapping("/editcompanydetails/{company_id}")
		public String UpdateCompanyDetails(@PathVariable(value="company_id")Integer id,Model model) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
	    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
	    	CompanyDetails companyDetails = companyDetailsService.get(id);
	    	model.addAttribute("companyDetails",companyDetails);
			return "editcompanydetails"; 
		}
		
		@GetMapping("/deletecompanydetails/{company_id}")
		public String deleteCompanyDetails(@PathVariable(value="company_id")Integer id) {
			//call delete employee method
			this.companyDetailsService.delete(id);
			return "redirect:/companydetailsList";
			
		}

	///////////////////////////////////////////////////////// group company ////////////////////////////////////////
		
		
		@GetMapping("/groupcompanydetailslist")
		public String GroupCompanyDetailsList(Model model) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
	    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
			model.addAttribute("GroupCompanyDetailsList",groupCompanyService.listAll());
			return "groupcompanydetailslist";
		}  
		
		@GetMapping("/groupcompanydetails")
		public String GroupCompanyDetails(Model model) {
			//create model attribute to bind from data
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
	    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
			GroupCompany groupCompany = new GroupCompany();
			model.addAttribute("groupCompany",groupCompany);
			return "groupcompanydetails";
		}
		
		@PostMapping("/savegroupcompanydetails")
		public String SaveGroupCompany(@ModelAttribute("groupCompany") GroupCompany groupCompany) throws MessagingException, IOException {
			//save employee to database
			groupCompany.setStatus(true);
			groupCompanyService.Save(groupCompany);
		    	
			BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(4);	
			LoginUser m = new LoginUser();
			m.setUsername(groupCompany.getEmail_id());
			m.setPassword(bCryptPasswordEncoder.encode("admin"));
			m.setStatus(true);
			loginuserService.save(m);
			LoginUserAuthority a = new LoginUserAuthority();
			a.setAuthority_id(2);
			a.setUser_id(loginuserService.GetAuthorityID(groupCompany.getEmail_id()));
			loginuserauthorityService.save(a);
			
		    Employee employee = new Employee();
			employee.setEmail(groupCompany.getEmail_id());
//			CompanyDetails c = companyDetailsService.getDataByEmailID(groupCompany.getEmail_id());
			GroupCompany c = groupCompanyService.getDataByEmailId(groupCompany.getEmail_id());
//		    employee.setGroup_id(c1.getGroupcompany_id());
		    employee.setCompany_id(c.getGroupcompany_id());
		    employee.setAuthority_id(a.getAuthority_id());
	    	employee.setPrefix(groupCompany.getCompanyprefix());
	    	employee.setSufix("000001");
	    	employee.setAge(0);
	    	employee.setDepartment("-");
	    	employee.setReporting_manager(0);
	    	employee.setStatus(true);
			employeeService.saveEmployee(employee);
				
			int e = employeeService.getEmployeeID(employee.getEmail());
				CompanyGrossPay cg = new CompanyGrossPay();
				cg.setEmployee_id(e);
				cg.setBasic("true");
				cg.setDa("false");
				cg.setHra("false");
				cg.setConveyance_allowance("false");
				cg.setDearness_allowance("false");
				cg.setMedical_allowance("false");
				cg.setOther_allowance("false");
				cg.setProject_allowance("false");
				cg.setProject_travel("false");
				cg.setSpecial_allowance("false");
				cg.setTravel_allowance("false");
				cg.setCompany_id(c.getGroupcompany_id());
				companyGrossPayService.Save(cg);
				
				EmployeeGrossPay g = new EmployeeGrossPay();
				g.setEmpId(e);
				g.setBasic(0);
				g.setDa(0);
				g.setHra(0);
				g.setConveyance_allowance(0);
				g.setDearness_allowance(0);
				g.setMedical_allowance(0);
				g.setOther_allowance(0);
				g.setProject_allowance(0);
				g.setProject_travel(0);
				g.setSpecial_allowance(0);
				g.setTravel_allowance(0);
				g.setEmployee_ESI(0);
				g.setEmployee_Gratuity(0);
				g.setEmployee_Loan_Recovery(0);
				g.setEmployee_PF(0);
				g.setEmployee_Professional_Tax(0);
				g.setEmployee_TDS(0);
				g.setEmployer_ESI(0);
				g.setEmployer_Gratuity(0);
				g.setEmployer_Loan_Recovery(0);
				g.setEmployer_PF(0);
				g.setEmployer_Professional_Tax(0);
				g.setEmployer_TDS(0);
				g.setCompany_id(c.getGroupcompany_id());
				employeeGrossPayService.Save(g);
				
				Deductions deductions = new Deductions();
				deductions.setId(e);
				deductions.setEmployee_ESI("false");
				deductions.setEmployee_Gratuity("false");
				deductions.setEmployee_Loan_Recovery("false");
				deductions.setEmployee_PF("false");
				deductions.setEmployee_Professional_Tax("false");
				deductions.setEmployee_TDS("false");
				deductions.setEmployer_ESI("false");
				deductions.setEmployer_Gratuity("false");
				deductions.setEmployer_Loan_Recovery("false");
				deductions.setEmployer_PF("false");
				deductions.setEmployer_Professional_Tax("false");
				deductions.setEmployer_TDS("false");
				deductions.setCompany_id(c.getGroupcompany_id());
				deductionsService.Save(deductions);
				
//				String text="Welcome " +  ","  +  "\n"  +  "This is Your Login Username :  "  +  m.getUsername() + " and Password : "  +  "admin"  + " for Online HR Portal."  +  "\n"  +  " Please do not share password with anyone.";
//				Mail mail = new Mail();
//			    mail.setFrom(mail_username);
//			    mail.setMailTo(m.getUsername());
//			    mail.setSubject("Welcome To Online HR Portal");
//			    emailSendLoginPasswordUserService.senduserloginpasswordEmail(mail, text);
			    
			return "redirect:/groupcompanydetailslist";
		}
		
		@PostMapping("/editsavegroupcompanydetails")
		public String editSaveGroupCompany(@ModelAttribute("groupCompany") GroupCompany groupCompany) {
			//save employee to database
		    	groupCompanyService.Save(groupCompany);
			return "redirect:/SavePage";
		}
		
		
		@GetMapping("/updategroupcompany/{groupcompany_id}")
		public String UpdateGroupCompany(@PathVariable(value="groupcompany_id")int id,Model model) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
	    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
	    	GroupCompany groupCompany = groupCompanyService.get(id);
	    	model.addAttribute("groupCompany",groupCompany);
			return "editgroupcompany"; 
		}
		
		@GetMapping("/deletegroupcompany/{groupcompany_id}")
		public String deleteGroupCompany(@PathVariable(value="groupcompany_id")Integer id) {
			//call delete employee method
			this.groupCompanyService.delete(id);
			return "redirect:/groupcompanydetailslist";
			
		}
		
////////////////////////////////////////// company ///////////////////////////////////////////////////////////		
		
		@RequestMapping(value = "/astatus/{status}/{company_id}", method = RequestMethod.GET)
		public String acceptOrReject(Model model, @PathVariable("status") String action, @PathVariable("company_id") Integer id) throws MessagingException, IOException {
		    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		    CompanyDetails c = companyDetailsService.get(id);
		    if (action.equals("active")) {
		        c.setStatus(true);
		        updateLoginStatusAdmin(c.getEmail_id(), true);
		    } else if (action.equals("inactive")) {
		        c.setStatus(false);
		        updateLoginStatusAdmin(c.getEmail_id(), false);
		    }
		    companyDetailsService.Save(c);
		    
		 // Update the status of associated employees
		    List<Employee> employees = employeeService.findByCompanyId(id);
		    for (Employee employee : employees) {
		    	if (action.equals("active")) {
			        employee.setStatus(true);
			        updateLoginStatus1(employee.getEmail(), true);
			    } else if (action.equals("inactive")) {
			        employee.setStatus(false);
			        updateLoginStatus1(employee.getEmail(), false);
			    }
		        employeeService.saveEmployee(employee);
		    }

		    return "redirect:/companydetailsList";
		}
		
		private void updateLoginStatusAdmin(String email, boolean status) {
		    LoginUser loginUser = loginuserService.findByUserName(email); // Assuming you have a method to retrieve a user by username
		    if (loginUser != null) {
		        loginUser.setStatus(status);
		        loginuserService.save(loginUser); // Assuming you have a method to save the updated login user
		    }
		}
		
		private void updateLoginStatus1(String email, boolean status) {
		    List<LoginUser> loginUser = loginuserService.getUserName(email); // Assuming you have a method to retrieve a user by username
		    for(LoginUser loginUser1:loginUser) {
			    if (loginUser1 != null) {
			        loginUser1.setStatus(status);
			        loginuserService.save(loginUser1); // Assuming you have a method to save the updated login user
			    }
		    }
		}
	
//////////////////////////////////////////////group company ////////////////////////////////////////////////////////
		
		@RequestMapping(value = "/gstatus/{status}/{groupcompany_id}", method = RequestMethod.GET)
		public String acceptOrRejectGroup(Model model, @PathVariable("status") String action, @PathVariable("groupcompany_id") Integer id) throws MessagingException, IOException {
		    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		    GroupCompany c = groupCompanyService.get(id);
		    if (action.equals("active")) {
		        c.setStatus(true);
		        updateLoginStatusAdminGroup(c.getEmail_id(), true);
		    } else if (action.equals("inactive")) {
		        c.setStatus(false);
		        updateLoginStatusAdminGroup(c.getEmail_id(), true);
		    }
		    groupCompanyService.Save(c);
		    
		 // Update the status of associated employees
		    List<Employee> employees = employeeService.findByCompanyId(id);
		    for (Employee employee : employees) {
		    	if (action.equals("active")) {
			        employee.setStatus(true);
			        updateLoginStatusGroup(employee.getEmail(), true);
			    } else if (action.equals("inactive")) {
			        employee.setStatus(false);
			        updateLoginStatusGroup(employee.getEmail(), true);
			    }
		        employeeService.saveEmployee(employee);
		    }

		    return "redirect:/groupcompanydetailslist";
		}
		
		private void updateLoginStatusAdminGroup(String email, boolean status) {
		    LoginUser loginUser = loginuserService.findByUserName(email); // Assuming you have a method to retrieve a user by username
		    if (loginUser != null) {
		        loginUser.setStatus(status);
		        loginuserService.save(loginUser); // Assuming you have a method to save the updated login user
		    }
		}
		
		private void updateLoginStatusGroup(String email, boolean status) {
		    List<LoginUser> loginUser = loginuserService.getUserName(email); // Assuming you have a method to retrieve a user by username
		    for(LoginUser loginUser1:loginUser) {
			    if (loginUser1 != null) {
			        loginUser1.setStatus(status);
			        loginuserService.save(loginUser1); // Assuming you have a method to save the updated login user
			    }
		    }
		}

}
