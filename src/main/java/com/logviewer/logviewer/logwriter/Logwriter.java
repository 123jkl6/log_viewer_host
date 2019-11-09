package com.logviewer.logviewer.logwriter;

import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Logwriter {

    public static void writeLogs(String request, String response, String txnReferenceNumber, String serviceName, String username, String envName) throws IOException {
        if (envName==null || envName.equals("")){
            envName = "UAT1";
        }
        //LocalDate date = LocalDate.now();
        LocalDateTime timestamp = LocalDateTime.now();
        String timestampString = timestamp.toString().replaceAll("-|:","").split("\\.")[0];
        String fileName = envName+"/logs/"+getDateForToday()+"/"+timestampString+"_"+txnReferenceNumber+"_"+(username==null?"":username+"_")+serviceName+".log";
        System.out.println("writing to "+fileName);
        File logsFile = new File(fileName);
        logsFile.getParentFile().mkdirs();
        //FileOutputStream fos = new FileOutputStream(logsFile);
        FileWriter fw = new FileWriter(logsFile);
        fw.write(timestamp.toString()+" "+serviceName+"\nRQ_JSON="+request+"\n"+"RS_JSON="+response);
        fw.close();
        System.out.println("written to "+fileName);
    }

    public static String getDateForToday(){
        LocalDate date = LocalDate.now();
        String resultString = date.toString().replaceAll("-","");
        return resultString;
    }
}
