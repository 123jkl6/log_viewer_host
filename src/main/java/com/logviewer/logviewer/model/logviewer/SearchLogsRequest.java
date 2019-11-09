package com.logviewer.logviewer.model.logviewer;

import com.logviewer.logviewer.logwriter.JSONStringify;
import com.logviewer.logviewer.model.demo.ErrorException;

import java.time.LocalDate;

public class SearchLogsRequest {
    private String txnReferenceNumber;
    private String username;
    private LocalDate date;
    private String serviceName;

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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String toString(){
        JSONStringify<SearchLogsRequest> stringifier = new JSONStringify<SearchLogsRequest>();
        return stringifier.stringify(this);
    }

}
