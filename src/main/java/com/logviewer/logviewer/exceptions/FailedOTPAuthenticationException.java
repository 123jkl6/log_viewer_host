package com.logviewer.logviewer.exceptions;

public class FailedOTPAuthenticationException extends Exception {

    public FailedOTPAuthenticationException(){}

    public FailedOTPAuthenticationException(String msg){
        super(msg);
    }
}
