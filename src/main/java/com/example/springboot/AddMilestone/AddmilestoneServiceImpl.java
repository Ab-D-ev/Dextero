package com.example.springboot.AddMilestone;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AddmilestoneServiceImpl implements AddmilestoneService {
	
	@Autowired
	private AddmilestoneRepository  milestoneRepository;

	@Override
	public List<Addmilestone> getAllmilestone() {
		// TODO Auto-generated method stub
		return milestoneRepository.findAll();
	}

	@Override
	public void saveAllmilestone(Addmilestone milestone) {
		this.milestoneRepository.save(milestone);
		
	}

	@Override
	public Addmilestone getMileStoneById(Integer id) {
		java.util.Optional<Addmilestone> optional = milestoneRepository.findById(id);
		Addmilestone milestone=null;
		if(optional.isPresent()) {
			milestone=optional.get();
		}else {
			throw new RuntimeException(" id not found"+id);
		}
		return  milestone;
		
	}

	@Override
	public void deleteMilestoneById(Integer id) {
		this.milestoneRepository.deleteById(id);
		
	}

	public List<Addmilestone> findMilestone(Integer client_id, Integer project_id){
		return milestoneRepository.findMilestone(client_id, project_id);
	}
	
	public List<String> findMilestoneByProjectNameProjectCode(String addproject, String pcode){
		return milestoneRepository.findMilestoneByProjectNameProjectCode(addproject, pcode);
	}
//	
//	public List<String> findlevelofMilestone(String levelofmilestone){
//		return  milestoneRepository.findlevelofMilestone(levelofmilestone);
//	}
	
	/*
	 * public List<String> findCompany(String company){ return
	 * milestoneRepository.findCompany(company); }
	 */
	
	public List<Addmilestone> getMileStoneListByCurrentDate(String currentdate){
		return milestoneRepository.getMileStoneListByCurrentDate(currentdate);
	}
}
