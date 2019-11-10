package com.logviewer.logviewer.model.demo;

import com.logviewer.logviewer.logwriter.JSONStringify;

public class OTPResponse {
    private String responseCode;
    private SMSOTPObject smsOTPObject;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public SMSOTPObject getSmsOTPObject() {
        return smsOTPObject;
    }

    public void setSmsOTPObject(SMSOTPObject smsOTPObject) {
        this.smsOTPObject = smsOTPObject;
    }

    public String toString(){
        JSONStringify<OTPResponse> stringifier = new JSONStringify<OTPResponse>();
        return stringifier.stringify(this);
    }
}
