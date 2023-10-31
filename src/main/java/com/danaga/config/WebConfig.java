package com.danaga.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.danaga.controller.AuthLoginAnnotationInterceptor;


@Configuration
public class WebConfig implements WebMvcConfigurer{
	/*
	 * 인터셉터등록
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		AuthLoginAnnotationInterceptor authLoginAnnotationInterceptor=
				new AuthLoginAnnotationInterceptor();
		registry.addInterceptor(authLoginAnnotationInterceptor)
		.addPathPatterns("/**")
		.excludePathPatterns("/css/**")
		.excludePathPatterns("/js/**")
		.excludePathPatterns("/image/**");
		
	}
	
	
}
