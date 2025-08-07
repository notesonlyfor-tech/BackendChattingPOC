package com.chatting.poc.WebConfig;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

	@Configuration
	public class WebConfig implements WebMvcConfigurer {

	    @Override
	    public void addCorsMappings(CorsRegistry registry) {
	        registry.addMapping("/**")
	            .allowedOriginPatterns("http://localhost:3000") // ✅ use pattern instead of allowedOrigins
	            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
	            .allowedHeaders("*")
	            .allowCredentials(true); // ✅ Now this will not conflict with origin
	    }
	    
	    @Override
	    public void addResourceHandlers(ResourceHandlerRegistry registry) {
	        Path uploadDir = Paths.get("uploads");
	        String uploadPath = uploadDir.toFile().getAbsolutePath();

	        registry.addResourceHandler("/uploads/**")
	                .addResourceLocations("file:" + uploadPath + "/");
	    }
	}

