package com.logviewer.logviewer.exceptions;

import com.logviewer.logviewer.model.demo.LogInfoBean;

public class FailedOTPAuthenticationException extends Exception {
    private LogInfoBean logInfoBean;

    public FailedOTPAuthenticationException(){}

    public FailedOTPAuthenticationException(String msg){
        super(msg);
    }

    public LogInfoBean getLogInfoBean() {
        return logInfoBean;
    }

    public void setLogInfoBean(LogInfoBean logInfoBean) {
        this.logInfoBean = logInfoBean;
    }
}
