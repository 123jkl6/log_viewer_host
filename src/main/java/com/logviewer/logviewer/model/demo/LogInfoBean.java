package com.logviewer.logviewer.model.demo;

public class LogInfoBean {
    private String txnReferenceNumber;
    private String username;
    private String serviceName;
    private String requestString;

    public String getTxnReferenceNumber() {
        return txnReferenceNumber;
    }

    public void setTxnReferenceNumber(String txnReferenceNumber) {
        this.txnReferenceNumber = txnReferenceNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getRequestString() {
        return requestString;
    }

    public void setRequestString(String requestString) {
        this.requestString = requestString;
    }

}
