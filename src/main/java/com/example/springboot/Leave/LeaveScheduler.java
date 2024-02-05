package com.example.springboot.Leave;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.springboot.EmployeesDetails.Employee;
import com.example.springboot.EmployeesDetails.EmployeeService;

@Component
public class LeaveScheduler {

	@Autowired
    private LeaveBalanceService leaveBalanceService;
	@Autowired
    private LeaveService leaveService;
	@Autowired
    private EmployeeService employeeService;
	@Autowired
	private LeavePolicyService leavePolicyService;
	
	// Scheduled task to update remaining paid leaves for each year
	@Scheduled(cron = "0 0 0 1 1 *") // Runs on the first day of each year at midnight
//	@Scheduled(cron = "0 54 15 * * ?") //every 2 mins
	public void updateRemainingPaidLeavesYear() throws ParseException {
		LocalDateTime mydate = LocalDateTime.now();
	    DateTimeFormatter myformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	
	    LocalDate currentDate = LocalDate.now();
	    int previousYear = Year.from(currentDate).minusYears(1).getValue();
	    // Get the list of employees
	    List<Employee> employees = employeeService.getAllEmployees();

	    // Iterate over each employee
	    for (Employee employee : employees) {
	        // Get the leave policy for the current year and company
	        LeavePolicy leavePolicy = leavePolicyService.findLeavePolicyByYearAndCompanyId(previousYear, employee.getCompany_id());

	        // Check if the LeaveBalance entry already exists for the employee and current year
	        LeaveBalance leaveBalance = leaveBalanceService.findByEmployeeIDAndYear(employee.getId(), previousYear);
	        if (leaveBalance == null) {
	            // Create a new LeaveBalance entry for the employee and current year
	            leaveBalance = new LeaveBalance();
	            leaveBalance.setEmployee_id(employee.getId());
	            leaveBalance.setCompany_id(employee.getCompany_id());
	            leaveBalance.setCurrentdate(mydate.format(myformatter));
	            SimpleDateFormat myformatter1 = new SimpleDateFormat("yyyy-MM-dd");
	        	SimpleDateFormat myformatter2 = new SimpleDateFormat("dd-MM-yyyy");
	        	Date d1 = myformatter2.parse(leaveBalance.getCurrentdate());
	        	leaveBalance.setCurrent_date_year(myformatter1.format(d1));
	        }

	        // Calculate the carried forward leave count
	        int totalPaidLeavesTaken = leaveService.findLeaveEmployeeIDYear(previousYear, employee.getId());
	        int carriedForwardLeaves = leavePolicy.getPaidLeave() - totalPaidLeavesTaken;

	        // Set the carried forward leave count
	        leaveBalance.setCarried_forward_leave(carriedForwardLeaves);

	        // Save the LeaveBalance entry
	        leaveBalanceService.Save(leaveBalance);
	    }
	}

	// Scheduled task to update remaining paid leaves for each month
	@Scheduled(cron = "0 0 0 1 * ?") // Runs on the first day of each month at midnight
//	@Scheduled(cron = "0 54 15 * * ?")
	public void updateRemainingPaidLeavesMonth() {
	    // Get the list of employees' leave balances
	    List<LeaveBalance> leaveBalances = leaveBalanceService.listAll();

	    for (LeaveBalance leaveBalance : leaveBalances) {
	    	// Get the current date
	    	LocalDate currentDate = LocalDate.now();
	    	// Calculate the previous month
	    	YearMonth previousMonth = YearMonth.from(currentDate).minusMonths(1);
	    	// Get the Month value of the previous month
	    	int previousMonthValue = previousMonth.getMonthValue();
	        // Get the count of paid leaves taken by the employee in the current month
	        int paidLeaveCount = leaveService.getEmployeeLeaveMonthEmpID(previousMonthValue, leaveBalance.getEmployee_id());

	        if (paidLeaveCount == 0) {
	            // Increment the carried forward leave count by 1
	            leaveBalance.setCarried_forward_leave(leaveBalance.getCarried_forward_leave() + 1);
	        } else {
	            // Set the carried forward leave count to 0
	            leaveBalance.setCarried_forward_leave(leaveBalance.getCarried_forward_leave());
	        }

	        leaveBalanceService.Save(leaveBalance);
	    }
	}
}