package com.logviewer.logviewer.exceptions;

import com.logviewer.logviewer.model.demo.LogInfoBean;

public class MissingFieldException extends Exception {
    private LogInfoBean logInfoBean;

    public MissingFieldException(){

    }

    public MissingFieldException(String msg){
        super(msg);
    }

    public LogInfoBean getLogInfoBean() {
        return logInfoBean;
    }

    public void setLogInfoBean(LogInfoBean logInfoBean) {
        this.logInfoBean = logInfoBean;
    }

}
