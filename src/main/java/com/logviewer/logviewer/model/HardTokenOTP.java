package com.logviewer.logviewer.model;

import com.logviewer.logviewer.logwriter.JSONStringify;

public class HardTokenOTP {
    private String tokenSerialNumber;
    private String tokenSysGenId;
    private String otp;
    private String oldOtp;

    public String getTokenSerialNumber() {
        return tokenSerialNumber;
    }

    public void setTokenSerialNumber(String tokenSerialNumber) {
        this.tokenSerialNumber = tokenSerialNumber;
    }

    public String getTokenSysGenId() {
        return tokenSysGenId;
    }

    public void setTokenSysGenId(String tokenSysGenId) {
        this.tokenSysGenId = tokenSysGenId;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getOldOtp() {
        return oldOtp;
    }

    public void setOldOtp(String oldOtp) {
        this.oldOtp = oldOtp;
    }

    public String toString(){
        JSONStringify<HardTokenOTP> stringifier = new JSONStringify<HardTokenOTP>();
        return stringifier.stringify(this);
    }
}
