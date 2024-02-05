package com.example.springboot.AddProjectCode;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;
import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.example.springboot.EmployeesDetails.Employee;


@Service
@Transactional
public class projectService{
	
	@Autowired
	private projectRepository ProjectRepository;
	
	
	public List<projectName> getAllprojectName() {
		return  ProjectRepository.findAll();
	}

	public void saveProject(projectName  ProjectName) {
		this.ProjectRepository.save( ProjectName);
		
	}
	
	public projectName get(int id) {
		return ProjectRepository.findById(id).get();
	}

	public projectName getProjectById(Integer id) {
	    return ProjectRepository.findById(id).orElse(null);
	}

	public void deleteprojectNameById(int id) {
		this.ProjectRepository.deleteById(id);
		
	}

//	public List<projectName> getprojectNameListByOrder(long id,String CompanyName,String projectcode){
//		return ProjectRepository.getprojectNameListByOrder(id,CompanyName, projectcode);
//	}

	public List<String> findprojectcode(String companyname) {
		return ProjectRepository.findprojectcode(companyname);
		
	}
	
	public List<String> findprojectName(String categoryName){
		return ProjectRepository.findprojectName(categoryName);
	}
	
	public List<projectName> getProjectListByCurrentDate(String currentdate){
		return ProjectRepository.getProjectListByCurrentDate(currentdate);
	}
	
	public int TotalNumberOfProject() {
		return ProjectRepository.TotalNumberOfProject();
	}
	public List<String> getProjectName(String pcode){
		return ProjectRepository.getProjectName(pcode);
	}
	public List<String> findprojectcodeByprojectname(String projectname){
		return ProjectRepository.findprojectcodeByprojectname(projectname);
	}
	
	public List<projectName> FilterByCompanyNameProjectCodeProjectName(String company_name,String project_code, String project_name){
		return ProjectRepository.FilterByCompanyNameProjectCodeProjectName(company_name, project_code, project_name);
	}
	
	public List<String> findprojectNameByCompanyName(@Param("CompanyName") String companyname){
		return ProjectRepository.findprojectNameByCompanyName(companyname);
	}
	
	public List<String> findprojectcodeByProjectName(@Param("categoryName")String category, @Param("projectName")String projectname){
		return ProjectRepository.findprojectcodeByProjectName(category, projectname);
	}
	
	public int CountProjectName(Long companyname_id,String category){
		return ProjectRepository.CountProjectName(companyname_id, category);
	}
	
	public String getProjectCodeList(Long companyname_id ,String category){
		return ProjectRepository.getProjectCodeList(companyname_id, category);
	}
	
	 public int findByProjectName(String category, String projectname){
		return ProjectRepository.findByProjectName(category ,projectname);
	}
	
	public List<projectName> findprojectcodeByCategoryProjectName(@Param("categoryName")String category, @Param("id")Integer projectid ){
		return ProjectRepository.findprojectcodeByCategoryProjectName(category, projectid);
	}
	
	public List<projectName> findProjectNameByCompanyNameCategoryName(@Param("companyNameID") Long companyId, @Param("categoryName") String category){
		return ProjectRepository.findProjectNameByCompanyNameCategoryName(companyId, category);
	}
	
	public Integer findCompanyNameIdByProjectIds(Integer projectId){
		return ProjectRepository.findCompanyNameIdByProjectIds(projectId);
	}
	
	public List<String> findprojectcodeByProjectName(String projectname){
		return ProjectRepository.findprojectcodeByProjectName(projectname);
	}
	
	public String getProjectCode(Integer project_id) {
		return ProjectRepository.getProjectCode(project_id);
	}
	public List<projectName> getProjectNameDataByCompanyID(Integer companyId){
		return ProjectRepository.getProjectNameDataByCompanyID(companyId);
	}
	
	public List<projectName> getProjectNameLimit1(){
		return ProjectRepository.getProjectNameLimit1();
	}
	
	 public Integer findByProjectNameCompAndCateg(Long companyId , String category, String projectname) {
		 return ProjectRepository.findByProjectNameCompAndCateg(companyId, category, projectname);
	 }
}
