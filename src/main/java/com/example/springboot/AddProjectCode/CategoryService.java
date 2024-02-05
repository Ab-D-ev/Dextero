package com.example.springboot.AddProjectCode;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repo;
	
	public List<Category> listAll(){
		return repo.findAll();
	}

	public void Save(Category Category1) {
		repo.save(Category1);
	}
	
	public Category get(int id) {
		return repo.findById(id).get();
	}
	
	public void delete(int id) {
		repo.deleteById(id);
	}

	public String findCategoryNameById(Integer id) {
		return repo.findCategoryNameById(id);
	}
	
	public Integer findCategoryIDByCategoryName(String category) {
		return repo.findCategoryIDByCategoryName(category);
	}
	
}
