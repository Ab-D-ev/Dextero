package com.example.springboot.AddMilestone;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface AddmilestoneRepository extends JpaRepository<Addmilestone,Integer>{
	
	@Query(value="select m from Addmilestone m where m.company_name_id=?1 AND m.project_id=?2")
	public List<Addmilestone> findMilestone(Integer client_id, Integer project_id);
	
	@Query(value="select add_milestone from Addmilestone where projectname=?1 AND addproject=?2")
	public List<String> findMilestoneByProjectNameProjectCode(String addproject, String pcode);
//	
	//@Query(value="select add_milestone from Addmilestone where company=?1")
	//public List<String> findCompany(String company);
	
	@Query(nativeQuery = true, value = "select * from milestone where currentdate=?1")
	public List<Addmilestone> getMileStoneListByCurrentDate(String currentdate);


}
