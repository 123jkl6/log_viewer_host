package com.logviewer.logviewer.exceptions;

public class UnprocessableFieldException extends Exception {

    public UnprocessableFieldException () {

    }

    public UnprocessableFieldException(String msg){
        super(msg);
    }
}
