package com.lifeix.football.common.model;

public class SuccResponse {

    public static String MSG_UPDATE_SUCCESS = "修改成功！";

    public static String MSG_DELETE_SUCCESS = "删除成功！";

    public static String MSG_ADD_SUCCESS = "添加成功！";

    public SuccResponse() {
	super();
    }

    public SuccResponse(String message) {
	super();
	this.message = message;
    }

    public SuccResponse(int code, String message) {
	super();
	this.code = code;
	this.message = message;
    }

    private int code = 200;

    private String message;

    public int getCode() {
	return code;
    }

    public void setCode(int code) {
	this.code = code;
    }

    public String getMessage() {
	return message;
    }

    public void setMessage(String message) {
	this.message = message;
    }

}
