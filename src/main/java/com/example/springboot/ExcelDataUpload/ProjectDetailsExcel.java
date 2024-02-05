package com.example.springboot.ExcelDataUpload;

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

import com.example.springboot.AddProjectCode.projectName;

public class ProjectDetailsExcel { 
	
	 private XSSFWorkbook workbook;
	 private XSSFSheet sheet;
	 private List<projectName> projectlist;
	 
	  public ProjectDetailsExcel(List<projectName> projectlist) {
	        this.projectlist = projectlist;
	        workbook = new XSSFWorkbook();
	    }
	 
	    private void writeHeaderLine() {
	        sheet = workbook.createSheet("Project Report");
	        
	        Row row = sheet.createRow(0);
	         
	        CellStyle style = workbook.createCellStyle();
	        XSSFFont font = workbook.createFont();
	        font.setBold(true);
	        font.setFontHeight(16);
	        style.setFont(font);
	         
	        createCell(row, 0, "Id", style); 
	        createCell(row, 1, "Company Name", style);   
	        createCell(row, 2, "Category", style);
	        createCell(row, 3, "Project Name", style);
	        createCell(row, 4, "Project Code", style);
	        createCell(row, 5, "UserName", style);
	         
	    }

	    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
	        sheet.autoSizeColumn(columnCount);
	        Cell cell = row.createCell(columnCount);
	        
	        if (value instanceof Integer) {
	            cell.setCellValue((Integer) value);
	        } else if (value instanceof Long) {
	            cell.setCellValue((Long) value);
	        } else if (value instanceof Boolean) {
	            cell.setCellValue((Boolean) value);
	        } else {
	            cell.setCellValue(value.toString());
	        }
	        
	        cell.setCellStyle(style);
	    }
			
		 private void writeDataLines() {
		        int rowCount = 1;
		 
		        CellStyle style = workbook.createCellStyle();
		        XSSFFont font = workbook.createFont();
		        font.setFontHeight(14);
		        style.setFont(font);
		                 
		        for (projectName project : projectlist) {
		            Row row = sheet.createRow(rowCount++);
		            int columnCount = 0;
		             
		            createCell(row, columnCount++, project.getId(), style);
		            createCell(row, columnCount++, project.getCompany_id(), style);
		            createCell(row, columnCount++, project.getCategoryName(), style);
		            createCell(row, columnCount++, project.getProjectName(), style);
		            createCell(row, columnCount++, project.getProjectcode(), style);
		            
		            
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
	