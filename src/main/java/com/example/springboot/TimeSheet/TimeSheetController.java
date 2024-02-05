package com.example.springboot.TimeSheet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.springboot.AddCompany.AddCustomer;
import com.example.springboot.AddCompany.AddCustomerRepository;
import com.example.springboot.AddCompany.AddCustomerService;
import com.example.springboot.AddMilestone.Addmilestone;
import com.example.springboot.AddMilestone.AddmilestoneService;
import com.example.springboot.AddProjectCode.Category;
import com.example.springboot.AddProjectCode.CategoryService;
import com.example.springboot.AddProjectCode.ProjectNameAuthoritiesService;
import com.example.springboot.AddProjectCode.projectName;
import com.example.springboot.AddProjectCode.projectService;
import com.example.springboot.AddTask.AddTask;
import com.example.springboot.AddTask.TaskService;
import com.example.springboot.AddTask.TaskServiceImpl;
import com.example.springboot.EmployeesDetails.Employee;
import com.example.springboot.EmployeesDetails.EmployeeService;
import com.example.springboot.Login.LoginUserAuthorityService;
import com.example.springboot.Login.LoginUserService;
import com.example.springboot.Login.Mainsidebarauthority;
import com.example.springboot.Login.MainsidebarauthorityService;

@Controller
public class TimeSheetController {
	
	@Autowired
	private projectService ProjectService;
	@Autowired
	private TimeSheetService timesheetService;
	@Autowired
	private AddCustomerService customerService;
	@Autowired
	private AddmilestoneService  milestoneService;
	@Autowired
	private TaskServiceImpl taskServiceImpl;
	@Autowired
	private TaskService taskService;
	@Autowired
	private MainsidebarauthorityService mainsidebarauthorityService;
	@Autowired
	private LoginUserAuthorityService loginuserauthorityService;
	@Autowired
	private LoginUserService loginuserService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private TimeSheetReportService timesheetReportService;
	@Autowired
	private TimeSheetMailReportService MailReportService;
	@Autowired 
	private TimeSheetResourceBasedService timeSheetResourceBasedService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ProjectNameAuthoritiesService projectNameAuthoritiesService;
	@Autowired
	private  AddCustomerRepository customerRepo;
	@Autowired
	private TimeSheetReportShiftedTableService timeSheetReportShiftedTableService;
	
	@GetMapping("/showTimeSheet")
	public String Addtimesheet(Model model,HttpServletRequest request) throws ParseException, InterruptedException,IOException, NullPointerException, Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
    	LocalDateTime mydate = LocalDateTime.now();
		DateTimeFormatter myformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//		System.out.println("Month : " + mydate.getMonthValue());
//		System.out.println("currentdate : " + mydate.format(myformatter));
		List<TimeSheet> listTimeSheet = timesheetService.getListByCurrentDate(mydate.format(myformatter));
		model.addAttribute("listTimeSheet",listTimeSheet);
		TimeSheet timesheet=new TimeSheet();
		model.addAttribute("timesheet", timesheet);
		List<AddCustomer> c1 = customerService.getAllCustomer();
		model.addAttribute("CompanyList", c1);
		HashSet<projectName> t = new HashSet<projectName>();
		t.addAll(ProjectService.getAllprojectName());
		model.addAttribute("Projectlist", t);
//		remove duplicate records
		HashMap<String, Addmilestone> uniqueMap = new HashMap<>();
        for (Addmilestone m : milestoneService.getAllmilestone()) {
            uniqueMap.putIfAbsent(m.getAdd_milestone(), m);
        }
        List<Addmilestone> m1 = new ArrayList<>(uniqueMap.values());
        model.addAttribute("list", m1);
		List<AddTask> ta=taskService.getAllTask();
		model.addAttribute("tasklist", ta);
		HashSet<Category> categories = new HashSet<Category>();
		categories.addAll(categoryService.listAll());
		model.addAttribute("Categorylist", categories);
		
		Employee emp = employeeService.getEmpID(request.getUserPrincipal().getName());
        Integer employeeID = emp.getId();
        List<Integer> pName = projectNameAuthoritiesService.findProjectIdsByEmployeeId(employeeID);
        List<AddCustomer> c = new LinkedList<AddCustomer>();
        HashSet<Integer> companyid = new HashSet<Integer>();
        for (Integer projectId : pName) {
            Integer companyId = ProjectService.findCompanyNameIdByProjectIds(projectId);
            companyid.add(companyId);
        }
        List<AddCustomer> customers = customerService.getAllCustomer();
        for (AddCustomer customer : customers) {
            for (Integer cid : companyid) {
                if (customer.getId() != null && cid != null && customer.getId().intValue() == cid) {
                    c.add(customer);
                }
            }
        }

        model.addAttribute("Company", c);
        
		model.addAttribute("UserName", employeeService.Update(request.getUserPrincipal().getName()));
		model.addAttribute("RemainingDuration",timesheetService.selectRemainingDuration(mydate.format(myformatter),employeeService.Update(request.getUserPrincipal().getName())));
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));

		return "showTimeSheet";
	}
	
		@PostMapping("/saveTimeSheet")
		public String saveTimeSheet(@ModelAttribute("timesheet") TimeSheet  timesheet, HttpServletRequest request,@Param("employee_id") Integer employee_id,
				@Param("currentdate") String currentdate) throws ParseException {
			//save to database
			
			LocalDateTime mydate = LocalDateTime.now();
			DateTimeFormatter myformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			timesheet.setCurrentdate(mydate.format(myformatter));
			timesheet.setEmployee_id(employeeService.Update(request.getUserPrincipal().getName()));
			employee_id = employeeService.Update(request.getUserPrincipal().getName());
			Employee c = employeeService.getEmployeeDataByEmailID(employee_id);
//			System.out.println("Employee_Id : " + employee_id);
			currentdate = mydate.format(myformatter);
			
			SimpleDateFormat format = new SimpleDateFormat("HH:mm");
			Date date1 = format.parse(timesheet.getStart_time());
			Date date2 = format.parse(timesheet.getEnd_time());
			long differenceInMilliSeconds = Math.abs(date2.getTime() - date1.getTime());
			// Calculating the difference in Hours
			long differenceInHours = (differenceInMilliSeconds / (60 * 60 * 1000)) % 24;
			// Calculating the difference in Minutes
			long differenceInMinutes = (differenceInMilliSeconds / (60 * 1000)) % 60;
			timesheet.setDuration(differenceInHours  + " Hrs " + differenceInMinutes  + " Mins ");
			
			if(timesheetService.getUserName(employee_id, timesheet.getStartDate())==0) {
				Date d5 = format.parse("09:00");
				Date d6 = format.parse(differenceInHours + ":" + differenceInMinutes);
				long differenceInMilliSeconds2 = Math.abs(d5.getTime() - d6.getTime());
				long differenceInHours2 = (differenceInMilliSeconds2 / (60 * 60 * 1000)) % 24;
				long differenceInMinutes2 = (differenceInMilliSeconds2 / (60 * 1000)) % 60;
				timesheet.setRemainingduration(differenceInHours2  + " Hrs " + differenceInMinutes2  + " Mins ");
			}
			else {
				TimeSheet t= timesheetService.selectDate(employee_id, timesheet.getStartDate());
				if(t.getRemainingduration().equals("00 Hrs 00 Mins")) {
					timesheet.setRemainingduration("00 Hrs 00 Mins");
				}
				else {
					
//					System.out.println(t.getRemainingduration().substring(0,1));
//					System.out.println(t.getRemainingduration().substring(0,1));
					Date d5 = format.parse((t.getRemainingduration().substring(0,1)) + ":" + (t.getRemainingduration().substring(6,7)));
					Date d6 = format.parse(differenceInHours + ":" + differenceInMinutes);
					long differenceInMilliSeconds2 = Math.abs(d5.getTime() - d6.getTime());
					long differenceInHours2 = (differenceInMilliSeconds2 / (60 * 60 * 1000)) % 24;
					long differenceInMinutes2 = (differenceInMilliSeconds2 / (60 * 1000)) % 60;
					timesheet.setRemainingduration(differenceInHours2  + " Hrs " + differenceInMinutes2  + " Mins ");
				}
			}
			
			timesheet.setCompany_id(c.getCompany_id());
			if(timesheet.getMilestone_id()==null) {
				timesheet.setMilestone_id(Long.valueOf(0));
			}
			if(timesheet.getTask_id()==null) {
				timesheet.setTask_id(0);
			}
			if(timesheet.getMilestoneStatus()==null) {
				timesheet.setMilestoneStatus("-");
			}
			if(timesheet.getTaskstatus()==null) {
				timesheet.setTaskstatus("-");
			}
			timesheetService.saveTime(timesheet);
			
			List<TimeSheet> t1 = timesheetService.getCurrentdateData(employee_id, timesheet.getStartDate());
			int differenceInHours2 = 0; int differenceInMinutes2 = 0;
			for(TimeSheet t:t1) {
				Date d1 = format.parse(t.getStart_time());
				Date d2 = format.parse(t.getEnd_time());
				long differenceInMilliSeconds1 = Math.abs(d2.getTime() - d1.getTime());
				long differenceInHours1 = (differenceInMilliSeconds1 / (60 * 60 * 1000)) % 24;
				long differenceInMinutes1 = (differenceInMilliSeconds1 / (60 * 1000)) % 60;

				differenceInHours2 = differenceInHours2 + (int)differenceInHours1;
				differenceInMinutes2 = differenceInMinutes2 + (int)differenceInMinutes1;
				while(differenceInMinutes2 > 60) {
					differenceInMinutes2 = differenceInMinutes2 - 60;
					differenceInHours2++;
				}
				if(differenceInHours2 > 8) {
					t.setRemainingduration("00 Hrs 00 Mins");
					t.setOvertime((differenceInHours2 - 9) + " Hrs " +  differenceInMinutes2 + " Mins ");
//					System.out.println(" Overtime : " +  (differenceInHours2 - 9) + " Hrs " +  differenceInMinutes2 + " Mins");
					Employee c1 = employeeService.getEmployeeDataByEmailID(employeeService.Update(request.getUserPrincipal().getName()));
					t.setCompany_id(c1.getCompany_id());
					if(t.getMilestone_id()==null) {
						t.setMilestone_id(Long.valueOf(0));
					}
					if(t.getTask_id()==null) {
						t.setTask_id(0);
					}
					if(t.getMilestoneStatus()==null) {
						t.setMilestoneStatus("-");
					}
					if(t.getTaskstatus()==null) {
						t.setTaskstatus("-");
					}
					timesheetService.saveTime(t);
					
					if(timesheetService.getUserName(employee_id, t.getStartDate())==1) {
						TimeSheetReport report = new TimeSheetReport();
						report.setEmployee_id(t.getEmployee_id());
						report.setStartdate(t.getStartDate());
						report.setOvertime(t.getOvertime());
						report.setWorkedhours(t.getDuration());
						report.setRemaininghours(t.getRemainingduration());
						report.setStarttime(t.getStart_time());
						report.setEndtime(t.getEnd_time());
						SimpleDateFormat myformatter1 = new SimpleDateFormat("yyyy-MM-dd");
				    	SimpleDateFormat myformatter2 = new SimpleDateFormat("dd-MM-yyyy");
				    	Date date11 = myformatter2.parse(t.getStartDate());
				    	report.setStartDateYear(myformatter1.format(date11));
				    	Employee c2 = employeeService.getEmployeeDataByEmailID(employeeService.Update(request.getUserPrincipal().getName()));
				    	report.setCompany_id(c2.getCompany_id());
				    	timesheetReportService.SaveTimeSheetReport(report);
//				    	System.out.println("report 2 : " + timesheetService.getUserName(employee_id, t.getStartDate()));
					}
					TimeSheetReport report1 = timesheetReportService.getusernameAndstartDate(employee_id, t.getStartDate());
//					System.out.println("Report : " + report1);
						report1.setWorkedhours(differenceInHours2 + " Hrs " + differenceInMinutes2 + " Mins ");
						report1.setEndtime(t.getEnd_time());
						report1.setRemaininghours("00 Hrs 00 Mins");
						report1.setOvertime((differenceInHours2 - 9) + " Hrs " + differenceInMinutes2 + " Mins ");
						timesheetReportService.SaveTimeSheetReport(report1);
//						System.out.println("report 3 : " + report1);
				}
				else {
					t.setOvertime("00 Hrs 00 Mins");
					t.setRemainingduration((8 - differenceInHours2) + " Hrs " + (60 - differenceInMinutes2) + " Mins ");
					Employee c1 = employeeService.getEmployeeDataByEmailID(employeeService.Update(request.getUserPrincipal().getName()));
					t.setCompany_id(c1.getCompany_id());
					if(t.getMilestone_id()==null) {
						t.setMilestone_id(Long.valueOf(0));
					}
					if(t.getTask_id()==null) {
						t.setTask_id(0);
					}
					if(t.getMilestoneStatus()==null) {
						t.setMilestoneStatus("-");
					}
					if(t.getTaskstatus()==null) {
						t.setTaskstatus("-");
					}
					timesheetService.saveTime(t);
					if(timesheetService.getUserName(employee_id, t.getStartDate())==1) {
							TimeSheetReport report = new TimeSheetReport();
							report.setEmployee_id(t.getEmployee_id());
							report.setStartdate(t.getStartDate());
							report.setOvertime(t.getOvertime());
							report.setWorkedhours(t.getDuration());
							report.setRemaininghours(t.getRemainingduration());
							report.setStarttime(t.getStart_time());
							report.setEndtime(t.getEnd_time());
							SimpleDateFormat myformatter1 = new SimpleDateFormat("yyyy-MM-dd");
					    	SimpleDateFormat myformatter2 = new SimpleDateFormat("dd-MM-yyyy");
					    	Date date11 = myformatter2.parse(t.getStartDate());
					    	report.setStartDateYear(myformatter1.format(date11));
					    	Employee c2 = employeeService.getEmployeeDataByEmailID(employeeService.Update(request.getUserPrincipal().getName()));
					    	report.setCompany_id(c2.getCompany_id());
							timesheetReportService.SaveTimeSheetReport(report);
//							System.out.println("report 4 : " + timesheetService.getUserName(employee_id, t.getStartDate()));
						}
						else {
							TimeSheetReport report1 = timesheetReportService.getusernameAndstartDate(employee_id, t.getStartDate());
							report1.setWorkedhours(differenceInHours2 + " Hrs " + differenceInMinutes2 + " Mins ");
							report1.setEndtime(t.getEnd_time());
							report1.setRemaininghours((8 - differenceInHours2) + " Hrs " + (60 - differenceInMinutes2) + " Mins ");
							report1.setOvertime(t.getOvertime());
							SimpleDateFormat myformatter1 = new SimpleDateFormat("yyyy-MM-dd");
					    	SimpleDateFormat myformatter2 = new SimpleDateFormat("dd-MM-yyyy");
					    	Date date11 = myformatter2.parse(t.getStartDate());
					    	report1.setStartDateYear(myformatter1.format(date11));
					    	Employee c3 = employeeService.getEmployeeDataByEmailID(employeeService.Update(request.getUserPrincipal().getName()));
					    	report1.setCompany_id(c3.getCompany_id());
							timesheetReportService.SaveTimeSheetReport(report1);
//							System.out.println("report 5 : " + report1);
						}
					
				}
			}
			List<TimeSheetReport> tReportList = timesheetReportService.listAll();
			List<Integer> list1 = new LinkedList<Integer>();
			for(TimeSheetReport t3 : tReportList) {
				
				String str = t3.getWorkedhours().substring(0, 2);
				list1.add(Integer.parseInt(str.replaceAll("\\s", "")));
				int m1 = Integer.parseInt(str.replaceAll("\\s", ""));
				t3.setHours(Integer.parseInt(str.replaceAll("\\s", "")));
				timesheetReportService.SaveTimeSheetReport(t3);
//				System.out.println(" m1 " + m1);
				
				String st3 = t3.getWorkedhours().substring(6,8);
				list1.add(Integer.parseInt(st3.replaceAll("\\s", "")));
				int m2 = Integer.parseInt(st3.replaceAll("\\s", ""));
				t3.setMinutes(Integer.parseInt(st3.replaceAll("\\s", "")));
				Employee c4 = employeeService.getEmployeeDataByEmailID(employeeService.Update(request.getUserPrincipal().getName()));
				t3.setCompany_id(c4.getCompany_id());
				timesheetReportService.SaveTimeSheetReport(t3);
//				System.out.println(" m2 " + m2);
			}
//	    	
			return "redirect:/showTimeSheet";
		}
		
		@PostMapping("/SaveEditTimeSheet")
		public String EditTimeSheet(@ModelAttribute("timesheet") TimeSheet  timesheet,HttpServletRequest request) throws ParseException {
			//save to database
			Employee c = employeeService.getEmployeeDataByEmailID(employeeService.Update(request.getUserPrincipal().getName()));
			timesheet.setCompany_id(c.getCompany_id());
			if(timesheet.getMilestone_id()==null && timesheet.getMilestoneStatus()==null && timesheet.getTaskstatus()==null) {
				timesheet.setMilestone_id(Long.valueOf(0));
				timesheet.setTask_id(0);
				timesheet.setMilestoneStatus("-");
				timesheet.setTaskstatus("-");
			}
			if(timesheet.getTask_id()==null) {
				timesheet.setTask_id(0);
			}
			timesheetService.saveTime(timesheet);
			return "redirect:/showTimeSheet";
		}
		
		@GetMapping("/EditForm/{id}")
		public String EditForm(@PathVariable(value="id")long id,Model model,HttpServletRequest request) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
			model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
			TimeSheet timesheet=timesheetService.getTimeSheetById(id);
			model.addAttribute("timesheet", timesheet);
			List<AddCustomer> c1 = customerService.getAllCustomer();
			model.addAttribute("CompanyList", c1);
			HashSet<projectName> t = new HashSet<projectName>();
			t.addAll(ProjectService.getAllprojectName());
			model.addAttribute("Projectlist", t);
			List<Addmilestone> m= milestoneService.getAllmilestone();
			model.addAttribute("list", m);
			List<AddTask> ta=taskService.getAllTask();
			model.addAttribute("tasklist", ta);
			HashSet<Category> categories = new HashSet<Category>();
			categories.addAll(categoryService.listAll());
			model.addAttribute("Categorylist", categories);
			
			Employee emp = employeeService.getEmpID(request.getUserPrincipal().getName());
	        Integer employeeID = emp.getId();
	        List<Integer> pName = projectNameAuthoritiesService.findProjectIdsByEmployeeId(employeeID);
	        List<AddCustomer> c = new LinkedList<AddCustomer>();
	        HashSet<Integer> companyid = new HashSet<Integer>();
	        for (Integer projectId : pName) {
	            Integer companyId = ProjectService.findCompanyNameIdByProjectIds(projectId);
	            companyid.add(companyId);
	        }
	        List<AddCustomer> customers = customerService.getAllCustomer();
	        for (AddCustomer customer : customers) {
	            for (Integer cid : companyid) {
	                if (customer.getId().intValue() == cid) {
	                    c.add(customer);
	                }
	            }
	        }
	        model.addAttribute("Company", c);
	        
			model.addAttribute("UserName", employeeService.Update(request.getUserPrincipal().getName()));
			model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
			return "edit_timesheet";
		}

		@GetMapping("/deleteForm/{id}")
		public String deleteForm(@PathVariable(value="id")long id) {
			//call delete method
			this.timesheetService.deleteTimeSheetById(id);
			return "redirect:/showTimeSheet";      
		}  

		
		@GetMapping("/TimeSheetReportExcel")
		public String ViewReportTimeSheetListExcel(Model model,@Param("username") Integer username, @Param("fromdate") String fromdate, @Param("todate") String todate) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
	    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
	    	LocalDateTime mydate = LocalDateTime.now();
			DateTimeFormatter myformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	    	int year= mydate.getYear();
			int month= mydate.getMonthValue();
			Calendar calendar = Calendar.getInstance();
		    calendar.set(1, month - 1, year);
	    	System.out.println("fromdate : " + "01-0" + month + "-" + year);
	    	System.out.println("todate" + mydate.format(myformatter));
	    	if(month <=9) {
	    		if(fromdate == null || todate == null) {
					List<TimeSheetReportShiftedTable> listTimeSheet1 = timeSheetReportShiftedTableService.listAll();
					model.addAttribute("listTimeSheetReport",listTimeSheet1);
					model.addAttribute("username",username);
					model.addAttribute("fromdate", "01-0" + month + "-" + year);
					model.addAttribute("todate", mydate.format(myformatter));	
				}
				else {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
					LocalDate FromDate = LocalDate.parse(fromdate, formatter);
					LocalDate ToDate = LocalDate.parse(todate, formatter);
					List<TimeSheetReportShiftedTable> TimeSheetList = new LinkedList<>();
					List<TimeSheetReportShiftedTable> TimeSheetList1 = timeSheetReportShiftedTableService.FilterByUserName(username);
					for(TimeSheetReportShiftedTable t:TimeSheetList1) {
						LocalDate date1 = LocalDate.parse(t.getStartdate(), formatter);
						LocalDate date2 = LocalDate.parse(t.getStartdate(), formatter);
						if(date1.compareTo(FromDate)>=0 && date2.compareTo(ToDate)<=0) {
							TimeSheetList.add(t);
						}
					}
					model.addAttribute("listTimeSheetReport",TimeSheetList);
					model.addAttribute("username",username);
					model.addAttribute("fromdate",fromdate);
					model.addAttribute("todate",todate);
				}
	    	}
	    	else {
	    		if(fromdate == null || todate == null) {
					List<TimeSheetReportShiftedTable> listTimeSheet1 = timeSheetReportShiftedTableService.listAll();
					model.addAttribute("listTimeSheetReport",listTimeSheet1);
					model.addAttribute("username",username);
					model.addAttribute("fromdate", "01-" + month + "-" + year);
					model.addAttribute("todate", mydate.format(myformatter));	
				}
				else {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
					LocalDate FromDate = LocalDate.parse(fromdate, formatter);
					LocalDate ToDate = LocalDate.parse(todate, formatter);
					List<TimeSheetReportShiftedTable> TimeSheetList = new LinkedList<>();
					List<TimeSheetReportShiftedTable> TimeSheetList1 = timeSheetReportShiftedTableService.FilterByUserName(username);
					for(TimeSheetReportShiftedTable t:TimeSheetList1) {
						LocalDate date1 = LocalDate.parse(t.getStartdate(), formatter);
						LocalDate date2 = LocalDate.parse(t.getStartdate(), formatter);
						if(date1.compareTo(FromDate)>=0 && date2.compareTo(ToDate)<=0) {
							TimeSheetList.add(t);
						}
					}
					model.addAttribute("listTimeSheetReport",TimeSheetList);
					model.addAttribute("username",username);
					model.addAttribute("fromdate",fromdate);
					model.addAttribute("todate",todate);
				}
	    	}
			
			model.addAttribute("EmployeeList", employeeService.getAllEmployees());
			model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
			return "TimeSheetReportExcel";
		}
		
		@GetMapping("/TimeSheetReportExcelForEmployee")
		public String ViewReportTimeSheetListExcelForEmployee(Model model, @Param("startdate") String startdate, @Param("enddate") String enddate,
				@Param("fromdate") String fromdate, @Param("todate") String todate, HttpServletRequest request) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
	    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
	    	
	    	LocalDateTime mydate = LocalDateTime.now();
			DateTimeFormatter myformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	    	int year= mydate.getYear();
			int month= mydate.getMonthValue();
			Calendar calendar = Calendar.getInstance();
		    calendar.set(1, month - 1, year);
//	    	System.out.println("fromdate : " + "01-0" + month + "-" + year);
//	    	System.out.println("todate" + mydate.format(myformatter));
	    	if(month <=9) {
	    		if(fromdate == null || todate == null) {
					List<TimeSheetReportShiftedTable> listTimeSheet1 = timeSheetReportShiftedTableService.listAll();
					model.addAttribute("listTimeSheetReport",listTimeSheet1);
					model.addAttribute("fromdate","01-0" + month + "-" + year);
					model.addAttribute("todate", mydate.format(myformatter));	
				}
				else {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
					LocalDate FromDate = LocalDate.parse(fromdate, formatter);
					LocalDate ToDate = LocalDate.parse(todate, formatter);
					List<TimeSheetReportShiftedTable> TimeSheetList = new LinkedList<>();
					List<TimeSheetReportShiftedTable> TimeSheetList1 = timeSheetReportShiftedTableService.listAll();
					for(TimeSheetReportShiftedTable t:TimeSheetList1) {
						LocalDate date1 = LocalDate.parse(t.getStartdate(), formatter);
						LocalDate date2 = LocalDate.parse(t.getStartdate(), formatter);
						if(date1.compareTo(FromDate)>=0 && date2.compareTo(ToDate)<=0) {
							TimeSheetList.add(t);
						}
					}
					model.addAttribute("listTimeSheetReport",TimeSheetList);
					model.addAttribute("fromdate",fromdate);
					model.addAttribute("todate", todate);
				}
	    	}
	    	else {
	    		if(fromdate == null || todate == null) {
					List<TimeSheetReportShiftedTable> listTimeSheet1 = timeSheetReportShiftedTableService.listAll();
					model.addAttribute("listTimeSheetReport",listTimeSheet1);
					model.addAttribute("fromdate","01-" + month + "-" + year);
					model.addAttribute("todate", mydate.format(myformatter));	
				}
				else {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
					LocalDate FromDate = LocalDate.parse(fromdate, formatter);
					LocalDate ToDate = LocalDate.parse(todate, formatter);
					List<TimeSheetReportShiftedTable> TimeSheetList = new LinkedList<>();
					List<TimeSheetReportShiftedTable> TimeSheetList1 = timeSheetReportShiftedTableService.listAll();
					for(TimeSheetReportShiftedTable t:TimeSheetList1) {
						LocalDate date1 = LocalDate.parse(t.getStartdate(), formatter);
						LocalDate date2 = LocalDate.parse(t.getStartdate(), formatter);
						if(date1.compareTo(FromDate)>=0 && date2.compareTo(ToDate)<=0) {
							TimeSheetList.add(t);
						}
					}
					model.addAttribute("listTimeSheetReport",TimeSheetList);
					model.addAttribute("fromdate",fromdate);
					model.addAttribute("todate", todate);
				}
	    	}
			model.addAttribute("UserName", employeeService.Update(request.getUserPrincipal().getName()));
			model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
			return "TimeSheetReportExcelForEmployee";
		}
		
		@GetMapping("/TimeSheetReport")
		public String ShowTimeSheetReport(Model model,HttpServletRequest request) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
	    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
			List<TimeSheetReport> tReportList = timesheetReportService.listAll();
			model.addAttribute("tReportList", tReportList);
			TimeSheetReport timeReport = new TimeSheetReport();
			model.addAttribute("timeReport", timeReport);
			model.addAttribute("EmployeeList", employeeService.getAllEmployees());
			model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
			return "TimeSheetReport";
		}
		
		@GetMapping("/TimeSheetMailReport")
		public String ShowTimeSheetMailReport(Model model,HttpServletRequest request) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
	    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
	    	model.addAttribute("ReportList",MailReportService.listAll());
	    	List<TimeSheetMailReport> sdate = MailReportService.getMonthDataGroupByDate();
	    	List<TimeSheetMailReport> unique = sdate.stream()
                    .collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparing(TimeSheetMailReport::getSdate))),
                                               ArrayList::new));
//	    	System.out.println("S : " + sdate.size());
////	    	int n1 = 0;
//	    	for(int n1=0 ; n1<sdate.size(); n1++) {
//	    	for(int i=n1 ; i<sdate.size(); i++) {
//	    		System.out.println(sdate.get(n1).getSdate() + " / " + sdate.get(i).getSdate());
//	    		if(sdate.get(n1).getSdate().equals(sdate.get(i).getSdate())) {
//	    			sdate.remove(i);
//	    			System.out.println("Remove");
//	    		}
////	    		else {
////	    			n1++;
////	    			System.out.println("Add");
////	    		}
//	    	}
//	    	System.out.println("Loop : " + n1);
//	    	}
//	    	System.out.println("S 1 : " + sdate.size());
	    	model.addAttribute("ReportListDate",unique);
			model.addAttribute("EmployeeNameList",employeeService.getAllEmployees());
	    	int n = loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName()));
	    	String status = "false";
	    	if(n==1 || n==3) {
	    		status = "true";
	    	}
	    	model.addAttribute("status", status);
	    	model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
			return "TimeSheetMailReport";
		}
		
//		@RequestMapping("/getDaysANDHoursChart")
//		@ResponseBody
//		public List<TimeSheetReport> getChartData(Model model, HttpServletRequest request){
//		    List<TimeSheetReport> dataList = timesheetReportService.getChartData(request.getUserPrincipal().getName());
////		    model.addAttribute("UserName", request.getUserPrincipal().getName());
//		    return dataList;
//		    
//		}
		
		@GetMapping("/TimeSheetUserReport")
		public String ShowTimeSheetUserReport(Model model,HttpServletRequest request) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
	    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
	    	model.addAttribute("UserReportList",MailReportService.getUserMonthDataGroupByDate(employeeService.Update(request.getUserPrincipal().getName())));
	    	int n = loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName()));
	    	String status = "false";
	    	if(n==1 || n==3) {
	    		status = "true";
	    	}
	    	model.addAttribute("status", status);
	    	model.addAttribute("username", employeeService.Update(request.getUserPrincipal().getName()));
	    	model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
			return "TimeSheetUserReport";
		}
		
		
		@GetMapping("/TimeSheetListExcel")
		public String ViewReportTimeSheetListExcel(Model model,@Param("username") String username,@Param("companyname") String companyname, @Param("projectCode") String projectCode, 
				@Param("fromdate") String fromdate, @Param("todate") String todate) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
	    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
	    	LocalDateTime mydate = LocalDateTime.now();
			DateTimeFormatter myformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	    	int year= mydate.getYear();
			int month= mydate.getMonthValue();
			Calendar calendar = Calendar.getInstance();
		    calendar.set(1, month - 1, year);
	    	System.out.println("fromdate : " + "01-0" + month + "-" + year);
	    	System.out.println("todate" + mydate.format(myformatter));
	    	if(month <=9) {
	    		if(fromdate == null || todate == null) {
	    			List<TimeSheet> listTimeSheet1 = timesheetService.getAllTimeSheet();
	    			model.addAttribute("listTimeSheet",listTimeSheet1);
	    			model.addAttribute("username",username);
	    			model.addAttribute("companyname",companyname);
	    			model.addAttribute("projectCode",projectCode);
	    			model.addAttribute("fromdate","01-0" + month + "-" + year);
	    			model.addAttribute("todate",mydate.format(myformatter));	
	    		}
	    		else {
	    			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	    			LocalDate FromDate = LocalDate.parse(fromdate, formatter);
	    			LocalDate ToDate = LocalDate.parse(todate, formatter);
	    			List<TimeSheet> TimeSheetList = new LinkedList<>();
//	    			List<TimeSheet> TimeSheetList1 = timesheetService.FilterByCompanyName(username,companyname, projectCode);
//	    			for(TimeSheet t:TimeSheetList1) {
//	    				LocalDate date1 = LocalDate.parse(t.getStartDate(), formatter);
//	    				LocalDate date2 = LocalDate.parse(t.getStartDate(), formatter);
//	    				if(date1.compareTo(FromDate)>=0 && date2.compareTo(ToDate)<=0) {
//	    					TimeSheetList.add(t);
//	    				}
//	    			}
	    			model.addAttribute("listTimeSheet",TimeSheetList);
	    			model.addAttribute("username",username);
	    			model.addAttribute("companyname",companyname);
	    			model.addAttribute("projectCode",projectCode);
	    			model.addAttribute("fromdate",fromdate);
	    			model.addAttribute("todate",todate);
	    		}
	    	}
	    	else {
	    		if(fromdate == null || todate == null) {
	    			List<TimeSheet> listTimeSheet1 = timesheetService.getAllTimeSheet();
	    			model.addAttribute("listTimeSheet",listTimeSheet1);
	    			model.addAttribute("username",username);
	    			model.addAttribute("companyname",companyname);
	    			model.addAttribute("projectCode",projectCode);
	    			model.addAttribute("fromdate","01-" + month + "-" + year);
	    			model.addAttribute("todate",mydate.format(myformatter));	
	    		}
	    		else {
	    			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	    			LocalDate FromDate = LocalDate.parse(fromdate, formatter);
	    			LocalDate ToDate = LocalDate.parse(todate, formatter);
	    			List<TimeSheet> TimeSheetList = new LinkedList<>();
//	    			List<TimeSheet> TimeSheetList1 = timesheetService.FilterByCompanyName(username,companyname, projectCode);
//	    			for(TimeSheet t:TimeSheetList1) {
//	    				LocalDate date1 = LocalDate.parse(t.getStartDate(), formatter);
//	    				LocalDate date2 = LocalDate.parse(t.getStartDate(), formatter);
//	    				if(date1.compareTo(FromDate)>=0 && date2.compareTo(ToDate)<=0) {
//	    					TimeSheetList.add(t);
//	    				}
//	    			}
	    			model.addAttribute("listTimeSheet",TimeSheetList);
	    			model.addAttribute("username",username);
	    			model.addAttribute("companyname",companyname);
	    			model.addAttribute("projectCode",projectCode);
	    			model.addAttribute("fromdate",fromdate);
	    			model.addAttribute("todate",todate);
	    		}
	    	}
	    	List<projectName> p= ProjectService.getAllprojectName();
			model.addAttribute("Projectlist", p);
			List<AddCustomer> c1 = customerService.getAllCustomer();
			model.addAttribute("CompanyList", c1);
			HashSet<projectName> t = new HashSet<projectName>();
			t.addAll(ProjectService.getAllprojectName());
			model.addAttribute("Projectlist", t);
			List<Addmilestone> m= milestoneService.getAllmilestone();
			model.addAttribute("list", m);
			List<AddTask> ta=taskService.getAllTask();
			model.addAttribute("tasklist", ta);
			HashSet<Category> categories = new HashSet<Category>();
			categories.addAll(categoryService.listAll());
			model.addAttribute("Categorylist", categories);
			model.addAttribute("EmployeeList", employeeService.getAllEmployees());
			model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
			return "TimeSheetListExcel";
		}
		
		@GetMapping("/TimeSheetListExcelForEmployee")
		public String ViewReportTimeSheetListExcelForEmployee(Model model,@Param("username") String username,@Param("companyname") String companyname, @Param("projectCode") String projectCode, 
				@Param("fromdate") String fromdate, @Param("todate") String todate, HttpServletRequest request) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
	    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
	    	LocalDateTime mydate = LocalDateTime.now();
			DateTimeFormatter myformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	    	int year= mydate.getYear();
			int month= mydate.getMonthValue();
			Calendar calendar = Calendar.getInstance();
		    calendar.set(1, month - 1, year);
	    	System.out.println("fromdate : " + "01-0" + month + "-" + year);
	    	System.out.println("todate" + mydate.format(myformatter));
	    	if(month <=9) {
	    		if(companyname==null || projectCode==null ||fromdate == null || todate == null) {
					List<TimeSheet> listTimeSheet1 = timesheetService.getAllTimeSheet();
					model.addAttribute("listTimeSheet",listTimeSheet1);
					model.addAttribute("username",username);
					model.addAttribute("companyname",companyname);
					model.addAttribute("projectCode",projectCode);
					model.addAttribute("fromdate","01-0" + month + "-" + year);
					model.addAttribute("todate",mydate.format(myformatter));	
				}
				else {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
					LocalDate FromDate = LocalDate.parse(fromdate, formatter);
					LocalDate ToDate = LocalDate.parse(todate, formatter);
					List<TimeSheet> TimeSheetList = new LinkedList<>();
//					List<TimeSheet> TimeSheetList1 = timesheetService.FilterByCompanyNameProjectCode(companyname, projectCode);
//					for(TimeSheet t:TimeSheetList1) {
//						LocalDate date1 = LocalDate.parse(t.getStartDate(), formatter);
//						LocalDate date2 = LocalDate.parse(t.getStartDate(), formatter);
//						if(date1.compareTo(FromDate)>=0 && date2.compareTo(ToDate)<=0) {
//							TimeSheetList.add(t);
//						}
//					}
					model.addAttribute("listTimeSheet",TimeSheetList);
					model.addAttribute("username",username);
					model.addAttribute("companyname",companyname);
					model.addAttribute("projectCode",projectCode);
					model.addAttribute("fromdate",fromdate);
					model.addAttribute("todate",todate);
				}
	    	}
	    	else {
	    		if(companyname==null || projectCode==null ||fromdate == null || todate == null) {
					List<TimeSheet> listTimeSheet1 = timesheetService.getAllTimeSheet();
					model.addAttribute("listTimeSheet",listTimeSheet1);
					model.addAttribute("username",username);
					model.addAttribute("companyname",companyname);
					model.addAttribute("projectCode",projectCode);
					model.addAttribute("fromdate","01-" + month + "-" + year);
					model.addAttribute("todate",mydate.format(myformatter));	
				}
				else {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
					LocalDate FromDate = LocalDate.parse(fromdate, formatter);
					LocalDate ToDate = LocalDate.parse(todate, formatter);
					List<TimeSheet> TimeSheetList = new LinkedList<>();
//					List<TimeSheet> TimeSheetList1 = timesheetService.FilterByCompanyNameProjectCode(companyname, projectCode);
//					for(TimeSheet t:TimeSheetList1) {
//						LocalDate date1 = LocalDate.parse(t.getStartDate(), formatter);
//						LocalDate date2 = LocalDate.parse(t.getStartDate(), formatter);
//						if(date1.compareTo(FromDate)>=0 && date2.compareTo(ToDate)<=0) {
//							TimeSheetList.add(t);
//						}
//					}
					model.addAttribute("listTimeSheet",TimeSheetList);
					model.addAttribute("username",username);
					model.addAttribute("companyname",companyname);
					model.addAttribute("projectCode",projectCode);
					model.addAttribute("fromdate",fromdate);
					model.addAttribute("todate",todate);
				}
	    	}
	    	List<projectName> p= ProjectService.getAllprojectName();
			model.addAttribute("Projectlist", p);
			List<AddCustomer> c1 = customerService.getAllCustomer();
			model.addAttribute("CompanyList", c1);
			HashSet<projectName> t = new HashSet<projectName>();
			t.addAll(ProjectService.getAllprojectName());
			model.addAttribute("Projectlist", t);
			List<Addmilestone> m= milestoneService.getAllmilestone();
			model.addAttribute("list", m);
			List<AddTask> ta=taskService.getAllTask();
			model.addAttribute("tasklist", ta);
			HashSet<Category> categories = new HashSet<Category>();
			categories.addAll(categoryService.listAll());
			model.addAttribute("Categorylist", categories);
			model.addAttribute("UserName", employeeService.Update(request.getUserPrincipal().getName()));
			model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
			return "TimeSheetListExcelForEmployee";
		}
	
////////////////////////////////////// TimeSheet Resource based ////////////////////////////////////////////////////////////
		
//		@GetMapping("/TimeSheetResourceBasedList")
//		public String ShowTimeSheetResourceBasedList(Model model, HttpServletRequest request) {
//			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//			List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
//	    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
//	    	List<Mainsidebar> listmainsiderbar = mainsidebarService.listAll();
//	    	model.addAttribute("listMainsiderbar", listmainsiderbar);
//			model.addAttribute("TimeList", timeSheetResourceBasedService.listAll());
//			model.addAttribute("EmployeeList", employeeService.getAllEmployees());
//			return "TimeSheetResourceBasedList";
//		}
		
		@GetMapping("/TimeSheetResourceBased")
		public String ShowTimeSheetResourceBased(Model model, HttpServletRequest request) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
	    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
			LocalDateTime mydate = LocalDateTime.now();
			DateTimeFormatter myformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			model.addAttribute("TimeList", timeSheetResourceBasedService.getTimeSheetListByEmployeeID(employeeService.Update(request.getUserPrincipal().getName())));
			TimeSheetResourceBased time1 = new TimeSheetResourceBased();
			model.addAttribute("time1", time1);
			List<projectName> p= ProjectService.getAllprojectName();
			model.addAttribute("Projectlist", p);
			List<AddCustomer> c1 = customerService.getAllCustomer();
			model.addAttribute("CompanyList", c1);
			List<Addmilestone> m= milestoneService.getAllmilestone();
			model.addAttribute("list", m);
			List<AddTask> ta=taskService.getAllTask();
			model.addAttribute("tasklist", ta);
			HashSet<Category> categories = new HashSet<Category>();
			categories.addAll(categoryService.listAll());
			model.addAttribute("Categorylist", categories);
			
			Employee emp = employeeService.getEmpID(request.getUserPrincipal().getName());
	        Integer employeeID = emp.getId();
	        List<Integer> pName = projectNameAuthoritiesService.findProjectIdsByEmployeeId(employeeID);
	        List<AddCustomer> c = new LinkedList<AddCustomer>();
	        HashSet<Integer> companyid = new HashSet<Integer>();
	        for (Integer projectId : pName) {
	            Integer companyId = ProjectService.findCompanyNameIdByProjectIds(projectId);
	            companyid.add(companyId);
	        }
	        List<AddCustomer> customers = customerService.getAllCustomer();
	        for (AddCustomer customer : customers) {
	            for (Integer cid : companyid) {
	                if (customer.getId() != null && cid != null && customer.getId().intValue() == cid) {
	                    c.add(customer);
	                }
	            }
	        }
	        model.addAttribute("Company", c);
	        
			model.addAttribute("UserName", employeeService.Update(request.getUserPrincipal().getName()));
			model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
			return "TimeSheetResourceBased";
		}
		
		@PostMapping("/SaveTimeSheetResourceBased")
		public String SaveTimeSheetResourceBased(@ModelAttribute("time1") TimeSheetResourceBased  time1, HttpServletRequest request) throws ParseException {
			//save project name to database
			time1.setEmployee_id(employeeService.Update(request.getUserPrincipal().getName()));
			LocalDateTime mydate = LocalDateTime.now();
			DateTimeFormatter myformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			time1.setCurrentdate(mydate.format(myformatter));
			SimpleDateFormat format = new SimpleDateFormat("HH:mm");

			Date date1 = format.parse(time1.getStarttime());
			Date date2 = format.parse(time1.getEndtime());
			
			long differenceInMilliSeconds = Math.abs(date2.getTime() - date1.getTime());
		
			// Calculating the difference in Hours
			long differenceInHours = (differenceInMilliSeconds / (60 * 60 * 1000)) % 24;

			// Calculating the difference in Minutes
			long differenceInMinutes = (differenceInMilliSeconds / (60 * 1000)) % 60;

			//System.out.println("Difference is " + differenceInHours + " hours " + differenceInMinutes + " minutes ");

			time1.setNumberofhours(differenceInHours  + " Hrs " + differenceInMinutes  + " Mins ");
			Employee c = employeeService.getEmployeeDataByEmailID(1);
			time1.setCompany_id(c.getCompany_id());

			timeSheetResourceBasedService.SaveTimeSheetResourceBased(time1);
			return "redirect:/TimeSheetResourceBased";
		}   
		
		@GetMapping("/EditTimeSheetResourceBased/{id}")
		public String EditTimeSheetResourceBased(@PathVariable(value="id")Integer id,Model model, HttpServletRequest request) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
	    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
	    	TimeSheetResourceBased time1 = timeSheetResourceBasedService.get(id);
	    	model.addAttribute("time1", time1);
	    	List<projectName> p= ProjectService.getAllprojectName();
			model.addAttribute("Projectlist", p);
			List<AddCustomer> c1 = customerService.getAllCustomer();
			model.addAttribute("CompanyList", c1);
			List<Addmilestone> m= milestoneService.getAllmilestone();
			model.addAttribute("list", m);
			List<AddTask> ta=taskService.getAllTask();
			model.addAttribute("tasklist", ta);
			HashSet<Category> categories = new HashSet<Category>();
			categories.addAll(categoryService.listAll());
			model.addAttribute("Categorylist", categories);
			
			Employee emp = employeeService.getEmpID(request.getUserPrincipal().getName());
	        Integer employeeID = emp.getId();
	        List<Integer> pName = projectNameAuthoritiesService.findProjectIdsByEmployeeId(employeeID);
	        List<AddCustomer> c = new LinkedList<AddCustomer>();
	        HashSet<Integer> companyid = new HashSet<Integer>();
	        for (Integer projectId : pName) {
	            Integer companyId = ProjectService.findCompanyNameIdByProjectIds(projectId);
	            companyid.add(companyId);
	        }
	        List<AddCustomer> customers = customerService.getAllCustomer();
	        for (AddCustomer customer : customers) {
	            for (Integer cid : companyid) {
	                if (customer.getId().intValue() == cid) {
	                    c.add(customer);
	                }
	            }
	        }
	        model.addAttribute("Company", c);
	        
	        model.addAttribute("UserName", employeeService.Update(request.getUserPrincipal().getName()));
			model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
			return "EditTimeSheetResourceBased";	
		}
		
		
		@GetMapping("/deleteTimeSheetResourceBased/{id}")
		public String deleteTimeSheetResourceBased(@PathVariable(value="id")Integer id) {
			//call delete  method
			this.timeSheetResourceBasedService.deleteTimeSheetResourceBased(id);
			return "redirect:/TimeSheetResourceBased";
			
		}

		@GetMapping("/ResourcesbasedTimeSheetExcel")
		public String ResourceBasedTimeSheetListExcel(Model model,@Param("username") String username,@Param("companyname") String companyname, @Param("projectname") String projectname, 
				@Param("fromdate") String fromdate, @Param("todate") String todate) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
	    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
	    	LocalDateTime mydate = LocalDateTime.now();
			DateTimeFormatter myformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	    	int year= mydate.getYear();
			int month= mydate.getMonthValue();
			Calendar calendar = Calendar.getInstance();
		    calendar.set(1, month - 1, year);
	    	System.out.println("fromdate : " + "01-0" + month + "-" + year);
	    	System.out.println("todate" + mydate.format(myformatter));
	    	if(month <=9) {
	    		if(fromdate == null || todate == null) {
	    			List<TimeSheetResourceBased> listTimeSheet1 = timeSheetResourceBasedService.listAll();
	    			model.addAttribute("TimeList",listTimeSheet1);
	    			model.addAttribute("username",username);
	    			model.addAttribute("companyname",companyname);
	    			model.addAttribute("projectname",projectname);
	    			model.addAttribute("fromdate","01-0" + month + "-" + year);
	    			model.addAttribute("todate",mydate.format(myformatter));	
	    		}
	    		else {
	    			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	    			LocalDate FromDate = LocalDate.parse(fromdate, formatter);
	    			LocalDate ToDate = LocalDate.parse(todate, formatter);
	    			List<TimeSheetResourceBased> TimeSheetList = new LinkedList<>();
//	    			List<TimeSheetResourceBased> TimeSheetList1 = timeSheetResourceBasedService.SearchByCompanyEmployeeDate(username, companyname, projectname);
//	    			for(TimeSheetResourceBased t:TimeSheetList1) {
//	    				LocalDate date1 = LocalDate.parse(t.getStartdate(), formatter);
//	    				LocalDate date2 = LocalDate.parse(t.getStartdate(), formatter);
//	    				if(date1.compareTo(FromDate)>=0 && date2.compareTo(ToDate)<=0) {
//	    					TimeSheetList.add(t);
//	    				}
//	    			}
	    			model.addAttribute("TimeList",TimeSheetList);
	    			model.addAttribute("username",username);
	    			model.addAttribute("companyname",companyname);
	    			model.addAttribute("projectname",projectname);
	    			model.addAttribute("fromdate",fromdate);
	    			model.addAttribute("todate",todate);
	    		}
	    	}
	    	else {
	    		if(fromdate == null || todate == null) {
	    			List<TimeSheetResourceBased> listTimeSheet1 = timeSheetResourceBasedService.listAll();
	    			model.addAttribute("TimeList",listTimeSheet1);
	    			model.addAttribute("username",username);
	    			model.addAttribute("companyname",companyname);
	    			model.addAttribute("projectname",projectname);
	    			model.addAttribute("fromdate","01-0" + month + "-" + year);
	    			model.addAttribute("todate",mydate.format(myformatter));	
	    		}
	    		else {
	    			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	    			LocalDate FromDate = LocalDate.parse(fromdate, formatter);
	    			LocalDate ToDate = LocalDate.parse(todate, formatter);
	    			List<TimeSheetResourceBased> TimeSheetList = new LinkedList<>();
//	    			List<TimeSheetResourceBased> TimeSheetList1 = timeSheetResourceBasedService.SearchByCompanyEmployeeDate(username, companyname, projectname);
//	    			for(TimeSheetResourceBased t:TimeSheetList1) {
//	    				LocalDate date1 = LocalDate.parse(t.getStartdate(), formatter);
//	    				LocalDate date2 = LocalDate.parse(t.getStartdate(), formatter);
//	    				if(date1.compareTo(FromDate)>=0 && date2.compareTo(ToDate)<=0) {
//	    					TimeSheetList.add(t);
//	    				}
//	    			}
	    			model.addAttribute("TimeList",TimeSheetList);
	    			model.addAttribute("username",username);
	    			model.addAttribute("companyname",companyname);
	    			model.addAttribute("projectname",projectname);
	    			model.addAttribute("fromdate",fromdate);
	    			model.addAttribute("todate",todate);
	    		}
	    	}
	    	List<projectName> p= ProjectService.getAllprojectName();
			model.addAttribute("Projectlist", p);
			List<AddCustomer> c1 = customerService.getAllCustomer();
			model.addAttribute("CompanyList", c1);
			HashSet<projectName> t = new HashSet<projectName>();
			t.addAll(ProjectService.getAllprojectName());
			model.addAttribute("Projectlist", t);
			List<Addmilestone> m= milestoneService.getAllmilestone();
			model.addAttribute("list", m);
			List<AddTask> ta=taskService.getAllTask();
			model.addAttribute("tasklist", ta);
			HashSet<Category> categories = new HashSet<Category>();
			categories.addAll(categoryService.listAll());
			model.addAttribute("Categorylist", categories);
			
			model.addAttribute("EmployeeList", employeeService.getAllEmployees());
			model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
			return "TimeSheetResourceBasedList";
		}
		
		
		
		@GetMapping("/TimeSheetReportExcelForEmployeeManager")
		public String ViewReportTimeSheetListExcelForEmployeeManager(Model model,@Param("fromdate") String fromdate, @Param("todate") String todate, HttpServletRequest request) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
	    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
	    	
	    	LocalDateTime mydate = LocalDateTime.now();
			DateTimeFormatter myformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	    	int year= mydate.getYear();
			int month= mydate.getMonthValue();
			Calendar calendar = Calendar.getInstance();
		    calendar.set(1, month - 1, year);
	    	System.out.println("fromdate : " + "01-0" + month + "-" + year);
	    	System.out.println("todate" + mydate.format(myformatter));
	    	if(month <=9) {
	    		if(fromdate == null || todate == null) {
					List<TimeSheetReportShiftedTable> listTimeSheet1 = timeSheetReportShiftedTableService.listAll();
					model.addAttribute("listTimeSheetReport",listTimeSheet1);
					model.addAttribute("fromdate","01-0" + month + "-" + year);
					model.addAttribute("todate", mydate.format(myformatter));	
				}
				else {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
					LocalDate FromDate = LocalDate.parse(fromdate, formatter);
					LocalDate ToDate = LocalDate.parse(todate, formatter);
					List<TimeSheetReportShiftedTable> TimeSheetList = new LinkedList<>();
					List<TimeSheetReportShiftedTable> TimeSheetList1 = timeSheetReportShiftedTableService.listAll();
					for(TimeSheetReportShiftedTable t:TimeSheetList1) {
						LocalDate date1 = LocalDate.parse(t.getStartdate(), formatter);
						LocalDate date2 = LocalDate.parse(t.getStartdate(), formatter);
						if(date1.compareTo(FromDate)>=0 && date2.compareTo(ToDate)<=0) {
							TimeSheetList.add(t);
						}
					}
					model.addAttribute("listTimeSheetReport",TimeSheetList);
					model.addAttribute("fromdate",fromdate);
					model.addAttribute("todate", todate);
				}
	    	}
	    	else {
	    		if(fromdate == null || todate == null) {
					List<TimeSheetReportShiftedTable> listTimeSheet1 = timeSheetReportShiftedTableService.listAll();
					model.addAttribute("listTimeSheetReport",listTimeSheet1);
					model.addAttribute("fromdate","01-" + month + "-" + year);
					model.addAttribute("todate", mydate.format(myformatter));	
				}
				else {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
					LocalDate FromDate = LocalDate.parse(fromdate, formatter);
					LocalDate ToDate = LocalDate.parse(todate, formatter);
					List<TimeSheetReportShiftedTable> TimeSheetList = new LinkedList<>();
					List<TimeSheetReportShiftedTable> TimeSheetList1 = timeSheetReportShiftedTableService.listAll();
					for(TimeSheetReportShiftedTable t:TimeSheetList1) {
						LocalDate date1 = LocalDate.parse(t.getStartdate(), formatter);
						LocalDate date2 = LocalDate.parse(t.getStartdate(), formatter);
						if(date1.compareTo(FromDate)>=0 && date2.compareTo(ToDate)<=0) {
							TimeSheetList.add(t);
						}
					}
					model.addAttribute("listTimeSheetReport",TimeSheetList);
					model.addAttribute("fromdate",fromdate);
					model.addAttribute("todate", todate);
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
//	    	for(Employee e:employees) {
//	    		System.out.println(e.getEmail());
//	    	}
	    	model.addAttribute("ListUserName", employees);
	    	List<AddCustomer> c1 = customerService.getAllCustomer();
			model.addAttribute("CompanyList", c1);
			HashSet<projectName> t = new HashSet<projectName>();
			t.addAll(ProjectService.getAllprojectName());
			model.addAttribute("Projectlist", t);
			List<Addmilestone> m= milestoneService.getAllmilestone();
			model.addAttribute("list", m);
			List<AddTask> ta=taskService.getAllTask();
			model.addAttribute("tasklist", ta);
	    	model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
			return "TimeSheetReportExcelForEmployeeManager";
		}
		
		
		
		
		@GetMapping("/TimeSheetListExcelForEmployeeManager")
		public String ViewReportTimeSheetListExcelForEmployeeManager(Model model,@Param("username") String username,@Param("companyname") String companyname, @Param("projectCode") String projectCode, 
				@Param("fromdate") String fromdate, @Param("todate") String todate, HttpServletRequest request) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
	    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
	    	LocalDateTime mydate = LocalDateTime.now();
			DateTimeFormatter myformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	    	int year= mydate.getYear();
			int month= mydate.getMonthValue();
			Calendar calendar = Calendar.getInstance();
		    calendar.set(1, month - 1, year);
	    	System.out.println("fromdate : " + "01-0" + month + "-" + year);
	    	System.out.println("todate" + mydate.format(myformatter));
	    	if(month <=9) {
	    		if(companyname==null || projectCode==null ||fromdate == null || todate == null) {
					List<TimeSheet> listTimeSheet1 = timesheetService.getAllTimeSheet();
					model.addAttribute("listTimeSheet",listTimeSheet1);
					model.addAttribute("username",username);
					model.addAttribute("companyname",companyname);
					model.addAttribute("projectCode",projectCode);
					model.addAttribute("fromdate","01-0" + month + "-" + year);
					model.addAttribute("todate",mydate.format(myformatter));	
				}
				else {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
					LocalDate FromDate = LocalDate.parse(fromdate, formatter);
					LocalDate ToDate = LocalDate.parse(todate, formatter);
					List<TimeSheet> TimeSheetList = new LinkedList<>();
//					List<TimeSheet> TimeSheetList1 = timesheetService.FilterByCompanyNameProjectCode(companyname, projectCode);
//					for(TimeSheet t:TimeSheetList1) {
//						LocalDate date1 = LocalDate.parse(t.getStartDate(), formatter);
//						LocalDate date2 = LocalDate.parse(t.getStartDate(), formatter);
//						if(date1.compareTo(FromDate)>=0 && date2.compareTo(ToDate)<=0) {
//							TimeSheetList.add(t);
//						}
//					}
					model.addAttribute("listTimeSheet",TimeSheetList);
					model.addAttribute("username",username);
					model.addAttribute("companyname",companyname);
					model.addAttribute("projectCode",projectCode);
					model.addAttribute("fromdate",fromdate);
					model.addAttribute("todate",todate);
				}
	    	}
	    	else {
	    		if(companyname==null || projectCode==null ||fromdate == null || todate == null) {
					List<TimeSheet> listTimeSheet1 = timesheetService.getAllTimeSheet();
					model.addAttribute("listTimeSheet",listTimeSheet1);
					model.addAttribute("username",username);
					model.addAttribute("companyname",companyname);
					model.addAttribute("projectCode",projectCode);
					model.addAttribute("fromdate","01-" + month + "-" + year);
					model.addAttribute("todate",mydate.format(myformatter));	
				}
				else {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
					LocalDate FromDate = LocalDate.parse(fromdate, formatter);
					LocalDate ToDate = LocalDate.parse(todate, formatter);
					List<TimeSheet> TimeSheetList = new LinkedList<>();
//					List<TimeSheet> TimeSheetList1 = timesheetService.FilterByCompanyNameProjectCode(companyname, projectCode);
//					for(TimeSheet t:TimeSheetList1) {
//						LocalDate date1 = LocalDate.parse(t.getStartDate(), formatter);
//						LocalDate date2 = LocalDate.parse(t.getStartDate(), formatter);
//						if(date1.compareTo(FromDate)>=0 && date2.compareTo(ToDate)<=0) {
//							TimeSheetList.add(t);
//						}
//					}
					model.addAttribute("listTimeSheet",TimeSheetList);
					model.addAttribute("username",username);
					model.addAttribute("companyname",companyname);
					model.addAttribute("projectCode",projectCode);
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
	    	List<AddCustomer> c1 = customerService.getAllCustomer();
			model.addAttribute("CompanyList", c1);
			HashSet<projectName> t = new HashSet<projectName>();
			t.addAll(ProjectService.getAllprojectName());
			model.addAttribute("Projectlist", t);
			List<Addmilestone> m= milestoneService.getAllmilestone();
			model.addAttribute("list", m);
			List<AddTask> ta=taskService.getAllTask();
			model.addAttribute("tasklist", ta);
	    	model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
			return "TimeSheetListExcelForEmployeeManager";
		}

		
		@GetMapping("/ResourcesbasedTimeSheetExcelEmployeeManager")
		public String ResourceBasedTimeSheetListExcelManager(Model model,@Param("username") String username,@Param("companyname") String companyname, @Param("projectname") String projectname, 
				@Param("fromdate") String fromdate, @Param("todate") String todate, HttpServletRequest request) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
	    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
	    	LocalDateTime mydate = LocalDateTime.now();
			DateTimeFormatter myformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	    	int year= mydate.getYear();
			int month= mydate.getMonthValue();
			Calendar calendar = Calendar.getInstance();
		    calendar.set(1, month - 1, year);
	    	System.out.println("fromdate : " + "01-0" + month + "-" + year);
	    	System.out.println("todate" + mydate.format(myformatter));
	    	if(month <=9) {
	    		if(fromdate == null || todate == null) {
	    			List<TimeSheetResourceBased> listTimeSheet1 = timeSheetResourceBasedService.listAll();
	    			model.addAttribute("TimeList",listTimeSheet1);
	    			model.addAttribute("username",username);
	    			model.addAttribute("companyname",companyname);
	    			model.addAttribute("projectname",projectname);
	    			model.addAttribute("fromdate","01-0" + month + "-" + year);
	    			model.addAttribute("todate",mydate.format(myformatter));	
	    		}
	    		else {
	    			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	    			LocalDate FromDate = LocalDate.parse(fromdate, formatter);
	    			LocalDate ToDate = LocalDate.parse(todate, formatter);
	    			List<TimeSheetResourceBased> TimeSheetList = new LinkedList<>();
//	    			List<TimeSheetResourceBased> TimeSheetList1 = timeSheetResourceBasedService.SearchByCompanyEmployeeDate(username, companyname, projectname);
//	    			for(TimeSheetResourceBased t:TimeSheetList1) {
//	    				LocalDate date1 = LocalDate.parse(t.getStartdate(), formatter);
//	    				LocalDate date2 = LocalDate.parse(t.getStartdate(), formatter);
//	    				if(date1.compareTo(FromDate)>=0 && date2.compareTo(ToDate)<=0) {
//	    					TimeSheetList.add(t);
//	    				}
//	    			}
	    			model.addAttribute("TimeList",TimeSheetList);
	    			model.addAttribute("username",username);
	    			model.addAttribute("companyname",companyname);
	    			model.addAttribute("projectname",projectname);
	    			model.addAttribute("fromdate",fromdate);
	    			model.addAttribute("todate",todate);
	    		}
	    	}
	    	else {
	    		if(fromdate == null || todate == null) {
	    			List<TimeSheetResourceBased> listTimeSheet1 = timeSheetResourceBasedService.listAll();
	    			model.addAttribute("TimeList",listTimeSheet1);
	    			model.addAttribute("username",username);
	    			model.addAttribute("companyname",companyname);
	    			model.addAttribute("projectname",projectname);
	    			model.addAttribute("fromdate","01-0" + month + "-" + year);
	    			model.addAttribute("todate",mydate.format(myformatter));	
	    		}
	    		else {
	    			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	    			LocalDate FromDate = LocalDate.parse(fromdate, formatter);
	    			LocalDate ToDate = LocalDate.parse(todate, formatter);
	    			List<TimeSheetResourceBased> TimeSheetList = new LinkedList<>();
//	    			List<TimeSheetResourceBased> TimeSheetList1 = timeSheetResourceBasedService.SearchByCompanyEmployeeDate(username, companyname, projectname);
//	    			for(TimeSheetResourceBased t:TimeSheetList1) {
//	    				LocalDate date1 = LocalDate.parse(t.getStartdate(), formatter);
//	    				LocalDate date2 = LocalDate.parse(t.getStartdate(), formatter);
//	    				if(date1.compareTo(FromDate)>=0 && date2.compareTo(ToDate)<=0) {
//	    					TimeSheetList.add(t);
//	    				}
//	    			}
	    			model.addAttribute("TimeList",TimeSheetList);
	    			model.addAttribute("username",username);
	    			model.addAttribute("companyname",companyname);
	    			model.addAttribute("projectname",projectname);
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
	    	List<projectName> p= ProjectService.getAllprojectName();
			model.addAttribute("Projectlist", p);
			List<AddCustomer> c1 = customerService.getAllCustomer();
			model.addAttribute("CompanyList", c1);
			HashSet<projectName> t = new HashSet<projectName>();
			t.addAll(ProjectService.getAllprojectName());
			model.addAttribute("Projectlist", t);
			List<Addmilestone> m= milestoneService.getAllmilestone();
			model.addAttribute("list", m);
			List<AddTask> ta=taskService.getAllTask();
			model.addAttribute("tasklist", ta);
			HashSet<Category> categories = new HashSet<Category>();
			categories.addAll(categoryService.listAll());
			model.addAttribute("Categorylist", categories);
	    	model.addAttribute("EmployeeList", employeeService.getAllEmployees());
	    	model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
			return "TimeSheetResourceBasedListEmployeeManager";
		}
		
		@GetMapping("/getCompanyId")
		@ResponseBody
		public Long getCompanyId(@RequestParam(name="id",required=false) String id) {
		    return customerService.findCompanyIDByCompanyName(id);
		}

		@GetMapping("/getTaskdropdown1")
		public @ResponseBody HashSet<AddTask> ViewlevelofTaskpage(@RequestParam(name="name",required=false)Integer name,@RequestParam(name="name1",required=false)Integer name1, @RequestParam(name="name2",required=false)Integer name2,Model model) {
			HashSet<AddTask> p = new HashSet<AddTask>();
			p.addAll(taskServiceImpl.findTaskByCompanyIdProjectIdMilestoneId(name, name1, name2));
		return p;
		}		
		
		@GetMapping("/getProjectdropdown1")
		public @ResponseBody HashSet<String> viewProjectCodeByProjectName(@RequestParam(name="name",required=false)String name) {
			HashSet<String> p = new HashSet<String>();
			p.addAll(ProjectService.findprojectcodeByProjectName(name));
		return p;
		}
		
		@GetMapping("/getDurationDropDown")
		public @ResponseBody String viewgetDurationDropDown(@RequestParam(name="name",required=false)String name, HttpServletRequest request) {
		return (timesheetService.selectRemainingDuration(name,employeeService.Update(request.getUserPrincipal().getName())));
		}
	
}