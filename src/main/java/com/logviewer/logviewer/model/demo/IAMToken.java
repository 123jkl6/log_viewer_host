package com.logviewer.logviewer.model.demo;

import com.logviewer.logviewer.logwriter.JSONStringify;

public class IAMToken {

    private String sessionId;
    private String userId;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String toString(){
        JSONStringify<IAMToken> stringifier = new JSONStringify<IAMToken>();
        return stringifier.stringify(this);
    }
}
