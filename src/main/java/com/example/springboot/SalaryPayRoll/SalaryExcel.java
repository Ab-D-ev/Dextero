package com.example.springboot.SalaryPayRoll;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.example.springboot.EmployeesDetails.EmployeeService;


public class SalaryExcel{

	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private List<EmployeeSalary> list;
	private EmployeeSalary eSalary;
	private EmployeeService employeeService;
	
	public SalaryExcel(List<EmployeeSalary> list,EmployeeSalary eSalary , EmployeeService employeeService) {
		 this.list=list;
		 this.eSalary=eSalary;
		 this.employeeService = employeeService; // Inject the EmployeeService
		 workbook = new XSSFWorkbook();
		
	}
	
	private void writeHeaderLine() {
		sheet=workbook.createSheet("Salary Slip");
		Row row=sheet.createRow(0);
		
		CellStyle style=workbook.createCellStyle();
		XSSFFont font=workbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		style.setFont(font);
			
			createCell(row,0,"Employee Id",style);
		 	createCell(row, 1, "Employee Name", style); 
			createCell(row,2,"FromDate",style);
		 	createCell(row, 3, "ToDate", style); 
		 	createCell(row, 4, "Total Days", style); 
		 	createCell(row, 5, "Number Of Sundays", style);
		 	createCell(row, 6, "Total Working Days", style);
		 	createCell(row, 7, "Number Of Leaves", style);
		 	createCell(row, 8, "Basic Salary", style);
		 	createCell(row, 9, "DA", style);
		 	createCell(row, 10, "HRA", style);
		 	createCell(row, 11, "Travel Allowance", style);
		 	createCell(row, 12, "Special Allowance", style);
		 	createCell(row, 13, "Project Allowance", style);
		 	createCell(row, 14, "Project Travel", style);
		 	createCell(row, 15, "Conveyance Allowance", style);
		 	createCell(row, 16, "Medical Allowance", style);
		 	createCell(row, 17, "Dearness Allowance", style);
		 	createCell(row, 18, "Other Allowance", style);
		 	createCell(row, 19, "GrossSalary", style);
		 	createCell(row, 20, "Employee PF", style);
		 	createCell(row, 21, "Employee Professional Tax", style);
		 	createCell(row, 22, "Employee TDS", style);
		 	createCell(row, 23, "Employee ESI", style);
		 	createCell(row, 24, "Employee Gratuity", style);
		 	createCell(row, 25, "Employee_Loan_Recovery", style);
		 	createCell(row, 26, "Total Deduction", style);
		 	createCell(row, 27, "NetSalary", style);
		 	
		
	    }

		private void createCell(Row row, int columnCount, Object value, CellStyle style) {
			sheet.autoSizeColumn(columnCount);
	        Cell cell = row.createCell(columnCount);
	        if (value instanceof Integer) {
	            cell.setCellValue((Integer) value);
	        } else if (value instanceof Boolean) {
	            cell.setCellValue((Boolean) value);
	        }else {
	            cell.setCellValue((String) value);
	        }
	        cell.setCellStyle(style);
	    }
			
		 private void writeDataLines() {
		        int rowCount = 1;
		 
		        CellStyle style = workbook.createCellStyle();
		        XSSFFont font = workbook.createFont();
		        font.setFontHeight(14);
		        style.setFont(font);
		                 
		        for (EmployeeSalary emp : list) {
		            Row row = sheet.createRow(rowCount++);
		            int columnCount = 0;
		             
		            String employeeName = employeeService.getEmployeeNameByID(emp.getEmployeeId());
		         // Remove commas from the employee name
		            employeeName = employeeName.replace(",", " "); // This removes commas

		            createCell(row, columnCount++,emp.getEmployeeId(), style);
		            createCell(row, columnCount++, employeeName , style);
		            createCell(row, columnCount++,emp.getFromDate(), style);
		            createCell(row, columnCount++,emp.getToDate(), style);
		            createCell(row, columnCount++,emp.getNumberOfDaysInMonth(), style);
		            createCell(row, columnCount++,emp.getNumberOfSundays(), style);
		            createCell(row, columnCount++,emp.getWorkingDays(), style);
		            createCell(row, columnCount++,emp.getUnpaidLeaves(), style);
		            createCell(row, columnCount++,emp.getBasic(), style);
		            createCell(row, columnCount++,emp.getDa(), style);
		            createCell(row, columnCount++,emp.getHra(), style);
		            createCell(row, columnCount++,emp.getTravel_allowance(), style);
		            createCell(row, columnCount++,emp.getSpecial_allowance(), style);
		            createCell(row, columnCount++,emp.getProject_allowance(), style);
		            createCell(row, columnCount++,emp.getProject_travel(), style);
		            createCell(row, columnCount++,emp.getConveyance_allowance(), style);
		            createCell(row, columnCount++,emp.getMedical_allowance(), style);
		            createCell(row, columnCount++,emp.getDearness_allowance(), style);
		            createCell(row, columnCount++,emp.getOther_allowance(), style);
		            createCell(row, columnCount++,emp.getGrossSalary(), style);
		            createCell(row, columnCount++,emp.getEmployee_PF(), style);
		            createCell(row, columnCount++,emp.getEmployee_Professional_Tax(), style);
		            createCell(row, columnCount++,emp.getEmployee_TDS(), style);
		            createCell(row, columnCount++,emp.getEmployee_ESI(), style);
		            createCell(row, columnCount++,emp.getEmployee_Gratuity(), style);
		            createCell(row, columnCount++,emp.getEmployee_Loan_Recovery(), style);
		            createCell(row, columnCount++,emp.getTotalDeduction(), style);
		            createCell(row, columnCount++,emp.getNetSalary(), style);
		            
		        }
		    }
		     
		    public void export(HttpServletResponse response) throws IOException {
		        writeHeaderLine();
		        writeDataLines();
		         
		        ServletOutputStream outputStream = response.getOutputStream();
		        workbook.write(outputStream);
		        workbook.close();
		        outputStream.close();
		         
		    }
}
