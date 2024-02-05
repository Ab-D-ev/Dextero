package com.example.springboot.HelpDesk;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface HelpDeskRepository extends JpaRepository< HelpDesk , Integer> {
	
	@Query(nativeQuery = true, value = "select * from helpdesk where company_name_id=?1 AND project_id=?2")
	public List<HelpDesk> getPriorityLevel(Integer companyId, Integer projectcode);
	
	@Query(nativeQuery = true, value = "select * from helpdesk where employee_id=?1")
	public List<HelpDesk> getListByEmployeeName(Integer employee_id);

}
