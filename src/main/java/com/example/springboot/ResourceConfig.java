package com.example.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ResourceConfig implements WebMvcConfigurer{
	@Autowired
	private ImagePathService Imagepathservice;
	@Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
//		Process For Give Folder Path In Image
		registry.addResourceHandler("/Image Folder/" + "**").addResourceLocations("file:" + Imagepathservice.getFilePath("Image Folder"));
		
		registry.addResourceHandler("/Resume Folder/" + "**").addResourceLocations("file:" + Imagepathservice.getFilePath("Resume Folder"));

		registry.addResourceHandler("/JD Folder/" + "**").addResourceLocations("file:" + Imagepathservice.getFilePath("JD Folder"));

    }
}

