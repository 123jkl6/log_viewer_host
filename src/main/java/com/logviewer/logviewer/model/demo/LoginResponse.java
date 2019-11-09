package com.logviewer.logviewer.model.demo;

import com.logviewer.logviewer.logwriter.JSONStringify;

public class LoginResponse {
    private String responseCode;
    private UserInfo userInfo;
    private IAMToken iamToken;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public IAMToken getIamToken() {
        return iamToken;
    }

    public void setIamToken(IAMToken iamToken) {
        this.iamToken = iamToken;
    }

    public String toString(){
        JSONStringify<LoginResponse> stringifier = new JSONStringify<LoginResponse>();
        return stringifier.stringify(this);
    }

}
