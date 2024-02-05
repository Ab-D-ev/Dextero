package com.example.springboot.ExcelDataUpload;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import com.example.springboot.Login.LoginUser;

public class LoginExcelHelper {
	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	static String[] HEADERs = {"UserName"};
	static String SHEET = "Employee_Details";
	
	public static boolean hasExcelFormat(MultipartFile file) {

	    if (!TYPE.equals(file.getContentType())) {
	      return false;
	    }

	    return true;
	  }

	  public static List<LoginUser> excelToTutorials(InputStream is) {
	    try {
	      XSSFWorkbook workbook = new XSSFWorkbook(is);
	      XSSFSheet  sheet = workbook.getSheet(SHEET);
	      System.out.println("Sheet : " +sheet);
	      
	      System.out.println("Iterator : " + sheet.rowIterator());
	      Iterator<Row> rows = sheet.rowIterator();
	      System.out.println(rows);

	      List<LoginUser> tutorials = new ArrayList<LoginUser>();

	      int rowNumber = 0;
	      while (rows.hasNext()) {
	        Row currentRow = rows.next();

	        // skip header
	        if (rowNumber == 0) {
	          rowNumber++;
	          continue;
	        }

	        Iterator<Cell> cellsInRow = currentRow.iterator();

	        LoginUser e = new LoginUser();
//	        List<Product> p = new ArrayList<Product>();

	        int cellIdx = 0;
	        while (cellsInRow.hasNext()) {
	          Cell currentCell = cellsInRow.next();
	        	  
	          switch (cellIdx) {
	          		case 0:
		        		System.out.println(" case 0");
			            break;
			            
	          		case 1:
		        	    System.out.println(" case 1");
		        	    break;
		            
	          		case 2:
			        	  BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(4);	
			        	  e.setPassword(bCryptPasswordEncoder.encode("123456"));
				          e.setUsername(currentCell.getStringCellValue());
				          break;
		          
		          default:
		            break;
		          }
	
		          cellIdx++;
		        }

			tutorials.add(e);
	      }

	      workbook.close();
	      System.out.println("end : " + tutorials);
	      
	      return tutorials;
	      
	    } catch (IOException e1) {
	      throw new RuntimeException("fail to parse Excel file: " + e1.getMessage());
	    }
	  }
}
