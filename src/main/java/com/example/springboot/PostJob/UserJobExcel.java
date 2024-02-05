package com.example.springboot.PostJob;

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
import org.springframework.beans.factory.annotation.Autowired;

import com.example.springboot.EmployeesDetails.Employee;
import com.example.springboot.MainMaster.PostName;
import com.example.springboot.MainMaster.PostNameService;

public class UserJobExcel {

	
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private List<Employee> list;
	private JobPost jobPosts;
	private PostNameService postNameService; 
	
	public UserJobExcel(List<Employee> list,JobPost jobPosts, PostNameService postName) {
		 this.list=list;
		 this.jobPosts=jobPosts;
		 this.postNameService=postName;
		 workbook = new XSSFWorkbook();
		
	}
	
    
	private void writeHeaderLine() {
		sheet=workbook.createSheet("Employee List");
		Row row=sheet.createRow(0);
		
		CellStyle style=workbook.createCellStyle();
		XSSFFont font=workbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		style.setFont(font);
			
			createCell(row,0,"Job Id",style);
		 	createCell(row, 1, "Job Title", style); 
			createCell(row,2,"Employee Id",style);
		 	createCell(row, 3, "Full Name", style); 
		 	createCell(row, 4, "Email Id", style); 
		 	createCell(row, 5, "Gender", style);
		 	createCell(row, 6, "Marital Status", style);
		 	createCell(row, 7, "Date of Birth", style);
		 	createCell(row, 8, "Date of Joining", style);
		 	createCell(row, 9, "Contact Number-1", style);
		 	createCell(row, 10, "Contact Number-2", style);
		 	
		
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
		                 
		        for (Employee emp : list) {
		            Row row = sheet.createRow(rowCount++);
		            int columnCount = 0;
		            int designation = jobPosts.getDesignation();
		            String postName = postNameService.getPostNmae(designation);
		            
		            createCell(row, columnCount++,jobPosts.getId(), style);
		            createCell(row, columnCount++, postName, style);
		            createCell(row, columnCount++,emp.getId(), style);
		            createCell(row, columnCount++,emp.getFirstname() + ' ' + emp.getMiddlename() + ' ' + emp.getLastname(), style);
		            createCell(row, columnCount++,emp.getEmail(), style);
		            createCell(row, columnCount++,emp.getGender(), style);
		            createCell(row, columnCount++,emp.getMaritalStatus(), style);
		            createCell(row, columnCount++,emp.getDateofBirth(), style);
		            createCell(row, columnCount++,emp.getJoiningdate(), style);
		            createCell(row, columnCount++,emp.getMobileNumber1(), style);
		            createCell(row, columnCount++,emp.getMobileNumber2(), style);
		           
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
