package com.logviewer.logviewer.exceptions;

public class MissingFieldException extends Exception {

    public MissingFieldException(){

    };

    public MissingFieldException(String msg){
        super(msg);
    }
}
