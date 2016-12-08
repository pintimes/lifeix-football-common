package com.lifeix.football.common.exception;

/**
 * @author zengguangwei
 * @description
 */
public class AuthorizationException extends BaseException {

    private static final long serialVersionUID = -2166016806460831157L;

    public AuthorizationException(String msg) {
        super(msg);
    }
    
    public AuthorizationException(String code, String msg) {
		super(code,msg);
	}
    
    public AuthorizationException() {
        super("don't have the right");
    }

}
