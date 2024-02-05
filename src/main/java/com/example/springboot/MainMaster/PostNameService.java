package com.example.springboot.MainMaster;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostNameService {

	@Autowired
	private PostNameRepo repo;
	
	public List<PostName> listAll() {
	      return repo.findAll();
	 }
	 
	 public PostName get(int id) {
		  return repo.findById(id).get();
	 }
	 
	 public void Save(PostName postName) {
	      repo.save(postName);
	 }
	 
	 public void delete(int id) {
			repo.deleteById(id);
	 }
	 
	 public String getPostNmae(Integer id) {
		 return repo.getPostNmae(id);
	 }
}
