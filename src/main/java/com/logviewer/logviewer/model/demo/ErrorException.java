package com.logviewer.logviewer.model.demo;

import com.logviewer.logviewer.logwriter.JSONStringify;

public class ErrorException {
    private String errorResponseCode;
    private String errorMessage;

    public String getErrorResponseCode() {
        return errorResponseCode;
    }

    public void setErrorResponseCode(String errorResponseCode) {
        this.errorResponseCode = errorResponseCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String toString(){
        JSONStringify<ErrorException> stringifier = new JSONStringify<ErrorException>();
        return stringifier.stringify(this);
    }
}
