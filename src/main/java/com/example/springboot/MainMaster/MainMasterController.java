package com.example.springboot.MainMaster;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.example.springboot.EmployeesDetails.Employee;
import com.example.springboot.EmployeesDetails.EmployeeService;
import com.example.springboot.Login.LoginUserAuthorityService;
import com.example.springboot.Login.LoginUserService;
import com.example.springboot.Login.Mainsidebarauthority;
import com.example.springboot.Login.MainsidebarauthorityService;

@Controller
public class MainMasterController {

	@Autowired
	private MainsidebarauthorityService mainsidebarauthorityService;
	@Autowired
	private LoginUserAuthorityService loginuserauthorityService;
	@Autowired
	private LoginUserService loginuserService;
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
	private EducationStreamService streamService;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private EmployeeDesignationDetailsService employeeDesignationDetailsService;
	@Autowired
	private PostNameService postNameService;
	
	
/////////////////////////////////////////////////////////////////// Employment Type /////////////////////////////
	
	@GetMapping("/EmploymentType")
	public String EmploymentType(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
		model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		EmploymentType Etype = new EmploymentType();
		model.addAttribute("Etype", Etype);		
		model.addAttribute("EmploymentTypeList", EtypeService.listAll());
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "AddEmploymentType";
	}

	@PostMapping("/SaveEmploymentType")
	public String SaveEmploymentType(@ModelAttribute("Etype") EmploymentType Etype, HttpServletRequest request){
		//save to database
		int employee_id = employeeService.Update(request.getUserPrincipal().getName());
		Employee c = employeeService.getEmployeeDataByEmailID(employee_id);
		Etype.setCompany_id(c.getCompany_id());
		EtypeService.Save(Etype);
		return "redirect:/EmploymentType";
	}   

	@GetMapping("/EditEmploymentType/{id}")
	public String EditEmploymentType(@PathVariable(value="id")Integer id,Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
		model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		EmploymentType Etype = EtypeService.get(id);
		model.addAttribute("Etype", Etype);
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "EditEmploymentType";	
	}
	
	@GetMapping("/deleteEmploymentType/{id}")
	public String deleteEmploymentType(@PathVariable(value="id")Integer id) {
		//call delete  method
		this.EtypeService.delete(id);
		return "redirect:/EmploymentType";
	
	}
	
	//////////////////////////////////////////////////////////Location ///////////////////////////////////////////////////////////////////
	
	@GetMapping("/JobLocation")
	public String ShowLocation(Model model){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
		model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		model.addAttribute("JobLocationList", jobLocationService.listAll());
		JobLocation loc = new JobLocation();
		model.addAttribute("Location", loc);
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "JobLocation";
	}
	
	@PostMapping("/SaveJobLocation")
	public String SaveLocation(@ModelAttribute("LocationList") JobLocation loc,HttpServletRequest request){
		int employee_id = employeeService.Update(request.getUserPrincipal().getName());
		Employee c = employeeService.getEmployeeDataByEmailID(employee_id);
		loc.setCompany_id(c.getCompany_id());
		jobLocationService.save(loc);
		return "redirect:/JobLocation";
	}
	
	@GetMapping("/EditJobLocation/{id}")
	public String EditJobLocation(@PathVariable(value="id")int id,Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
		model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		JobLocation loc = jobLocationService.get(id);
		model.addAttribute("Location", loc);
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "EditJobLocation";
	}
	
	@GetMapping("/DeleteJobLocation/{id}")
	public String DeleteLocation(@PathVariable(value="id")int id,Model model) {
		this.jobLocationService.delete(id);
		return "redirect:/JobLocation";
	}
	
	//////////////////////////////////////////////////////////Industry ///////////////////////////////////////////////////////////////////
	
	@GetMapping("/Industry")
	public String ShowIndustry(Model model){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
		model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		model.addAttribute("IndustryList", industryService.listAll());
		Industry industry = new Industry();
		model.addAttribute("industry", industry);
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "Industry";
	}
	
	@PostMapping("/SaveIndustry")
	public String SaveIndustry(@ModelAttribute("IndustryList") Industry industry, HttpServletRequest request){
		int employee_id = employeeService.Update(request.getUserPrincipal().getName());
		Employee c = employeeService.getEmployeeDataByEmailID(employee_id);
		industry.setCompany_id(c.getCompany_id());
		industryService.save(industry);
		return "redirect:/Industry";
	}
	
	@GetMapping("/EditIndustry/{id}")
	public String EditIndustry(@PathVariable(value="id")int id,Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
		model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		Industry industry = industryService.get(id);
		model.addAttribute("industry", industry);
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "EditIndustry";
	}
	
	@GetMapping("/DeleteIndustry/{id}")
	public String DeleteIndustry(@PathVariable(value="id")int id,Model model) {
		this.industryService.delete(id);
		return "redirect:/Industry";
	}
	
	
	//////////////////////////////////////////////////////////Functional Area ///////////////////////////////////////////////////////////////////
	
	@GetMapping("/AddFunctionalArea")
	public String ShowFunctionalArea(Model model){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
		model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		model.addAttribute("FunctionalAreaList", FAreaService.listAll());
		FunctionalArea farea = new FunctionalArea();
		model.addAttribute("farea", farea);
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "AddFunctionalArea";
	}
	
	@PostMapping("/SaveFunctionalArea")
	public String SaveFunctionalArea(@ModelAttribute("FunctionalAreaList") FunctionalArea farea,HttpServletRequest request){
		int employee_id = employeeService.Update(request.getUserPrincipal().getName());
		Employee c = employeeService.getEmployeeDataByEmailID(employee_id);
		farea.setCompany_id(c.getCompany_id());
		FAreaService.Save(farea);
		return "redirect:/AddFunctionalArea";
	}
	
	@GetMapping("/EditFunctionalArea/{id}")
	public String EditFunctionalArea(@PathVariable(value="id")int id,Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
		model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		FunctionalArea farea = FAreaService.get(id);
		model.addAttribute("farea", farea);
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "EditFunctionalArea";
	}
	
	@GetMapping("/DeleteFunctionalArea/{id}")
	public String DeleteFunctionalArea(@PathVariable(value="id")int id,Model model) {
		this.FAreaService.delete(id);
		return "redirect:/AddFunctionalArea";
	}
	
	@GetMapping("/AddWorkMode")
	public String ShowWorkMode(Model model){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
		model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		model.addAttribute("WorkModeList", workModeService.listAll());
		WorkMode mode = new WorkMode();
		model.addAttribute("mode", mode);
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "AddWorkMode";
	}
	
	@PostMapping("/SaveWorkMode")
	public String SaveWorkMode(@ModelAttribute("WorkModeList") WorkMode mode, HttpServletRequest request){
		int employee_id = employeeService.Update(request.getUserPrincipal().getName());
		Employee c = employeeService.getEmployeeDataByEmailID(employee_id);
		mode.setCompany_id(c.getCompany_id());
		workModeService.Save(mode);
		return "redirect:/AddWorkMode";
	}
	
	@GetMapping("/EditWorkMode/{id}")
	public String EditWorkMode(@PathVariable(value="id")int id,Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
		model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		WorkMode mode = workModeService.get(id);
		model.addAttribute("mode", mode);
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "EditWorkMode";
	}
	
	@GetMapping("/DeleteWorkMode/{id}")
	public String DeleteWorkMode(@PathVariable(value="id")int id,Model model) {
		this.workModeService.delete(id);
		return "redirect:/AddWorkMode";
	}
	
	///////////////////////////////////////////////////////////// Annual Salary /////////////////////////////////////////////////
	
	@GetMapping("/AddAnnualSalary")
	public String ShowAnnualSalary(Model model){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
		model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		model.addAttribute("AnnualSalaryList", annualSalaryService.listAll());
		AnnualSalary salary = new AnnualSalary();
		model.addAttribute("salary", salary);
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "AddAnnualSalary";
	}
	
	@PostMapping("/SaveAnnualSalary")
	public String SaveAnnualSalary(@ModelAttribute("AnnualSalaryList") AnnualSalary salary, HttpServletRequest request){
		int employee_id = employeeService.Update(request.getUserPrincipal().getName());
		Employee c = employeeService.getEmployeeDataByEmailID(employee_id);
		salary.setCompany_id(c.getCompany_id());
		annualSalaryService.Save(salary);
		return "redirect:/AddAnnualSalary";
	}
		
	@GetMapping("/EditAnnualSalary/{id}")
	public String EditAnnualSalary(@PathVariable(value="id")int id,Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
		model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		AnnualSalary salary = annualSalaryService.get(id);
		model.addAttribute("salary", salary);
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "EditAnnualSalary";
	}
	
	@GetMapping("/DeleteAnnualSalary/{id}")
	public String DeleteAnnualSalary(@PathVariable(value="id")int id,Model model) {
		this.annualSalaryService.delete(id);
		return "redirect:/AddAnnualSalary";
	}
	
	/////////////////////////////////////////// Educational Qualification ///////////////////////////////////////////
	
	@GetMapping("/AddQualification")
	public String ShowEducationalQualification(Model model){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
		model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		model.addAttribute("QualificationList", educationalQualificationService.listAll());
		EducationalQualification qualification = new EducationalQualification();
		model.addAttribute("nqualification", qualification);
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "AddQualification";
	}
	
	@PostMapping("/SaveQualification")
	public String SaveQualification(@ModelAttribute("QualificationList") EducationalQualification qualification, HttpServletRequest request){
		int employee_id = employeeService.Update(request.getUserPrincipal().getName());
		Employee c = employeeService.getEmployeeDataByEmailID(employee_id);
		qualification.setCompany_id(c.getCompany_id());
		educationalQualificationService.Save(qualification);
		return "redirect:/AddQualification";
	}
	
	@GetMapping("/EditQualification/{id}")
	public String EditEducationalQualification(@PathVariable(value="id")int id,Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
		model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		EducationalQualification qualification = educationalQualificationService.get(id);
		model.addAttribute("nqualification", qualification);
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "EditQualification";
	}
	
	@GetMapping("/DeleteQualification/{id}")
	public String DeleteEducationalQualification(@PathVariable(value="id")int id,Model model) {
		this.educationalQualificationService.delete(id);
		return "redirect:/AddQualification";
	}
	
	//////////////////////////////////////////////////////////Roles ///////////////////////////////////////////////////////
	
	@GetMapping("/Roles")
	public String ShowRoles(Model model){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
		model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		model.addAttribute("RolesList", rolesService.listAll());
		Roles role = new Roles();
		model.addAttribute("role", role);
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "Roles";
	}
	
	@PostMapping("/SaveRoles")
	public String SaveRoles(@ModelAttribute("RolesList") Roles role, HttpServletRequest request){
		int employee_id = employeeService.Update(request.getUserPrincipal().getName());
		Employee c = employeeService.getEmployeeDataByEmailID(employee_id);
		role.setCompany_id(c.getCompany_id());
		rolesService.Save(role);
		return "redirect:/Roles";
	}
	
	@GetMapping("/EditRoles/{id}")
	public String EditRoles(@PathVariable(value="id")int id,Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
		model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		Roles role = rolesService.get(id);
		model.addAttribute("role", role);
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "EditRoles";
	}
	
	@GetMapping("/DeleteRoles/{id}")
	public String DeleteRoles(@PathVariable(value="id")int id) {
		this.rolesService.delete(id);
		return "redirect:/Roles";
	}

	///////////////////////////////////////////////////////   Educational Stream ////////////////////////////////////////////////////////
	
	
	@GetMapping("/AddEducationStream")
	public String ShowEducationStream(Model model){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
		model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		model.addAttribute("StreamList", streamService.listAll());
		EducationStream stream = new EducationStream();
		model.addAttribute("stream", stream);
		List<EducationalQualification> qualifications = educationalQualificationService.listAll();
		model.addAttribute("qualificationList", qualifications);
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "AddEducationStream";
	}
	
	@PostMapping("/SaveEducationStream")
	public String SaveEducationStream(@ModelAttribute("StreamList") EducationStream stream, HttpServletRequest request){
		int employee_id = employeeService.Update(request.getUserPrincipal().getName());
		Employee c = employeeService.getEmployeeDataByEmailID(employee_id);
		stream.setCompany_id(c.getCompany_id());
		streamService.Save(stream);
		return "redirect:/AddEducationStream";
	}
		
	@GetMapping("/EditEducationStream/{id}")
	public String EditEducationStream(@PathVariable(value="id")int id,Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
		model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		EducationStream stream = streamService.get(id);
		model.addAttribute("stream", stream);
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "EditEducationStream";
	}
	
	@GetMapping("/DeleteEducationStream/{id}")
	public String DeleteEducationStream(@PathVariable(value="id")int id,Model model) {
		this.streamService.delete(id);
		return "redirect:/AddEducationStream";
	}
	
	@GetMapping("/getCoursedropdown")
	public @ResponseBody List<String> Viewcoursename(@RequestParam(name="name",required=false)String name,Model model) {
		return streamService.findCourse(name);						
	}
	
	//////////////////////////////////////////// Department //////////////////////////////////////////////////////////////
	

	@GetMapping("/AddDepartment")
	public String ShowDepartment(Model model){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
		model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		model.addAttribute("DepartmentList", departmentService.listAll());
		Department department = new Department();
		model.addAttribute("department", department);
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "AddDepartment";
	}
	
	@PostMapping("/SaveDepartment")
	public String SaveDepartment(@ModelAttribute("department") Department department, HttpServletRequest request){
		int employee_id = employeeService.Update(request.getUserPrincipal().getName());
		Employee c = employeeService.getEmployeeDataByEmailID(employee_id);
		department.setCompany_id(c.getCompany_id());
		departmentService.Save(department);
		return "redirect:/AddDepartment";
	}
		
	@GetMapping("/EditDepartment/{id}")
	public String EditDepartment(@PathVariable(value="id")Integer id,Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
		model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		Department department = departmentService.get(id);
		model.addAttribute("department", department);
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "EditDepartment";
	}
	
	@GetMapping("/DeleteDepartment/{id}")
	public String DeleteDepartment(@PathVariable(value="id")Integer id,Model model) {
		this.departmentService.delete(id);
		return "redirect:/AddDepartment";
	}
	
	//////////////////////////////////////////////////////////////// Add Employee Designation Details /////////////////////////////////////////////
	
	@GetMapping("/employee/designation")
	public String ShowEmployeeDesignation(Model model){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
		model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		model.addAttribute("EmployeeDesignationList", employeeDesignationDetailsService.listAll());
		EmployeeDesignationDetails designation = new EmployeeDesignationDetails();
		model.addAttribute("designationDetails", designation);
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "addEmployeeDesignation";
	}
	
	@PostMapping("/save/designation")
	public String SaveEmployeeDesignation(@ModelAttribute("designationDetails") EmployeeDesignationDetails designationDetails, HttpServletRequest request){
		int employee_id = employeeService.Update(request.getUserPrincipal().getName());
		Employee c = employeeService.getEmployeeDataByEmailID(employee_id);
		designationDetails.setCompany_id(c.getCompany_id());
		employeeDesignationDetailsService.Save(designationDetails);
		return "redirect:/employee/designation";
	}
		
	@GetMapping("edit/designation/{id}")
	public String EditDesignation(@PathVariable(value="id")int id,Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
		model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		EmployeeDesignationDetails designation = employeeDesignationDetailsService.get(id);
		model.addAttribute("designationDetails", designation);
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "EditEmployeeDesignation";
	}
	
/////////////////////////////////////////////////////// Post Name ////////////////////////////////////////////////////////////
	
	@GetMapping("/post_name")
	public String ShowPostName(Model model){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
		model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		model.addAttribute("PostNameList", postNameService.listAll());
		PostName p = new PostName();
		model.addAttribute("postname", p);
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "postName";
	}
	
	@PostMapping("/save_post")
	public String SavePostName(@ModelAttribute("postname") PostName postname, HttpServletRequest request){
		int employee_id = employeeService.Update(request.getUserPrincipal().getName());
		Employee c = employeeService.getEmployeeDataByEmailID(employee_id);
		postname.setCompany_id(c.getCompany_id());
		postNameService.Save(postname);
		return "redirect:/post_name";
	}
	
	@GetMapping("/edit_post_name/{id}")
	public String EditPostName(@PathVariable(value="id")int id,Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Mainsidebarauthority> listmainsidebarauthority = mainsidebarauthorityService.Mainsidebarauthoritybyauthid(loginuserauthorityService.GetAuthorityID(loginuserService.GetAuthorityID(auth.getName())));
		model.addAttribute("listMainsidebarAuthority", listmainsidebarauthority);
		PostName role = postNameService.get(id);
		model.addAttribute("postname", role);
		model.addAttribute("CompanyID",employeeService.getCompanyID(auth.getName()));
		return "editPostName";
	}
	
	@GetMapping("/delete_post_name/{id}")
	public String DeletePostName(@PathVariable(value="id")int id) {
		this.postNameService.delete(id);
		return "redirect:/postName";
	}
	
}
