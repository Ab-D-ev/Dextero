package com.example.springboot.Expense;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.example.springboot.ImagePathService;
import com.example.springboot.AddCompany.AddCustomer;
import com.example.springboot.AddProjectCode.projectName;
import com.example.springboot.CompanyProfile.CompanyDetails;
import com.example.springboot.CompanyProfile.CompanyDetailsService;
import com.example.springboot.EmployeesDetails.Employee;
import com.example.springboot.EmployeesDetails.EmployeeService;
import com.example.springboot.Leave.Leave;
import com.example.springboot.Login.EmailService;
import com.example.springboot.Login.LoginUserAuthorityService;
import com.example.springboot.Login.LoginUserService;
import com.example.springboot.Login.Mail;
import com.example.springboot.Login.Mainsidebarauthority;
import com.example.springboot.Login.MainsidebarauthorityService;

@Controller
public class ExpensesController{
	
	@Autowired 
	private CreateExpenseReportService ExReportService;
	@Autowired
	private AllExpensesService ExpenseService;
	@Autowired 
	private ExpenseReportService reportservice;
	@Autowired 
	private ImagePathService imagepathservice;
	@Autowired
	private MainsidebarauthorityService mainsidebarauthorityService;
	@Autowired
	private LoginUserAuthorityService loginuserauthorityService;
	@Autowired
	private LoginUserService loginuserService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private EmailService emailService;
	@Autowired
	private CurrencyService currencyService;
	@Autowired
	private CompanyDetailsService companyDetailsService;

	@Value("${spring.mail.username}") 
	private String mail_username;
	
	
	@GetMapping("/Expense")
	public String Expense(Model model,@Param("startDate") String startDate, @Param("endDate") String endDate,HttpServletRequest request) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
    	AllExpenses expense=new AllExpenses();
		model.addAttribute("expense",expense);
		List<Currency> c = currencyService.listAll();
		model.addAttribute("currencyList", c);
		LocalDateTime mydate = LocalDateTime.now();
		DateTimeFormatter myformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    	int year= mydate.getYear();
		int month= mydate.getMonthValue();
		Calendar calendar = Calendar.getInstance();
	    calendar.set(1, month - 1, year);
//    	System.out.println("fromdate : " + "01-0" + month + "-" + year);
//    	System.out.println("todate" + mydate.format(myformatter));
    	if(month <=9) {
    		if(startDate == null && endDate == null) {
        		List<AllExpenses> ExpenseList1 = ExpenseService.listAll();
    			model.addAttribute("ExpenseList", ExpenseList1);
    			model.addAttribute("startDate", "01-0" + month + "-" + year);
    			model.addAttribute("endDate",mydate.format(myformatter));	
    		}
    		else {
    			DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    			LocalDate date1 = LocalDate.parse(startDate, df);
    			LocalDate date2 = LocalDate.parse(endDate, df);
    			List<AllExpenses> ExpenseList = new LinkedList<>();
    			List<AllExpenses> ExpenseList1 = ExpenseService.listAll();
    			for(AllExpenses a1:ExpenseList1) {
    				LocalDate  d1 = LocalDate.parse(a1.getFromDate(), df);
    				LocalDate  d2 = LocalDate.parse(a1.getToDate(), df);
    				
    				if(d1.compareTo(date1)>=0 && d2.compareTo(date2)<=0) {
    					ExpenseList.add(a1);
    				}
    			}
    			model.addAttribute("ExpenseList",ExpenseList);
    			model.addAttribute("startDate",startDate);
    			model.addAttribute("endDate",endDate);
    		}
    	}
    	else {
    		if(startDate == null && endDate == null) {
        		List<AllExpenses> ExpenseList1 = ExpenseService.listAll();
    			model.addAttribute("ExpenseList", ExpenseList1);
    			model.addAttribute("startDate", "01-" + month + "-" + year);
    			model.addAttribute("endDate",mydate.format(myformatter));	
    		}
    		else {
    			DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    			LocalDate date1 = LocalDate.parse(startDate, df);
    			LocalDate date2 = LocalDate.parse(endDate, df);
    			List<AllExpenses> ExpenseList = new LinkedList<>();
    			List<AllExpenses> ExpenseList1 = ExpenseService.listAll();
    			for(AllExpenses a1:ExpenseList1) {
    				LocalDate  d1 = LocalDate.parse(a1.getFromDate(), df);
    				LocalDate  d2 = LocalDate.parse(a1.getToDate(), df);
    				if(d1.compareTo(date1)>=0 && d2.compareTo(date2)<=0) {
    					ExpenseList.add(a1);
    				}
    			}
    			model.addAttribute("ExpenseList",ExpenseList);
    			model.addAttribute("startDate",startDate);
    			model.addAttribute("endDate",endDate);
    		}
    	}
    	
		model.addAttribute("UserName", employeeService.Update(request.getUserPrincipal().getName()));
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "Expense";
	}	
	
	@PostMapping("/SaveExpense")
	public String SaveExpense(@ModelAttribute("expense") AllExpenses expense,@RequestParam("ReceiptImg") MultipartFile ReceiptImg, HttpServletRequest request) throws IOException {
		int employee_id = employeeService.Update(request.getUserPrincipal().getName());
		LocalDateTime mydate = LocalDateTime.now();
		DateTimeFormatter myformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy-hh-mm-ss");
		Path path=Paths.get(imagepathservice.getFilePath("Image Folder"));
		InputStream inputstreamlogo=ReceiptImg.getInputStream();
		String fileName=ReceiptImg.getOriginalFilename();
		Files.copy(inputstreamlogo,path.resolve(Paths.get(FilenameUtils.getBaseName(fileName) + "_" + mydate.format(myformatter) + "." + FilenameUtils.getExtension(fileName))));
		String modifiedlogo = FilenameUtils.getBaseName(fileName) + "_" + mydate.format(myformatter) + "." + FilenameUtils.getExtension(fileName);
		expense.setReceipt(modifiedlogo);
		expense.setEmployee_id(employeeService.Update(request.getUserPrincipal().getName()));
		Employee c = employeeService.getEmployeeDataByEmailID(employee_id);
		expense.setCompany_id(c.getCompany_id());
		ExpenseService.save(expense);
		return "redirect:/Expense";
		
	}
	
	@RequestMapping(value="/SaveEdit1Expense", method=RequestMethod.POST)
	public String SaveEdit1Expense(@ModelAttribute("expense") AllExpenses expense,@RequestParam("ReceiptImg") MultipartFile ReceiptImg,@RequestParam("oldimage") String oldimage,HttpServletRequest request) throws IOException {
		int employee_id = employeeService.Update(request.getUserPrincipal().getName());
		if(ReceiptImg.getOriginalFilename().equals("")) {
			expense.setReceipt(oldimage);
			expense.setEmployee_id(employeeService.Update(request.getUserPrincipal().getName()));
			Employee c = employeeService.getEmployeeDataByEmailID(employee_id);
			expense.setCompany_id(c.getCompany_id());
			ExpenseService.save(expense);
		}
		else {
			LocalDateTime mydate = LocalDateTime.now();
			DateTimeFormatter myformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy-hh-mm-ss");
			Path path=Paths.get(imagepathservice.getFilePath("Image Folder"));
			InputStream inputstreamlogo=ReceiptImg.getInputStream();
			String fileName=ReceiptImg.getOriginalFilename();
			Files.copy(inputstreamlogo,path.resolve(Paths.get(FilenameUtils.getBaseName(fileName) + "_" + mydate.format(myformatter) + "." + FilenameUtils.getExtension(fileName))));
			String modifiedlogo = FilenameUtils.getBaseName(fileName) + "_" + mydate.format(myformatter) + "." + FilenameUtils.getExtension(fileName);
			File file = new File(imagepathservice.getFilePath("Image Folder") + oldimage);
			file.delete();
			expense.setReceipt(modifiedlogo);
			expense.setEmployee_id(employeeService.Update(request.getUserPrincipal().getName()));
			Employee c = employeeService.getEmployeeDataByEmailID(employee_id);
			expense.setCompany_id(c.getCompany_id());
			ExpenseService.save(expense);
		}
		return "redirect:/Expense";
	}
	
	@GetMapping("/EditExpense/{id}")
	public String EditExpense(@PathVariable(value="id") Integer id,Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		AllExpenses expense=ExpenseService.get(id);
		model.addAttribute("expense", expense);
		List<Currency> c = currencyService.listAll();
		model.addAttribute("currencyList", c);
		return "EditExpense";
	}
	
	@GetMapping("/deleteAllExpense/{id}")
	public String deleteExpense(@PathVariable(value="id")Integer id) {
		//call delete method
		this.ExpenseService.deleteAllExpenses(id);
		return "redirect:/Expense";
		      
	} 
	
	
	@GetMapping("/ExpenseReportList/{id}")
	public String ExpenseReportList(@PathVariable(value="id") Integer id,Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		//ExpenseReport expense=reportservice.get(id);
		model.addAttribute("ExpenseReportList", reportservice.getAllExpense(id));
		model.addAttribute("reportid",id);
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "ExpenseReport";
		
	}
	
	@RequestMapping(value="/ExpenseReportPage",method= RequestMethod.GET)
    public String AvailableExpenses(Model model, @PathVariable("id") int id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
		model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		model.addAttribute("ExpenseReportList",reportservice.listAll());
	//	mav.setViewName("ExpenseReport");
	//	List<ExpenseReport> l = reportservice.getAllExpense(id);
	//	for(ExpenseReport l1:l) {
	//		System.out.println("active : " + l1.isApprovedRejectFlag() + ":" + l1.isActive() );
	//	}
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "ExpenseReport";
    }

	@RequestMapping(value="/ApprovedMultiExpenseReport",method= RequestMethod.GET)
	public String ApprovedMultiExpensesReport(Model model ,@RequestParam(value="expense_id",required=false) String[] id, HttpServletRequest request) throws MessagingException, IOException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
		model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		int eid = 0;
		if(id == null){
			return "redirect:/AllExpenseReportList";
		}
		else {
			for(String i : id) {
				ExpenseReport expense = reportservice.get(Integer.parseInt(i));
					expense.setApprovedRejectFlag(true);
					expense.setActive(true);
					reportservice.save(expense);
					eid= expense.getReportid();
			}
			return "redirect:/ExpenseReportList/" + eid;
		}
	} 

	
	@RequestMapping(value = "/ExpenseReport/{action}/{id}", method = RequestMethod.GET)
	public String approvedOrRejectExpense(Model model, @PathVariable("action") String action,@PathVariable("id") int id, HttpServletRequest request) throws MessagingException, IOException {
		ExpenseReport expense = reportservice.get(id);
		if (action.equals("approved")) {
			expense.setApprovedRejectFlag(true);
			expense.setActive(true);
		} else if (action.equals("pending")) {
			expense.setApprovedRejectFlag(false);
			expense.setActive(false);
		}else if (action.equals("reject")) {
			expense.setApprovedRejectFlag(true);
			expense.setActive(false);
		}
		reportservice.save(expense);
		model.addAttribute("successMessage", "Updated Successfully!");
		return  "redirect:/ExpenseReportList/" + expense.getReportid();
	}
	
	@GetMapping("/EditBackToEmployeeExpense/{id}")
	public String EditBackToEmplyeeExpense(Model model,@PathVariable("id") int id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		ExpenseReport report=reportservice.get(id);
		model.addAttribute("report", report);
//		model.addAttribute("UserName",request.getUserPrincipal().getName());
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "EditBackToEmployeeExpense";
	}
	
	@RequestMapping(value="/SaveEditBackToEmployeeExpense", method=RequestMethod.POST)
	public String SaveEditBackToEmployeeExpense(@ModelAttribute("report") ExpenseReport report,@RequestParam("ReceiptImg") MultipartFile ReceiptImg,@RequestParam("oldimage") String oldimage,
			HttpServletRequest request) throws IOException {
		int employee_id = employeeService.Update(request.getUserPrincipal().getName());
		if(ReceiptImg.getOriginalFilename().equals("")) {
			report.setReceipt(oldimage);
			Employee c = employeeService.getEmployeeDataByEmailID(employee_id);
			report.setCompany_id(c.getCompany_id());
			reportservice.save(report);
			
		}
		else {
			LocalDateTime mydate = LocalDateTime.now();
			DateTimeFormatter myformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy-hh-mm-ss");
			Path path=Paths.get(imagepathservice.getFilePath("Image Folder"));
			InputStream inputstreamlogo=ReceiptImg.getInputStream();
			String fileName=ReceiptImg.getOriginalFilename();
			Files.copy(inputstreamlogo,path.resolve(Paths.get(FilenameUtils.getBaseName(fileName) + "_" + mydate.format(myformatter) + "." + FilenameUtils.getExtension(fileName))));
			String modifiedlogo = FilenameUtils.getBaseName(fileName) + "_" + mydate.format(myformatter) + "." + FilenameUtils.getExtension(fileName);
			File file = new File(imagepathservice.getFilePath("Image Folder") + oldimage);
			file.delete();
			report.setReceipt(modifiedlogo);
			Employee c = employeeService.getEmployeeDataByEmailID(employee_id);
			report.setCompany_id(c.getCompany_id());
			reportservice.save(report);
		}
		CreateExpenseReport e=ExReportService.get(report.getReportid());
		e.setActive(false);
		e.setApprovedRejectFlag(false);
		Employee c = employeeService.getEmployeeDataByEmailID(employee_id);
		e.setCompany_id(c.getCompany_id());
		ExReportService.save(e);
		return "redirect:/EditExpenseReportListPage/" + e.getId();
	}
	
	
	
	public List<ExpenseReport> report = new LinkedList<>();
	
	@GetMapping("/CreateExpenseReport")
	public String Expense(Model model,HttpServletRequest request) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		model.addAttribute("CreateExpenseReportList", ExReportService.listAll());
		CreateExpenseReport expense = new CreateExpenseReport();
		model.addAttribute("expense", expense);
		model.addAttribute("UserName", employeeService.Update(request.getUserPrincipal().getName()));
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "CreateExpenseReport";
	}
	
	@GetMapping("/AllExpenseReportList")
	public String UserExpenseReport(Model model, HttpServletRequest request) throws MessagingException, IOException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		model.addAttribute("CreateExpenseReportList", ExReportService.listAll());
		CreateExpenseReport expense = new CreateExpenseReport();
		model.addAttribute("expense", expense);
    	model.addAttribute("EmployeeList", employeeService.getAllEmployees());
    	model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "AllExpenseReportList";
	}
	
	@PostMapping("/SaveExpenseReport")
	public String SaveExpense(@ModelAttribute("expense") CreateExpenseReport expense, HttpServletRequest request,Model model) throws MessagingException, IOException{
		//save to database
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		System.out.println("ACtive1 : " );
		//System.out.println("ACtive : " + isActive());
		LocalDateTime mydate = LocalDateTime.now();
		DateTimeFormatter myformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		expense.setCurrentdate(mydate.format(myformatter));
		expense.setEmployee_id(employeeService.Update(request.getUserPrincipal().getName()));
		int employee_id = employeeService.Update(request.getUserPrincipal().getName());
		Employee c = employeeService.getEmployeeDataByEmailID(employee_id);
//		Employee c = employeeService.getEmployeeDataByEmailID(request.getUserPrincipal().getName());
		expense.setCompany_id(c.getCompany_id());
		ExReportService.save(expense);
		for(ExpenseReport e: report) {
//			System.out.println("ACtive2 : " );
			e.setReportid(ExReportService.getExpense(expense.getExpenseName(), expense.getEdate(), expense.getCurrentdate()));
			e.setApprovedamount(e.getAmount());
			Employee c1 = employeeService.getEmployeeDataByEmailID(employee_id);
			e.setCompany_id(c1.getCompany_id());
			reportservice.save(e);
		}
		Employee emp = employeeService.getUsername(request.getUserPrincipal().getName());
			Mail mail = new Mail();
			mail.setFrom(emp.getEmail());
			mail.setMailTo(mail_username);
			mail.setSubject("Expense Report");
			Map<String, Object> model1 = new HashMap<String, Object>();
			model1.put("companyName", companyDetailsService.getCompanyNameByID(emp.getCompany_id()));
			model1.put("ename", emp.getFirstname() + " " + emp.getLastname());
			model1.put("ExpenseMailReportList", ExReportService.getListByUserNameANDCurrentdate(employeeService.Update(request.getUserPrincipal().getName()), mydate.format(myformatter)));
			mail.setProps(model1);
			emailService.sendEmail2(mail);
			
		model.addAttribute("ExpenseMailReportList",ExReportService.getListByUserNameANDCurrentdate(employeeService.Update(request.getUserPrincipal().getName()), mydate.format(myformatter)));
		model.addAttribute("EmpList",employeeService.getAllEmployees());
    	int n = loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName()));
    	String status = "false";
    	if(n==1 || n==2 || n==3) {
    		status = "true";
    	}
    	model.addAttribute("status", status);
    	model.addAttribute("UserName", employeeService.Update(request.getUserPrincipal().getName()));
		return "redirect:/SavePage";
		
	}
	
	@GetMapping("/EditCreateExpenseReport/{id}")
	public String EditCreateExpenseReport(@PathVariable(value="id")Integer id,Model model,HttpServletRequest request) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		CreateExpenseReport expense = ExReportService.get(id);
		model.addAttribute("expense", expense);
		LocalDateTime mydate = LocalDateTime.now();
		DateTimeFormatter myformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy-hh-mm-ss");
		expense.setCurrentdate(mydate.format(myformatter));
		model.addAttribute("UserName",employeeService.Update(request.getUserPrincipal().getName()));
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "EditCreateExpenseReport";

		
		
	}
//	/*@GetMapping("/deleteCreateExpenseReport/{id}")
//	public String deleteExpense(@PathVariable(value="id")Integer id) {
//		//call delete method
//		this.ExReportService.deleteCreateExpenseReport(id);
//		return "redirect:/CreateExpenseReport";
//		      
//	} */
//	
	@RequestMapping(value="/ExpenseReport",method= RequestMethod.GET)
	public String ExpenseReport(Model model ,@RequestParam(value="travel_id",required=false) String[] id,HttpServletRequest request) {
		List<AllExpenses> trav = ExpenseService.listAll();
		report.clear();
		//List<TravelExpenses> trav2 = new LinkedList<>();
		for(AllExpenses t : trav) {
			for(int i=0 ; i < id.length ; i++) {
				if(Integer.parseInt(id[i]) == t.getId()) {
					ExpenseReport ex=new ExpenseReport();
					ex.setCategory(t.getCategory());
					ex.setFromLocation(t.getFromLocation());
					ex.setToLocation(t.getToLocation());
					ex.setLocation(t.getLocation());
					ex.setFromDate(t.getFromDate());
					ex.setToDate(t.getToDate());
					ex.setModeOfTravel(t.getModeOfTravel());
					ex.setVendor(t.getVendor());
					ex.setReceipt(t.getReceipt());
					ex.setAmount(t.getAmount());
					ex.setCurrency(t.getCurrency());
					//reportservice.save(ex);
					ex.setEmployee_id(employeeService.Update(request.getUserPrincipal().getName()));
					report.add(ex);
					ExpenseService.deleteAllExpenses(t.getId());
					
					}
//				System.out.println("id" + id[i]);
//				System.out.println("t" + t.getId());
			}	
		}
		return "redirect:/CreateExpenseReport";
	} 
	
	@GetMapping("/EditExpenseReportPage")
	public String EditExpenseReportPage(Model model,HttpServletRequest request) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		model.addAttribute("CreateExpenseReportList", ExReportService.listAll());
		model.addAttribute("UserName", employeeService.Update(request.getUserPrincipal().getName()));
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "EditExpenseReport";
	}
	

	@RequestMapping(value = "/CreateExpenseReport/{action}/{id}", method = RequestMethod.GET)
	public String approvedOrRejectCreateExpense(Model model, @PathVariable("action") String action,@PathVariable("id") int id,HttpServletRequest request) throws MessagingException, IOException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		CreateExpenseReport expense = ExReportService.get(id);
		if (action.equals("approved")) {
			expense.setApprovedRejectFlag(true);
			expense.setActive(true);
		} else if (action.equals("pending")) {
			expense.setApprovedRejectFlag(false);
			expense.setActive(false);
		}else if (action.equals("reject")) {
			expense.setApprovedRejectFlag(true);
			expense.setActive(false);
		}else if (action.equals("amend")) {
			expense.setApprovedRejectFlag(false);
			expense.setActive(true);
		}
		ExReportService.save(expense);
		
		Employee emp = employeeService.getUsername(request.getUserPrincipal().getName());
//		Mail mail = new Mail();
//		mail.setFrom(mail_username);
//		mail.setMailTo(emp.getEmail());
//		mail.setSubject("Expense Report");
//		Map<String, Object> model1 = new HashMap<String, Object>();
//		model1.put("ExpenseMailReportList", ExReportService.getListByUserName(expense.getEmployee_id()));
//		mail.setProps(model1);
//		emailService.sendEmail2(mail);
		model.addAttribute("ExpenseMailReportList",ExReportService.getListByUserName(expense.getEmployee_id()));

		model.addAttribute("successMessage", "Updated Successfully!");
//		mav.setView(new RedirectView("/AllExpenseReportList"));
		return "redirect:/AllExpenseReportList";
	}
	
	@GetMapping("/EditExpenseReportListPage/{id}")
	  public String EditBackToEmployeeExpenseReportListPage(Model model,@PathVariable("id") int id,HttpServletRequest request) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
    	model.addAttribute("ExpenseReportList",reportservice.getAllExpense(id));
    	model.addAttribute("UserName", employeeService.Update(request.getUserPrincipal().getName()));
    	model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
    	return "EditExpenseReportList"; 
	  }
	 
	@GetMapping("/Reason")
	public String SaveExpense(Model model, @RequestParam("id") String[] id,@RequestParam("reason") String[] reason){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		int reportid=0;
		for(int i=0; i < id.length; i++){
			ExpenseReport e=reportservice.get(Integer.parseInt(id[i]));
			e.setReason(reason[i]);
			reportservice.save(e);
			reportid=e.getReportid();
		}
		return "redirect:/ExpenseReportList/" + reportid;
		
	}
	
	@RequestMapping("/getChartData1")
	@ResponseBody
	public List<Employee> getData(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    List<Employee> employee =new LinkedList<Employee>();
	    Employee e =new Employee();
	    e.setFirstname(String.valueOf(ExReportService.getApprovedCount(employeeService.getCompanyID(auth.getName()))));
	    e.setAge(ExReportService.getUnapprovedCount(employeeService.getCompanyID(auth.getName())));
	    e.setMiddlename(String.valueOf(ExReportService.getPendingCount(employeeService.getCompanyID(auth.getName()))));
	    employee.add(e);
	    return employee;
	}
	
	@GetMapping("/addCurrency")
	public String showNewCurrency(Model model) {
		//create model attribute to bind from data
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		model.addAttribute("CurrencyList",currencyService.listAll());
		Currency currency =new Currency();
		model.addAttribute("currency", currency);
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "addCurrency";
	}
	
	@PostMapping("/saveCurrency")
	public String saveCurrency(@ModelAttribute("currency") Currency  currency, HttpServletRequest request) {
		//save project name to database
		int employee_id = employeeService.Update(request.getUserPrincipal().getName());
		Employee c = employeeService.getEmployeeDataByEmailID(employee_id);
		currency.setCompany_id(c.getCompany_id());
		currencyService.save(currency);
		return "redirect:/addCurrency";
		
	}   
	
	@GetMapping("/EditCurrency/{id}")
	public String EditCurrency(@PathVariable(value="id")int id,Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		Currency currency = currencyService.get(id);
		model.addAttribute("currency", currency);
		return "EditCurrency";

		
	}
	@GetMapping("/deleteCurrency/{id}")
	public String deleteCurrency(@PathVariable(value="id")int id) {
		//call delete project name method
		this.currencyService.deleteCurrency(id);
		return "redirect:/addCurrency";
		
	}

	
	@GetMapping("/EmployeeExpenseReport")
	public String EmployeeExpenseReport(Model model,@Param("fromdate") String fromdate, @Param("todate") String todate,HttpServletRequest request) {
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
    			List<CreateExpenseReport> ExpenseList1 = ExReportService.listAll();
    			model.addAttribute("CreateExpenseReportList", ExpenseList1);
    			model.addAttribute("fromdate","01-0" + month + "-" + year);
    			model.addAttribute("todate",mydate.format(myformatter));
    		}
    		else {
    			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    			LocalDate FromDate = LocalDate.parse(fromdate, formatter);
    			LocalDate ToDate = LocalDate.parse(todate, formatter);
    			List<CreateExpenseReport> ExpenseList=new LinkedList<>();
    			List<CreateExpenseReport> ExpenseList1 = ExReportService.listAll();
    			model.addAttribute("CreateExpenseReportList", ExpenseList1);
    			for(CreateExpenseReport l:ExpenseList1) {
    				LocalDate date1 = LocalDate.parse(l.getEdate(), formatter);
    				LocalDate date2 = LocalDate.parse(l.getEdate(), formatter);
    				if(date1.compareTo(FromDate)>=0 && date2.compareTo(ToDate)<=0) {
    					ExpenseList.add(l);
    				}
    			}
    			model.addAttribute("CreateExpenseReportList",ExpenseList);
    			model.addAttribute("fromdate",fromdate);
    			model.addAttribute("todate",todate);
    		}
    	}
    	else {
    		if(fromdate == null || todate == null) {
    			List<CreateExpenseReport> ExpenseList1 = ExReportService.listAll();
    			model.addAttribute("CreateExpenseReportList", ExpenseList1);
    			model.addAttribute("fromdate","01-" + month + "-" + year);
    			model.addAttribute("todate",mydate.format(myformatter));
    		}
    		else {
    			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    			LocalDate FromDate = LocalDate.parse(fromdate, formatter);
    			LocalDate ToDate = LocalDate.parse(todate, formatter);
    			List<CreateExpenseReport> ExpenseList=new LinkedList<>();
    			List<CreateExpenseReport> ExpenseList1 = ExReportService.listAll();
    			model.addAttribute("CreateExpenseReportList", ExpenseList1);
    			for(CreateExpenseReport l:ExpenseList1) {
    				LocalDate date1 = LocalDate.parse(l.getEdate(), formatter);
    				LocalDate date2 = LocalDate.parse(l.getEdate(), formatter);
    				if(date1.compareTo(FromDate)>=0 && date2.compareTo(ToDate)<=0) {
    					ExpenseList.add(l);
    				}
    			}
    			model.addAttribute("CreateExpenseReportList",ExpenseList);
    			model.addAttribute("fromdate",fromdate);
    			model.addAttribute("todate",todate);
    		}
    	}
		
    	int n = loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName()));
    	String status = "false";
    	if(n==5) {
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
		model.addAttribute("UserName", employeeService.Update(request.getUserPrincipal().getName()));
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "EmployeeExpenseReport";
	}	
	
	
////	@GetMapping("/EmployeeManagerExpenseReport")
////	public String ViewEmployeeManagerExpenseReport(Model model, @Param("fromdate") String fromdate, @Param("todate") String todate, HttpServletRequest request){
////		//String fname = "";
////		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
////		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
////    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
////    	LocalDateTime mydate = LocalDateTime.now();
////		DateTimeFormatter myformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
////    	int year = mydate.getYear();
////		int month = mydate.getMonthValue();
////		Calendar calendar = Calendar.getInstance();
////	    calendar.set(1, month - 1, year);
////    	System.out.println("fromdate : " + "01-0" + month + "-" + year);
////    	System.out.println("todate" + mydate.format(myformatter));
////    	if(month <=9) {
////    		if(fromdate == null || todate == null) {
////    			List<CreateExpenseReport> ExpenseList1 = ExReportService.listAll();
////    			model.addAttribute("ExpenseReportList", ExpenseList1);
//////    			model.addAttribute("username",username);
////    			model.addAttribute("fromdate","01-0" + month + "-" + year);
////    			model.addAttribute("todate",mydate.format(myformatter));
////    		}
////    		else {
////    			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
////    			LocalDate FromDate = LocalDate.parse(fromdate, formatter);
////    			LocalDate ToDate = LocalDate.parse(todate, formatter);
////    			List<CreateExpenseReport> ExpenseList=new LinkedList<>();
////    			List<CreateExpenseReport> ExpenseList1 = ExReportService.listAll();
////    			model.addAttribute("ExpenseReportList", ExpenseList1);
////    			for(CreateExpenseReport l:ExpenseList1) {
////    				LocalDate date1 = LocalDate.parse(l.getEdate(), formatter);
////    				LocalDate date2 = LocalDate.parse(l.getEdate(), formatter);
////    				if(date1.compareTo(FromDate)>=0 && date2.compareTo(ToDate)<=0) {
////    					ExpenseList.add(l);
////    				}
////    			}
////    			model.addAttribute("ExpenseReportList",ExpenseList);
//////    			model.addAttribute("username",username);
////    			model.addAttribute("fromdate",fromdate);
////    			model.addAttribute("todate",todate);
////    		}
////    	}
////    	else {
////    		if(fromdate == null || todate == null) {
////    			List<CreateExpenseReport> ExpenseList1 = ExReportService.listAll();
////    			model.addAttribute("ExpenseReportList", ExpenseList1);
//////    			model.addAttribute("username",username);
////    			model.addAttribute("fromdate","01-" + month + "-" + year);
////    			model.addAttribute("todate",mydate.format(myformatter));
////    		}
////    		else {
////    			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
////    			LocalDate FromDate = LocalDate.parse(fromdate, formatter);
////    			LocalDate ToDate = LocalDate.parse(todate, formatter);
////    			List<CreateExpenseReport> ExpenseList=new LinkedList<>();
////    			List<CreateExpenseReport> ExpenseList1 = ExReportService.listAll();
////    			model.addAttribute("ExpenseReportList", ExpenseList1);
////    			for(CreateExpenseReport l:ExpenseList1) {
////    				LocalDate date1 = LocalDate.parse(l.getEdate(), formatter);
////    				LocalDate date2 = LocalDate.parse(l.getEdate(), formatter);
////    				if(date1.compareTo(FromDate)>=0 && date2.compareTo(ToDate)<=0) {
////    					ExpenseList.add(l);
////    				}
////    			}
////    			model.addAttribute("ExpenseReportList",ExpenseList);
//////    			model.addAttribute("username",username);
////    			model.addAttribute("fromdate",fromdate);
////    			model.addAttribute("todate",todate);
////    		}
////    	}
////		
////    	List<Employee> employees = employeeService.FilterByReportingManager(request.getUserPrincipal().getName());
////    	model.addAttribute("ListUserName", employees);
////    	model.addAttribute("EmployeeList", employeeService.getAllEmployees());
////		return "EmployeeManagerExpenseReport";
////	}
//

}