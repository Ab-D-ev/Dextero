package com.example.springboot.SalaryPayRoll;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.aspectj.weaver.ast.And;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.springboot.EmployeesDetails.Employee;
import com.example.springboot.EmployeesDetails.EmployeeService;
import com.example.springboot.Leave.Leave;
import com.example.springboot.Leave.LeaveService;
import com.example.springboot.TimeSheet.TimeSheetReport;
import com.example.springboot.TimeSheet.TimeSheetReportService;

@Component
public class SalarySchedular {
	
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private TimeSheetReportService timeSheetReportService;
	@Autowired
	private LeaveService leaveService;
	@Autowired
	private EmployeeSalaryService employeeSalaryService;
	@Autowired
	private EmployeeGrossPayService employeeGrossPayService;
	
//	@Scheduled(cron = "0 0 6 21 * ?")  cron expression to trigger a function on 21th of every month at 6 A.M in the morning?

	
	@Scheduled(cron = "0 0 6 21 * ?")
//	@Scheduled(fixedDelay = 60*60)
	public void run() throws IOException, NullPointerException, Exception{
		LocalDate mydate = LocalDate.now();
		DateTimeFormatter myformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		List<Employee> employee = employeeService.getAllEmployees();
		for(Employee e :employee) {
			
			List<EmployeeGrossPay> g = employeeGrossPayService.getEmployeeNameByID(e.getId());
			for(EmployeeGrossPay eGrossPay : g) {
				
				EmployeeSalary eSalary = new EmployeeSalary();
				eSalary.setEmployeeId(e.getId());
				eSalary.setUser_id(e.getId());
				eSalary.setBasic(String.valueOf(eGrossPay.getBasic()));
				eSalary.setDa(String.valueOf(eGrossPay.getDa()));
				eSalary.setHra(String.valueOf(eGrossPay.getHra()));
				eSalary.setTravel_allowance(String.valueOf(eGrossPay.getTravel_allowance()));
				eSalary.setConveyance_allowance(String.valueOf(eGrossPay.getConveyance_allowance()));
				eSalary.setDearness_allowance(String.valueOf(eGrossPay.getDearness_allowance()));
				eSalary.setMedical_allowance(String.valueOf(eGrossPay.getMedical_allowance()));
				eSalary.setOther_allowance(String.valueOf(eGrossPay.getOther_allowance()));
				eSalary.setProject_allowance(String.valueOf(eGrossPay.getProject_allowance()));
				eSalary.setProject_travel(String.valueOf(eGrossPay.getProject_travel()));
				eSalary.setSpecial_allowance(String.valueOf(eGrossPay.getSpecial_allowance()));
				eSalary.setCurrentdate(mydate.format(myformatter));
				eSalary.setGrossSalary(String.valueOf(eGrossPay.getGrossSalary()));
				
				int month2 = mydate.getMonthValue();
				if(eGrossPay.getEmployee_Professional_Tax() > 10000 && month2 == 2) {
					eSalary.setEmployee_Professional_Tax(String.valueOf(eGrossPay.getEmployee_Professional_Tax()) + 100);
				}
				else {
					eSalary.setEmployee_Professional_Tax(String.valueOf(eGrossPay.getEmployee_Professional_Tax()));
				}
				eSalary.setEmployee_PF(String.valueOf(eGrossPay.getEmployee_PF()));
				eSalary.setEmployee_ESI(String.valueOf(eGrossPay.getEmployee_ESI()));
				eSalary.setEmployee_TDS(String.valueOf(eGrossPay.getEmployee_TDS()));
				eSalary.setEmployee_Gratuity(String.valueOf(eGrossPay.getEmployee_Gratuity()));
				eSalary.setEmployee_Loan_Recovery(String.valueOf(eGrossPay.getEmployee_Loan_Recovery()));
				eSalary.setTotalDeduction(String.valueOf(eGrossPay.getTotal_Deduction()));
				eSalary.setCompany_id(eGrossPay.getCompany_id());
				LocalDateTime mydate1 = LocalDateTime.now();
				int year = mydate1.getYear();
				int month = mydate1.getMonthValue();
					
				if(eSalary.getWorkingDays()==null || eSalary.getUnpaidLeaves()==null ||eSalary.getPaidLeaves()==null) {
					eSalary.setWorkingDays("0");
					eSalary.setUnpaidLeaves("0");
					eSalary.setPaidLeaves("0");
					
					if(month <=9) {
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
						List<Leave> l1 = leaveService.getAllLeaves();
						for(Leave l2 :l1) {
						 List<Leave> myleaves = leaveService.countNumberOfPaidOrSickLeave(l2.getLeavecategory_id(),e.getId());
						
						int npaidleaves=0;
						int sundaysOnFirstDayOfMonth = 0;
						long days1=0;
						LocalDate FromDate = LocalDate.parse(year + "-0" +(month - 1) + "-" + "21");
						LocalDate ToDate = LocalDate.parse( year + "-0" + month + "-" + "20");
						for(Leave l:myleaves) {
							LocalDate date1 =LocalDate.parse(l.getFromDateYear(),formatter);
							if(date1.compareTo(FromDate) >=0 && date1.compareTo(ToDate)<=0) {
								npaidleaves = npaidleaves + (l.getnDays());
								System.out.println("Fromdate : " + FromDate + "TOdate :" + npaidleaves + "Date1 1 : " + date1 );
							}
						}
						
						LocalDate currentDay = FromDate;				            
						while (currentDay.isBefore(ToDate) || currentDay.isEqual(ToDate)) {
							if (currentDay.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
								sundaysOnFirstDayOfMonth++;
							}
				                currentDay = currentDay.plusDays(1);
						}
						
						days1 = ChronoUnit.DAYS.between(FromDate, ToDate);
						System.out.println("No.of Sundays : " + sundaysOnFirstDayOfMonth);
						eSalary.setPaidLeaves(String.valueOf(npaidleaves));
						eSalary.setNumberOfSundays(String.valueOf(sundaysOnFirstDayOfMonth));
						eSalary.setNumberOfDaysInMonth(String.valueOf(days1));
						System.out.println("No.of Days in Month : " + days1);
						eSalary.setFromDate(String.valueOf(FromDate));
						eSalary.setToDate(String.valueOf(ToDate));
							
							List<TimeSheetReport> timeSheets = timeSheetReportService.countTotalWorkingDays(e.getId());
							int workingDays=0;
								for(TimeSheetReport t1:timeSheets) {
									System.out.println("FromDate Time  : " + FromDate  + "ToDate : " + ToDate);
									LocalDate date2 =LocalDate.parse(t1.getStartDateYear(),formatter);
									System.out.println("date2  : " + date2);
										if(date2.compareTo(FromDate) >=0 && date2.compareTo(ToDate)<=0) {
											workingDays++;
											System.out.println("workingDays : " + workingDays);
										}
								}
								Integer totalWorkingDays = 0;
								totalWorkingDays = npaidleaves + sundaysOnFirstDayOfMonth + workingDays;
								eSalary.setWorkingDays(String.valueOf(totalWorkingDays));
								int unpaidLeaves = 0;
								unpaidLeaves = (int)days1 - totalWorkingDays;
								eSalary.setUnpaidLeaves(String.valueOf(unpaidLeaves));
								System.out.println("totalWorkingDays : " + totalWorkingDays);
					  	}
					}
					else {
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
						List<Leave> l1 = leaveService.getAllLeaves();
						for(Leave l2 :l1) {
						 List<Leave> myleaves = leaveService.countNumberOfPaidOrSickLeave(l2.getLeavecategory_id(),e.getId());
						int npaidleaves=0;
						int sundaysOnFirstDayOfMonth=0;
						long days1=0;
						LocalDate FromDate = LocalDate.parse(year + (month - 1) + "-" + "21");
						LocalDate ToDate = LocalDate.parse( year + month + "-" + "20");
						for(Leave l:myleaves) {
							LocalDate date1 =LocalDate.parse(l.getFromDateYear(),formatter);
							if(date1.compareTo(FromDate) >=0 && date1.compareTo(ToDate)<=0) {
								npaidleaves = npaidleaves+(l.getnDays());
								System.out.println("Fromdate : " + FromDate + "TOdate :" + npaidleaves + "Date1 1 : " + date1 );
							}
						}
						LocalDate currentDay = FromDate;				            
						while (currentDay.isBefore(ToDate) || currentDay.isEqual(ToDate)) {
							if (currentDay.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
								sundaysOnFirstDayOfMonth++;
							}
				                currentDay = currentDay.plusDays(1L);
						}
						days1 = ChronoUnit.DAYS.between(FromDate, ToDate);
						System.out.println("No. Of Sundays : " + sundaysOnFirstDayOfMonth);
						eSalary.setPaidLeaves(String.valueOf(npaidleaves));
						eSalary.setNumberOfSundays(String.valueOf(sundaysOnFirstDayOfMonth));
						eSalary.setNumberOfDaysInMonth(String.valueOf(days1));
						System.out.println("No.of Days in Month : " + days1);
						eSalary.setFromDate(String.valueOf(FromDate));
						eSalary.setToDate(String.valueOf(ToDate));
							
							List<TimeSheetReport> timeSheets = timeSheetReportService.countTotalWorkingDays(e.getId());
							int workingDays=0;
								for(TimeSheetReport t1:timeSheets) {
									System.out.println("FromDate Time  : " + FromDate  + "ToDate : " + ToDate);
									LocalDate date2 =LocalDate.parse(t1.getStartDateYear(),formatter);
									System.out.println("date2  : " + date2);
										if(date2.compareTo(FromDate) >=0 && date2.compareTo(ToDate)<=0) {
											workingDays++;
											System.out.println("workingDays : " + workingDays);
										}
								}
								Integer totalWorkingDays = 0;
								totalWorkingDays = npaidleaves + sundaysOnFirstDayOfMonth + workingDays;	
								eSalary.setWorkingDays(String.valueOf(totalWorkingDays));
								int unpaidLeaves = 0;
								unpaidLeaves = (int)days1 - totalWorkingDays;
								eSalary.setUnpaidLeaves(String.valueOf(unpaidLeaves));
								System.out.println("totalWorkingDays : " + totalWorkingDays);
					  	}
					}
				}
				
					double totalSalary=0;
					String numberOfDaysStr = eSalary.getNumberOfDaysInMonth();
					int numberOfDaysInMonth = 0;

					if (numberOfDaysStr != null && !numberOfDaysStr.isEmpty()) {
					    numberOfDaysInMonth = Integer.parseInt(numberOfDaysStr);
					}

					double oneDaySalary = 0.0;

					if (numberOfDaysInMonth != 0) {
					    oneDaySalary = Double.parseDouble(eSalary.getGrossSalary()) / numberOfDaysInMonth;
					}
//					double oneDaySalary = Double.parseDouble(eSalary.getGrossSalary()) / Integer.parseInt(eSalary.getNumberOfDaysInMonth());
					System.out.println("oneDaySalary : " + oneDaySalary);
					System.out.println("WorkingDays : " + eSalary.getWorkingDays());
					totalSalary = (oneDaySalary * Integer.parseInt(eSalary.getWorkingDays())) - Double.parseDouble(eSalary.getTotalDeduction());
					eSalary.setNetSalary(String.valueOf(totalSalary));
					System.out.println("totalSalary : " + totalSalary);
					employeeSalaryService.Save(eSalary);
				}
				
			}
		}
}

