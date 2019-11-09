package com.logviewer.logviewer.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LoginRequest {
    private String txnReferenceNumber;
    private String loginType;
    private String iamToken;
    private UserCredentials userCredentials;

    public String getTxnReferenceNumber() {
        return txnReferenceNumber;
    }

    public void setTxnReferenceNumber(String txnReferenceNumber) {
        this.txnReferenceNumber = txnReferenceNumber;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public String getIamToken() {
        return iamToken;
    }

    public void setIamToken(String iamToken) {
        this.iamToken = iamToken;
    }

    public UserCredentials getUserCredentials() {
        return userCredentials;
    }

    public void setUserCredentials(UserCredentials userCredentials) {
        this.userCredentials = userCredentials;
    }

    public String toString() {
        ObjectMapper obj = new ObjectMapper();
        String jsonString = "";
        try {
            jsonString = obj.writeValueAsString(this);
        } catch (JsonProcessingException jpe){
            jpe.printStackTrace();
        }
        return jsonString;
    }
}
