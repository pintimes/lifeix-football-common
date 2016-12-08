package com.lifeix.football.common.exception;

public class BaseException extends RuntimeException {

	private String code;

	public BaseException(String msg) {
		super(msg);
	}
	
	public BaseException(String code, String msg) {
		super(msg);
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	private static final long serialVersionUID = 1L;

}
