package com.logviewer.logviewer.model;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PublicKey {
    private String responseCode;
    private String modulus;

    public String getResponseCode() {
        return responseCode;
    }

    public String getModulus() {
        return modulus;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public void setModulus(String modulus) {
        this.modulus = modulus;
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
