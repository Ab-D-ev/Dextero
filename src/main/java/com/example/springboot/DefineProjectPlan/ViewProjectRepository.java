package com.example.springboot.DefineProjectPlan;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ViewProjectRepository extends  JpaRepository< ViewProject , Integer > {

	@Query(nativeQuery = true, value = "select enddate from viewproject ORDER BY id DESC LIMIT 1 ")
	public String selectDate();
	
	@Query(nativeQuery = true, value = "select * from viewproject where currentdate=?1")
	public List<ViewProject> getListByCurrentDate(String currentdate);
//	
//	@Query("select c from ViewProject c where c.companyname LIKE %?1% AND c.projectcode LIKE %?2%")
//	public List<ViewProject> FilterByCompanyName( String companyname,String projectcode);
	
//	@Query("select c from ViewProject c where c.companyname LIKE %?1% AND c.projectcode LIKE %?2% AND c.empname LIKE %?3%")
//	public List<ViewProject> FilterByCompanyANDEmployee( String companyname,String projectcode,String empname);
	
//	@Query("select p from ViewProject p where p.projectcode LIKE %?1%")
//	public List<ViewProject> FilterByProjectcodeName( String projectcode);
//	
//	@Query("select e from ViewProject e where e.empname LIKE %?1%")
//	public List<ViewProject> FilterByEmployeeName( String empname);
	

}
