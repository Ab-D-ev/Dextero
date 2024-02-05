package com.example.springboot.AddProjectCode;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.springboot.EmployeesDetails.Employee;


@Repository
public interface projectRepository extends JpaRepository<projectName,Integer>{
	
	
//	 @Query(" FROM projectName ORDER BY id,CompanyName, projectcode ASC")
//	   public List<projectName> getprojectNameListByOrder(long id,String CompanyName,String projectcode);
	    
	   @Query(value="select projectcode from projectName where projectName=?1")
		public List<String> findprojectcode(String companyname);
	   
	   @Query(value="select projectName from projectName where categoryName=?1")
		public List<String> findprojectName(String categoryName);
	   
	   @Query("select projectName from projectName where companyNameID IN(select id from AddCustomer where CompanyName=:CompanyName)")
		public List<String> findprojectNameByCompanyName(@Param("CompanyName") String companyname);
	   
	   @Query(nativeQuery = true, value = "select * from project_name where currentdate=?1")
		public List<projectName> getProjectListByCurrentDate(String currentdate);

		@Query(nativeQuery=true , value="SELECT  COUNT(*) AS 'Total Project' FROM project_name")
		public int TotalNumberOfProject();
		
		@Query(value="select projectName from projectName where projectcode=?1")
		public List<String> getProjectName(String pcode);
		
		@Query(value="select projectcode from projectName where projectName=?1")
		public List<String> findprojectcodeByprojectname(String projectname);
		
		@Query(nativeQuery = true, value = "select * from project_name where company_name LIKE ?1%  OR projectcode LIKE ?2% OR project_name LIKE ?3%")
		public List<projectName> FilterByCompanyNameProjectCodeProjectName(String company_name,String project_code, String project_name);
		
//		@Modifying
//		@Query(value = "insert into project_name (company_name , currentdate , project_name , projectcode, company_id, category_name) VALUES (:company_name , :currentdate , :project_name , :projectcode, :company_id, :category_name)", nativeQuery = true)
//		public void insertDataIntoProject(String company_name ,String currentdate ,String project_name ,String projectcode,int company_id,String category_name);
		
//		@Query(nativeQuery = true,value="select company_name from project_name where id=?1")
//		public String findCompanyNameById(Integer id);
		
//		@Query(value="select employeeName from projectName where CompanyName=?1 AND projectName=?2 AND projectcode=?3")
//		public List<String> getEmployeeNameListByCompanyNameProjectNameProjectCode(String companyname, String projectname, String projectcode);

//		@Query(value="select projectcode from projectName where categoryName=?1 AND id=?2")
//		public List<String> findprojectcodeByProjectName(String category, Integer projectId);
		
		@Query("select projectcode from projectName Where id IN(select id from projectName where categoryName=:categoryName AND projectName=:projectName)")
		public List<String> findprojectcodeByProjectName(@Param("categoryName") String category, @Param("projectName")String projectname);
		
		@Query("select p from projectName p where p.companyNameID=:companyNameID AND p.categoryName=:categoryName")
		public List<projectName> findProjectNameByCompanyNameCategoryName(@Param("companyNameID") Long companyId, @Param("categoryName") String category);
		
		@Query(nativeQuery=true , value="SELECT  COUNT(*) from project_name where company_nameid=?1 AND category_name=?2")
		public int CountProjectName(Long companyname_id,String category);
		
		@Query(nativeQuery = true,value="select projectcode from project_name where company_nameid=?1 AND category_name=?2  order by id desc LIMIT 1 ")
		public String getProjectCodeList(Long companyname_id ,String category);
		
		@Query("select count(l) from projectName l where l.categoryName = ?1 AND l.projectName = ?2")
		 public int findByProjectName(String category, String projectname);
		
		@Query("select p from projectName p where p.categoryName=:categoryName AND p.id=:id")
		public List<projectName> findprojectcodeByCategoryProjectName(@Param("categoryName")String category, @Param("id")Integer projectid);
		
//		@Query(value="select projectName from projectName where companyNameID=?1 AND categoryName=?2")
//		public List<String> findProjectNameById(Integer companyNameId, String category);
		
		
		@Query(nativeQuery = true, value="select company_nameid from project_name where id=?1")
		 public Integer findCompanyNameIdByProjectIds(Integer projectId);
		
		@Query("select projectcode from projectName Where projectName=?1")
		public List<String> findprojectcodeByProjectName(String projectname);
		
		@Query(value="select projectcode from projectName where id=?1")
		public String getProjectCode(Integer project_id);
		
		@Query(nativeQuery = true, value="select * from project_name where company_id=?1")
		public List<projectName> getProjectNameDataByCompanyID(Integer companyId);
		
		@Query(nativeQuery = true, value="select * from project_name  ORDER BY id ASC limit 1")
		public List<projectName> getProjectNameLimit1();
		
		@Query(nativeQuery = true, value="select count(*) from project_name  where company_nameid=?1 AND category_name =?2 AND project_name = ?3")
		 public Integer findByProjectNameCompAndCateg(Long companyId , String category, String projectname);
}
