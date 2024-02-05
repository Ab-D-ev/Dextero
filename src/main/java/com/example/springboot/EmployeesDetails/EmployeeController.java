package com.example.springboot.EmployeesDetails;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.springboot.AddCompany.AddCustomer;
import com.example.springboot.CompanyProfile.CompanyDetails;
import com.example.springboot.CompanyProfile.CompanyDetailsService;
import com.example.springboot.ExcelDataUpload.ExcelHelper;
import com.example.springboot.ExcelDataUpload.LoginExcelHelper;
import com.example.springboot.ExcelDataUpload.UploadForm;
import com.example.springboot.Leave.LeaveBalance;
import com.example.springboot.Leave.LeaveBalanceService;
import com.example.springboot.Login.EmailSendLoginPasswordUserService;
import com.example.springboot.Login.LoginUser;
import com.example.springboot.Login.LoginUserAuthority;
import com.example.springboot.Login.LoginUserAuthorityRepository;
import com.example.springboot.Login.LoginUserAuthorityService;
import com.example.springboot.Login.LoginUserRepository;
import com.example.springboot.Login.LoginUserService;
import com.example.springboot.Login.Mail;
import com.example.springboot.Login.Mainsidebarauthority;
import com.example.springboot.Login.MainsidebarauthorityService;
import com.example.springboot.MainMaster.Department;
import com.example.springboot.MainMaster.DepartmentService;
import com.example.springboot.MainMaster.EducationalQualification;
import com.example.springboot.MainMaster.EducationalQualificationService;
import com.example.springboot.MainMaster.EmployeeDesignationDetails;
import com.example.springboot.MainMaster.EmployeeDesignationDetailsService;
import com.example.springboot.SalaryPayRoll.CompanyGrossPay;
import com.example.springboot.SalaryPayRoll.CompanyGrossPayService;
import com.example.springboot.SalaryPayRoll.Deductions;
import com.example.springboot.SalaryPayRoll.DeductionsService;
import com.example.springboot.SalaryPayRoll.EmployeeGrossPay;
import com.example.springboot.SalaryPayRoll.EmployeeGrossPayService;
import com.example.springboot.SalaryPayRoll.EmployeeSalaryService;
import com.example.springboot.SalaryPayRoll.MasterPercentage;
import com.example.springboot.SalaryPayRoll.MasterPercentageService;


@Controller
public class EmployeeController{
	
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private MainsidebarauthorityService mainsidebarauthorityService;
	@Autowired
	private LoginUserAuthorityService loginuserauthorityService;
	@Autowired
	private LoginUserService loginuserService;
	@Autowired
	private EducationalQualificationService educationalQualificationService;
	@Autowired
	private CompanyDetailsService companyDetailsService;
	@Autowired
	private EmailSendLoginPasswordUserService emailSendLoginPasswordUserService;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private EmployeeGrossPayService employeeGrossPayService;
	@Autowired
	private DeductionsService deductionsService;
	@Autowired
	private CompanyGrossPayService companyGrossPayService;
	@Autowired
	public ExcelHelper excelHelper;
	@Autowired
	private EmployeeAddressDetailsService employeeAddressDetailsService;
	@Autowired
	private EmployeeEducationDetailsService employeeEducationDetailsService;
	@Autowired
	private EmployeeCertificationDetailsService employeeCertificationDetailsService;
	@Autowired
	private EmployeeBankDetailsService employeeBankDetailsService;
	@Autowired
	private EmployeeWorkExperienceService employeeWorkExperienceService;
	@Autowired
	private EmployeeCareerBreakDetailsService employeeCareerBreakDetailsService;
	@Autowired
	private EmployeeDesignationDetailsService employeeDesignationDetailsService;
	@Autowired
	private LeaveBalanceService leaveBalanceService;
	
	@Value("${spring.mail.username}") 
	private String mail_username;
	

	@GetMapping("/hr-portal/downloadexcel")
	public void DownloadExcel(HttpServletResponse response,Model model) throws IOException{
		response.setContentType("application/octet-stream");
	    response.setHeader("Content-Disposition", "attachment; filename= Employee-Details.xlsx");
	    InputStream inputStream = new FileInputStream(new File("ExcelFile/Employee-Details.xlsx"));
	    int nRead;
	    while ((nRead = inputStream.read()) != -1) {
	        response.getWriter().write(nRead);
	    } 
	}

	@GetMapping("/bulkemployeedetailsexcel")
	public String showNewEmployeeForm1(Model model,HttpServletRequest request) {
		//create model attribute to bind from data
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		Employee employee=new Employee();
		model.addAttribute("employee",employee);
//		Employee c = employeeService.getEmployeeDataByEmailID(request.getUserPrincipal().getName());
//		if(c.getNemployee() >= employeeService.CountByCompanyId(c.getCompany_id())) {
//			model.addAttribute("countstatus","true");
//		}
//		else {
//			model.addAttribute("countstatus","false");
//		}
		return "addNewEmployee";
	}

//	@PostMapping("/uploadexcel")
//	public String uploadFile(@ModelAttribute("uploadForm") UploadForm uploadForm, Model model, HttpServletRequest request) throws IOException {
//        MultipartFile file = uploadForm.getFile();
//        List<String> uploadResult = new ArrayList<>();
//        try (InputStream is = file.getInputStream()) {
//            Workbook workbook = WorkbookFactory.create(is);
//            Sheet sheet = workbook.getSheetAt(0);
//            for (Row row : sheet) {
//                if (row.getRowNum() == 0) {
//                    // skip header row
//                    continue;
//                }
//                String firstName = row.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue().trim();
//                String lastName = row.getCell(1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue().trim();
//                String email = row.getCell(2, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue().trim();
//                Double profile = row.getCell(3, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                String profile1 = String.valueOf(profile);
//                String department = row.getCell(4, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue().trim();
//                String reportingManager = row.getCell(5, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue().trim();
//
//                // check if employee already exists in database
//                Optional<Employee> existingEmployee = employeeRepository.findByEmailIgnoreCase(email);
//                
//                if (existingEmployee.isPresent()) {
//                    uploadResult.add("Employee with email " + email + " already exists in database.");
//                } else {
//                	Employee employee = new Employee();
//                    employee.setFirstname(firstName);
//                    employee.setLastname(lastName);
//                    employee.setEmail(email);
//                    employee.setDepartment(department);
//                    employee.setReporting_manager(reportingManager);
//                    employee.setAuthority_id(Integer.parseInt(profile1));
//                    Employee c = employeeService.getEmployeeDataByEmailID(request.getUserPrincipal().getName());
//        	    	employee.setGroup_id(c.getGroup_id());
//        	    	employee.setCompany_id(c.getCompany_id());
//        	    	
//        	    	if (employeeService.countPrefixValue(request.getUserPrincipal().getName()) == null) {
//        	    		employee.setPrefix(employeeService.getCompanyPrefix(request.getUserPrincipal().getName()));
//        	    		employee.setSufix("000001");
//        			} else {
//        				String prefixNumber = employeeService.getCompanyPrefix(request.getUserPrincipal().getName());
//        				employee.setPrefix(prefixNumber);
//        				String sufixno = employeeService.getPreviousSufixValue(request.getUserPrincipal().getName());
//        				int n = (Integer.parseInt(sufixno)) + 1 ;
//        				String s = String.format("%06d",n);
//        				employee.setSufix(s);
//        			}
//                    employeeService.saveEmployee(employee);
//
//                    uploadResult.add("Employee " + firstName + " " + lastName + " saved to database.");
//                }
//                String password = row.getCell(1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue().trim();
//                 // check if employee already exists in database
//                Optional<LoginUser> existingEmployee1 = loginUserRepo.findByUsername(email);
//                BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(4);
//                if (existingEmployee1.isPresent()) {
//                	uploadResult.add("Employee with email " + email + " already exists in database.");
//                } 
//                else {
//                	LoginUser user = new LoginUser();
//                	user.setUsername(email);
//                   	user.setPassword(bCryptPasswordEncoder.encode("123456"));
//                 	loginuserService.save(user);
//                    List<LoginUserAuthority> loginUserAuthorities = new ArrayList<>();
//        			LoginUserAuthority e = new LoginUserAuthority();
//        			e.setAuthority_id(Integer.parseInt(profile1));
//        			e.setUser_id(loginuserService.GetAuthorityID(email));
//        			loginUserAuthorities.add(e);
//        			loginuserauthorityService.saveAll(loginUserAuthorities);
//        		}
//                uploadResult.add("Employee " + email + " " + password + " saved to database.");
//             }
//         }
//        model.addAttribute("uploadResult", uploadResult);
//        
//		return "redirect:/employeeList"; 
//	}
	

	//display list of employees
	@GetMapping("/employeeList")
	public String viewHomePage(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		model.addAttribute("listEmployees",employeeService.getAllEmployees());
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "employeeList";
	}  
	
	@GetMapping("/showNewEmployeeForm")
	public String showNewEmployeeForm(Model model,HttpServletRequest request) {
		//create model attribute to bind from data
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		Employee employee = new Employee();
		model.addAttribute("employee",employee);
		List<EducationalQualification> qualifications = educationalQualificationService.listAll();
		model.addAttribute("qualificationList", qualifications);
		List<Department> departments = departmentService.listAll();
		model.addAttribute("departmentslist", departments);
		List<EmployeeDesignationDetails> designation = employeeDesignationDetailsService.listAll();
		model.addAttribute("Designationlist", designation);
		List<EmployeeAddressDetails> a = employeeAddressDetailsService.listAll();
		model.addAttribute("Addresslist", a);
		List<EmployeeEducationDetails> e = employeeEducationDetailsService.listAll();
		model.addAttribute("Educationlist", e);
		List<EmployeeCertificationDetails> c = employeeCertificationDetailsService.listAll();
		model.addAttribute("Certificationlist", c);
		List<EmployeeBankDetails> b = employeeBankDetailsService.listAll();
		model.addAttribute("BankNamelist", b);
		List<EmployeeCareerBreakDetails> c1 = employeeCareerBreakDetailsService.listAll();
		model.addAttribute("CareerBreaklist", c1);
		List<Employee> employeesManager = employeeService.getEmployeeManager(employee.getDepartment());
		model.addAttribute("employeesManagerList", employeesManager);
		List<Employee> employeesManager1 = employeeService.findTeamLeaderName();
		model.addAttribute("employeesManagerNameList", employeesManager1);
		int emp = employeeService.getCompanyID(auth.getName());
		CompanyDetails d = companyDetailsService.getDataByID(emp);
//		System.out.println("Nemp: " + employeeService.CountByCompanyId(d.getCompany_id()));
//		System.out.println("no.od emp :" + d.getNemployee());
		if(d.getNemployee() >= employeeService.CountByCompanyId(d.getCompany_id())) {
			model.addAttribute("countstatus","true");
		}
		else {
			model.addAttribute("countstatus","false");
		}
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "addemployee";
	}
	
	@PostMapping("/saveEmployee")
	public String saveEmployee(@ModelAttribute("employee") Employee employee, @RequestParam(value="mcode",required=false) String mcode, 
			@RequestParam(value="MobileNumber1",required=false) String MobileNumber1,@RequestParam(value="MobileNumber2",required=false) String MobileNumber2,
			@RequestParam(value="tcode",required=false) String tcode,@RequestParam(value="TelephoneNumber1",required=false) String TelephoneNumber1,
			@RequestParam(value="TelephoneNumber2",required=false) String TelephoneNumber2, HttpServletRequest request, Model model) throws ParseException, MessagingException, IOException {
			//save employee to database
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    	Employee c = employeeService.getEmployeeDataByEmailID(employee.getId());
	    	employee.setGroup_id(c.getGroup_id());
	    	employee.setCompany_id(c.getCompany_id());
	    	int emp = employeeService.getCompanyID(auth.getName());
	    	if (employeeService.countPrefixValue(emp) == null) {
	    	    employee.setPrefix(employeeService.getCompanyPrefix(emp));
	    	    employee.setSufix("000001");
	    	} else {
	    	    String prefixNumber = employeeService.getCompanyPrefix(emp);
	    	    employee.setPrefix(prefixNumber);
	    	    String sufixno = employeeService.getPreviousSufixValue(emp);

	    	    // Add a check for empty or non-numeric sufixno
	    	    int n = 1;
	    	    if (!sufixno.isEmpty() && sufixno.matches("\\d+")) {
	    	        n = Integer.parseInt(sufixno) + 1;
	    	    }

	    	    String s = String.format("%06d", n);
	    	    employee.setSufix(s);
	    	}
	    	if(employee.getAge()==null) {
	    		employee.setAge(0);
	    	}
	    	if(employee.getReporting_manager()==null) {
	    		employee.setReporting_manager(0);
	    	}
	    	employee.setStatus(true);
	    	employee.setEmail_status(false);
	    	employeeService.saveEmployee(employee);
	    	
	    	BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(4);	
			LoginUser m = new LoginUser();
			m.setUsername(employee.getEmail());
			m.setPassword(bCryptPasswordEncoder.encode("123456"));
			m.setStatus(true);
			loginuserService.save(m);
			LoginUserAuthority a = new LoginUserAuthority();
			a.setAuthority_id(employee.getAuthority_id());
			a.setUser_id(loginuserService.GetAuthorityID(employee.getEmail()));
			loginuserauthorityService.save(a);
			
	    	
	    	String text="Welcome " +  employee.getFirstname()  +  ","  +  "\n"  +  "This is Your Login Username :  "  +  m.getUsername() + " and Password : "  +  "123456"  + " for Online HR Portal."  +  "\n"  +  " Please do not share password with anyone.";
			Mail mail = new Mail();
		    mail.setFrom(mail_username);
		    mail.setMailTo(m.getUsername());
		    mail.setSubject("Welcome To Online HR Portal");
		    emailSendLoginPasswordUserService.senduserloginpasswordEmail(mail, text);
			if(employee.getDesignation_id() == employeeDesignationDetailsService.getDesignationIDAsIntern(employee.getCompany_id())) {
				
				int e = employeeService.getEmployeeID(employee.getEmail());
				CompanyGrossPay cg = new CompanyGrossPay();
				cg.setEmployee_id(e);
				cg.setBasic("true");
				cg.setDa("true");
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
			}
			else if(employee.getDesignation_id()==employeeDesignationDetailsService.getDesignationIDAsJrDeveloper(employee.getCompany_id()) || 
					employee.getDesignation_id()==employeeDesignationDetailsService.getDesignationIDAsDeveloper(employee.getCompany_id()) ||
					employee.getDesignation_id()==employeeDesignationDetailsService.getDesignationIDAsSrDeveloper(employee.getCompany_id()) ||
					employee.getDesignation_id()==employeeDesignationDetailsService.getDesignationIDAsProjectManager(employee.getCompany_id())) {
				int e = employeeService.getEmployeeID(employee.getEmail());
				CompanyGrossPay cg = new CompanyGrossPay();
				cg.setEmployee_id(e);
				cg.setBasic("true");
				cg.setDa("true");
				cg.setHra("true");
				cg.setConveyance_allowance("false");
				cg.setDearness_allowance("false");
				cg.setMedical_allowance("false");
				cg.setOther_allowance("false");
				cg.setProject_allowance("true");
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
			}
			else if(employee.getDesignation_id()==employeeDesignationDetailsService.getDesignationIDAsConsultant(employee.getCompany_id())) {
				
				int e = employeeService.getEmployeeID(employee.getEmail());
				CompanyGrossPay cg = new CompanyGrossPay();
				cg.setEmployee_id(e);
				cg.setBasic("true");
				cg.setDa("true");
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
				deductions.setEmployee_TDS("true");
				deductions.setEmployer_ESI("false");
				deductions.setEmployer_Gratuity("false");
				deductions.setEmployer_Loan_Recovery("false");
				deductions.setEmployer_PF("false");
				deductions.setEmployer_Professional_Tax("false");
				deductions.setEmployer_TDS("false");
				deductions.setCompany_id(c.getCompany_id());
				deductionsService.Save(deductions);
			}
			else {
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
			}
			int e = employeeService.getEmployeeID(employee.getEmail());
			LeaveBalance leaveBalance = new LeaveBalance();
			leaveBalance.setCarried_forward_leave(0);
			leaveBalance.setCompany_id(employee.getCompany_id());
			LocalDateTime mydate = LocalDateTime.now();
			DateTimeFormatter myformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			leaveBalance.setCurrentdate(mydate.format(myformatter));
			SimpleDateFormat myformatter1 = new SimpleDateFormat("yyyy-MM-dd");
	    	SimpleDateFormat myformatter2 = new SimpleDateFormat("dd-MM-yyyy");
	    	Date d1 = myformatter2.parse(leaveBalance.getCurrentdate());
			leaveBalance.setCurrent_date_year(myformatter1.format(d1));
			leaveBalance.setEmployee_id(e);
			leaveBalanceService.Save(leaveBalance);
			
			EmployeeAddressDetails addressDetails = new EmployeeAddressDetails();
			addressDetails.setCity(" ");
			addressDetails.setCompany_id(employee.getCompany_id());
			addressDetails.setCountry(" ");
			addressDetails.setEmployee_id(e);
			addressDetails.setPinCode(" ");
			addressDetails.setPresentAddress1(" ");
			addressDetails.setPresentAddress2(" ");
			addressDetails.setState(" ");
			employeeAddressDetailsService.Save(addressDetails);
			
			EmployeeBankDetails eBankDetails = new EmployeeBankDetails();
			eBankDetails.setAccountholdername(" ");
			eBankDetails.setAccountnumber(" ");
			eBankDetails.setBankname(" ");
			eBankDetails.setBranch(" ");
			eBankDetails.setCompany_id(employee.getCompany_id());
			eBankDetails.setEmployee_id(e);
			eBankDetails.setESI_Number(" ");
			eBankDetails.setIFSC_Code(" ");
			eBankDetails.setPF_Number(" ");
			eBankDetails.setUAN_Number(" ");
			employeeBankDetailsService.Save(eBankDetails);
			
		return "redirect:/savePage";
	}
	
	@PostMapping("/saveEditEmployee")
	public String saveEditEmployee(@ModelAttribute("employee") Employee employee, @RequestParam(value="mcode",required=false) String mcode, 
			@RequestParam(value="MobileNumber1",required=false) String MobileNumber1,@RequestParam(value="MobileNumber2",required=false) String MobileNumber2,
			@RequestParam(value="tcode",required=false) String tcode,@RequestParam(value="TelephoneNumber1",required=false) String TelephoneNumber1,
			@RequestParam(value="TelephoneNumber2",required=false) String TelephoneNumber2, HttpServletRequest request,RedirectAttributes redirectAttributes) throws ParseException {
		//save employee to database
		//employeeService.saveEmployee(employee);
	    	//Employee e=new Employee();
	    	SimpleDateFormat myformatter = new SimpleDateFormat("yyyy-MM-dd");
	    	SimpleDateFormat myformatter1 = new SimpleDateFormat("dd-MM-yyyy");
	    	Date d1 = myformatter1.parse(employee.getDateofBirth());
	    	Date d2 = myformatter1.parse(employee.getJoiningdate());
			employee.setBirthyear(myformatter.format(d1));
			employee.setJoiningdateYear(myformatter.format(d2));
			if(employee.getMobileNumber1()==null) {
				employee.setMobileNumber1("+91-" + MobileNumber1);
			}
			else {
				employee.setMobileNumber1(MobileNumber1);
			}
			if(employee.getMobileNumber2()==null) {
				employee.setMobileNumber2("+91-" + MobileNumber2);
			}
			else {
				employee.setMobileNumber2(MobileNumber2);
			}
	    	if(employee.getTelephoneNumber1()==null) {
	    		employee.setTelephoneNumber1("022-" +TelephoneNumber1);
	    	}
	    	else {
	    		employee.setTelephoneNumber1(TelephoneNumber1);
			}
	    	if(employee.getTelephoneNumber2()==null) {
	    		employee.setTelephoneNumber2("022-" +TelephoneNumber2);
	    	}
	    	else {
	    		employee.setTelephoneNumber2(TelephoneNumber2);
	    	}
	    	
	    	employee.setGroup_id(employee.getGroup_id());
	    	employee.setCompany_id(employee.getCompany_id());
	    	employee.setPrefix(employee.getPrefix());
			employee.setSufix(employee.getSufix());
			if(employee.getAge()==null) {
	    		employee.setAge(0);
	    	}
			if(employee.getReporting_manager()==null) {
	    		employee.setReporting_manager(0);
	    	}
	    	employeeService.saveEmployee(employee);
	    	redirectAttributes.addFlashAttribute("successMessage", "Data saved successfully!");
	    	return "redirect:/showFormForUpdate";
	   }
	
	@PostMapping("/saveEditEmployeeByAdmin")
	public String saveEditEmployeeByAdmin(@ModelAttribute("employee") Employee employee, @RequestParam(value="mcode",required=false) String mcode, 
			@RequestParam(value="MobileNumber1",required=false) String MobileNumber1,@RequestParam(value="MobileNumber2",required=false) String MobileNumber2,
			@RequestParam(value="tcode",required=false) String tcode,@RequestParam(value="TelephoneNumber1",required=false) String TelephoneNumber1,
			@RequestParam(value="TelephoneNumber2",required=false) String TelephoneNumber2, HttpServletRequest request, 
			@RequestParam("old_email") String old_email) throws ParseException {
		//save employee to database
	    	employee.setGroup_id(employee.getGroup_id());
	    	employee.setCompany_id(employee.getCompany_id());
	    	employee.setPrefix(employee.getPrefix());
			employee.setSufix(employee.getSufix());
			if(employee.getAge()==null) {
	    		employee.setAge(0);
	    	}
			if(employee.getReporting_manager()==null) {
	    		employee.setReporting_manager(0);
	    	}
			SimpleDateFormat myformatter = new SimpleDateFormat("yyyy-MM-dd");
	    	SimpleDateFormat myformatter1 = new SimpleDateFormat("dd-MM-yyyy");
	    	Date d1 = myformatter1.parse(employee.getDateofBirth());
	    	Date d2 = myformatter1.parse(employee.getJoiningdate());
			employee.setBirthyear(myformatter.format(d1));
			employee.setJoiningdateYear(myformatter.format(d2));
		    employeeService.saveEmployee(employee);

	    	 LoginUser user = loginuserService.findByUserName(old_email);
	            if (user != null) {
	                user.setUsername(employee.getEmail());
	                loginuserService.save(user);
	            }
		        if (user != null) {
		            // Update the authority_id in LoginUserAuthority
		            LoginUserAuthority userAuthority = loginuserauthorityService.findByUserID(user.getLogin_user_id());
		            if (userAuthority != null) {
		                userAuthority.setAuthority_id(employee.getAuthority_id());
		                loginuserauthorityService.save(userAuthority);
		            }
		        }
		       
	    	return "redirect:/SavePage";
	   }

	
	
	@GetMapping("/showFormForUpdate")
	public String UpdateEmployeeDetails(Model model,HttpServletRequest request) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		Employee employee=employeeService.get(employeeService.Update(request.getUserPrincipal().getName()));
		model.addAttribute("employee",employee);
		List<EducationalQualification> qualifications = educationalQualificationService.listAll();
		model.addAttribute("qualificationList", qualifications);
		List<Department> departments = departmentService.listAll();
		model.addAttribute("departmentslist", departments);
		List<Employee> employeesManager = employeeService.getEmployeeManager(employee.getDepartment());
		model.addAttribute("employeesManagerList", employeesManager);
		List<EmployeeDesignationDetails> designation = employeeDesignationDetailsService.listAll();
		model.addAttribute("Designationlist", designation);
		model.addAttribute("CompanyID",companyDetailsService.getCompanyID(auth.getName()));
		return "update_employee";
	}
	
	@GetMapping("/UpdatePersonalDetails/{id}")
	public String UpdatePersonalDetails(@PathVariable(value="id")int id,Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
    	Employee employee=employeeService.get(id);
    	model.addAttribute("employee",employee);
    	List<EducationalQualification> qualifications = educationalQualificationService.listAll();
		model.addAttribute("qualificationList", qualifications);
		List<Department> departments = departmentService.listAll();
		model.addAttribute("departmentslist", departments);
		List<Employee> employeesManager = employeeService.getEmployeeManager(employee.getDepartment());
		model.addAttribute("employeesManagerList", employeesManager);
		List<EmployeeDesignationDetails> designation = employeeDesignationDetailsService.listAll();
		model.addAttribute("Designationlist", designation);
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "update_employee"; 
	}
	
	@GetMapping("/UpdatePersonalDetailsByAdmin/{id}")
	public String UpdatePersonalDetailsByAdmin(@PathVariable(value="id")int id,Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
    	Employee employee=employeeService.get(id);
    	model.addAttribute("employee",employee);
    	List<EducationalQualification> qualifications = educationalQualificationService.listAll();
		model.addAttribute("qualificationList", qualifications);
		List<Department> departments = departmentService.listAll();
		model.addAttribute("departmentslist", departments);
		List<Employee> employeesManager = employeeService.getEmployeeManager(employee.getDepartment());
		model.addAttribute("employeesManagerList", employeesManager);
		List<EmployeeDesignationDetails> designation = employeeDesignationDetailsService.listAll();
		model.addAttribute("Designationlist", designation);
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "UpdateEmployeeDetails"; 
	}
	
	@GetMapping("/deleteEmployee/{id}")
	public String deleteEmployee(@PathVariable(value="id")Integer id) {
		//call delete employee method
		this.employeeService.deleteEmployeeById(id);
		return "redirect:/employeeList";
		
	}
	
//	@GetMapping("/ViewEmployeeDetails")
//	public String ViewEmployeeDetails(Model model,HttpServletRequest request) {
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
//    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
//		Employee employee=employeeService.get(employeeService.Update(request.getUserPrincipal().getName()));
//		model.addAttribute("employee",employee);
//		model.addAttribute("CompanyID",companyDetailsService.getCompanyID(auth.getName()));
//		return "ViewEmployee";
//	}
	
	@RequestMapping("/getChartData")
	@ResponseBody
	public List<Employee> getData(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    List<Employee> employee =new LinkedList<Employee>();
	    Employee e =new Employee();
	    e.setFirstname(String.valueOf(employeeService.getMaleCount(employeeService.getCompanyID(auth.getName()))));
	    e.setAge(employeeService.getFemaleCount(employeeService.getCompanyID(auth.getName())));
	    employee.add(e);
	    return employee;
	}
	
	
	@GetMapping("/EmployeeBirthdayAndAnniversary")
	public String LandingPage(Model model){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
    	model.addAttribute("listEmployees",employeeService.getWorkAnniversaryDate());
    	model.addAttribute("EmployeesList",employeeService.getBirthDate());
//    	System.out.println("aniver : " + employeeService.getWorkAnniversaryDate());
//    	System.out.println("birth : " + employeeService.getBirthDate());
    	int n = loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName()));
    	String status = "false";
    	if(n==1 || n==3) {
    		status = "true";
    	}
    	model.addAttribute("status", status);
    	model.addAttribute("EmployeeList1", employeeService.getAllEmployees());
    	model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "EmployeeBirthdayAndAnniversary";
	}  
	
	@GetMapping("/save-details")
	public String SaveDetails(Model model){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		return "save-details";
	}
	
	@GetMapping("/savePage")
	public String SavePage2(Model model){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		return "savePage2";
	}
	
	@GetMapping("/SavePage")
	public String SavePage(Model model){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		return "SavePage";
	}
	
	@GetMapping("/EmployeeListExcel")
	public String ViewReportEmployeeList(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
		model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
    	List<Employee> listEmployees1 = employeeService.getEmployeeListByDescOrder();
    	model.addAttribute("listEmployees",listEmployees1);
    	List<EmployeeDesignationDetails> designation = employeeDesignationDetailsService.listAll();
		model.addAttribute("Designationlist", designation);
		List<EmployeeAddressDetails> a = employeeAddressDetailsService.listAll();
		model.addAttribute("Addresslist", a);
		List<EmployeeEducationDetails> e = employeeEducationDetailsService.listAll();
		model.addAttribute("Educationlist", e);
		List<EmployeeCertificationDetails> c = employeeCertificationDetailsService.listAll();
		model.addAttribute("Certificationlist", c);
		List<EmployeeBankDetails> b = employeeBankDetailsService.listAll();
		model.addAttribute("BankNamelist", b);
		List<EmployeeCareerBreakDetails> c1 = employeeCareerBreakDetailsService.listAll();
		model.addAttribute("CareerBreaklist", c1);
		List<LoginUser> users = loginuserService.listAll();
		model.addAttribute("Userlist", users);
    	model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "EmployeeListExcel";
	}
	
	@RequestMapping(value = "/Status/{status}/{id}", method = RequestMethod.GET)
	public String acceptOrReject(Model model, @PathVariable("status") String action, @PathVariable("id") Integer id) throws MessagingException, IOException {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    Employee employee = employeeService.get(id);
	    if (action.equals("active")) {
	        employee.setStatus(true);
	        updateLoginStatus(employee.getEmail(), true); // Update login status to active
	    } else if (action.equals("inactive")) {
	        employee.setStatus(false);
	        updateLoginStatus(employee.getEmail(), false); // Update login status to inactive
	    }
	    employeeService.saveEmployee(employee);
	    return "redirect:/EmployeeListExcel";
	}

	private void updateLoginStatus(String email, boolean status) {
	    LoginUser loginUser = loginuserService.findByUserName(email); // Assuming you have a method to retrieve a user by username
	    if (loginUser != null) {
	        loginUser.setStatus(status);
	        loginuserService.save(loginUser); // Assuming you have a method to save the updated login user
	    }
	}


	@GetMapping("/getEmployeeManagerNameDropdown")
	public @ResponseBody List<Employee> EmployeeManagerName(@RequestParam(name="name",required=false)String name,Model model,HttpServletRequest request) {
	return employeeService.findEmployeeManagerName(name,employeeService.getCompanyID(request.getUserPrincipal().getName()));				
	}
	
	
	//////////////////////////////////// Employee AddressDetails ///////////////////////////////////////////////////////////////
	
//	@GetMapping("/address_list")
//	public String EmployeeAddressList(Model model, HttpServletRequest request) {
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
//    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
//    	model.addAttribute("AddressList", employeeAddressDetailsService.listAll());
//    	model.addAttribute("EmployeeList", employeeService.getAllEmployees());
//    	model.addAttribute("UserName",employeeService.Update(request.getUserPrincipal().getName()));
//    	model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
//		return "addressList";	
//	}
	
//	@GetMapping("/address")
//	public String ShowEmployeeAddress(Model model,HttpServletRequest request){
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
//		model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
////		model.addAttribute("AddressList", employeeAddressDetailsService.listAll());
//		EmployeeAddressDetails address = new EmployeeAddressDetails();
//		model.addAttribute("addressDetails", address);
//		model.addAttribute("AddressList", employeeAddressDetailsService.listAll());
//    	model.addAttribute("EmployeeList", employeeService.getAllEmployees());
//    	model.addAttribute("UserName",employeeService.Update(request.getUserPrincipal().getName()));
//    	model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
//		return "employeeAddress";
//	}
	
	@GetMapping("/address")
	public String UpdateAddressDetails(Model model,HttpServletRequest request) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
    	EmployeeAddressDetails addressDetails = employeeAddressDetailsService.get(employeeService.Update(request.getUserPrincipal().getName()));
		model.addAttribute("addressDetails",addressDetails);
		if (model.containsAttribute("successMessage")) {
	        String successMessage = (String) model.asMap().get("successMessage");
	        model.addAttribute("successMessage", successMessage);
	    }
		return "editAddress";
	}
	
	@PostMapping("/save/address")
	public String SaveEmployeeAddress(@ModelAttribute("addressDetails") EmployeeAddressDetails addressDetails, HttpServletRequest request, RedirectAttributes redirectAttributes){
		int employee_id = employeeService.Update(request.getUserPrincipal().getName());
		Employee c = employeeService.getEmployeeDataByEmailID(employee_id);
		addressDetails.setCompany_id(c.getCompany_id());
		addressDetails.setEmployee_id(employeeService.Update(request.getUserPrincipal().getName()));
		employeeAddressDetailsService.Save(addressDetails);
		redirectAttributes.addFlashAttribute("successMessage", "Data saved successfully!");
		return "redirect:/address";
	}
		
	@GetMapping("/edit/address/{employee_id}")
	public String EditAddress(@PathVariable(value="employee_id")int id,Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
		model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		EmployeeAddressDetails address = employeeAddressDetailsService.get(id);
		model.addAttribute("addressDetails", address);
		return "editAddress";
	}
	
	//////////////////////////////////////// Employee Education Details //////////////////////////////////////////////////
	
//	@GetMapping("/education_list")
//	public String EmployeeEducationList(Model model, HttpServletRequest request) {
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
//    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
//    	model.addAttribute("EducationList", employeeEducationDetailsService.listAll());
//    	model.addAttribute("EmployeeList", employeeService.getAllEmployees());
//    	model.addAttribute("UserName",employeeService.Update(request.getUserPrincipal().getName()));
//    	model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
//		return "educationList";	
//	}
	
	@GetMapping("/education")
	public String ShowEmployeeEducation(Model model,HttpServletRequest request){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
		model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
//		model.addAttribute("EducationList", employeeEducationDetailsService.listAll());
		EmployeeEducationDetails education = new EmployeeEducationDetails();
		model.addAttribute("educationDetails", education);
		List<EducationalQualification> qualifications = educationalQualificationService.listAll();
		model.addAttribute("qualificationList", qualifications);
		model.addAttribute("EducationList", employeeEducationDetailsService.listAll());
    	model.addAttribute("EmployeeList", employeeService.getAllEmployees());
    	model.addAttribute("UserName",employeeService.Update(request.getUserPrincipal().getName()));
    	model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "employeeEducation";
	}
	
	@PostMapping("/save/education")
	public String SaveEmployeeEducation(@ModelAttribute("educationDetails") EmployeeEducationDetails educationDetails, HttpServletRequest request){
		int employee_id = employeeService.Update(request.getUserPrincipal().getName());
		Employee c = employeeService.getEmployeeDataByEmailID(employee_id);
		educationDetails.setCompany_id(c.getCompany_id());
		educationDetails.setEmployee_id(employeeService.Update(request.getUserPrincipal().getName()));
		employeeEducationDetailsService.Save(educationDetails);
		return "redirect:/education";
	}
		
	@GetMapping("/edit/education/{id}")
	public String EditEducation(@PathVariable(value="id")int id,Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
		model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		EmployeeEducationDetails education = employeeEducationDetailsService.get(id);
		model.addAttribute("educationDetails", education);
		List<EducationalQualification> qualifications = educationalQualificationService.listAll();
		model.addAttribute("qualificationList", qualifications);
		return "editEducation";
	}
	
	
	///////////////////////////////////////////////////// Employee Certification details /////////////////////////////////////////////
	
//	@GetMapping("/certificate_list")
//	public String EmployeeCertificateList(Model model, HttpServletRequest request) {
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
//    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
//    	model.addAttribute("CertificationList", employeeCertificationDetailsService.listAll());
//    	model.addAttribute("EmployeeList", employeeService.getAllEmployees());
//    	model.addAttribute("UserName",employeeService.Update(request.getUserPrincipal().getName()));
//    	model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
//		return "certificateList";	
//	}
	
	@GetMapping("/certificate_details")
	public String ShowEmployeeCertification(Model model,HttpServletRequest request){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
		model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
//		model.addAttribute("CertificationList", employeeCertificationDetailsService.listAll());
		EmployeeCertificationDetails certification = new EmployeeCertificationDetails();
		model.addAttribute("certificationDetails", certification);
		model.addAttribute("CertificationList", employeeCertificationDetailsService.listAll());
    	model.addAttribute("EmployeeList", employeeService.getAllEmployees());
    	model.addAttribute("UserName",employeeService.Update(request.getUserPrincipal().getName()));
    	model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "employeeCertification";
	}
	
	@PostMapping("/save/certificate")
	public String SaveEmployeeCertificate(@ModelAttribute("certificationDetails") EmployeeCertificationDetails certificationDetails, HttpServletRequest request){
		int employee_id = employeeService.Update(request.getUserPrincipal().getName());
		Employee c = employeeService.getEmployeeDataByEmailID(employee_id);
		certificationDetails.setCompany_id(c.getCompany_id());
		certificationDetails.setEmployee_id(employeeService.Update(request.getUserPrincipal().getName()));
		employeeCertificationDetailsService.Save(certificationDetails);
		return "redirect:/certificate_details";
	}
		
	@GetMapping("/edit/certification/{id}")
	public String EditCertificate(@PathVariable(value="id")int id,Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
		model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		EmployeeCertificationDetails certificate = employeeCertificationDetailsService.get(id);
		model.addAttribute("certificationDetails", certificate);
		return "editCertificate";
	}
	
	
	////////////////////////////////////////// Employee Bank Details ////////////////////////////////////////////////////
	
//	@GetMapping("/bank_details_list")
//	public String EmployeeBankList(Model model, HttpServletRequest request) {
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
//    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
//    	model.addAttribute("BankDetailsList", employeeBankDetailsService.listAll());
//    	model.addAttribute("EmployeeList", employeeService.getAllEmployees());
//    	model.addAttribute("UserName",employeeService.Update(request.getUserPrincipal().getName()));
//    	model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
//		return "bankDetailsList";	
//	}
	
//	@GetMapping("/bank_details")
//	public String ShowEmployeeBankDetails(Model model,HttpServletRequest request){
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
//		model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
//		model.addAttribute("BankDetailsList", employeeBankDetailsService.listAll());
//		EmployeeBankDetails bankDetails = new EmployeeBankDetails();
//		model.addAttribute("bankDetails", bankDetails);
//		model.addAttribute("BankDetailsList", employeeBankDetailsService.listAll());
//    	model.addAttribute("EmployeeList", employeeService.getAllEmployees());
//    	model.addAttribute("UserName",employeeService.Update(request.getUserPrincipal().getName()));
//    	model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
//		return "employeeBankDetails";
//	}
	
	@GetMapping("/bank_details")
	public String UpdateBankDetails(Model model,HttpServletRequest request) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
    	EmployeeBankDetails bankDetails = employeeBankDetailsService.get(employeeService.Update(request.getUserPrincipal().getName()));
    	model.addAttribute("bankDetails", bankDetails);
		if (model.containsAttribute("successMessage")) {
	        String successMessage = (String) model.asMap().get("successMessage");
	        model.addAttribute("successMessage", successMessage);
	    }
		return "editBankDetails";
	}
		
	@PostMapping("/save/bank_details")
	public String SaveEmployeeBankDetails(@ModelAttribute("bankDetails") EmployeeBankDetails bankDetails, HttpServletRequest request, RedirectAttributes redirectAttributes){
		int employee_id = employeeService.Update(request.getUserPrincipal().getName());
		Employee c = employeeService.getEmployeeDataByEmailID(employee_id);
		bankDetails.setCompany_id(c.getCompany_id());
		bankDetails.setEmployee_id(employeeService.Update(request.getUserPrincipal().getName()));
		employeeBankDetailsService.Save(bankDetails);
		redirectAttributes.addFlashAttribute("successMessage", "Data saved successfully!");
		return "redirect:/bank_details";
	}
		
	@GetMapping("/edit/bank/details/{employee_id}")
	public String EditBankDetails(@PathVariable(value="employee_id")int id,Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
		model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		EmployeeBankDetails bankDetails = employeeBankDetailsService.get(id);
		model.addAttribute("bankDetails", bankDetails);
		return "editBankDetails";
	}
	
	////////////////////////////////////////// Employee Work Experience //////////////////////////////////////////////////////
	
//	@GetMapping("/work_experience_list")
//	public String EmployeeWorkExperience(Model model, HttpServletRequest request) {
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
//    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
//    	model.addAttribute("WorkExperienceList", employeeWorkExperienceService.listAll());
//    	model.addAttribute("EmployeeList", employeeService.getAllEmployees());
//    	model.addAttribute("UserName",employeeService.Update(request.getUserPrincipal().getName()));
//    	model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
//		return "workExperienceList";	
//	}
	
	@GetMapping("/work_experience")
	public String ShowEmployeeWorkExperience(Model model, HttpServletRequest request){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
		model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
//		model.addAttribute("WorkExperienceList", employeeWorkExperienceService.listAll());
		EmployeeWorkExperience experience = new EmployeeWorkExperience();
		model.addAttribute("workExperience", experience);
		model.addAttribute("WorkExperienceList", employeeWorkExperienceService.listAll());
    	model.addAttribute("EmployeeList", employeeService.getAllEmployees());
    	model.addAttribute("UserName",employeeService.Update(request.getUserPrincipal().getName()));
    	model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "employeeWorkExperience";
	}
	
	@PostMapping("/save/work_experience")
	public String SaveEmployeeWork_experience(@ModelAttribute("workExperience") EmployeeWorkExperience workExperience, HttpServletRequest request){
		int employee_id = employeeService.Update(request.getUserPrincipal().getName());
		Employee c = employeeService.getEmployeeDataByEmailID(employee_id);
		workExperience.setCompany_id(c.getCompany_id());
		workExperience.setEmployee_id(employeeService.Update(request.getUserPrincipal().getName()));
		employeeWorkExperienceService.Save(workExperience);
		return "redirect:/work_experience";
	}
		
	@GetMapping("/edit/work_experience/{id}")
	public String EditWorkExperience(@PathVariable(value="id")int id,Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
		model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		EmployeeWorkExperience workExperience = employeeWorkExperienceService.get(id);
		model.addAttribute("workExperience", workExperience);
		return "editWorkExperience";
	}
	
	///////////////////////////////////////////// Employee Career Break Details ///////////////////////////////////////////////////
	
//	@GetMapping("/career_break_list")
//	public String EmployeeCareerBreak(Model model, HttpServletRequest request) {
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
//    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
//    	model.addAttribute("CareerBreakList", employeeCareerBreakDetailsService.listAll());
//    	model.addAttribute("EmployeeList", employeeService.getAllEmployees());
//    	model.addAttribute("UserName",employeeService.Update(request.getUserPrincipal().getName()));
//    	model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
//		return "careerBreakList";	
//	}
	
	@GetMapping("/career_break")
	public String ShowEmployeeCareerBreak(Model model,HttpServletRequest request){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
		model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
//		model.addAttribute("CareerBreakList", employeeCareerBreakDetailsService.listAll());
		EmployeeCareerBreakDetails careerBreakDetails = new EmployeeCareerBreakDetails();
		model.addAttribute("careerBreakDetails", careerBreakDetails);
		model.addAttribute("CareerBreakList", employeeCareerBreakDetailsService.listAll());
    	model.addAttribute("EmployeeList", employeeService.getAllEmployees());
    	model.addAttribute("UserName",employeeService.Update(request.getUserPrincipal().getName()));
    	model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "employeeCareerBreakDetails";
	}
	
	@PostMapping("/save/career_break")
	public String SaveEmployeeCareerBreakDetails(@ModelAttribute("careerBreakDetails") EmployeeCareerBreakDetails careerBreakDetails, HttpServletRequest request){
		int employee_id = employeeService.Update(request.getUserPrincipal().getName());
		Employee c = employeeService.getEmployeeDataByEmailID(employee_id);
		careerBreakDetails.setCompany_id(c.getCompany_id());
		careerBreakDetails.setEmployee_id(employeeService.Update(request.getUserPrincipal().getName()));
		employeeCareerBreakDetailsService.Save(careerBreakDetails);
		return "redirect:/career_break";
	}
		
	@GetMapping("/edit/career_break/{id}")
	public String EditCareerBreakDetails(@PathVariable(value="id")int id,Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
		model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		EmployeeCareerBreakDetails careerBreakDetails = employeeCareerBreakDetailsService.get(id);
		model.addAttribute("careerBreakDetails", careerBreakDetails);
		return "editCareerBreak";
	}
	
	
	@RequestMapping(value = "/emailstatus/{email_status}/{id}", method = RequestMethod.GET)
	public String EmailacceptOrReject(Model model, @PathVariable("email_status") String action, @PathVariable("id") Integer id) throws MessagingException, IOException {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    Employee employee = employeeService.get(id);
	    if (action.equals("active")) {
	        employee.setEmail_status(true);
	    } else if (action.equals("inactive")) {
	        employee.setEmail_status(false);
	    }
	    employeeService.saveEmployee(employee);
	    return "redirect:/EmployeeListExcel";
	}
	

}