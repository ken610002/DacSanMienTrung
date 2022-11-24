package com.msstore.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import ch.qos.logback.core.net.server.Client;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter{
	@Autowired
	AdminInterceptor ad;
	
	@Autowired
	ClientInterceptor client;
		@Override
		public void addInterceptors(InterceptorRegistry registry) {


			// Interceptor này áp dụng cho các URL có dạng /admin/*
			// Loại đi trường hợp /admin/oldLogin
			registry.addInterceptor(ad)//
					.addPathPatterns("/admin/**");
			
			registry.addInterceptor(client)//
			.addPathPatterns("/home/**");

		}
}
