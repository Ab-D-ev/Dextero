package com.example.springboot.ExcelDataUpload;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.example.springboot.EmployeesDetails.Employee;
import com.example.springboot.EmployeesDetails.EmployeeService;
import com.example.springboot.Login.LoginUser;
import com.example.springboot.Login.LoginUserAuthority;
import com.example.springboot.Login.LoginUserAuthorityService;
import com.example.springboot.Login.LoginUserService;

@Component
public class ExcelHelper {
	
//	@Autowired
//	private EmployeeService employeeService;
//	@Autowired
//	private LoginUserAuthorityService loginuserauthorityService;
//	@Autowired
//	private LoginUserService loginuserService;
//	
//	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
//	static String[] HEADERs = { "First Name", "Middle Name", "Last Name", "Email ID", "Gender", "Age", "DateofBirth", "Address line1", "Address line2",
//								"Country","State", "City","Pin Code", "Mobile Number", "Other Mobile Number", "Telephone Number", "Other Telephone Number", 
//								"Joining Date", "Skill Set", "Blood Group", "Marital Status", "University", "Degree", "Course", "Specialization", "Course Type",
//								"Course Duration", "Grading System", "Grade", "Certification Name", "Certification Completion ID", "Certification Url", "Certification Validity",
//								"Account Holder Name", "Bank Name", "Branch", "Account Number", "IFSC Code", "Disability", "Disability Type", "Career Break",
//								"Reason Of Break", "Break Start From", "Break End", "UAN Number", "PF Number", "ESI Number", "Department", "Reporting Manager"};
//	static String SHEET = "Employee_Details";
//	
//	public static boolean hasExcelFormat(MultipartFile file) {
//
//	    if (!TYPE.equals(file.getContentType())) {
//	      return false;
//	    }
//
//	    return true;
//	  }
//
//	  public List<Employee> excelToTutorials(InputStream is) {
//	    try {
//	      XSSFWorkbook workbook = new XSSFWorkbook(is);
//	      XSSFSheet  sheet = workbook.getSheet(SHEET);
//	      System.out.println("Sheet : " +sheet);
//	      System.out.println("Iterator : " + sheet.rowIterator());
//	      Iterator<Row> rows = sheet.iterator();
//	      System.out.println(rows);
//	      
//	      List<Employee> tutorials = new ArrayList<Employee>();
//	        int rowNumber = 0;
//	        while (rows.hasNext()) {
//	          Row currentRow = rows.next();
//	          // skip header
//	          if (rowNumber == 0) {
//	            rowNumber++;
//	            continue;
//	          }
//	          
////	          for (int i = sheet.getLastRowNum(); i >= 1; i--){
////	        	 //Detect if a row is blank
////	        	  if (sheet.getRow()[i-1].isBlank()){
////	        		  //Remove blank rows
////		        		sheet.deleteRow(i);
////		        	}
////	          }
//	          
//	        Iterator<Cell> cellsInRow = currentRow.iterator();
//
//	        Employee e = new Employee();
//
//	        int cellIdx = 0;
//	        String cellIdx1 ="";
//	        while (cellsInRow.hasNext()) {
//	          Cell currentCell = cellsInRow.next();
//	        	  SimpleDateFormat myformatter = new SimpleDateFormat("dd-MM-yyyy");
//	        	  SimpleDateFormat myformatter1 = new SimpleDateFormat("yyyy-MM-dd");
//	        	  SimpleDateFormat myformatter2 = new SimpleDateFormat("yyyy");
//	        	  
//	          switch (cellIdx) {
//		          case 0:
//		        	if(currentCell.getStringCellValue() == "") {
//		        		System.out.println(" case 0");
//		        		e.setFirstname("");
//		        	}
//		        	else {
//			            e.setFirstname(currentCell.getStringCellValue());
//		        	}
//			            break;
//			            
//		          case 1:
//		        	  if(currentCell.getStringCellValue() == "") {
//		        		  System.out.println(" case 1");
//		        		  e.setMiddlename("");
//		        	  }
//		        	  else {
//		        		  e.setMiddlename(currentCell.getStringCellValue());
//		        	  }
//		            break;
//	
//		          case 2:
//		        	  if(currentCell.getStringCellValue() == "") {
//		        		  System.out.println(" case 2");
//		        		  e.setLastname("");
//		        	  }
//		        	  else {
//		        		  e.setLastname(currentCell.getStringCellValue());
//		        	  }
//		            break;
//		            
//		          case 3:
//		        	  if(currentCell.getStringCellValue() == "") {
//		        		  System.out.println(" case 3");
//		        		  e.setEmail("");
//		        	  }
//		        	  else {
//		        		  e.setEmail(currentCell.getStringCellValue());
//		        	  }
//		        	break;
//			            
//		          case 4:
//		        	  if(currentCell.getStringCellValue() == "") {
//		        		  System.out.println(" case 4");
//		        		  e.setGender("");
//		        	  }
//		        	  else {
//		        		  e.setGender(currentCell.getStringCellValue());
//		        	  }
//		        	break;
//			            
//		          case 5:
//		        	  if(cellIdx1 == "") {
//		        		  System.out.println(" case 5");
//		        		  e.setAge(0);
//		        	  }
//		        	  else {
//		        		  e.setAge((int)currentCell.getNumericCellValue());
//		        	  }
//		        	break;
//		        	
//		          case 6:
//		        	  if(cellIdx1 == "") {
//		        		  System.out.println(" case 6");
//		        		  e.setDateofBirth("");
//		        		  e.setBirthyear("");
//		        	  }
//		        	  else {
//			        	e.setDateofBirth(myformatter.format(currentCell.getDateCellValue()));
//			        	e.setBirthyear(myformatter1.format(currentCell.getDateCellValue()));
//		        	  }
//			        break;
//			            
//		          case 7:
//		        	  if(cellIdx1  == "") {
//		        		  System.out.println(" case 7");
//		        		  e.setPresentAddress1("");
//		        	  }
//		        	  else {
//		        		  e.setPresentAddress1(currentCell.getStringCellValue());
//		        	  }
//			        break;
//			            
//		          case 8:
//		        	  if(cellIdx1  == "") {
//		        		  System.out.println(" case 8");
//		        		  e.setPresentAddress2("");
//		        	  }
//		        	  else {
//		        		  e.setPresentAddress2(currentCell.getStringCellValue());
//		        	  }
//			        break;
//			     
//		          case 9:
//		        	  if(cellIdx1  == "") {
//		        		  System.out.println(" case 9");
//		        		  e.setCountry("");
//		        	  }
//		        	  else {
//			        	e.setCountry(currentCell.getStringCellValue());
//		        	  }
//		        	break;
//				        
//		          case 10:
//		        	  if(cellIdx1  == "") {
//		        		  System.out.println(" case 10");
//		        		  e.setState("");
//		        	  }
//		        	  else {
//		        	  	e.setState(currentCell.getStringCellValue());
//		        	  }
//		        	break;
//				        
//		          case 11:
//		        	  if(cellIdx1  == "") {
//		        		  System.out.println(" case 11");
//		        		  e.setCity("");
//		        	  }
//		        	  else {
//		        		  e.setCity(currentCell.getStringCellValue());
//		        	  }
//				    break;
//				        
//		          case 12:
//		        	  if(cellIdx1 == "") {
//		        		  System.out.println(" case 12");
//		        		  e.setPinCode("");
//		        	  }
//		        	  else {
//			        	e.setPinCode(String.valueOf(currentCell.getNumericCellValue()));
//		        	  }
//				     break;
//				        
//		          case 13:
//		        	  if(cellIdx1 == "") {
//		        		  System.out.println(" case 13");
//		        		  e.setMobileNumber1("");
//		        	  }
//		        	  else {
//			        	e.setMobileNumber1(String.valueOf(currentCell.getNumericCellValue()));
//		        	  }
//				    break;
//				        
//		          case 14:
//		        	  if(cellIdx1  == "") {
//		        		  System.out.println(" case 14");
//		        		  e.setMobileNumber2("");
//		        	  }
//		        	  else {
//			        	e.setMobileNumber2(String.valueOf(currentCell.getNumericCellValue()));
//		        	  }
//				        break;
//				        
//		          case 15:
//		        	  if(cellIdx1  == "") {
//		        		  System.out.println(" case 15");
//		        		  e.setTelephoneNumber1("");
//		        	  }
//		        	  else {
//			        	e.setTelephoneNumber1(String.valueOf(currentCell.getNumericCellValue()));
//		        	  }
//				    break;
//				        
//		          case 16:
//		        	  if(cellIdx1  == "") {
//		        		  System.out.println(" case 16");
//		        		  e.setTelephoneNumber2("");
//		        	  }
//		        	  else {
//			        	e.setTelephoneNumber2(String.valueOf(currentCell.getNumericCellValue()));
//		        	  }
//			         break;
//			        	
//		          case 17:
//		        	  if(cellIdx1  == "") {
//		        		  System.out.println(" case 17");
//		        		  e.setJoiningdate("");
//				          e.setJoiningdateYear("");
//		        	  }
//		        	  else {
//		        	  	e.setJoiningdate(myformatter.format(currentCell.getDateCellValue()));
//			        	e.setJoiningdateYear(myformatter1.format(currentCell.getDateCellValue()));
//		        	  }
//				     break;
//				        
//		          case 18:
//		        	  if(cellIdx1  == "") {
//		        		  System.out.println(" case 18");
//		        		  e.setSkillset("");
//		        	  }
//		        	  else {
//			        	e.setSkillset(currentCell.getStringCellValue());
//		        	  }
//				     break;
//				        
//		          case 19:
//		        	  if(cellIdx1  == "") {
//		        		  System.out.println(" case 19");
//		        		  e.setBloodgroup("");
//		        	  }
//		        	  else {
//		        		  e.setBloodgroup(currentCell.getStringCellValue());
//		        	  }
//				     break;
//				        
//		          case 20:
//		        	  if(cellIdx1 == "") {
//		        		  System.out.println(" case 20");
//		        		  e.setMaritalStatus("");
//		        	  }
//		        	  else {
//		        		  e.setMaritalStatus(currentCell.getStringCellValue());
//		        	  }
//				     break;
//				        
//		          case 21:
//		        	  if(cellIdx1  == "") {
//		        		  System.out.println(" case 21");
//		        		  e.setUniversity("");
//		        	  }
//		        	  else {
//		        		  e.setUniversity(currentCell.getStringCellValue());
//		        	  }
//				     break;
//				        
//		          case 22:
//		        	  if(cellIdx1  == "") {
//		        		  System.out.println(" case 22");
//		        		  e.setDegree("");
//		        	  }
//		        	  else {
//		        		  e.setDegree(currentCell.getStringCellValue());
//		        	  }
//				     break;
//				        
//		          case 23:
//		        	  if(cellIdx1  == "") {
//		        		  System.out.println(" case 23");
//		        		  e.setCourse("");
//		        	  }
//		        	  else {
//			        	e.setCourse(currentCell.getStringCellValue());
//		        	  }
//				     break;
//				        
//		          case 24:
//		        	  if(cellIdx1 == "") {
//		        		  System.out.println(" case 24");
//		        		  e.setSpecialization("");
//		        	  }
//		        	  else {
//			        	e.setSpecialization(currentCell.getStringCellValue());
//		        	  }
//				     break;
//				        
//		          case 25:
//		        	  if(cellIdx1  == "") {
//		        		  System.out.println(" case 25");
//		        		  e.setCourseType("");
//		        	  }
//		        	  else {
//			        	e.setCourseType(currentCell.getStringCellValue());
//		        	  }
//				     break;
//				        
//		          case 26:
//		        	  if(cellIdx1 == "") {
//		        		  System.out.println(" case 26");
//		        		  e.setCourseDuration("");
//		        	  }
//		        	  else {
//			        	e.setCourseDuration(currentCell.getStringCellValue());
//		        	  }
//				     break;
//				        
//		          case 27:
//		        	  if(cellIdx1  == "") {
//		        		  System.out.println(" case 27");
//		        		  e.setGradingSystem("");
//		        	  }
//		        	  else {
//			        	e.setGradingSystem(currentCell.getStringCellValue());
//		        	  }
//				     break;
//				        
//		          case 28:
//		        	  if(cellIdx1  == "") {
//		        		  System.out.println(" case 28");
//		        		  e.setGrade("");
//		        	  }
//		        	  else {
//			        	e.setGrade(currentCell.getStringCellValue());
//		        	  }
//				     break;
//				        
//		          case 29:
//		        	  if(cellIdx1 == "") {
//		        		  System.out.println(" case 29");
//		        		  e.setCertificationName("");
//		        	  }
//		        	  else {
//			        	e.setCertificationName(currentCell.getStringCellValue());
//		        	  }
//				     break;
//				        
//		          case 30:
//		        	  if(cellIdx1  == "") {
//		        		  System.out.println(" case 30");
//		        		  e.setCertificationCompletionID("");
//		        	  }
//		        	  else {
//			        	e.setCertificationCompletionID(String.valueOf(currentCell.getStringCellValue()));
//		        	  }
//				     break;
//				        
//		          case 31:
//		        	  if(cellIdx1  == "") {
//		        		  System.out.println(" case 31");
//		        		  e.setCertificationUrl("");
//		        	  }
//		        	  else {
//			        	e.setCertificationUrl(String.valueOf(currentCell.getStringCellValue()));
//		        	  }
//				     break;
//				        
//		          case 32:
//		        	  if(cellIdx1  == "") {
//		        		  System.out.println(" case 32");
//		        		  e.setCertificationValidity("");
//		        	  }
//		        	  else {
//			        	e.setCertificationValidity(myformatter2.format(currentCell.getDateCellValue()));
//		        	  }
//				     break;
//				        
//		          case 33:
//		        	  if(cellIdx1  == "") {
//		        		  System.out.println(" case 33");
//		        		  e.setAccountholdername("");
//		        	  }
//		        	  else {
//			        	e.setAccountholdername(currentCell.getStringCellValue());
//		        	  }
//				     break;
//				        
//		          case 34:
//		        	  if(cellIdx1  == "") {
//		        		  System.out.println(" case 34");
//		        		  e.setBankname("");
//		        	  }
//		        	  else {
//			        	e.setBankname(currentCell.getStringCellValue());
//		        	  }
//				     break;
//				        
//		          case 35:
//		        	  if(cellIdx1  == "") {
//		        		  System.out.println(" case 35");
//		        		  e.setBranch("");
//		        	  }
//		        	  else {
//			        	e.setBranch(currentCell.getStringCellValue());
//		        	  }
//				     break;
//				        
//		          case 36:
//		        	  if(cellIdx1  == "") {
//		        		  System.out.println(" case 36");
//		        		  e.setAccountnumber("");
//		        	  }
//		        	  else {
//			        	e.setAccountnumber(String.valueOf(currentCell.getNumericCellValue()));
//		        	  }
//				     break;
//				        
//		          case 37:
//		        	  if(cellIdx1  == "") {
//		        		  System.out.println(" case 37");
//		        		  e.setIFSC_Code("");
//		        	  }
//		        	  else {
//			        	e.setIFSC_Code(currentCell.getStringCellValue());
//		        	  }
//				     break;
//				        
//		          case 38:
//		        	  if(cellIdx1 == "") {
//		        		  System.out.println(" case 38");
//		        		  e.setDisability("");
//		        	  }
//		        	  else {
//			        	e.setDisability(currentCell.getStringCellValue());
//		        	  }
//				     break;
//				        
//		          case 39:
//		        	  if(cellIdx1  == "") {
//		        		  System.out.println(" case 39");
//		        		  e.setDisabilityType("");
//		        	  }
//		        	  else {
//			        	e.setDisabilityType(currentCell.getStringCellValue());
//		        	  }
//				     break;
//				        
//		          case 40:
//		        	  if(cellIdx1 == "") {
//		        		  System.out.println(" case 40");
//		        		  e.setCareerBreak("");
//		        	  }
//		        	  else {
//			        	e.setCareerBreak(currentCell.getStringCellValue());
//		        	  }
//				     break;
//				        
//		          case 41:
//		        	  if(cellIdx1 == "") {
//		        		  System.out.println(" case 41");
//		        		  e.setReasonOfBreak("");
//		        	  }
//		        	  else {
//			        	e.setReasonOfBreak(currentCell.getStringCellValue());
//		        	  }
//				     break;
//				        
//		          case 42:
//		        	  if(cellIdx1  == "") {
//		        		  System.out.println(" case 42");
//		        		  e.setBreakStartFrom("");
//		        	  }
//		        	  else {
//			        	e.setBreakStartFrom(String.valueOf(currentCell.getNumericCellValue()));
//		        	  }
//				     break;
//				        
//		          case 43:
//		        	  if(cellIdx1  == "") {
//		        		  System.out.println(" case 43");
//		        		  e.setBreakEnd("");
//		        	  }
//		        	  else {
//			        	e.setBreakEnd(String.valueOf(currentCell.getNumericCellValue()));
//		        	  }
//				     break;
//				     
//		          case 44:
//		        	  if(cellIdx1 == "") {
//		        		  System.out.println(" case 44");
//		        		  e.setUAN_Number("");
//		        	  }
//		        	  else {
//			        	e.setUAN_Number(String.valueOf(currentCell.getNumericCellValue()));
//		        	  }
//				     break;
//				     
//		          case 45:
//		        	  if(cellIdx1 == "") {
//		        		  System.out.println(" case 45");
//		        		  e.setPF_Number("");
//		        	  }
//		        	  else {
//			        	e.setPF_Number(String.valueOf(currentCell.getStringCellValue()));
//		        	  }
//				     break; 
//				    
//		          case 46:
//		        	  if(cellIdx1 == "") {
//		        		  System.out.println(" case 46");
//		        		  e.setESI_Number("");
//		        	  }
//		        	  else {
//			        	e.setESI_Number(String.valueOf(currentCell.getNumericCellValue()));
//		        	  }
//				     break;
//				     
//		          case 47:
//		        	  if(cellIdx1 == "") {
//		        		  System.out.println(" case 47");
//		        		  e.setDepartment("");
//		        	  }
//		        	  else {
//			        	e.setDepartment(currentCell.getStringCellValue());
//		        	  }
//				     break;
//				   
//		          case 48:
//		        	  if(cellIdx1 == "") {
//		        		  System.out.println(" case 48");
//		        		  e.setReporting_manager(0);;
//		        	  }
//		        	  else {
//			        	e.setReporting_manager((int)currentCell.getNumericCellValue());
//		        	  }
//				     break;
//				   
//		          default:
//		            break;
//		          }
//	
//		          cellIdx++;
//		        }
//
//	        System.out.println("Email : " + rowNumber + "/" + rowNumber);
////			tutorials.add(e);
////	        if(currentRow != null && cellsInRow !=null) {
//	        	if(employeeService.findByEmailID(e.getEmail()) == 0) {
////		        	 employeeService.saveEmployee(e);
//		        	 LoginUser user = new LoginUser();
//		        	 user.setUsername(e.getEmail());
//		        	 BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(4);	
//		        	 user.setPassword(bCryptPasswordEncoder.encode("123456"));
////		        	 loginuserService.save(user);
//		        	 LoginUserAuthority a = new LoginUserAuthority();
//			    	 a.setAuthority_id(6);
//			    	 a.setUser_id(loginuserService.GetAuthorityID(user.getUsername()));
////			    	 loginuserauthorityService.save(a);
//		        }
//	        }
//	        
////	      }
//
//	      workbook.close();
//	      System.out.println("end : " + tutorials);
//	      
//	      return tutorials;
//	      
//	    } catch (IOException e1) {
//	      throw new RuntimeException("fail to parse Excel file: " + e1.getMessage());
//	    }
//	  }
}
