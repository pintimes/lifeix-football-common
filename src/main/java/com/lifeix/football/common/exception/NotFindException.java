package com.lifeix.football.common.exception;

/**
 * @author gcc
 */
public class NotFindException extends BaseException {

    private static final long serialVersionUID = 6446745746634380862L;

    public NotFindException(String msg) {
        super(msg);
    }
    
    public NotFindException(String code, String msg) {
		super(code,msg);
	}

}
