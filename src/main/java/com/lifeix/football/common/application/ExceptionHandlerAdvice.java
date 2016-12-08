package com.lifeix.football.common.application;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.alibaba.fastjson.JSONObject;
import com.lifeix.football.common.exception.AuthorizationException;
import com.lifeix.football.common.exception.BaseException;
import com.lifeix.football.common.exception.IllegalparamException;
import com.lifeix.football.common.exception.NotFindException;

/**
 * 参见 https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc 异常处理类
 * 返回rest形式数据 https://www.jayway.com/2014/10/19/spring-boot-error-responses/
 * 将通用的异常放于此处
 * 
 * add not find exception gcc
 * 
 * @author zengguangwei,gcc
 *
 */
public class ExceptionHandlerAdvice {

	private Logger logger = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);

	/**
	 * 授权失败，请求了需要授权的API
	 * 
	 * @param e
	 */
	@ExceptionHandler(AuthorizationException.class)
	public void AuthorizationException(HttpServletResponse response, AuthorizationException e) {
		handerException(response, HttpStatus.UNAUTHORIZED, e);
	}

	/**
	 * 错误参数异常
	 * 
	 * @param e
	 */
	@ExceptionHandler(IllegalparamException.class)
	public void LegalparamException(HttpServletResponse response, IllegalparamException e) {
		handerException(response, HttpStatus.BAD_REQUEST, e);
	}

	/**
	 * 资源未找到异常
	 * 
	 * @param e
	 */
	@ExceptionHandler(NotFindException.class)
	public void handleNotFindException(HttpServletResponse response, NotFindException e) throws IOException {
		handerException(response, HttpStatus.NOT_FOUND, e);
	}

	/**
	 * 基础异常，客户端端请求不能受理
	 * 
	 * @param e
	 */
	@ExceptionHandler(BaseException.class)
	public void handleBaseException(HttpServletResponse response, BaseException e) {
		handerException(response, HttpStatus.BAD_REQUEST, e);
	}

	/**
	 * 其他异常不能捕获则进入此方法 ，比如数据库网络失效等非运行时异常
	 * 
	 * @param e
	 * @throws IOException
	 */
	@ExceptionHandler(Exception.class)
	public void Exception(HttpServletResponse response, Exception e) throws IOException {
		logger.error(e.getMessage(), e);
		handerException(response, HttpStatus.INTERNAL_SERVER_ERROR, "","服务器异常");
	}
	
	/**
	 * 错误处理
	 * @description
	 * @author zengguangwei 
	 * @version 2016年10月10日下午5:18:15
	 *
	 * @param response
	 * @param status
	 * @param message
	 */
	private void handerException(HttpServletResponse response, HttpStatus status, String code,String message) {
		try {
			response.setContentType("application/json");
			response.setStatus(status.value());
			
			PrintWriter writer = response.getWriter();
			JSONObject json = new JSONObject();
			json.put("code", code);
			json.put("message", message);
			writer.write(json.toJSONString());
			writer.flush();
			writer.close();
		} catch (IOException ioE) {
			logger.error("handerException", ioE);
		}
	}
	
	private void handerException(HttpServletResponse response, HttpStatus status, BaseException e) {
		handerException(response, status, e.getCode(), e.getMessage());
	}

}