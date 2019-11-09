package com.logviewer.logviewer.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

@Component
public class LogViewerDAO {

    @Value("${logs.path}")
    private String logsPath;

    public byte[] getSingleLog(String envName, String date, String fileName) throws IOException {
        String fileAbsolutePath = logsPath+"/"+envName+"/logs/"+date+"/"+fileName;
        byte[] logsResults = Files.readAllBytes(Paths.get(fileAbsolutePath));
        return logsResults;
    }
}
