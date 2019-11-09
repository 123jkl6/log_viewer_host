package com.logviewer.logviewer.advice;

import com.logviewer.logviewer.exceptions.FailedOTPAuthenticationException;
import com.logviewer.logviewer.exceptions.MissingFieldException;
import com.logviewer.logviewer.logwriter.Logwriter;
import com.logviewer.logviewer.model.demo.ErrorException;
import com.logviewer.logviewer.model.demo.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    Logger logger = LoggerFactory.getLogger(ControllerAdvice.class);

    @Value("${env.name}")
    private String envName;

    @ExceptionHandler(FailedOTPAuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleFailOTPAuthentication(FailedOTPAuthenticationException e) throws IOException {
        //setting ErrorException
        ErrorException errorException = new ErrorException();
        errorException.setErrorResponseCode("1013");
        errorException.setErrorMessage(e.getMessage());

        //setting ErrorResponse
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setReponseCode("1013");
        errorResponse.setErrorException(errorException);

        //logging error
        String txnReferenceNumber = e.getLogInfoBean().getTxnReferenceNumber();
        String serviceName = e.getLogInfoBean().getServiceName();
        String username = e.getLogInfoBean().getUsername();
        String requestString = e.getLogInfoBean().getRequestString();
        logger.error(txnReferenceNumber + " RS_JSON=" + errorResponse.toString());
        Logwriter.writeLogs(requestString,errorResponse.toString(),txnReferenceNumber,serviceName,username,envName);
        return ResponseEntity.status(HttpStatus.OK).body(errorResponse);
    }

    @ExceptionHandler(MissingFieldException.class)
    public ResponseEntity<ErrorResponse> handleFailOTPAuthentication(MissingFieldException e) throws IOException {
        //setting ErroException
        ErrorException errorException = new ErrorException();
        errorException.setErrorResponseCode("1400");
        errorException.setErrorMessage(e.getMessage());

        //setting ErrorResponse
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setReponseCode("1400");
        errorResponse.setErrorException(errorException);

        //logging error
        String txnReferenceNumber = e.getLogInfoBean().getTxnReferenceNumber();
        String serviceName = e.getLogInfoBean().getServiceName();
        String username = e.getLogInfoBean().getUsername();
        String requestString = e.getLogInfoBean().getRequestString();
        logger.error(txnReferenceNumber + " RS_JSON=" + errorResponse.toString());
        Logwriter.writeLogs(requestString,errorResponse.toString(),txnReferenceNumber,serviceName,username,envName);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorResponse);
    }
}
