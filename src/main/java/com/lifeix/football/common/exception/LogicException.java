package com.lifeix.football.common.exception;

public class LogicException extends BaseException {

    private static final long serialVersionUID = -509529501774632931L;

    public LogicException(String msg) {
	super(msg);
    }

    public LogicException(String code, String msg) {
	super(code, msg);
    }
}
