package com.example.springboot.Leave;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.example.springboot.CompanyProfile.CompanyDetails;
import com.example.springboot.CompanyProfile.CompanyDetailsService;
import com.example.springboot.EmployeesDetails.Employee;
import com.example.springboot.EmployeesDetails.EmployeeService;
import com.example.springboot.LeaveCategory.LeaveCategory;
import com.example.springboot.LeaveCategory.LeaveCategoryService;
import com.example.springboot.Login.EmailService;
import com.example.springboot.Login.LoginUserAuthorityService;
import com.example.springboot.Login.LoginUserService;
import com.example.springboot.Login.Mail;
import com.example.springboot.Login.Mainsidebarauthority;
import com.example.springboot.Login.MainsidebarauthorityService;
import com.example.springboot.MainMaster.EmployeeDesignationDetails;
import com.example.springboot.MainMaster.FunctionalArea;
import com.example.springboot.TimeSheet.TimeSheet;
import com.example.springboot.TimeSheet.TimeSheetMailReport;
import com.example.springboot.TimeSheet.TimeSheetReport;
import com.example.springboot.TimeSheet.TimeSheetReportService;

@Controller
public class LeaveController {
	

	@Autowired
	private LeaveService leaveService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private MainsidebarauthorityService mainsidebarauthorityService;
	@Autowired
	private LoginUserAuthorityService loginuserauthorityService;
	@Autowired
	private LoginUserService loginuserService;
	@Autowired
	private EmailService emailService;
	@Autowired
	private LeaveCategoryService leaveCategoryService;
	@Autowired 
	private LeavePolicyService leavePolicyService;
	@Autowired
	private LeaveBalanceService leaveBalanceService;
	@Autowired
	private CompanyDetailsService companyDetailsService;

	@Value("${spring.mail.username}") 
	private String mail_username;
	
	
	//display leaves
	@GetMapping("/leaveSheet")
	public String leavepage(Model model,HttpServletRequest request) throws ParseException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
    	LocalDateTime mydate = LocalDateTime.now();
		DateTimeFormatter myformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    	List<Leave> leaves = leaveService.getLeaveListByCurrentDate(mydate.format(myformatter));
		model.addAttribute("leaveList",leaves);
		Leave leave=new Leave();
		model.addAttribute("leave", leave);
		List<LeaveCategory> leaveCategories = leaveCategoryService.listAll();
		model.addAttribute("leavecategorylist", leaveCategories);
		model.addAttribute("UserName", employeeService.Update(request.getUserPrincipal().getName()));
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		
	    LocalDateTime now = LocalDateTime.now();
	    int currentYear = Year.now().getValue();
    	int currentMonthValue = now.getMonthValue();
//	    System.out.println("month : " + currentMonthValue);
	    int employeeId = employeeService.Update(request.getUserPrincipal().getName());
//	    System.out.println("employee _id : " + employeeId);
	    int paidLeaveCount = leaveService.getEmployeeLeaveMonthEmpID(currentMonthValue, employeeId);
//	    System.out.println("Count Paid Leave : " + paidLeaveCount);
	    int carriedForwardLeave1 = leaveBalanceService.getCarriedForwardLeave(employeeId,currentYear);
//	    System.out.println("Count CarriedForwardLeave : " + carriedForwardLeave1);
	    int monthlyPaidLeave = leavePolicyService.findMonthlyPaidLeaveCount(employeeService.getCompanyID(auth.getName()), currentYear);
	    int totalPaidLeave = monthlyPaidLeave + carriedForwardLeave1 ;
//	    System.out.println("totalPaidLeave : " + totalPaidLeave);
	    String leaveStatus = "false";
	    LeavePolicy leavePolicy = leavePolicyService.findLeavePolicyByYearAndCompanyId(currentYear, employeeService.getCompanyID(auth.getName()));
	    if(paidLeaveCount > leavePolicy.getPaidLeave()) {
	    	leaveStatus = "true";
	    }
	    else {
	    	int carriedForwardLeave = leaveBalanceService.getCarriedForwardLeave(employeeId,currentYear);
	    	 if(carriedForwardLeave > 0) {
	    		 leaveStatus = "true";
	    	 }
	    	 else {
				leaveStatus="false";
			}
		}
	   
//	    System.out.println("Leave Status : " + leaveStatus);
	    model.addAttribute("leaveStatus",leaveStatus);
	    if (model.containsAttribute("successMessage")) {
	        String successMessage = (String) model.asMap().get("successMessage");
	        model.addAttribute("successMessage", successMessage);
	    }
	    model.addAttribute("successMessage", "Your remaining paid leave is : " + carriedForwardLeave1);
        return "leavesheet";
	}
	
	 
	@PostMapping("/saveLeaveSheet")
	public String saveLeaveSheet(@ModelAttribute("leave") Leave leave,HttpServletRequest request,Model model,
			@RequestParam("ndays") Integer ndays, RedirectAttributes redirectAttributes) throws ParseException, MessagingException, IOException {
		//save to database
		int employee_id = employeeService.Update(request.getUserPrincipal().getName());
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		LocalDateTime mydate = LocalDateTime.now();
		DateTimeFormatter myformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDateTime now = LocalDateTime.now();
	    int currentYear = Year.now().getValue();
    	int currentMonthValue = now.getMonthValue();
//	    System.out.println("month : " + currentMonthValue);
	    int employeeId = employeeService.Update(request.getUserPrincipal().getName());
//	    System.out.println("employee _id : " + employeeId);
	    int carriedForwardLeave1 = leaveBalanceService.getCarriedForwardLeave(employeeId,currentYear);
//	    System.out.println("Count CarriedForwardLeave : " + carriedForwardLeave1);
	    Employee c1 = employeeService.getEmployeeDataByEmailID(employee_id);
	    int monthlyPaidLeave = leavePolicyService.findMonthlyPaidLeaveCount(c1.getCompany_id(), currentYear);
	    int totalPaidLeave = monthlyPaidLeave + carriedForwardLeave1 ;
//	    System.out.println("totalPaidLeave : " + totalPaidLeave);
	    
			if(leave.getType().equals("Paid Leave")) {
			    int paidLeaveCount = leaveService.getEmployeeLeaveMonthEmpID(currentMonthValue, employeeId);
			    LeavePolicy leavePolicy = leavePolicyService.findLeavePolicyByYearAndCompanyId(currentYear, c1.getCompany_id());
			    if(paidLeaveCount > leavePolicy.getPaidLeave()) {
			    	
			    	if (ndays > totalPaidLeave) {
			    		model.addAttribute("message", "The selected date range exceeds your total paid leave balance.");
			    		return "redirect:/leaveSheet";
			        }
			    	else {
						SimpleDateFormat myformatter1 = new SimpleDateFormat("yyyy-MM-dd");
				    	SimpleDateFormat myformatter2 = new SimpleDateFormat("dd-MM-yyyy");
				    	Date d1 = myformatter2.parse(leave.getDateFrom());
				    	Date d2 = myformatter2.parse(leave.getDateTo());
				    	leave.setFromDateYear(myformatter1.format(d1));
						leave.setToDateYear(myformatter1.format(d2));
						leave.setCurrentdate(mydate.format(myformatter));
						leave.setEmployee_id(employeeService.Update(request.getUserPrincipal().getName()));
						Employee c = employeeService.getEmployeeDataByEmailID(employee_id);
						leave.setCompany_id(c.getCompany_id());
						leaveService.saveLeaves(leave);
						
						Integer n_days = leaveService.countNumberOfDays(employeeService.Update(request.getUserPrincipal().getName()),myformatter1.format(d1),myformatter1.format(d2));
//						System.out.println("Count : " + n_days);
						leave.setnDays(n_days);
						leaveService.saveLeaves(leave);
						return "redirect:/leaveSheet";
			    	}
			   }
			   else {
				   if (ndays > totalPaidLeave) {
			    		model.addAttribute("message", "The selected date range exceeds your total paid leave balance.");
			    		return "redirect:/leaveSheet";
			        }
				   else {
			    	int carriedForwardLeave = leaveBalanceService.getCarriedForwardLeave(employeeId,currentYear);
			    	if (carriedForwardLeave > 0) {
			    		LeaveBalance l = leaveBalanceService.getBalanceLeave(employeeService.Update(request.getUserPrincipal().getName()), currentYear);
			    	    if (carriedForwardLeave >= ndays) {
			    	        l.setCarried_forward_leave(l.getCarried_forward_leave() - ndays);
			    	        leaveBalanceService.Save(l);
			    	        
				    	    SimpleDateFormat myformatter1 = new SimpleDateFormat("yyyy-MM-dd");
				    	    SimpleDateFormat myformatter2 = new SimpleDateFormat("dd-MM-yyyy");
				    	    Date d1 = myformatter2.parse(leave.getDateFrom());
				    	    Date d2 = myformatter2.parse(leave.getDateTo());
				    	    leave.setFromDateYear(myformatter1.format(d1));
				    	    leave.setToDateYear(myformatter1.format(d2));
				    	    leave.setCurrentdate(mydate.format(myformatter));
				    	    leave.setEmployee_id(employeeService.Update(request.getUserPrincipal().getName()));
				    	    Employee c = employeeService.getEmployeeDataByEmailID(employee_id);
				    	    leave.setCompany_id(c.getCompany_id());
				    	    leaveService.saveLeaves(leave);
				    	    
				    	    Integer n_days = leaveService.countNumberOfDays(employeeService.Update(request.getUserPrincipal().getName()), myformatter1.format(d1), myformatter1.format(d2));
//				    	    System.out.println("Count : " + n_days);
				    	    leave.setnDays(n_days);
				    	    leaveService.saveLeaves(leave);
			    	    } else {
//			    	        System.out.println("Carried Forward Leave is not sufficient. Data not saved in the database.");
			    	        // You can handle this case as per your requirements, such as showing an error message
			    	        return "redirect:/leaveSheet";
			    	    }
			    	    
			    	    return "redirect:/leaveSheet";
			    	}
			    	 else {
			 			SimpleDateFormat myformatter1 = new SimpleDateFormat("yyyy-MM-dd");
			 	    	SimpleDateFormat myformatter2 = new SimpleDateFormat("dd-MM-yyyy");
			 	    	Date d1 = myformatter2.parse(leave.getDateFrom());
			 	    	Date d2 = myformatter2.parse(leave.getDateTo());
			 	    	leave.setFromDateYear(myformatter1.format(d1));
			 			leave.setToDateYear(myformatter1.format(d2));
			 			leave.setCurrentdate(mydate.format(myformatter));
			 			leave.setEmployee_id(employeeService.Update(request.getUserPrincipal().getName()));
			 			Employee c = employeeService.getEmployeeDataByEmailID(employee_id);
			 			leave.setCompany_id(c.getCompany_id());
			 			leave.setType("Unpaid Leave");
			 			leaveService.saveLeaves(leave);
			 			
			 			Integer n_days = leaveService.countNumberOfDays(employeeService.Update(request.getUserPrincipal().getName()),myformatter1.format(d1),myformatter1.format(d2));
//			 			System.out.println("Count : " + n_days);
			 			leave.setnDays(n_days);
			 			leaveService.saveLeaves(leave);
			 			return "redirect:/leaveSheet";
					}
				}
			   }
			}
			else {
				SimpleDateFormat myformatter1 = new SimpleDateFormat("yyyy-MM-dd");
		    	SimpleDateFormat myformatter2 = new SimpleDateFormat("dd-MM-yyyy");
		    	Date d1 = myformatter2.parse(leave.getDateFrom());
		    	Date d2 = myformatter2.parse(leave.getDateTo());
		    	leave.setFromDateYear(myformatter1.format(d1));
				leave.setToDateYear(myformatter1.format(d2));
				leave.setCurrentdate(mydate.format(myformatter));
				leave.setEmployee_id(employeeService.Update(request.getUserPrincipal().getName()));
				Employee c = employeeService.getEmployeeDataByEmailID(employee_id);
				leave.setCompany_id(c.getCompany_id());
				leaveService.saveLeaves(leave);
				
				Integer n_days = leaveService.countNumberOfDays(employeeService.Update(request.getUserPrincipal().getName()),myformatter1.format(d1),myformatter1.format(d2));
//				System.out.println("Count : " + n_days);
				leave.setnDays(n_days);
				leaveService.saveLeaves(leave);
			}
		
		Employee emp = employeeService.getUsername(request.getUserPrincipal().getName());
		Mail mail = new Mail();
		mail.setFrom(emp.getEmail());
		mail.setMailTo(mail_username);
		mail.setSubject("Leave Report");
		Map<String, Object> model1 = new HashMap<String, Object>();
		model1.put("companyName", companyDetailsService.getCompanyNameByID(emp.getCompany_id()));
		model1.put("ename", emp.getFirstname() + " " + emp.getLastname());
		model1.put("LeaveMailReportList", leaveService.getListByUserNameANDCurrentdate(employeeService.Update(request.getUserPrincipal().getName()), mydate.format(myformatter)));
		mail.setProps(model1);
		emailService.sendEmail1(mail);

		model.addAttribute("LeaveMailReportList",leaveService.getListByUserNameANDCurrentdate(employeeService.Update(request.getUserPrincipal().getName()), mydate.format(myformatter)));
		model.addAttribute("EmpList",employeeService.getAllEmployees());
		int n = loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName()));
		String status = "false";
		if(n==1 || n==3) {
			status = "true";
		}
		model.addAttribute("status", status);
		model.addAttribute("UserName", employeeService.Update(request.getUserPrincipal().getName()));
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		redirectAttributes.addFlashAttribute("successMessage", "Carried Forward Leave is not sufficient. Data not saved in the database!");
		return "redirect:/leaveSheet";
	}
	
	@PostMapping("/EditSaveLeaveSheet")
	public String EditSaveLeaveSheet(@ModelAttribute("leave") Leave leave,HttpServletRequest request,Model model, @RequestParam("ndays") Integer ndays) throws ParseException, MessagingException, IOException {
		//save to database
		LocalDateTime now = LocalDateTime.now();
	    int currentYear = Year.now().getValue();
    	int currentMonthValue = now.getMonthValue();
	    System.out.println("month : " + currentMonthValue);
	    int employeeId = employeeService.Update(request.getUserPrincipal().getName());
	    System.out.println("employee _id : " + employeeId);
	    int carriedForwardLeave1 = leaveBalanceService.getCarriedForwardLeave(employeeId,currentYear);
	    System.out.println("Count CarriedForwardLeave : " + carriedForwardLeave1);
	    Employee c1 = employeeService.getEmployeeDataByEmailID(employeeId);
	    int monthlyPaidLeave = leavePolicyService.findMonthlyPaidLeaveCount(c1.getCompany_id(), currentYear);
	    int totalPaidLeave = monthlyPaidLeave + carriedForwardLeave1 ;
	    System.out.println("totalPaidLeave : " + totalPaidLeave);
	    
			if(leave.getType().equals("Paid Leave")) {
			    int paidLeaveCount = leaveService.getEmployeeLeaveMonthEmpID(currentMonthValue, employeeId);
			    LeavePolicy leavePolicy = leavePolicyService.findLeavePolicyByYearAndCompanyId(currentYear, c1.getCompany_id());
			    if(paidLeaveCount > leavePolicy.getPaidLeave()) {
			    	
			    	if (ndays > totalPaidLeave) {
			    		model.addAttribute("message", "The selected date range exceeds your total paid leave balance.");
			    		return "redirect:/leaveSheet";
			        }
			    	else {
				    	LocalDateTime mydate = LocalDateTime.now();
						DateTimeFormatter myformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
						SimpleDateFormat myformatter1 = new SimpleDateFormat("yyyy-MM-dd");
				    	SimpleDateFormat myformatter2 = new SimpleDateFormat("dd-MM-yyyy");
				    	Date d1 = myformatter2.parse(leave.getDateFrom());
				    	Date d2 = myformatter2.parse(leave.getDateTo());
				    	leave.setFromDateYear(myformatter1.format(d1));
						leave.setToDateYear(myformatter1.format(d2));
						leave.setCurrentdate(mydate.format(myformatter));
						leave.setEmployee_id(employeeService.Update(request.getUserPrincipal().getName()));
						Employee c = employeeService.getEmployeeDataByEmailID(employeeId);
						leave.setCompany_id(c.getCompany_id());
						leaveService.saveLeaves(leave);
						
						Integer n_days = leaveService.countNumberOfDays(employeeService.Update(request.getUserPrincipal().getName()),myformatter1.format(d1),myformatter1.format(d2));
						System.out.println("Count : " + n_days);
						leave.setnDays(n_days);
						leaveService.saveLeaves(leave);
						return "redirect:/leaveSheet";
			    	}
			   }
			   else {
				   if (ndays > totalPaidLeave) {
			    		model.addAttribute("message", "The selected date range exceeds your total paid leave balance.");
			    		return "redirect:/leaveSheet";
			        }
				   else {
			    	int carriedForwardLeave = leaveBalanceService.getCarriedForwardLeave(employeeId,currentYear);
			    	if (carriedForwardLeave > 0) {
			    		LeaveBalance l = leaveBalanceService.getBalanceLeave(employeeService.Update(request.getUserPrincipal().getName()), currentYear);
			    	    if (carriedForwardLeave >= ndays) {
			    	        l.setCarried_forward_leave(l.getCarried_forward_leave() - ndays);
			    	        leaveBalanceService.Save(l);
			    	        
			    	        LocalDateTime mydate = LocalDateTime.now();
				    	    DateTimeFormatter myformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
				    	    SimpleDateFormat myformatter1 = new SimpleDateFormat("yyyy-MM-dd");
				    	    SimpleDateFormat myformatter2 = new SimpleDateFormat("dd-MM-yyyy");
				    	    Date d1 = myformatter2.parse(leave.getDateFrom());
				    	    Date d2 = myformatter2.parse(leave.getDateTo());
				    	    leave.setFromDateYear(myformatter1.format(d1));
				    	    leave.setToDateYear(myformatter1.format(d2));
				    	    leave.setCurrentdate(mydate.format(myformatter));
				    	    leave.setEmployee_id(employeeService.Update(request.getUserPrincipal().getName()));
				    	    Employee c = employeeService.getEmployeeDataByEmailID(employeeId);
				    	    leave.setCompany_id(c.getCompany_id());
				    	    leaveService.saveLeaves(leave);
				    	    
				    	    Integer n_days = leaveService.countNumberOfDays(employeeService.Update(request.getUserPrincipal().getName()), myformatter1.format(d1), myformatter1.format(d2));
				    	    System.out.println("Count : " + n_days);
				    	    leave.setnDays(n_days);
				    	    leaveService.saveLeaves(leave);
			    	    } else {
			    	        System.out.println("Carried Forward Leave is not sufficient. Data not saved in the database.");
			    	        // You can handle this case as per your requirements, such as showing an error message
			    	        return "redirect:/leaveSheet";
			    	    }
			    	    
			    	    return "redirect:/leaveSheet";
			    	}
			    	 else {
			    		LocalDateTime mydate = LocalDateTime.now();
			 			DateTimeFormatter myformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			 			SimpleDateFormat myformatter1 = new SimpleDateFormat("yyyy-MM-dd");
			 	    	SimpleDateFormat myformatter2 = new SimpleDateFormat("dd-MM-yyyy");
			 	    	Date d1 = myformatter2.parse(leave.getDateFrom());
			 	    	Date d2 = myformatter2.parse(leave.getDateTo());
			 	    	leave.setFromDateYear(myformatter1.format(d1));
			 			leave.setToDateYear(myformatter1.format(d2));
			 			leave.setCurrentdate(mydate.format(myformatter));
			 			leave.setEmployee_id(employeeService.Update(request.getUserPrincipal().getName()));
			 			Employee c = employeeService.getEmployeeDataByEmailID(employeeId);
			 			leave.setCompany_id(c.getCompany_id());
			 			leave.setType("Unpaid Leave");
			 			leaveService.saveLeaves(leave);
			 			
			 			Integer n_days = leaveService.countNumberOfDays(employeeService.Update(request.getUserPrincipal().getName()),myformatter1.format(d1),myformatter1.format(d2));
			 			System.out.println("Count : " + n_days);
			 			leave.setnDays(n_days);
			 			leaveService.saveLeaves(leave);
			 			return "redirect:/leaveSheet";
					}
				}
			   }
			}
			else {
				LocalDateTime mydate = LocalDateTime.now();
				DateTimeFormatter myformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
				SimpleDateFormat myformatter1 = new SimpleDateFormat("yyyy-MM-dd");
		    	SimpleDateFormat myformatter2 = new SimpleDateFormat("dd-MM-yyyy");
		    	Date d1 = myformatter2.parse(leave.getDateFrom());
		    	Date d2 = myformatter2.parse(leave.getDateTo());
		    	leave.setFromDateYear(myformatter1.format(d1));
				leave.setToDateYear(myformatter1.format(d2));
				leave.setCurrentdate(mydate.format(myformatter));
				leave.setEmployee_id(employeeService.Update(request.getUserPrincipal().getName()));
				Employee c = employeeService.getEmployeeDataByEmailID(employeeId);
				leave.setCompany_id(c.getCompany_id());
				leaveService.saveLeaves(leave);
				
				Integer n_days = leaveService.countNumberOfDays(employeeService.Update(request.getUserPrincipal().getName()),myformatter1.format(d1),myformatter1.format(d2));
				System.out.println("Count : " + n_days);
				leave.setnDays(n_days);
				leaveService.saveLeaves(leave);
			}
		return "redirect:/leaveSheet";
	}
	
	@GetMapping("/EditLeaveForm/{id}")
	public String EditLeaveForm(@PathVariable(value="id")long id,Model model,HttpServletRequest request) throws MessagingException, IOException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		Leave leave=leaveService.getLeavesById(id);
		model.addAttribute("leave", leave);
		List<LeaveCategory> leaveCategories = leaveCategoryService.listAll();
		model.addAttribute("leavecategorylist", leaveCategories);
		model.addAttribute("UserName", employeeService.Update(request.getUserPrincipal().getName()));
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "edit_leavesheet";

		 
	}
	@GetMapping("/deleteLeaveForm/{id}")
	public String deleteLeaveForm(@PathVariable(value="id")long id) {
		//call delete method
		this.leaveService.deleteLeavesById(id);
		return "redirect:/ManageLeaves";
		      
	}
	
	@RequestMapping(value="/ManageLeaves",method= RequestMethod.GET)
    public String manageLeaves(Model model, HttpServletRequest request) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
		model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		model.addAttribute("leaveList",leaveService.getAllLeaves());
		HashSet<Employee> employees = new HashSet<Employee>();
    	
    	List<Employee> employees1 = employeeService.FilterByReportingManager(employeeService.Update(request.getUserPrincipal().getName()));
    	for(Employee e:employees1) {
    		employees.add(e);
    		List<Employee> employees2 = employeeService.FilterByReportingManager(e.getId());
    		for(Employee e1:employees2) {
    			employees.add(e1);
    			List<Employee> employees3 = employeeService.FilterByReportingManager(e1.getId());
    			for(Employee e2:employees3) {
    				employees.add(e2);
    				List<Employee> employees4 = employeeService.FilterByReportingManager(e2.getId());
    				for(Employee e3:employees4) {
    					employees.add(e3);
    				}
    			}
    		}
    	}
    	int n = loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName()));
		String status = "false";
		if(n==3) {
			status = "true";
		}
		model.addAttribute("status", status);
    	model.addAttribute("ListUserName", employees);
    	List<LeaveCategory> leaveCategories = leaveCategoryService.listAll();
		model.addAttribute("leavecategorylist", leaveCategories);
    	model.addAttribute("EmployeeList", employeeService.getAllEmployees());
    	model.addAttribute("UserName",request.getUserPrincipal().getName());
    	model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "ManageLeaves";
    }

	
	@RequestMapping(value = "/ManageLeaves/{action}/{id}", method = RequestMethod.GET)
	public String acceptOrRejectLeaves(Model model, @PathVariable("action") String action,@PathVariable("id") int id , HttpServletRequest request) throws MessagingException, IOException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Leave leave = leaveService.getLeavesById(id);
		if (action.equals("accept")) {
		    leave.setAcceptRejectFlag(true);
		    leave.setActive(true);
		} else if (action.equals("pending")) {
		    leave.setAcceptRejectFlag(false);
		    leave.setActive(false);
		}else if (action.equals("reject")) {
		    leave.setAcceptRejectFlag(true);
		    leave.setActive(false);
		}
		leaveService.saveLeaves(leave);
		
		Employee emp = employeeService.getUsername(request.getUserPrincipal().getName());
		Mail mail = new Mail();
		mail.setFrom(mail_username);
		mail.setMailTo(emp.getEmail());
		mail.setSubject("Leave Report");
		Map<String, Object> model1 = new HashMap<String, Object>();
		model1.put("LeaveMailReportList", leaveService.getListByUserName(employeeService.Update(request.getUserPrincipal().getName())));
		mail.setProps(model1);
		emailService.sendEmail1(mail);
		int n = loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName()));
		String status = "false";
		if(n==1 || n==3) {
			status = "true";
		}
		model.addAttribute("status", status);
		model.addAttribute("successMessage", "Updated Successfully!");
		return "redirect:/ManageLeaves";
	}

	//display Manage leaves
	@GetMapping("/myLeaves")
	public String MyLeaves(Model model,HttpServletRequest request) {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
    	model.addAttribute("UserName", employeeService.Update(request.getUserPrincipal().getName()));
		model.addAttribute("leaveList",leaveService.getAllLeaves());
		List<LeaveCategory> leaveCategories = leaveCategoryService.listAll();
		model.addAttribute("leavecategorylist", leaveCategories);
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "myLeaves";	
	}
	
	@GetMapping("/EmployeeOnHoliday")
	public String employeeONHolidays(Model model,HttpServletRequest request) {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
    	model.addAttribute("EmployeeList", employeeService.getAllEmployees());
		model.addAttribute("leaveList",leaveService.getLeaveListByOrder1());
		model.addAttribute("leavecategorylist", leaveCategoryService.listAll());
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "EmployeeOnHoliday";	
	}
	
	@GetMapping("/LeaveListExcel")
	public String ViewReportLeaveListExcel(Model model,@Param("username") String username,@Param("fromdate") String fromdate, @Param("todate") String todate){
		//String fname = "";
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
    	LocalDateTime mydate = LocalDateTime.now();
		DateTimeFormatter myformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    	int year = mydate.getYear();
		int month = mydate.getMonthValue();
		Calendar calendar = Calendar.getInstance();
	    calendar.set(1, month - 1, year);
//    	System.out.println("fromdate : " + "01-0" + month + "-" + year);
//    	System.out.println("todate" + mydate.format(myformatter));
    	if(month <=9) {
    		if(fromdate == null || todate == null) {
    			List<Leave> leaveList1 = leaveService.getAllLeaves();
    			model.addAttribute("leaveList",leaveList1);
    			model.addAttribute("username",username);
    			model.addAttribute("fromdate","01-0" + month + "-" + year);
    			model.addAttribute("todate",mydate.format(myformatter));
    		}
    		else {
    			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    			LocalDate FromDate = LocalDate.parse(fromdate, formatter);
    			LocalDate ToDate = LocalDate.parse(todate, formatter);
    			List<Leave> leaveList=new LinkedList<>();
    			List<Leave> leaveList1 = leaveService.getAllLeaves();
//    			for(Leave l:leaveList1) {
//    				LocalDate date1 = LocalDate.parse(l.getDateFrom(), formatter);
//    				LocalDate date2 = LocalDate.parse(l.getDateTo(), formatter);
//    				if(date1.compareTo(FromDate)>=0 && date2.compareTo(ToDate)<=0) {
//    					leaveList.add(l);
//    				}
//    			}
    			model.addAttribute("leaveList",leaveList);
    			model.addAttribute("username",username);
    			model.addAttribute("fromdate",fromdate);
    			model.addAttribute("todate",todate);
    		}
    	}
    	else {
    		if(fromdate == null || todate == null) {
    			List<Leave> leaveList1 = leaveService.getAllLeaves();
    			model.addAttribute("leaveList",leaveList1);
    			model.addAttribute("username",username);
    			model.addAttribute("fromdate","01-" + month + "-" + year);
    			model.addAttribute("todate",mydate.format(myformatter));
    		}
    		else {
    			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    			LocalDate FromDate = LocalDate.parse(fromdate, formatter);
    			LocalDate ToDate = LocalDate.parse(todate, formatter);
    			List<Leave> leaveList=new LinkedList<>();
    			List<Leave> leaveList1 = leaveService.getAllLeaves();
//    			for(Leave l:leaveList1) {
//    				LocalDate date1 = LocalDate.parse(l.getDateFrom(), formatter);
//    				LocalDate date2 = LocalDate.parse(l.getDateTo(), formatter);
//    				if(date1.compareTo(FromDate)>=0 && date2.compareTo(ToDate)<=0) {
//    					leaveList.add(l);
//    				}
//    			}
    			model.addAttribute("leaveList",leaveList);
    			model.addAttribute("username",username);
    			model.addAttribute("fromdate",fromdate);
    			model.addAttribute("todate",todate);
    		}
    	}
		
		model.addAttribute("EmployeeList", employeeService.getAllEmployees());
		model.addAttribute("leavecategorylist", leaveCategoryService.listAll());
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "LeaveListExcel";
	}	
	
	
	@GetMapping("/LeaveListExcelEmployee")
	public String ViewReportLeaveListExcelEmployee(Model model,@Param("username") String username, @Param("fromdate") String fromdate, @Param("todate") String todate, HttpServletRequest request){
		//String fname = "";
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
    	LocalDateTime mydate = LocalDateTime.now();
		DateTimeFormatter myformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    	int year = mydate.getYear();
		int month = mydate.getMonthValue();
		Calendar calendar = Calendar.getInstance();
	    calendar.set(1, month - 1, year);
//    	System.out.println("fromdate : " + "01-0" + month + "-" + year);
//    	System.out.println("todate" + mydate.format(myformatter));
    	if(month <=9) {
    		if(fromdate == null || todate == null) {
    			List<Leave> leaveList1 = leaveService.getAllLeaves();
    			model.addAttribute("leaveList",leaveList1);
    			model.addAttribute("username",username);
    			model.addAttribute("fromdate","01-0" + month + "-" + year);
    			model.addAttribute("todate",mydate.format(myformatter));
    		}
    		else {
    			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    			LocalDate FromDate = LocalDate.parse(fromdate, formatter);
    			LocalDate ToDate = LocalDate.parse(todate, formatter);
    			List<Leave> leaveList=new LinkedList<>();
//    			List<Leave> leaveList1 = leaveService.findByEmployeeName(username);
//    			for(Leave l:leaveList1) {
//    				LocalDate date1 = LocalDate.parse(l.getDateFrom(), formatter);
//    				LocalDate date2 = LocalDate.parse(l.getDateTo(), formatter);
//    				if(date1.compareTo(FromDate)>=0 && date2.compareTo(ToDate)<=0) {
//    					leaveList.add(l);
//    				}
//    			}
    			model.addAttribute("leaveList",leaveList);
    			model.addAttribute("username",username);
    			model.addAttribute("fromdate",fromdate);
    			model.addAttribute("todate",todate);
    		}
    	}
    	else {
    		if(fromdate == null || todate == null) {
    			List<Leave> leaveList1 = leaveService.getAllLeaves();
    			model.addAttribute("leaveList",leaveList1);
    			model.addAttribute("username",username);
    			model.addAttribute("fromdate","01-" + month + "-" + year);
    			model.addAttribute("todate",mydate.format(myformatter));
    		}
    		else {
    			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    			LocalDate FromDate = LocalDate.parse(fromdate, formatter);
    			LocalDate ToDate = LocalDate.parse(todate, formatter);
    			List<Leave> leaveList=new LinkedList<>();
//    			List<Leave> leaveList1 = leaveService.findByEmployeeName(username);
//    			for(Leave l:leaveList1) {
//    				LocalDate date1 = LocalDate.parse(l.getDateFrom(), formatter);
//    				LocalDate date2 = LocalDate.parse(l.getDateTo(), formatter);
//    				if(date1.compareTo(FromDate)>=0 && date2.compareTo(ToDate)<=0) {
//    					leaveList.add(l);
//    				}
//    			}
    			model.addAttribute("leaveList",leaveList);
    			model.addAttribute("username",username);
    			model.addAttribute("fromdate",fromdate);
    			model.addAttribute("todate",todate);
    		}
    	}
		
    	HashSet<Employee> employees = new HashSet<Employee>();
    	
    	List<Employee> employees1 = employeeService.FilterByReportingManager(employeeService.Update(request.getUserPrincipal().getName()));
    	for(Employee e:employees1) {
    		employees.add(e);
    		List<Employee> employees2 = employeeService.FilterByReportingManager(e.getId());
    		for(Employee e1:employees2) {
    			employees.add(e1);
    			List<Employee> employees3 = employeeService.FilterByReportingManager(e1.getId());
    			for(Employee e2:employees3) {
    				employees.add(e2);
    				List<Employee> employees4 = employeeService.FilterByReportingManager(e2.getId());
    				for(Employee e3:employees4) {
    					employees.add(e3);
    				}
    			}
    		}
    	}
    	model.addAttribute("ListUserName", employees);
    	model.addAttribute("EmployeeList", employeeService.getAllEmployees());
    	model.addAttribute("leavecategorylist", leaveCategoryService.listAll());
    	model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "LeaveListExcelEmployee";
	}

	/////////////////////////////////////////////////////////////// Leave Policy ///////////////////////////////////////////////////////
	
	@GetMapping("/leave_policy")
	public String ShowLeavePolicy(Model model){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
		model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		model.addAttribute("LeavePolicyList", leavePolicyService.listAll());
		LeavePolicy leavePolicy = new LeavePolicy();
		model.addAttribute("leavePolicy", leavePolicy);
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "leavePolicy";
	}
	
	@PostMapping("/save/leave_policy")
	public String SaveLeavePolicy(@ModelAttribute("leavePolicy") LeavePolicy leavePolicy, HttpServletRequest request){
		Employee c = employeeService.getEmployeeDataByEmailID(1);
		leavePolicy.setCompany_id(c.getCompany_id());
		leavePolicyService.Save(leavePolicy);
		return "redirect:/leave_policy";
	}
		
	@GetMapping("edit/leave_policy/{id}")
	public String EditLeavePolicy(@PathVariable(value="id")int id,Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
		model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		LeavePolicy leavePolicy = leavePolicyService.get(id);
		model.addAttribute("leavePolicy", leavePolicy);
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "editLeavePolicy";
	}
	
	
	@GetMapping("/leave_balance")
	public String ShowLeaveBalance(Model model){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
		model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		model.addAttribute("BalanceLeaveList", leaveBalanceService.listAll());
		model.addAttribute("EmployeeList" , employeeService.getAllEmployees());
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "leave-balance";
	}

	
//	@GetMapping("/getPaidLeaveCount")
//	public String getEmployeeId(HttpServletRequest request, Model model) {
//	    
//	    
//	    return "redirect:/leaveSheet"; 
//	}

	@GetMapping("/leave_details")
	public String leaveDetails(Model model, HttpServletRequest request) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
	    model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
	    
	    LocalDateTime mydate = LocalDateTime.now();
	    DateTimeFormatter myformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	    List<Leave> leaves = leaveService.getLeaveListByCurrentDate(mydate.format(myformatter));
	    model.addAttribute("leaveList", leaves);
	    
	    Leave leave = new Leave();
	    model.addAttribute("leave", leave);
	    
	    List<LeaveCategory> leaveCategories = leaveCategoryService.listAll();
	    model.addAttribute("leavecategorylist", leaveCategories);
	    
	    Integer username = employeeService.Update(request.getUserPrincipal().getName());
	    model.addAttribute("UserName", username);
	    
	    Integer companyId = employeeService.getCompanyID(auth.getName());
	    model.addAttribute("CompanyID", companyId);
	    
	    LocalDateTime now = LocalDateTime.now();
	    int currentYear = Year.now().getValue();
	    int currentMonthValue = now.getMonthValue();
//	    System.out.println("month : " + currentMonthValue);
	    
	    int employeeId = employeeService.Update(request.getUserPrincipal().getName());
//	    System.out.println("employee _id : " + employeeId);
	    
	    int paidLeaveCount = leaveService.getEmployeeLeaveMonthEmpID(currentMonthValue, employeeId);
//	    System.out.println("Count Paid Leave : " + paidLeaveCount);
	    
	    int carriedForwardLeave1 = leaveBalanceService.getCarriedForwardLeave(employeeId, currentYear);
//	    System.out.println("Count CarriedForwardLeave : " + carriedForwardLeave1);
	    model.addAttribute("carriedForwardLeave1" , carriedForwardLeave1);
	    
	    int monthlyPaidLeave = leavePolicyService.findMonthlyPaidLeaveCount(employeeService.getCompanyID(auth.getName()), currentYear);
	    int totalPaidLeave = monthlyPaidLeave + carriedForwardLeave1 ;
//	    System.out.println("totalPaidLeave : " + totalPaidLeave);
	    
	    LeavePolicy leavePolicy = leavePolicyService.findLeavePolicyByYearAndCompanyId(currentYear, employeeService.getCompanyID(auth.getName()));
	    
	    int currentYear1= YearMonth.now().getYear();
        // Count the number of months in the current year
        int monthCount = YearMonth.of(currentYear1, 12).getMonthValue();
//        System.out.println("Number of months in the year: " + monthCount);
	    // Calculate and include the total paid leave in a year
	    int totalPaidLeaveInYear = leavePolicy.getPaidLeave() * monthCount;
	    model.addAttribute("totalPaidLeaveInYear", totalPaidLeaveInYear);
	    
	    int remainingPaidLeave = totalPaidLeaveInYear - paidLeaveCount;
	    model.addAttribute("remainingPaidLeave", remainingPaidLeave);
	    
	    return "employeeLeaveDetails";
	}




}
