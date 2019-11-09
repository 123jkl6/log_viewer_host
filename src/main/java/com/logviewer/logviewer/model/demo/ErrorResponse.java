package com.logviewer.logviewer.model.demo;

import com.logviewer.logviewer.logwriter.JSONStringify;

public class ErrorResponse {
    private String reponseCode;
    private ErrorException errorException;

    public String getReponseCode() {
        return reponseCode;
    }

    public void setReponseCode(String reponseCode) {
        this.reponseCode = reponseCode;
    }

    public ErrorException getErrorException() {
        return errorException;
    }

    public void setErrorException(ErrorException errorException) {
        this.errorException = errorException;
    }

    public String toString(){
        JSONStringify<ErrorResponse> stringifier = new JSONStringify<ErrorResponse>();
        return stringifier.stringify(this);
    }
}
