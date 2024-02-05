package com.example.springboot.Login;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface LoginUserAuthorityRepository  extends JpaRepository< LoginUserAuthority , Integer>{
	
//	public  List<LoginUserAuthority> findByUser_id(Integer user_id);
	
//	@Query("select a.user_id , a.authority_id from LoginUserAuthority a ")
//    public List<Integer> findUserid();
	
//	@Query("select a.user_id from LoginUserAuthority a where a.authority_id = ?1")
//	  Integer GetUserID(Integer authority_id);
//	
//	@Modifying
//	@Query("delete from LoginUserAuthority a where a.authority_id = ?1")
//	void DeleteLoginUserAuthority(Integer authority_id);
	
	@Query("select a.authority_id from LoginUserAuthority a where a.user_id = ?1")
	  Integer GetAuthorityID(Integer user_id);
	
	@Query("select count(a.user_id) from LoginUserAuthority a where a.authority_id = ?1")
	  Integer GetCountID(Integer authority_id);
	
	@Query("select a from LoginUserAuthority a where a.user_id = ?1")
	 public LoginUserAuthority findByUserID(Integer user_id);
}
