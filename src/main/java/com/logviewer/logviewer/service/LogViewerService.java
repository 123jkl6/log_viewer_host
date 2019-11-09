package com.logviewer.logviewer.service;

import com.logviewer.logviewer.dao.LogViewerDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


import com.logviewer.logviewer.logwriter.Logwriter;

@Service
public class LogViewerService {

    Logger logger = LoggerFactory.getLogger(LogViewerService.class);

    @Autowired
    private LogViewerDAO logViewerDAO;

    @Value("${logs.path}")
    private String logsPath;

    public List<String> getAllLogsForToday(String envName){
        String currentDate = Logwriter.getDateForToday();
        List<String> result = new ArrayList<String>();
        List<String> rawResults = logViewerDAO.getAllLogsByDate(envName,currentDate);
//        try (Stream<Path> walk = Files.walk(Paths.get(logsPath+"/"+envName+"/logs/"+currentDate))) {
//            List<String> rawResult = walk.filter(Files::isRegularFile)
//                    .map(x -> x.toString()).collect(Collectors.toList());
//            rawResult.forEach(x->{
//                String[] xArr = x.split("/");
//                result.add(xArr[xArr.length-1]);
//            });
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        for (String oneFileName : rawResults){
            String[] oneFileNameArr = oneFileName.split("/");
            result.add(oneFileNameArr[oneFileNameArr.length-1]);
        }
        return result;
    }

    public List<String> searchLogs(String envName, String date, String txnReferenceNumber, String serviceName,String username){
        List<String> rawResults = null;
        List<String> results =null;

        if (date==null){
            //generate today's date
            date = LocalDate.now().toString().replaceAll("-","");
        }

        if (username!=null && serviceName != null && txnReferenceNumber != null) {
            //search for that match all 3 criteria
            logger.info("searching by username, serviceName and transaction reference");

        } else if (username!=null && serviceName!=null && txnReferenceNumber==null) {
            //only username and serviceName given
            logger.info("searching by username and serviceName");

        } else if (username!=null && txnReferenceNumber!=null && serviceName==null){
            //only username and transaction ref given
            logger.info("searching by username and transaction reference");

        } else if (txnReferenceNumber!=null && serviceName!=null && username==null){
            //only transaction ref and service name given
            logger.info("searching by serviceName and transaction reference");

        } else if (username!=null && serviceName==null && txnReferenceNumber==null){
            //only username given
            logger.info("searching by only username");
            rawResults = convertAbsolutePathToFilename(logViewerDAO.getAllLogsByDate(envName,date));
            results = filterByUsername(rawResults,username);
        } else if (serviceName!=null && username==null && txnReferenceNumber==null){
            //only serviceName given
            logger.info("searching by only serviceName");

        } else if (txnReferenceNumber!=null && username==null && serviceName==null){
            //only transaction reference given
            logger.info("searching by only transaction reference");

        } else {
            //last case, username, serviceName and transaction ref not provided.
            //use previously implemented method to retrive all logs
            logger.info("searching by only date");
            rawResults = logViewerDAO.getAllLogsByDate(envName,date);
            results = convertAbsolutePathToFilename(rawResults);
        }

        return results;
    }


    public List<String> filterByUsernameAndServiceNameAndTransactionReference(List<String> rawResults,String serviceName){
        List<String> result = new ArrayList<String>();

        return result;
    }

    public List<String> filterByUsernameAndServiceName(List<String> rawResults,String serviceName){
        List<String> result = new ArrayList<String>();

        return result;
    }

    public List<String> filterByUsernameAndTransactionReference(List<String> rawResults,String serviceName){
        List<String> result = new ArrayList<String>();

        return result;
    }

    public List<String> filterByTransactionReferceAndServiceName(List<String> rawResults,String serviceName){
        List<String> result = new ArrayList<String>();

        return result;
    }

    public List<String>  filterByUsername(List<String> rawResults,String username){
        List<String> result;

        result = rawResults.stream().filter(oneFileName->{
            String[] fileNameArr = oneFileName.split("_");
            if (fileNameArr.length==4 && fileNameArr[2].equals(username)){
                return true;
            }

            return false;
        }).collect(Collectors.toList());

        return result;
    }

    public List<String>  filterByServiceName(List<String> rawResults,String serviceName){
        List<String> result = new ArrayList<String>();

        return result;
    }

    public List<String>  filterByTransactionReference(List<String> rawResults,String txnReferenceNumber){
        List<String> result = new ArrayList<String>();

        return result;
    }

    public List<String> convertAbsolutePathToFilename(List<String> input){
        List<String> result = new ArrayList<String>();
        for (String oneFileName : input){
            result.add(getFilename(oneFileName));
        }

        return result;
    }

    public String getFilename(String absolutePath){
        String[] oneFileNameArr = absolutePath.split("/");
        String filename = oneFileNameArr[oneFileNameArr.length-1];
        return filename;
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
