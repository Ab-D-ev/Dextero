package com.example.springboot.AddProjectCode;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, Integer>{
	
	@Query(nativeQuery = true,value="select category from category where id=?1")
	public String findCategoryNameById(Integer id);
	
	@Query(nativeQuery = true,value="select id from category where category=?1")
	public Integer findCategoryIDByCategoryName(String category);
	
}
