package com.example.springboot.PostJob;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.springboot.ImagePathService;
import com.example.springboot.EmployeesDetails.Employee;
import com.example.springboot.EmployeesDetails.EmployeeService;
import com.example.springboot.Login.LoginUser;
import com.example.springboot.Login.LoginUserAuthorityService;
import com.example.springboot.Login.LoginUserService;
import com.example.springboot.Login.Mainsidebarauthority;
import com.example.springboot.Login.MainsidebarauthorityService;
import com.example.springboot.MainMaster.AnnualSalary;
import com.example.springboot.MainMaster.AnnualSalaryService;
import com.example.springboot.MainMaster.EducationalQualification;
import com.example.springboot.MainMaster.EducationalQualificationService;
import com.example.springboot.MainMaster.EmploymentType;
import com.example.springboot.MainMaster.EmploymentTypeService;
import com.example.springboot.MainMaster.FunctionalArea;
import com.example.springboot.MainMaster.FunctionalAreaService;
import com.example.springboot.MainMaster.Industry;
import com.example.springboot.MainMaster.IndustryService;
import com.example.springboot.MainMaster.JobLocation;
import com.example.springboot.MainMaster.JobLocationService;
import com.example.springboot.MainMaster.PostName;
import com.example.springboot.MainMaster.PostNameRepo;
import com.example.springboot.MainMaster.PostNameService;
import com.example.springboot.MainMaster.Roles;
import com.example.springboot.MainMaster.RolesService;
import com.example.springboot.MainMaster.WorkMode;
import com.example.springboot.MainMaster.WorkModeService;

@Controller
public class JobPostController {

	@Autowired
	private MainsidebarauthorityService mainsidebarauthorityService;
	@Autowired
	private LoginUserAuthorityService loginuserauthorityService;
	@Autowired
	private LoginUserService loginuserService;
	@Autowired
	private JobPostService JpostService;
	@Autowired 
	private ImagePathService imagepathservice;
	@Autowired
	private EmploymentTypeService EtypeService;
	@Autowired
	private JobLocationService jobLocationService;
	@Autowired
	private IndustryService industryService;
	@Autowired
	private FunctionalAreaService FAreaService;
	@Autowired
	private WorkModeService workModeService;
	@Autowired
	private AnnualSalaryService annualSalaryService;
	@Autowired
	private EducationalQualificationService educationalQualificationService;
	@Autowired
	private RolesService rolesService;
	@Autowired
	private UserJobApplyService userJobApplyService;
	@Autowired
	private JobResponseService jResponseService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private PostNameService postNameService;
	
	@GetMapping("/JobPostList")
	public String ShowJobPost(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		model.addAttribute("List", JpostService.listAll());
		List<EmploymentType> Etype = EtypeService.listAll();
		model.addAttribute("EtypeList", Etype);	
		List<JobLocation> location =jobLocationService.listAll();
		model.addAttribute("LocationList", location);	
		List<Industry> industry = industryService.listAll();
		model.addAttribute("IndustryList", industry);
		List<FunctionalArea> farea = FAreaService.listAll();
		model.addAttribute("FunctionalAreaList", farea);
		List<WorkMode> modes = workModeService.listAll();
		model.addAttribute("WorkModeList", modes);
		List<AnnualSalary> salary = annualSalaryService.listAll();
		model.addAttribute("AnnualSalaryList", salary);
		List<EducationalQualification> qualifications = educationalQualificationService.listAll();
		model.addAttribute("qualificationList", qualifications);
		List<Roles> roles =rolesService.listAll();
		model.addAttribute("RoleList", roles);
		List<PostName> p = postNameService.listAll();
		model.addAttribute("PostNameList", p);
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "JobPostList";
	}

	@GetMapping("/PostJob")
	public String PostJobDetails(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		JobPost jpost = new JobPost();
		model.addAttribute("jpost", jpost);		
		List<EmploymentType> Etype = EtypeService.listAll();
		model.addAttribute("EtypeList", Etype);	
		List<JobLocation> location =jobLocationService.listAll();
		model.addAttribute("LocationList", location);	
		List<Industry> industry = industryService.listAll();
		model.addAttribute("IndustryList", industry);
		List<FunctionalArea> farea = FAreaService.listAll();
		model.addAttribute("FunctionalAreaList", farea);
		List<WorkMode> modes = workModeService.listAll();
		model.addAttribute("WorkModeList", modes);
		List<AnnualSalary> salary = annualSalaryService.listAll();
		model.addAttribute("AnnualSalaryList", salary);
		List<EducationalQualification> qualifications = educationalQualificationService.listAll();
		model.addAttribute("qualificationList", qualifications);
		List<Roles> roles =rolesService.listAll();
		model.addAttribute("RoleList", roles);
		List<PostName> p = postNameService.listAll();
		model.addAttribute("PostNameList", p);
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "AddJob";
	}
	
	@PostMapping("/SaveJobPost")
	public String SaveJobPost(@ModelAttribute("jpost") JobPost jpost,@RequestParam("readFile") MultipartFile[] readFile, HttpServletRequest request) throws IOException{
		//save to database
		int employee_id = employeeService.Update(request.getUserPrincipal().getName());
		LocalDateTime mydate = LocalDateTime.now();
		DateTimeFormatter myformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		for (MultipartFile file : readFile) {
			Path path=Paths.get(imagepathservice.getFilePath("JD Folder"));
			InputStream inputstreamlogo=file.getInputStream();
			String fileName=file.getOriginalFilename();
		 	Files.copy(inputstreamlogo,path.resolve(Paths.get(FilenameUtils.getBaseName(fileName)+"." + FilenameUtils.getExtension(fileName))));
		  	String modifiedlogo = FilenameUtils.getBaseName(fileName)+"." + FilenameUtils.getExtension(fileName);
		 	jpost.setJobDescription(modifiedlogo);
			jpost.setPostedDate(mydate.format(myformatter));
			jpost.setCurrentdate(mydate.format(myformatter));
			jpost.setEmployee_id(employeeService.Update(request.getUserPrincipal().getName()));
			Employee c = employeeService.getEmployeeDataByEmailID(employee_id);
			jpost.setCompany_id(c.getCompany_id());
//			jpost.setResponse(0);
			if(jpost.getResponse()== null) {
				jpost.setResponse(0);
			}
			JpostService.Save(jpost);
		}
		JobResponse response = new JobResponse();
		response.setId(jpost.getId());
		response.setJobTitle(jpost.getDesignation());
		response.setResponse(jpost.getResponse());
		response.setPostedDate(jpost.getPostedDate());
		response.setPostedBy(jpost.getEmployee_id());
		response.setCompany_id(jpost.getCompany_id());
		jResponseService.Save(response);
		return "redirect:/save-details";
	}   
	
	@RequestMapping(value="/EditSaveJobPost", method=RequestMethod.POST)
	public String EditSaveJobPost(@ModelAttribute("jpost") JobPost jpost,@RequestParam("readFile") MultipartFile[] readFile,HttpServletRequest request,
			@RequestParam("oldfile") String oldfile) throws IOException{
		//save to database
		int employee_id = employeeService.Update(request.getUserPrincipal().getName());
		LocalDateTime mydate = LocalDateTime.now();
		DateTimeFormatter myformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		for (MultipartFile file : readFile) {
			if(file.getOriginalFilename().equals("")) {
				jpost.setJobDescription(oldfile);
				jpost.setPostedDate(mydate.format(myformatter));
				jpost.setCurrentdate(mydate.format(myformatter));
				jpost.setEmployee_id(employeeService.Update(request.getUserPrincipal().getName()));
				Employee c = employeeService.getEmployeeDataByEmailID(employee_id);
				jpost.setCompany_id(c.getCompany_id());
				if(jpost.getResponse()== null) {
					jpost.setResponse(0);
				}
				else {
					jpost.setResponse(jpost.getResponse()+1);
				}
				JpostService.Save(jpost);
			}
			else {
				Path path=Paths.get(imagepathservice.getFilePath("JD Folder"));
				InputStream inputstreamlogo=file.getInputStream();
				String fileName=file.getOriginalFilename();
				Files.copy(inputstreamlogo,path.resolve(Paths.get(FilenameUtils.getBaseName(fileName) + "_" + "." + FilenameUtils.getExtension(fileName))));
				String modifiedfile = FilenameUtils.getBaseName(fileName) + "_" + "." + FilenameUtils.getExtension(fileName);
				File file1 = new File(imagepathservice.getFilePath("JD Folder") + oldfile);
				file1.delete();
				jpost.setJobDescription(modifiedfile);
				jpost.setPostedDate(mydate.format(myformatter));
				jpost.setCurrentdate(mydate.format(myformatter));
				jpost.setEmployee_id(employeeService.Update(request.getUserPrincipal().getName()));
				Employee c = employeeService.getEmployeeDataByEmailID(employee_id);
				jpost.setCompany_id(c.getCompany_id());
				if(jpost.getResponse()== null) {
					jpost.setResponse(0);
				}
				else {
					jpost.setResponse(jpost.getResponse()+1);
				}
				JpostService.Save(jpost);
			}
		}
		return "redirect:/SavePage";
	}   
	
	@GetMapping("/EditJobPost/{id}")
	public String EditPostJob(@PathVariable(value="id")Integer id,Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
    	JobPost jpost = JpostService.get(id);
    	model.addAttribute("jpost", jpost);
		List<EmploymentType> Etype = EtypeService.listAll();
		model.addAttribute("EtypeList", Etype);	
		List<JobLocation> location =jobLocationService.listAll();
		model.addAttribute("LocationList", location);	
		List<Industry> industry = industryService.listAll();
		model.addAttribute("List", industry);
		List<FunctionalArea> farea = FAreaService.listAll();
		model.addAttribute("AreaList", farea);
		List<WorkMode> modes = workModeService.listAll();
		model.addAttribute("WorkModeList", modes);
		List<AnnualSalary> salary = annualSalaryService.listAll();
		model.addAttribute("AnnualSalaryList", salary);
		List<EducationalQualification> qualifications = educationalQualificationService.listAll();
		model.addAttribute("qualificationList", qualifications);
		List<Roles> roles =rolesService.listAll();
		model.addAttribute("RoleList", roles);
		List<PostName> p = postNameService.listAll();
		model.addAttribute("PostNameList", p);
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "EditJobPost";	
	}
	
	@GetMapping("/deleteJobPost/{id}")
	public String deleteJobPost(@PathVariable(value="id")Integer id) {
		//call delete  method
		this.JpostService.delete(id);
		return "redirect:/JobPostList";
		
	}
	

///////////////////////////////////////////////// Registration //////////////////////////////////////////////////////////////
	
//	@GetMapping("/registration")
//	public String UserRegistration(Model model){
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
//		model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
//		LoginUser aform = new LoginUser();
//		model.addAttribute("aform", aform);
//		return "registration";
//	}
//	
//	@PostMapping("/SaveUser")
//	public String SaveRegistration(@ModelAttribute("aform") LoginUser aform, RedirectAttributes redirectAttributes){ 
//		int username1 = loginuserService.findByUser(aform.getUsername());
//		 if(username1 == 0){
//			 BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(4);	
//		    	aform.setPassword(bCryptPasswordEncoder.encode(aform.getPassword()));
//		    	loginuserService.save(aform);
//		    	LoginUserAuthority a = new LoginUserAuthority();
//				a.setAuthority_id(4);
//				a.setUser_id(loginuserService.GetAuthorityID(aform.getUsername()));
//				loginuserauthorityService.save(a);	
//				UpdateUserProfile userProfile = new UpdateUserProfile();
//				userProfile.setEmailId(aform.getUsername());
//				updateUserProfileService.Save(userProfile);
//		    }
//		    else {
//		    	String error="true";
//				redirectAttributes.addAttribute("error", error);
//		    	return "redirect:/registration";
//		    }
//			return "redirect:/login";
//	}
//	
//	@GetMapping("/EditRegistration/{id}")
//	public String EditRegistration(@PathVariable(value="login_user_id")int id,Model model) {
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
//		model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
//		LoginUser aform = loginuserService.get(id);
//		model.addAttribute("aform", aform);
//		return "EditRegistration";
//	}
//	
//	@GetMapping("/DeleteRegistration/{id}")
//	public String DeleteRegistration(@PathVariable(value="login_user_id")int id,Model model) {
//		this.loginuserService.deleteLoginUser(id);
//		return "redirect:/registration";
//	}

	
/////////////////////////////////////////////////////////// Work Mode //////////////////////////////////////////////////////////
	
	
	
//////////////////////////////////////////////////// Recommended Job /////////////////////////////////////////////////
//	@GetMapping("/recommendedjob")
//	public String ShowRecommendedjob(Model model) throws ParseException{
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
//		model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
//		model.addAttribute("List", JpostService.listAll());
//		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
//		List<EmploymentType> Etype = EtypeService.listAll();
//		model.addAttribute("EtypeList", Etype);	
//		List<JobLocation> location =jobLocationService.listAll();
//		model.addAttribute("LocationList", location);	
//		List<Industry> industry = industryService.listAll();
//		model.addAttribute("IndustryList", industry);
//		List<FunctionalArea> farea = FAreaService.listAll();
//		model.addAttribute("FunctionalAreaList", farea);
//		List<WorkMode> modes = workModeService.listAll();
//		model.addAttribute("WorkModeList", modes);
//		List<AnnualSalary> salary = annualSalaryService.listAll();
//		model.addAttribute("AnnualSalaryList", salary);
//		List<EducationalQualification> qualifications = educationalQualificationService.listAll();
//		model.addAttribute("qualificationList", qualifications);
//		List<Roles> roles =rolesService.listAll();
//		model.addAttribute("RoleList", roles);
//		List<PostName> p = postNameService.listAll();
//		model.addAttribute("PostNameList", p);
//		model.addAttribute("EmployerName", employeeService.getAllEmployees());
//		return "RecommendedJob";
//	}
//	
//	
//	@GetMapping("/Joblistings/{id}")
//	public String ShowJoblistings(Model model, @PathVariable("id") int id) throws ParseException{
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
//		model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
//		model.addAttribute("List", JpostService.getJobById(id));
//		List<EmploymentType> Etype = EtypeService.listAll();
//		model.addAttribute("EtypeList", Etype);	
//		List<JobLocation> location =jobLocationService.listAll();
//		model.addAttribute("LocationList", location);	
//		List<Industry> industry = industryService.listAll();
//		model.addAttribute("IndustryList", industry);
//		List<FunctionalArea> farea = FAreaService.listAll();
//		model.addAttribute("FunctionalAreaList", farea);
//		List<WorkMode> modes = workModeService.listAll();
//		model.addAttribute("WorkModeList", modes);
//		List<AnnualSalary> salary = annualSalaryService.listAll();
//		model.addAttribute("AnnualSalaryList", salary);
//		List<EducationalQualification> qualifications = educationalQualificationService.listAll();
//		model.addAttribute("qualificationList", qualifications);
//		List<Roles> roles =rolesService.listAll();
//		model.addAttribute("RoleList", roles);
//		List<PostName> p = postNameService.listAll();
//		model.addAttribute("PostNameList", p);
//		model.addAttribute("EmployerName", employeeService.getAllEmployees());
//		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
//		return "Joblistings";
//	}
//	
/////////////////////////////////////////// jobs and Responses //////////////////////////////////////////////////
//	
//	@GetMapping("/jobResponse")
//	public String ShowJobResponses(Model model){
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
//		model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
//		model.addAttribute("allResponse", JpostService.listAll());
//		List<EmploymentType> Etype = EtypeService.listAll();
//		model.addAttribute("EtypeList", Etype);	
//		List<JobLocation> location =jobLocationService.listAll();
//		model.addAttribute("LocationList", location);	
//		List<Industry> industry = industryService.listAll();
//		model.addAttribute("IndustryList", industry);
//		List<FunctionalArea> farea = FAreaService.listAll();
//		model.addAttribute("FunctionalAreaList", farea);
//		List<WorkMode> modes = workModeService.listAll();
//		model.addAttribute("WorkModeList", modes);
//		List<AnnualSalary> salary = annualSalaryService.listAll();
//		model.addAttribute("AnnualSalaryList", salary);
//		List<EducationalQualification> qualifications = educationalQualificationService.listAll();
//		model.addAttribute("qualificationList", qualifications);
//		List<Roles> roles =rolesService.listAll();
//		model.addAttribute("RoleList", roles);
//		List<PostName> p = postNameService.listAll();
//		model.addAttribute("PostNameList", p);
//		model.addAttribute("EmployerName", employeeService.getAllEmployees());
//		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
//		return "JobResponses";
//	}
	
	@GetMapping("/recommendedjob")
	public String ShowRecommendedjob(Model model) throws ParseException{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
		model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		model.addAttribute("List", JpostService.listAll());
		List<JobLocation> location =jobLocationService.listAll();
		model.addAttribute("LocationList", location);	
		List<PostName> p = postNameService.listAll();
		model.addAttribute("PostNameList", p);
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "RecommendedJob";
	}
	
	
	@GetMapping("/Joblistings/{id}")
	public String ShowJoblistings(Model model, @PathVariable("id") int id) throws ParseException{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
		model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		model.addAttribute("List", JpostService.getJobById(id));
		List<EmploymentType> Etype = EtypeService.listAll();
		model.addAttribute("EtypeList", Etype);	
		List<JobLocation> location =jobLocationService.listAll();
		model.addAttribute("LocationList", location);	
		List<Industry> industry = industryService.listAll();
		model.addAttribute("IndustryList", industry);
		List<FunctionalArea> farea = FAreaService.listAll();
		model.addAttribute("FunctionalAreaList", farea);
		List<WorkMode> modes = workModeService.listAll();
		model.addAttribute("WorkModeList", modes);
		List<AnnualSalary> salary = annualSalaryService.listAll();
		model.addAttribute("AnnualSalaryList", salary);
		List<EducationalQualification> qualifications = educationalQualificationService.listAll();
		model.addAttribute("qualificationList", qualifications);
		List<Roles> roles =rolesService.listAll();
		model.addAttribute("RoleList", roles);
		List<PostName> p = postNameService.listAll();
		model.addAttribute("PostNameList", p);
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "Joblistings";
	}
	
///////////////////////////////////////// jobs and Responses //////////////////////////////////////////////////
	
	@GetMapping("/jobResponse")
	public String ShowJobResponses(Model model){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
		model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		model.addAttribute("allResponse", jResponseService.listAll());
		model.addAttribute("EmployerName",employeeService.getAllEmployees());
		List<PostName> p = postNameService.listAll();
		model.addAttribute("PostNameList", p);
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "JobResponses";
	}
		
	
	@GetMapping("/Applyjob/Save/{id}")
	public String ShowJobApply(Model model,@PathVariable("id") int id,HttpServletRequest request){
		JobPost jobPost = JpostService.get(id);
		UserJobApply userJobApply = new UserJobApply();
		userJobApply.setJob_id(id);
		userJobApply.setUser_id(loginuserService.GetAuthorityID(request.getUserPrincipal().getName()));
		userJobApplyService.save(userJobApply);
		
		jobPost.setResponse(jobPost.getResponse()+1);
		JpostService.Save(jobPost);
		JobResponse jobResponse= jResponseService.get(id);
		jobResponse.setResponse(jobPost.getResponse());
		int employee_id = employeeService.Update(request.getUserPrincipal().getName());
		Employee c = employeeService.getEmployeeDataByEmailID(employee_id);
		jobResponse.setCompany_id(c.getCompany_id());
		jResponseService.Save(jobResponse);
		return "redirect:/recommendedjob";
	}
		
	
//	@GetMapping("/Applyjob/Save/{id}")
//	public String ShowJobApply(Model model,@PathVariable("id") int id,HttpServletRequest request){
//		JobPost jobPost = JpostService.get(id);
//		UserJobApply userJobApply = new UserJobApply();
//		userJobApply.setJob_id(id);
//		userJobApply.setUser_id(loginuserService.GetAuthorityID(request.getUserPrincipal().getName()));
//		userJobApplyService.save(userJobApply);
//		
//		jobPost.setResponse(jobPost.getResponse()+1);
//		JpostService.Save(jobPost);
//		JobResponse jobResponse= jResponseService.get(id);
//		jobResponse.setResponse(jobPost.getResponse());
//		Employee c = employeeService.getEmployeeDataByEmailID(request.getUserPrincipal().getName());
//		jobResponse.setCompany_id(c.getCompany_id());
//		jResponseService.Save(jobResponse);
//		return "redirect:/recommendedjob";
//	}
	
///////////////////////////////////////////////////////// update user profile //////////////////////////////////////////////////////////////
	
//	@GetMapping("/addUserDetails")
//	public String UpdateUserProfile(Model model){
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
//		model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
//		UpdateUserProfile userProfile = new UpdateUserProfile();
//		model.addAttribute("userProfile", userProfile);
//		List<EducationalQualification> qualifications = educationalQualificationService.listAll();
//		model.addAttribute("Educationlist", qualifications);
//		List<Industry> industries = industryService.listAll();
//		model.addAttribute("Industrylist", industries);
//		List<FunctionalArea> functionalAreas = FAreaService.listAll();
//		model.addAttribute("FunctionalArealist", functionalAreas);
//		List<JobLocation> jobLocations = jobLocationService.listAll();
//		model.addAttribute("JobLocationlist",jobLocations);
//		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
//		return "addUserDetails";
//	}
	
//	@PostMapping("/SaveUserProfile")
//	public String SaveUserProfile(@ModelAttribute("userProfile") UpdateUserProfile userProfile, @RequestParam("file1") MultipartFile[] file1,
//			HttpServletRequest request,LoginUser loginUser ) throws IOException{ 
//		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(4);	
//			for (MultipartFile file : file1) {
//				LocalDateTime mydate = LocalDateTime.now();
//				DateTimeFormatter myformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy-hh-mm-ss");
//	        	Path path=Paths.get(imagepathservice.getFilePath("Resume Folder"));
//	    		InputStream inputstreamlogo=file.getInputStream();
//	    		String fileName=file.getOriginalFilename();
//	    		Files.copy(inputstreamlogo,path.resolve(Paths.get(FilenameUtils.getBaseName(fileName) + "_" + mydate.format(myformatter) + "." + FilenameUtils.getExtension(fileName))));
//				String modifiedlogo = FilenameUtils.getBaseName(fileName) + "_" + mydate.format(myformatter) + "." + FilenameUtils.getExtension(fileName);
//	    		userProfile.setResume(modifiedlogo);
//				userProfile.setCurrentdate(mydate.format(myformatter));
//				Employee c = employeeService.getEmployeeDataByEmailID(request.getUserPrincipal().getName());
//				userProfile.setCompany_id(c.getCompany_id());
//	//			userProfile.setContactNumber(contactNumber);
//	//			userProfile.setContactNumberOther(contactNumberOther);
//	//			userProfile.setEmailId(loginUser.getUsername());
//		    	updateUserProfileService.Save(userProfile);
//			}
//	    	LoginUser m = new LoginUser();
//			m.setUsername(userProfile.getEmailId());
//			m.setPassword(bCryptPasswordEncoder.encode("123456"));
//			loginuserService.save(m);
//			LoginUserAuthority a = new LoginUserAuthority();
//			a.setUser_id(loginuserService.GetAuthorityID(userProfile.getEmailId()));
//			a.setAuthority_id(6);
//			loginuserauthorityService.save(a);
//			
//			return "redirect:/save-details";
//	}
	
//	@PostMapping("/EditSaveUserProfile")
//	public String EditSaveUserProfile(@ModelAttribute("userProfile") UpdateUserProfile userProfile, @RequestParam("file1") MultipartFile[] file1,@RequestParam("oldimage") String oldimage,
//			HttpServletRequest request,LoginUser loginUser ) throws IOException{ 
//		
//		for (MultipartFile file : file1) {
//	    	
//			if(file.getOriginalFilename().equals("")) {
//				userProfile.setResume(oldimage);
//				userProfile.setEmailId(request.getUserPrincipal().getName());
//				Employee c = employeeService.getEmployeeDataByEmailID(request.getUserPrincipal().getName());
//				userProfile.setCompany_id(c.getCompany_id());
//				updateUserProfileService.Save(userProfile);
//			}
//			else {
//				LocalDateTime mydate = LocalDateTime.now();
//				DateTimeFormatter myformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy  hh-mm-ss");
//				Path path=Paths.get(imagepathservice.getFilePath("Resume Folder"));
//	    		InputStream inputstreamlogo=file.getInputStream();
//	    		String fileName=file.getOriginalFilename();
//				Files.copy(inputstreamlogo,path.resolve(Paths.get(FilenameUtils.getBaseName(fileName) + "_" + mydate.format(myformatter) + "." + FilenameUtils.getExtension(fileName))));
//				String modifiedlogo = FilenameUtils.getBaseName(fileName) + "_" + mydate.format(myformatter) + "." + FilenameUtils.getExtension(fileName);
//				File file2 = new File(imagepathservice.getFilePath("Resume Folder") + oldimage);
//				file2.delete();
//				userProfile.setCurrentdate(mydate.format(myformatter));
//				userProfile.setResume(modifiedlogo);
//				userProfile.setEmailId(request.getUserPrincipal().getName());
//				Employee c = employeeService.getEmployeeDataByEmailID(request.getUserPrincipal().getName());
//				userProfile.setCompany_id(c.getCompany_id());
//				updateUserProfileService.Save(userProfile);
//				
//			}
//		}
//		return "redirect:/SavePage";
//	}
	
//	@GetMapping("/UpdateUser/Profile")
//	public String UpdateEmployeeDetails(Model model,HttpServletRequest request) {
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
//    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
//    	UpdateUserProfile userProfile = updateUserProfileService.get(updateUserProfileService.Update(request.getUserPrincipal().getName()));
//		model.addAttribute("userProfile",userProfile);
//		List<EducationalQualification> qualifications = educationalQualificationService.listAll();
//		model.addAttribute("Educationlist", qualifications);
//		List<Industry> industries = industryService.listAll();
//		model.addAttribute("Industrylist", industries);
//		List<FunctionalArea> functionalAreas = FAreaService.listAll();
//		model.addAttribute("FunctionalArealist", functionalAreas);
//		List<JobLocation> jobLocations = jobLocationService.listAll();
//		model.addAttribute("JobLocationlist",jobLocations);
//		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
//		return "UpdateUserProfile";
//	}
//	
//	@GetMapping("/EditUserProfile/{id}")
//	public String EditUserProfile(@PathVariable(value="id")int id,Model model) {
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
//		model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
//		UpdateUserProfile userProfile = updateUserProfileService.get(id);
//		model.addAttribute("userProfile", userProfile);
//		List<EducationalQualification> qualifications = educationalQualificationService.listAll();
//		model.addAttribute("Educationlist", qualifications);
//		List<Industry> industries = industryService.listAll();
//		model.addAttribute("Industrylist", industries);
//		List<FunctionalArea> functionalAreas = FAreaService.listAll();
//		model.addAttribute("FunctionalArealist", functionalAreas);
//		List<JobLocation> jobLocations = jobLocationService.listAll();
//		model.addAttribute("JobLocationlist",jobLocations);
//		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
//		return "UpdateUserProfile";
//	}
//	
//	@GetMapping("/DeleteUserProfile/{id}")
//	public String DeleteUserProfile(@PathVariable(value="id")int id,Model model) {
//		this.userJobApplyService.delete(id);
//		return "redirect:/UpdateUserProfileList";
//	}
	
	@GetMapping("/export/excel/{id}")
    public void exportToExcel(HttpServletResponse response,@PathVariable("id")int id) throws IOException {
		JobPost jobPost = JpostService.get(id);
        String postName = postNameService.getPostNmae(jobPost.getDesignation());
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + postName + ".xlsx";
        response.setHeader(headerKey, headerValue);
        List<UserJobApply> userJobApplies = userJobApplyService.findByJobId(id);
        List<Employee> ListEmployees1 = employeeService.getAllEmployees();
        List<Employee> ListEmployees = new LinkedList<Employee>();
        for(UserJobApply u:userJobApplies) {
        	LoginUser loginUser = loginuserService.get(u.getUser_id());
        	for(Employee u1:ListEmployees1) {
        		if(loginUser.getUsername().equals(u1.getEmail())) {
        			ListEmployees.add(u1);
        		}
        	}
        }
        UserJobExcel excelExporter = new UserJobExcel(ListEmployees, jobPost, postNameService);
        excelExporter.export(response);
    } 
	
//	@GetMapping("/UpdateUserProfileList")
//	public String ViewUserProfileExcel(Model model,@Param("username") String username, @Param("location") String location,@Param("skills") String skills,
//		@Param("education") String education,@Param("course") String course,@Param("birthDate") String birthDate) {
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
//    	model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
//    		if(username == null || location == null || skills==null || education==null || course==null || birthDate==null) {
//				List<UpdateUserProfile> updateUserProfiles = updateUserProfileService.listAll();
//				model.addAttribute("UpdateUserList",updateUserProfiles);
//				model.addAttribute("username",username);
//				model.addAttribute("location",location);
//				model.addAttribute("skills",skills);
//				model.addAttribute("education",education);
//				model.addAttribute("course",course);
//				model.addAttribute("birthDate",birthDate);	
//			}
//			else {
//				List<UpdateUserProfile> updateUserProfiles2 = updateUserProfileService.advanceFilter(username, location, education, course, birthDate, skills);
//				model.addAttribute("UpdateUserList",updateUserProfiles2);
//				model.addAttribute("username",username);
//				model.addAttribute("location",location);
//				model.addAttribute("skills",skills);
//				model.addAttribute("education",education);
//				model.addAttribute("course",course);
//				model.addAttribute("birthDate",birthDate);	
//			}
//    	
////		model.addAttribute("UserList", updateUserProfileService.listAll());
//    		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
//		return "UserProfileList";
//	}
	
}
