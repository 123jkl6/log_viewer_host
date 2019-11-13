package com.logviewer.logviewer.dao;

import com.logviewer.logviewer.logwriter.Logwriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class LogViewerDAO {

    @Value("${logs.path}")
    private String logsPath;

    public List<String> getServiceNames(){
        List<String> serviceNames = new ArrayList<String>();
        ClassPathResource cl = new ClassPathResource("service_names");

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(cl.getURL().openStream()))) {
            Stream<String> stream = br.lines();
            serviceNames = stream.collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return serviceNames;
    }

    public List<String> getAllLogsByDate(String envName,String currentDate) {
        List<String> result = new ArrayList<String>();

        try (Stream<Path> walk = Files.walk(Paths.get(logsPath+"/"+envName+"/logs/"+currentDate))) {
            result = walk.filter(Files::isRegularFile)
                    .map(x -> x.toString()).collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public byte[] getSingleLog(String envName, String date, String fileName) throws IOException {
        String fileAbsolutePath = logsPath+"/"+envName+"/logs/"+date+"/"+fileName;
        byte[] logsResults = Files.readAllBytes(Paths.get(fileAbsolutePath));
        return logsResults;
    }

    public void writeLogs(String request, String response, String txnReferenceNumber, String serviceName, String username, String envName) throws IOException {
        if (envName==null || envName.equals("")){
            envName = "UAT1";
        }
        //LocalDate date = LocalDate.now();
        LocalDateTime timestamp = LocalDateTime.now();
        String timestampString = timestamp.toString().replaceAll("-|:","").split("\\.")[0];
        String fileName = envName+"/logs/"+ Logwriter.getDateForToday()+"/"+timestampString+"_"+txnReferenceNumber+"_"+(username==null?"":username+"_")+serviceName+".log";
        System.out.println("writing to "+fileName);
        File logsFile = new File(fileName);
        logsFile.getParentFile().mkdirs();
        //FileOutputStream fos = new FileOutputStream(logsFile);
        FileWriter fw = new FileWriter(logsFile);
        fw.write(timestamp.toString()+" "+serviceName+"\nRQ_JSON="+request+"\n"+"RS_JSON="+response);
        fw.close();
        System.out.println("written to "+fileName);
    }

}
