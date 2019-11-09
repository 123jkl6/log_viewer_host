package com.logviewer.logviewer.dao;

import com.logviewer.logviewer.logwriter.Logwriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class LogViewerDAO {

    @Value("${logs.path}")
    private String logsPath;

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
}
