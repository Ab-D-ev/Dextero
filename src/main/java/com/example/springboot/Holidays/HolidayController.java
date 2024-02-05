package com.example.springboot.Holidays;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
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

import com.example.springboot.CompanyProfile.CompanyDetails;
import com.example.springboot.CompanyProfile.CompanyDetailsService;
import com.example.springboot.EmployeesDetails.Employee;
import com.example.springboot.EmployeesDetails.EmployeeService;
import com.example.springboot.Login.LoginUserAuthorityService;
import com.example.springboot.Login.LoginUserService;
import com.example.springboot.Login.Mainsidebarauthority;
import com.example.springboot.Login.MainsidebarauthorityService;


@Controller
public class HolidayController {

	@Autowired
	private HolidayService holidayService;
	@Autowired
	private MainsidebarauthorityService mainsidebarauthorityService;
	@Autowired
	private LoginUserAuthorityService loginuserauthorityService;
	@Autowired
	private LoginUserService loginuserService;
	@Autowired
	private EmployeeService employeeService;

		
	//display Holidays
	@GetMapping("/addHolidays")
	public String holidayviewpage(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		model.addAttribute("Holidaylist",holidayService.getAllHolidays());
		AddHolidays holiday=new AddHolidays();
		model.addAttribute("holiday", holiday);
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "addHolidays";
	}
	
	@PostMapping("/saveAllHolidays")
	public String saveHolidays(@ModelAttribute("holiday") AddHolidays holiday, HttpServletRequest request) throws ParseException {
		//save Holidays to database
		int employee_id = employeeService.Update(request.getUserPrincipal().getName());
		SimpleDateFormat myformatter = new SimpleDateFormat("yyyy-MM-dd");
    	SimpleDateFormat myformatter2 = new SimpleDateFormat("dd-MM-yyyy");
    	Date d1 = myformatter2.parse(holiday.getDates());
    	holiday.setDateInYears(myformatter.format(d1));
    	Employee c = employeeService.getEmployeeDataByEmailID(employee_id);
    	holiday.setCompany_id(c.getCompany_id());
		holidayService.saveHolidays(holiday);
		return "redirect:/addHolidays";
	    
	} 
	
	@PostMapping("/SaveEditHolidays")
	public String EditHolidays(@ModelAttribute("holiday") AddHolidays holiday, HttpServletRequest request) throws ParseException {
		//save Holidays to database
		int employee_id = employeeService.Update(request.getUserPrincipal().getName());
		SimpleDateFormat myformatter = new SimpleDateFormat("yyyy-MM-dd");
    	SimpleDateFormat myformatter1 = new SimpleDateFormat("dd-MM-yyyy");
    	Date d1 = myformatter1.parse(holiday.getDates());
    	holiday.setDateInYears(myformatter.format(d1));
    	Employee c = employeeService.getEmployeeDataByEmailID(employee_id);
    	holiday.setCompany_id(c.getCompany_id());
		holidayService.saveHolidays(holiday);
		return "redirect:/addHolidays";
	    
	} 
	
	@GetMapping("/editHoliday/{id}")
	public String ShowHolidaysEditForm(@PathVariable(value="id")long id,Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		AddHolidays holiday=holidayService.getHolidaysById(id);
		model.addAttribute("holiday", holiday);
		return "editHoliday";

	}
	@GetMapping("/deleteHoliday/{id}")
	public String DeleteHolidays(@PathVariable(value="id")long id) {
		//call delete method
		this.holidayService.deleteHolidaysById(id);
		return "redirect:/addHolidays";
		
	}
	
	@GetMapping("/HolidayList")
	public String HolidayListpage(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		model.addAttribute("Holidaylist",holidayService.getAllHolidays());
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "HolidayList";
		
	}
	
	@GetMapping("/HolidayListExcel")
	public String ViewReportHolidayListExcel(Model model,@Param("fromdate") String fromdate, @Param("todate") String todate) {
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
    			List<AddHolidays> holidaylist1 = holidayService.getAllHolidays();
    			model.addAttribute("Holidaylist",holidaylist1);
    			model.addAttribute("fromdate","01-0" + month + "-" + year);
    			model.addAttribute("todate",mydate.format(myformatter));	
    		}
    		else {
    			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    			LocalDate FromDate = LocalDate.parse(fromdate, formatter);
    			LocalDate ToDate = LocalDate.parse(todate, formatter);
    			List<AddHolidays> holidaylist = new LinkedList<>();
    			List<AddHolidays> holidaylist1 = holidayService.getAllHolidays();
    			for(AddHolidays h:holidaylist1) {
    				LocalDate date1 = LocalDate.parse(h.getDates(), formatter);
    				LocalDate date2 = LocalDate.parse(h.getDates(), formatter);
    				if(date1.compareTo(FromDate)>=0 && date2.compareTo(ToDate)<=0) {
    					holidaylist.add(h);
    				}
    			}
    			model.addAttribute("Holidaylist",holidaylist);
    			model.addAttribute("fromdate",fromdate);
    			model.addAttribute("todate",todate);
    		}
    	}
    	else {
    		if(fromdate == null || todate == null) {
    			List<AddHolidays> holidaylist1 = holidayService.getAllHolidays();
    			model.addAttribute("Holidaylist",holidaylist1);
    			model.addAttribute("fromdate","01-" + month + "-" + year);
    			model.addAttribute("todate",mydate.format(myformatter));	
    		}
    		else {
    			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    			LocalDate FromDate = LocalDate.parse(fromdate, formatter);
    			LocalDate ToDate = LocalDate.parse(todate, formatter);
    			List<AddHolidays> holidaylist = new LinkedList<>();
    			List<AddHolidays> holidaylist1 = holidayService.getAllHolidays();
    			for(AddHolidays h:holidaylist1) {
    				LocalDate date1 = LocalDate.parse(h.getDates(), formatter);
    				LocalDate date2 = LocalDate.parse(h.getDates(), formatter);
    				if(date1.compareTo(FromDate)>=0 && date2.compareTo(ToDate)<=0) {
    					holidaylist.add(h);
    				}
    			}
    			model.addAttribute("Holidaylist",holidaylist);
    			model.addAttribute("fromdate",fromdate);
    			model.addAttribute("todate",todate);
    		}
    	}
    	model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "HolidayListExcel";
	}	

}
