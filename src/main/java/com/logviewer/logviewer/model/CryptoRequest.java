package com.logviewer.logviewer.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CryptoRequest {
    public String txnReferenceNumber;

    public String getTxnReferenceNumber() {
        return txnReferenceNumber;
    }

    public void setTxnReferenceNumber(String txnReferenceNumber) {
        this.txnReferenceNumber = txnReferenceNumber;
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
