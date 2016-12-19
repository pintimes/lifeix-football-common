package com.lifeix.football.common.model;

public class SuccResponse {

	public SuccResponse() {
		super();
	}

	public SuccResponse(String message) {
		super();
		this.message = message;
	}

	public SuccResponse(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	private String code = "200";

	private String message;

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

}
