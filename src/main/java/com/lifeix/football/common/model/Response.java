package com.lifeix.football.common.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {

    public Response() {
	super();
    }

    public Response(String code, String message) {
	super();
	this.code = code;
	this.message = message;
    }

    private String code;

    private String message;

    private T data;

    public String getCode() {
	return code;
    }

    public void setCode(String code) {
	this.code = code;
    }

    public String getMessage() {
	return message;
    }

    public void setMessage(String message) {
	this.message = message;
    }

    public T getData() {
	return data;
    }

    public void setData(T data) {
	this.data = data;
    }

}
