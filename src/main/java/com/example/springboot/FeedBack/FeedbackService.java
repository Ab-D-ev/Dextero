package com.example.springboot.FeedBack;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {

	@Autowired
	private FeedbackRepo repo;
	
	public List<Feedback> listAll(){
		return repo.findAll();
	}

	public void Save(Feedback feedback) {
		repo.save(feedback);
	}
	
	public Feedback get(int id) {
		return repo.findById(id).get();
	}
	public void delete(int id) {
		repo.deleteById(id);
	}
}
