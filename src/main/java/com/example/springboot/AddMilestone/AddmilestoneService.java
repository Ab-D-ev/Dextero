package com.example.springboot.AddMilestone;

import java.util.List;

import org.springframework.stereotype.Service;


@Service
public interface AddmilestoneService {
	

	List<Addmilestone> getAllmilestone();
	void saveAllmilestone(Addmilestone milestone);
	
	Addmilestone getMileStoneById(Integer id);
	void deleteMilestoneById(Integer id);
	
	
	
	
	

}
