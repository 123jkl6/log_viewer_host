package com.logviewer.logviewer.model.demo;

import com.logviewer.logviewer.logwriter.JSONStringify;

public class SMSOTPObject {
    private String opaque;
    private String otp;

    public String getOpaque() {
        return opaque;
    }

    public void setOpaque(String opaque) {
        this.opaque = opaque;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String toString(){
        JSONStringify<SMSOTPObject> stringifier = new JSONStringify<SMSOTPObject>();
        return stringifier.stringify(this);
    }
}
