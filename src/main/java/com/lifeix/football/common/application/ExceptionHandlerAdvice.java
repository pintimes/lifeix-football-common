package com.lifeix.football.common.application;

import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.lifeix.football.common.exception.AuthorizationException;
import com.lifeix.football.common.exception.BaseException;
import com.lifeix.football.common.exception.IllegalparamException;
import com.lifeix.football.common.exception.NotFindException;

/**
 * 参见 https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc 异常处理类
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
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "AuthorizationException")
    @ExceptionHandler(AuthorizationException.class)
    public void AuthorizationException(AuthorizationException e) {
	logger.error("AuthorizationException->" + e.getMessage(), e);
    }

    /**
     * 错误参数异常
     * 
     * @param e
     */
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "LegalparamException")
    @ExceptionHandler(IllegalparamException.class)
    public void LegalparamException(IllegalparamException e) {
	logger.error("LegalparamException->" + e.getMessage(), e);
    }

    /**
     * 资源未找到异常
     * 
     * @param e
     */
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "NotFindException")
    @ExceptionHandler(NotFindException.class)
    public void NotFindException(NotFindException e) {
	logger.error("NotFindException->" + e.getMessage(), e);
    }

    /**
     * 业务异常，客户端端请求不能受理
     * 
     * @param e
     */
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "BaseException")
    @ExceptionHandler(BaseException.class)
    public void BaseException(HttpResponse response, BaseException e) {
	logger.error("BaseException->" + e.getMessage(), e);
    }

    /**
     * 其他异常不能捕获则进入此方法 ，比如数据库网络失效等非运行时异常
     * 
     * @param e
     */
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Exception")
    @ExceptionHandler(Exception.class)
    public void Exception(Exception e) {
	logger.error("Exception->" + e.getMessage(), e);
    }

}