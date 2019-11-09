package com.logviewer.logviewer.model.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RandomNumber {
    private String responseCode;
    private String random;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getRandom() {
        return random;
    }

    public void setRandom(String random) {
        this.random = random;
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
