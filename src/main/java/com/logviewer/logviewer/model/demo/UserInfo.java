package com.logviewer.logviewer.model.demo;

import com.logviewer.logviewer.logwriter.JSONStringify;

import java.time.LocalDate;

public class UserInfo {
    private String userId;
    private String tokenSysGenId;
    private String cin;
    private String cinSuffix;
    private String otpInStatus;
    private LocalDate lastLoginDate;
    private String lastLoginInTime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTokenSysGenId() {
        return tokenSysGenId;
    }

    public void setTokenSysGenId(String tokenSysGenId) {
        this.tokenSysGenId = tokenSysGenId;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getCinSuffix() {
        return cinSuffix;
    }

    public void setCinSuffix(String cinSuffix) {
        this.cinSuffix = cinSuffix;
    }

    public String getOtpInStatus() {
        return otpInStatus;
    }

    public void setOtpInStatus(String otpInStatus) {
        this.otpInStatus = otpInStatus;
    }

    public LocalDate getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(LocalDate lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getLastLoginInTime() {
        return lastLoginInTime;
    }

    public void setLastLoginInTime(String lastLoginInTime) {
        this.lastLoginInTime = lastLoginInTime;
    }

    public String toString(){
        JSONStringify<UserInfo> stringifier = new JSONStringify<UserInfo>();
        return stringifier.stringify(this);
    }

}
