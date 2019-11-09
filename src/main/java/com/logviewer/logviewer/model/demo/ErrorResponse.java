package com.logviewer.logviewer.model.demo;

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

}
