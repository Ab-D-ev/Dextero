package com.example.springboot.MainMaster;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostNameRepo extends JpaRepository<PostName, Integer>{

	@Query(nativeQuery=true , value="select post from post_name where id = ?1")
	public String getPostNmae(Integer id);

}
