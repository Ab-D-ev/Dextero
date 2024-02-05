package com.example.springboot.Login;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MainsidebarauthorityRepository  extends JpaRepository < Mainsidebarauthority , Integer >{
	@Query("select m from Mainsidebarauthority m where m.authorityid = ?1")
	  List<Mainsidebarauthority> Mainsidebarauthoritybyauthid (Integer authorityid);
	
	@Modifying
	@Query("delete from Mainsidebarauthority m where m.authorityid = ?1")
	void DeleteMainsidebarauthority(Integer authorityid);
}
