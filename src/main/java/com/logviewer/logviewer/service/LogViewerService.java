package com.logviewer.logviewer.service;

import com.logviewer.logviewer.dao.LogViewerDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.logviewer.logviewer.logwriter.Logwriter;

@Service
public class LogViewerService {

    @Autowired
    private LogViewerDAO logViewerDAO;

    @Value("${logs.path}")
    private String logsPath;

    public List<String> getAllLogsForToday(String envName){
        List<String> result = new ArrayList<String>();
        String currentDate = Logwriter.getDateForToday();
        try (Stream<Path> walk = Files.walk(Paths.get(logsPath+"/"+envName+"/logs/"+currentDate))) {
            List<String> rawResult = walk.filter(Files::isRegularFile)
                    .map(x -> x.toString()).collect(Collectors.toList());
            rawResult.forEach(x->{
                String[] xArr = x.split("/");
                result.add(xArr[xArr.length-1]);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public byte[] getSingleLog(String envName, String fileName){
        byte[] logsResults = null;
        String[] fileNameStringArr = fileName.split("T");
        String date =  fileNameStringArr[0];

        try {
            logsResults = logViewerDAO.getSingleLog(envName, date, fileName);
        }
        catch (IOException ioe){
            ioe.printStackTrace();
        }
        return logsResults;
    }
}
