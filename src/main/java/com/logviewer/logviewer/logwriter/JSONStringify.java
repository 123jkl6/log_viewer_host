package com.logviewer.logviewer.logwriter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONStringify<T> {
    public String stringify(T objIn){
        ObjectMapper obj = new ObjectMapper();
        String jsonString = "";
        try {
            jsonString = obj.writeValueAsString(objIn);
        } catch (JsonProcessingException jpe){
            jpe.printStackTrace();
        }
        return jsonString;
    }
}
