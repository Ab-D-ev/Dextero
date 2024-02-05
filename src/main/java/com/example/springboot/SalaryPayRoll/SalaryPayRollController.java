package com.example.springboot.SalaryPayRoll;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.example.springboot.EmployeesDetails.Employee;
import com.example.springboot.EmployeesDetails.EmployeeService;
import com.example.springboot.Login.LoginUserAuthorityService;
import com.example.springboot.Login.LoginUserService;
import com.example.springboot.Login.Mainsidebarauthority;
import com.example.springboot.Login.MainsidebarauthorityService;

@Controller
public class SalaryPayRollController {
	
	@Autowired
	private MainsidebarauthorityService mainsidebarauthorityService;
	@Autowired
	private LoginUserAuthorityService loginuserauthorityService;
	@Autowired
	private LoginUserService loginuserService;
	@Autowired
	private CompanyGrossPayService companyGrossPayService;
	@Autowired
	private EmployeeGrossPayService employeeGrossPayService;
	@Autowired
	private DeductionsService deductionsService;
	@Autowired
	private MasterPercentageService masterPercentageService;
	@Autowired
	private EmployeeSalaryService employeeSalaryService;
	@Autowired
	private EmployeeService employeeService;
	
	
	
	@GetMapping("/companygrosspay")
	public String CompanyGrossPay(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
		model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		CompanyGrossPay grossPay = new CompanyGrossPay();
		model.addAttribute("grossPay", grossPay);		
		model.addAttribute("CompanyGrossPayList", companyGrossPayService.listAll());
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "CompanyGrossPay";
	}

	@PostMapping("/savecompanygrosspay")
	public String SaveCompanyGrossPay(@ModelAttribute("grossPay") CompanyGrossPay grossPay,HttpServletRequest request){
		//save to database
		int c = employeeService.getCompanyID(request.getUserPrincipal().getName());
		grossPay.setCompany_id(c);
		companyGrossPayService.Save(grossPay);
		return "redirect:/companygrosspay";
	}   
	
	@GetMapping("/edit/companygrosspay/{employee_id}")
	public String EditCompanyGrossPay(@PathVariable(value="employee_id")Integer id,Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
		model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		CompanyGrossPay grossPay = companyGrossPayService.get(id);
		model.addAttribute("grossPay", grossPay);
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "EditCompanyGrossPay";	
	}
	
	@GetMapping("/delete/companygrosspay/{employee_id}")
	public String deletecompanyGrossPay(@PathVariable(value="employee_id")Integer id) {
		//call delete  method
		this.companyGrossPayService.delete(id);
		return "redirect:/companygrosspay";
	
	}
	
//////////////////////////////////////////////// Employee GrossPay //////////////////////////////////////////////////////////////
	
	
	@GetMapping("/employeegrosspay")
	public String EmployeeGrossPay(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
		model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		EmployeeGrossPay grossPay = new EmployeeGrossPay();
		model.addAttribute("grossPay", grossPay);		
		model.addAttribute("EmployeeGrossPayList", employeeGrossPayService.listAll());
		model.addAttribute("EmployeeList", employeeService.getAllEmployees());
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "EmployeeGrossPay";
	}

	@PostMapping("/save/employeegrosspay")
	public String SaveEmployeeGrossPay(@ModelAttribute("grossPay") EmployeeGrossPay grossPay,HttpServletRequest request){
		//save to database
		double basic = (grossPay.getBasic());
		double da = (grossPay.getDa());
		double hra = (grossPay.getHra());
		double Conveyance_allowance = (grossPay.getConveyance_allowance());
		double Dearness_allowance = (grossPay.getDearness_allowance());
		double Medical_allowance = (grossPay.getMedical_allowance());
		double Other_allowance = (grossPay.getOther_allowance());
		double Project_allowance = (grossPay.getProject_allowance());
		double Project_travel = (grossPay.getProject_travel());
		double Special_allowance = (grossPay.getSpecial_allowance());
		double Travel_allowance = (grossPay.getTravel_allowance());
		
		double Gross_Salary = basic + da + hra + Conveyance_allowance + Dearness_allowance + Medical_allowance + Other_allowance +
							Project_allowance + Project_travel + Special_allowance + Travel_allowance;
		System.out.println("Grosspay : " + Gross_Salary);
		grossPay.setGrossSalary(Gross_Salary);
	
		double employee_pf = (grossPay.getEmployee_PF());
		double employee_professional_Tax = (grossPay.getEmployee_Professional_Tax());
		double employee_esi = (grossPay.getEmployee_ESI());
		double employee_tds = (grossPay.getEmployee_TDS());
		double employee_gratuity = (grossPay.getEmployee_Gratuity());
		double employee_loan_recovery = (grossPay.getEmployee_Loan_Recovery());
		
		double totalDeductions = employee_pf + employee_professional_Tax + employee_esi + employee_tds + employee_gratuity + employee_loan_recovery;
		grossPay.setTotal_Deduction(totalDeductions);
		
		int c = employeeService.getCompanyID(request.getUserPrincipal().getName());
		grossPay.setCompany_id(c);
		employeeGrossPayService.Save(grossPay);
		return "redirect:/employeegrosspay";
	}   

	@GetMapping("/edit/employeegrosspay/{empId}")
	public String EditEmployeeGrossPay(@PathVariable(value="empId")Integer id,Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
		model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		EmployeeGrossPay grossPay = employeeGrossPayService.get(id);
		model.addAttribute("grossPay", grossPay);
		List<MasterPercentage> m = masterPercentageService.listAll();
		model.addAttribute("PercentageList", m);
		CompanyGrossPay c = companyGrossPayService.get(id);
		model.addAttribute("companygrosspay", c);
		Deductions d = deductionsService.get(id);
		model.addAttribute("deduction", d);
		model.addAttribute("EmployeeList", employeeService.getAllEmployees());
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "EditEmployeeGrossPay";	
	}
	
	@GetMapping("/delete/employeegrosspay/{empId}")
	public String deleteEmployeeGrossPay(@PathVariable(value="empId")Integer id) {
		//call delete  method
		this.employeeGrossPayService.delete(id);
		return "redirect:/employeegrosspay";
	
	}
	
	///////////////////////////////////////////// Deductions /////////////////////////////////////////////////////////////////
	
	
	@GetMapping("/deduction")
	public String Deductions(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
		model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		Deductions deductions = new Deductions();
		model.addAttribute("deductions", deductions);		
		model.addAttribute("DeductionList", deductionsService.listAll());
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "Deduction";
	}

	@PostMapping("/save/deduction")
	public String SaveDeductions(@ModelAttribute("deductions") Deductions deductions,HttpServletRequest request){
		//save to database
		int c = employeeService.getCompanyID(request.getUserPrincipal().getName());
		deductions.setCompany_id(c);
		deductionsService.Save(deductions);
		return "redirect:/deduction";
	}   

	@GetMapping("/edit/deduction/{id}")
	public String EditDeduction(@PathVariable(value="id")Integer id,Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
		model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		Deductions deductions = deductionsService.get(id);
		model.addAttribute("deductions", deductions);
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "EditDeduction";	
	}
	
	@GetMapping("/delete/deduction/{id}")
	public String deleteDeduction(@PathVariable(value="id")Integer id) {
		//call delete  method
		this.deductionsService.delete(id);
		return "redirect:/deduction";
	
	}
	
////////////////////////////////////////////////// Master Percentage ///////////////////////////////////////////////////////////////////
	
	@GetMapping("/master-percentage")
	public String MasterPercentage(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
		model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		MasterPercentage masterPercentage = new MasterPercentage();
		model.addAttribute("masterPercentage", masterPercentage);		
		model.addAttribute("masterPercentageList", masterPercentageService.listAll());
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "deductionPercentage";
	}

	@PostMapping("/save/deductionPercentage")
	public String SaveDeductionsPercentage(@ModelAttribute("masterPercentage") MasterPercentage masterPercentage, HttpServletRequest request){
		//save to database
//		Employee employee = new Employee();
//		int e = employeeService.getEmployeeID(employee.getEmail());
		int c = employeeService.getCompanyID(request.getUserPrincipal().getName());
		masterPercentage.setCompany_id(c);
		masterPercentageService.Save(masterPercentage);
		return "redirect:/master-percentage";
	}   

	@GetMapping("/edit/deductionPercentage/{id}")
	public String EditDeductionPercentage(@PathVariable(value="id")Integer id,Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
		model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		MasterPercentage masterPercentage = masterPercentageService.get(id);
		model.addAttribute("masterPercentage", masterPercentage);
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "EditDeductionPercentage";	
	}
	
	@GetMapping("/delete/deductionPercentage/{id}")
	public String deleteDeductionPercentage(@PathVariable(value="id")Integer id) {
		//call delete  method
		this.masterPercentageService.delete(id);
		return "redirect:/master-percentage";
	
	}
	
	@GetMapping("/getEmployee_PF")
	public @ResponseBody String ViewEmployee_PF(@RequestParam(name="basic_sal",required=false)String basic_sal,Model model,HttpServletRequest r) {
		
		int companyid = employeeService.getCompanyID(r.getUserPrincipal().getName());
		if(Double.parseDouble(basic_sal) <= 15000) {
			return masterPercentageService.findEmployee_PFlessEqual15000(companyid);
		}
		else {
			return masterPercentageService.findEmployee_PFGrater15000(companyid);
		}
	}
	
	@GetMapping("/getEmployer_PF")
	public @ResponseBody String ViewEmployer_PF(@RequestParam(name="basic_sal",required=false)String basic_sal,Model model,HttpServletRequest r) {
		
		int companyid = employeeService.getCompanyID(r.getUserPrincipal().getName());
		if(Double.parseDouble(basic_sal) <= 15000) {
			return masterPercentageService.findEmployer_PFlessEqual15000(companyid);
		}
		else {
			return masterPercentageService.findEmployer_PFGrater15000(companyid);
		}
	}
	
	@GetMapping("/getEmployee_ESI")
	public @ResponseBody String ViewEmployee_ESI(@RequestParam(name="basic_sal",required=false)String basic_sal, @RequestParam(name="empID",required=false)Integer eid, Model model,HttpServletRequest r) {
		
		int companyid = employeeService.getCompanyID(r.getUserPrincipal().getName());
		String disability = employeeService.getEmployeeDisability(eid);
		if (Double.parseDouble(basic_sal) <= 25000 && disability.equals("Yes")) {
	        return masterPercentageService.findEmployee_ESILessEqual25000(eid, companyid);
	    } 
		else if (Double.parseDouble(basic_sal) > 25000 && disability.equals("Yes")) {
	        return masterPercentageService.findEmployee_ESIGraterEqual25000(eid, companyid);
	    } 
		if (Double.parseDouble(basic_sal) <= 21000 && disability.equals("No")) {
	        return masterPercentageService.findEmployee_ESIlessthan21000(eid,companyid);
	    } 
		else if (Double.parseDouble(basic_sal) > 21000 && disability.equals("No")) {
	        return masterPercentageService.findEmployee_ESIGrater21000(eid,companyid);
	    } else {
	        return "0";
	    }
	}
	
	@GetMapping("/getEmployer_ESI")
	public @ResponseBody String ViewEmployer_ESI(@RequestParam(name="basic_sal",required=false)String basic_sal, @RequestParam(name="empID",required=false)Integer eid, Model model,HttpServletRequest r) {
		int companyid = employeeService.getCompanyID(r.getUserPrincipal().getName());
		String disability = employeeService.getEmployeeDisability(eid);
		
		if (Double.parseDouble(basic_sal) <= 25000 && disability.equals("Yes")) {
	        return masterPercentageService.findEmployer_ESILessEqual25000(eid, companyid);
	    } 
		else if (Double.parseDouble(basic_sal) > 25000 && disability.equals("Yes")) {
	        return masterPercentageService.findEmployer_ESIGraterEqual25000(eid, companyid);
	    } 
		if (Double.parseDouble(basic_sal) <= 21000 && disability.equals("No")) {
	        return masterPercentageService.findEmployer_ESIlessthan21000(eid,companyid);
	    } 
		else if (Double.parseDouble(basic_sal) > 21000 && disability.equals("No")) {
	        return masterPercentageService.findEmployer_ESIGrater21000(eid,companyid);
	    } else {
	        return "0";
	    }
	}
	
	@GetMapping("/getProfessional_Tax1")
	public @ResponseBody String ViewProfessional_Tax(@RequestParam(name="basic_sal",required=false)String basic_sal,@RequestParam(name="empID",required=false)Integer eid,
			Model model,HttpServletRequest r) {
		
		int companyid = employeeService.getCompanyID(r.getUserPrincipal().getName());
		if(Double.parseDouble(basic_sal) > 10000) {
			return masterPercentageService.findEmployee_PTaxGrater10(eid,companyid);
		}
		else if(Double.parseDouble(basic_sal) <= 10000) {
			return masterPercentageService.findEmployeePTaxLessEqual10000(eid, companyid);
		}
//		else if(Double.parseDouble(basic_sal) >= 10000) {
//			return masterPercentageService.findEmployee_PTaxGrater10000(eid, companyid);
//		}
		else {
			return masterPercentageService.findEmployee_PTaxlessthan10(companyid);
		}
	}
	
	@GetMapping("/getGratuity")
	public @ResponseBody String ViewGratuity(@RequestParam(name="basic_sal",required=false)String basic_sal,Model model,HttpServletRequest r) {
		
		int companyid = employeeService.getCompanyID(r.getUserPrincipal().getName());
		if(Double.parseDouble(basic_sal) > 10000) {
			return masterPercentageService.findEmployee_Gratuity(companyid);
		}
		else {
			return masterPercentageService.findEmployee_Gratuityless10000(companyid);
		}
	}
	
////////////////////////////// Salary Slip ///////////////////////////////////////////////////
	
	@GetMapping("/employee_Salary_Slip")
	public String SalaryDetails(Model model,HttpServletRequest request) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
		model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		EmployeeSalary eSalary = new EmployeeSalary();
		model.addAttribute("eSalary", eSalary);		
		model.addAttribute("SalaryList", employeeSalaryService.listAll());
		model.addAttribute("EmployeeList", employeeService.getAllEmployees());
		model.addAttribute("username", employeeService.Update(request.getUserPrincipal().getName()));
		int n = loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName()));
    	String status = "false";
    	if(n==1 || n==3) {
    		status = "true";
    	}
    	model.addAttribute("status", status);
    	model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "employee_Salary_Slip";
	}
	
	@GetMapping("salary_slip/excel/{employeeId}")
    public void exportToExcel(HttpServletResponse response,@PathVariable("employeeId")int employeeId,Model model,HttpServletRequest request) throws IOException {
		EmployeeSalary eSalary = employeeSalaryService.get(employeeId);
		response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + "Salary-Slip" + ".xlsx";
        response.setHeader(headerKey, headerValue);
        List<EmployeeSalary> userSalary = employeeSalaryService.findSalarySlipByEmployeeId(employeeId);
        List<EmployeeSalary> ListEmployees1 = employeeSalaryService.listAll();
        List<EmployeeSalary> ListEmployees = new LinkedList<EmployeeSalary>();
        for(EmployeeSalary u:userSalary) {
        	Employee loginUser = employeeService.get(u.getEmployeeId());
        	for(EmployeeSalary u1:ListEmployees1) {
        		if(loginUser.getId().equals(u1.getUser_id())) {
        			ListEmployees.add(u1);
        		}
        	}
        }
        SalaryExcel excelExporter = new SalaryExcel(ListEmployees, eSalary, employeeService);
        excelExporter.export(response); 
        
     }
	
}
