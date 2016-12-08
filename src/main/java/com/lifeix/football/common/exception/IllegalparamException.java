package com.lifeix.football.common.exception;

/**
 * @author gcc
 */
public class IllegalparamException extends BaseException {

    private static final long serialVersionUID = 1L;

    public IllegalparamException(String msg) {
        super(msg);
    }
    
    public IllegalparamException(String code, String msg) {
		super(code,msg);
	}

}
