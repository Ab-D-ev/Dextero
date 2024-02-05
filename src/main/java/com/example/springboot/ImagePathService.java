package com.example.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;

import javax.servlet.ServletContext;

@Service
@Transactional
public class ImagePathService {
	
	@Autowired
	ServletContext context;
	
	public String getFilePath(String path) {
		boolean exists = new File(context.getRealPath("/"+path+"/")).exists();
		if(!exists) {
			new File(context.getRealPath("/"+path+"/")).mkdir();
		}
		String modifiedFilePath = context.getRealPath("/"+path+"/");
	
		return modifiedFilePath;
	}
	

}
