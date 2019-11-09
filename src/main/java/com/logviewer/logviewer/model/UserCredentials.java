package com.logviewer.logviewer.model;

import com.logviewer.logviewer.logwriter.JSONStringify;

public class UserCredentials {
    private String userId;
    private Integer encryptedPinLength;
    private String encryptedPin;
    private HardTokenOTP hardTokenOTP;
    private SMSOTPObject smsOTPObject;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getEncryptedPinLength() {
        return encryptedPinLength;
    }

    public void setEncryptedPinLength(Integer encryptedPinLength) {
        this.encryptedPinLength = encryptedPinLength;
    }

    public String getEncryptedPin() {
        return encryptedPin;
    }

    public void setEncryptedPin(String encryptedPin) {
        this.encryptedPin = encryptedPin;
    }

    public HardTokenOTP getHardTokenOTP() {
        return hardTokenOTP;
    }

    public void setHardTokenOTP(HardTokenOTP hardTokenOTP) {
        this.hardTokenOTP = hardTokenOTP;
    }

    public SMSOTPObject getSmsOTPObject() {
        return smsOTPObject;
    }

    public void setSmsOTPObject(SMSOTPObject smsOTPObject) {
        this.smsOTPObject = smsOTPObject;
    }

    public String toString(){
        JSONStringify<UserCredentials> stringifier = new JSONStringify<UserCredentials>();
        return stringifier.stringify(this);
    }
}
