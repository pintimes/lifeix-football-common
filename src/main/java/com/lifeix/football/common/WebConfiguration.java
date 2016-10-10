package com.lifeix.football.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.lifeix.football.common.intercepter.TimeInterceptor;

/**
 * 拦截器配置
 * 
 * @author zengguangwei
 *
 */
@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter {

	/**
	 * 记录每一个请求处理事件
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new TimeInterceptor());
	}

	/**
	 * 跨域处理	
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedHeaders("*").allowedMethods("*").allowedOrigins("*");
	}

}