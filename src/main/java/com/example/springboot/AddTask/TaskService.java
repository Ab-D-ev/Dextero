package com.example.springboot.AddTask;

import java.util.List;

import org.springframework.stereotype.Service;


@Service
public interface TaskService {
	
	List<AddTask> getAllTask();
	void saveAllTask(AddTask task);
	
	AddTask getTaskById(Integer id);
	void deleteTaskById(Integer id);

}
