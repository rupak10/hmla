package com.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{
	
	@Autowired
    private SessionInterceptor sessionInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(sessionInterceptor)
        .excludePathPatterns("/static/**") // Exclude static resources
        .excludePathPatterns("/images/**") // Exclude specific patterns
        .excludePathPatterns("/css/**")
        .excludePathPatterns("/js/**")
        .excludePathPatterns("/templates/**")
        .excludePathPatterns("/login")
		.excludePathPatterns("/register");
	}

}
