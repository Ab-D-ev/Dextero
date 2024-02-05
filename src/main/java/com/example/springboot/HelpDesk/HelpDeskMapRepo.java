package com.example.springboot.HelpDesk;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface HelpDeskMapRepo extends JpaRepository<HelpdeskMap, Integer>{

	
	@Modifying
	@Query(value = "insert into help_desk_map(helpdesk_id , employee_id) VALUES (:helpdesk_id , :employee_id)", nativeQuery = true)
	public void insertDataIntoHelpDeskMap(Integer helpdesk_id, Integer employee_id);
}
