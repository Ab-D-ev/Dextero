package com.example.springboot.TimeSheet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.springboot.CompanyProfile.CompanyDetailsService;
import com.example.springboot.EmployeesDetails.Employee;
import com.example.springboot.EmployeesDetails.EmployeeService;
import com.example.springboot.Login.EmailService;
import com.example.springboot.Login.Mail;

@Component
public class TimeSheetSchedular {

	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private TimeSheetService timesheetService;
	@Autowired
	private TimeSheetMailReportService MailReportService;
	@Autowired
	private EmailService emailService;
	@Autowired
	private CompanyDetailsService companyDetailsService;
	@Autowired
	private TimeSheetReportShiftedTableService timeSheetReportShiftedTableService;
	@Autowired
	private TimeSheetReportService timeSheetReportService;
	
	@Value("${spring.mail.username}") 
	private String mail_username;
	
//	@Scheduled(cron = "0 21 14 * * ?")
	public void daily_update() throws IOException, NullPointerException, Exception{
		/////// daily update data //////////
		List<TimeSheetMailReport> employee1 = MailReportService.getMonthData();
		for(TimeSheetMailReport e :employee1) {
			int count = timesheetService.CountByUserName(e.getEmployee_id(), e.getSdate());
			if(count == 0) {
				e.setCompletionstatus("Not Filled");
				MailReportService.save(e);
			}
			else {
				String remaining = timesheetService.getRemainingDuration(e.getSdate(),e.getEmployee_id());
				int r1 = Integer.parseInt(String.valueOf(remaining.charAt(0)));
					if(r1 > 0) {
						e.setCompletionstatus("Partial Filled");
						MailReportService.save(e);
	
					}
			
					else {
						e.setCompletionstatus("Filled");
						MailReportService.save(e);
					}
				}
			}
		}
	
	
//	@Scheduled(cron = "0 29 16 * * ?") // 4.29pm
//	@Scheduled(fixedDelay = 60*60)
//	@Scheduled(cron = "0 0 10 ? * MON-SAT") //morning 10am 
	public void new_data() throws IOException, NullPointerException, Exception{
		/////// daily new data //////////
		LocalDate mydate1 = LocalDate.now();
//		for (int i = 0; i < 120; i++) { // Loop through the last 100 days
//	    LocalDate previousDate = LocalDate.now().minusDays(i);
		LocalDate previousDate =  mydate1.minusDays(1);
		DateTimeFormatter myformatter1 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		List<Employee> employee = employeeService.getEmployeeList();
		for(Employee e :employee) {
				int count = timesheetService.CountByUserName(e.getId(), previousDate.format(myformatter1));
				if(count == 0) {
					TimeSheetMailReport report = new TimeSheetMailReport();
					report.setSdate(previousDate.format(myformatter1));
					report.setEmployee_id(e.getId());
					report.setCompany_id(e.getCompany_id());
					SimpleDateFormat s1 = new SimpleDateFormat("yyyy-MM-dd");
			    	SimpleDateFormat s2 = new SimpleDateFormat("dd-MM-yyyy");
			    	Date d1 = s2.parse(report.getSdate());
					report.setDateyear(s1.format(d1));
					report.setCompletionstatus("Not Filled");
					MailReportService.save(report);
					
				}
			else {
				String remaining = timesheetService.getRemainingDuration(previousDate.format(myformatter1),e.getId());
				int r1 = 0;
				r1 = Integer.parseInt(String.valueOf(remaining.charAt(0)));
					if(r1 > 0) {
						TimeSheetMailReport report = new TimeSheetMailReport();
						report.setSdate(previousDate.format(myformatter1));
						report.setEmployee_id(e.getId());
						report.setCompany_id(e.getCompany_id());
						SimpleDateFormat s1 = new SimpleDateFormat("yyyy-MM-dd");
				    	SimpleDateFormat s2 = new SimpleDateFormat("dd-MM-yyyy");
				    	Date d1 = s2.parse(report.getSdate());
						report.setDateyear(s1.format(d1));
						report.setCompletionstatus("Partial Filled");
						MailReportService.save(report);
						
					}
					else {
						TimeSheetMailReport report = new TimeSheetMailReport();
						report.setSdate(previousDate.format(myformatter1));
						report.setEmployee_id(e.getId());
						report.setCompany_id(e.getCompany_id());
						SimpleDateFormat s1 = new SimpleDateFormat("yyyy-MM-dd");
				    	SimpleDateFormat s2 = new SimpleDateFormat("dd-MM-yyyy");
				    	Date d1 = s2.parse(report.getSdate());
						report.setDateyear(s1.format(d1));
						report.setCompletionstatus("Filled");
						MailReportService.save(report);
					}
					
				}
			}
	
		}
	
	////////////////////////////////////// send Mail /////////////////
	
//	@Scheduled(cron = "0 20 14 * * ?")
//	@Scheduled(fixedDelay = 60*60)
//	@Scheduled(cron = "0 0 11 ? * MON-SAT")
	public void SendMail() throws IOException, NullPointerException, Exception {
	    List<Employee> employees = employeeService.getAllEmployees();
	    for (Employee e : employees) {
	        if (e.isEmail_status()==true) { // Check if email_status is active
	            int mcount = MailReportService.CountByEmployeeName(e.getId());
	            if (mcount > 0) {
	                // Check the completionstatus of the associated TimeSheetMailReports
	                List<TimeSheetMailReport> timeSheetReports = MailReportService.getListByEmployeeName(e.getId());
	                boolean sendEmail = false; // Flag to determine if email should be sent

	                for (TimeSheetMailReport report : timeSheetReports) {
	                    if (report.getCompletionstatus().equals("Not Filled") || report.getCompletionstatus().equals("Partial Filled")) {
	                        sendEmail = true;
	                        break; // No need to check further, send email if at least one report is partial or not filled
	                    }
	                }

	                if (sendEmail) {
	                    Mail mail = new Mail();
	                    mail.setFrom(mail_username);
	                    mail.setMailTo(e.getEmail());
	                    mail.setSubject("TimeSheet Report");
	                    Map<String, Object> model1 = new HashMap<String, Object>();
	                    model1.put("companyName", companyDetailsService.getCompanyNameByID(e.getCompany_id()));
	                    model1.put("ename", e.getFirstname() + ' ' + e.getLastname());
	                    model1.put("ReportList", timeSheetReports);
	                    model1.put("EmployeeNameList", employeeService.getAllEmployees()); // You may need to modify this to get only the names
	                    mail.setProps(model1);
	                    emailService.sendEmail(mail);
	                }
	            }
	        }
	    }
	}

////////////////////////////////////////////////// shift data in another table ////////////////////////////////////////////////////////
//	@Scheduled(cron = "0 53 11 * * ?")
	public void dailyShiftData() {
	    // Fetch data from TimeSheetReport table
	    List<TimeSheetReport> timeReportList = timeSheetReportService.listAll();

	    for (TimeSheetReport timeSheetReport : timeReportList) {
	        Integer id = timeSheetReport.getId();

	        // Check if the record exists in TimeSheetReportShiftedTable
	        if (timeSheetReportShiftedTableService.getExistingId(id) == 0) {

	            // If the record doesn't exist in TimeSheetReportShiftedTable, add it
	            TimeSheetReportShiftedTable shiftedRecord = new TimeSheetReportShiftedTable();
	            shiftedRecord.setId(id);
	            shiftedRecord.setWorkedhours(timeSheetReport.getWorkedhours());
	            shiftedRecord.setCompany_id(timeSheetReport.getCompany_id());
	            shiftedRecord.setEmployee_id(timeSheetReport.getEmployee_id());
	            shiftedRecord.setEndtime(timeSheetReport.getEndtime());
	            shiftedRecord.setHours(timeSheetReport.getHours());
	            shiftedRecord.setMinutes(timeSheetReport.getMinutes());
	            shiftedRecord.setOvertime(timeSheetReport.getOvertime());
	            shiftedRecord.setRemaininghours(timeSheetReport.getRemaininghours());
	            shiftedRecord.setStartdate(timeSheetReport.getStartdate());
	            shiftedRecord.setStartDateYear(timeSheetReport.getStartDateYear());
	            shiftedRecord.setStarttime(timeSheetReport.getStarttime());
	            timeSheetReportShiftedTableService.Save(shiftedRecord);

	            // Delete the record from TimeSheetReport table
	            timeSheetReportService.deleteTimeSheetReport(id);
	        }
	    }
	}

}
