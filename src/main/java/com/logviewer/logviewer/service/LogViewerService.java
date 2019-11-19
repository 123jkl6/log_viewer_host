package com.logviewer.logviewer.service;

import com.logviewer.logviewer.constants.ServiceName;
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

    public List<String> getServiceNames(){
        List<String> serviceNames = logViewerDAO.getServiceNames();
        return serviceNames;
    }

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

    public List<String> searchOTP(String envName, String date, String txnReferenceNumber, String username,String opaque) {
        List<String> searchResults = searchLogs(envName,date,txnReferenceNumber, ServiceName.GENERATE_OTP,username);
        List<String> finalResults = new ArrayList<String>();

        if (opaque!=null){
            for (String oneResult:searchResults){
                getSingleLog(envName,oneResult);
                String otpLogs = getSingleLogText(envName,oneResult);
                //get response string to avoid searching for opaque in request string.
                int rsJSONIndex = otpLogs.lastIndexOf("RS_JSON");
                String responseString = otpLogs.substring(rsJSONIndex,otpLogs.length());
                int opaqueIndex = responseString.indexOf("opaque");
                //add length of the word opaque, the double quotes, the colon and length of actual opaque
                String opaqueGenerated = responseString.substring(opaqueIndex+9,opaqueIndex+9+11);
                //must check that first 4 characters match, not just any match in the 10 character opaque
                logger.info("searchOtp() opaqueGenerated : "+opaqueGenerated);
                logger.info("searchOtp() opaque : "+opaque);
                int opaqueCheck = opaqueGenerated.indexOf(opaque);
                logger.info("searchOtp() opaqueCheck : "+opaqueCheck);
                if (opaqueCheck==0){
                    finalResults.add(oneResult);
                }
            }
        }
        else {
            finalResults = searchResults;
        }

        return finalResults;
    }

    public List<String> searchLogs(String envName, String date, String txnReferenceNumber, String serviceName,String username){
        List<String> rawResults = null;
        List<String> results =null;

        if (date==null){
            //generate today's date
            date = LocalDate.now().toString().replaceAll("-","");
        }

        //with date given, retrieve all logs first, filter later
        rawResults = convertAbsolutePathToFilename(logViewerDAO.getAllLogsByDate(envName,date));

        if (username!=null && serviceName != null && txnReferenceNumber != null) {
            //search for that match all 3 criteria
            logger.info("searching by username, serviceName and transaction reference");
            results = filterByUsernameAndServiceNameAndTransactionReference(rawResults,username,serviceName,txnReferenceNumber);
        } else if (username!=null && serviceName!=null && txnReferenceNumber==null) {
            //only username and serviceName given
            logger.info("searching by username and serviceName");
            results = filterByUsernameAndServiceName(rawResults,username,serviceName);
        } else if (username!=null && txnReferenceNumber!=null && serviceName==null){
            //only username and transaction ref given
            logger.info("searching by username and transaction reference");
            results = filterByUsernameAndTransactionReference(rawResults,username,txnReferenceNumber);
        } else if (txnReferenceNumber!=null && serviceName!=null && username==null){
            //only transaction ref and service name given
            logger.info("searching by serviceName and transaction reference");
            results = filterByTransactionReferenceAndServiceName(rawResults,txnReferenceNumber,serviceName);
        } else if (username!=null && serviceName==null && txnReferenceNumber==null){
            //only username given
            logger.info("searching by only username");
            results = filterByUsername(rawResults,username);
        } else if (serviceName!=null && username==null && txnReferenceNumber==null){
            //only serviceName given
            logger.info("searching by only serviceName");
            results = filterByServiceName(rawResults,serviceName);
        } else if (txnReferenceNumber!=null && username==null && serviceName==null){
            //only transaction reference given
            logger.info("searching by only transaction reference");
            results = filterByTransactionReference(rawResults,txnReferenceNumber);
        } else {
            //last case, username, serviceName and transaction ref not provided.
            //use previously implemented method to retrive all logs
//            logger.info("searching by only date");
            logger.info("Reached else case in seacrhLogs() searching only by date. ");
            results = rawResults;
            //do nothing, already queried by date in the beginning.

        }

        return results;
    }


    public List<String> filterByUsernameAndServiceNameAndTransactionReference(List<String> rawResults,String username, String serviceName, String txnReferenceNumber){
        List<String> result = new ArrayList<String>();

        result = rawResults.stream().filter(oneFileName->{
            String[] fileNameArr = oneFileName.split("_");
            if (fileNameArr.length==4 && fileNameArr[2].equals(username) && fileNameArr[3].split("\\.")[0].equals(serviceName) && fileNameArr[1].equals(txnReferenceNumber)){
                return true;
            }

            return false;
        }).collect(Collectors.toList());

        return result;
    }

    public List<String> filterByUsernameAndServiceName(List<String> rawResults, String username, String serviceName){
        List<String> result = new ArrayList<String>();

        result = rawResults.stream().filter(oneFileName->{
            String[] fileNameArr = oneFileName.split("_");
            if (fileNameArr.length==4 && fileNameArr[2].equals(username) && fileNameArr[3].split("\\.")[0].equals(serviceName)){
                return true;
            }

            return false;
        }).collect(Collectors.toList());


        return result;
    }

    public List<String> filterByUsernameAndTransactionReference(List<String> rawResults,String username, String txnReferenceNumber){
        List<String> result = new ArrayList<String>();

        result = rawResults.stream().filter(oneFileName->{
            String[] fileNameArr = oneFileName.split("_");
            if (fileNameArr.length==4 && fileNameArr[2].equals(username) && fileNameArr[1].equals(txnReferenceNumber)){
                return true;
            }

            return false;
        }).collect(Collectors.toList());

        return result;
    }

    public List<String> filterByTransactionReferenceAndServiceName(List<String> rawResults, String txnReferenceNumber, String serviceName){
        List<String> result = new ArrayList<String>();

        result = rawResults.stream().filter(oneFileName->{
            String[] fileNameArr = oneFileName.split("_");
            if (fileNameArr[fileNameArr.length-1].split("\\.")[0].equals(serviceName) && fileNameArr[1].equals(txnReferenceNumber)){
                return true;
            }

            return false;
        }).collect(Collectors.toList());

        return result;
    }

    public List<String>  filterByUsername(List<String> rawResults,String username){
        List<String> result;

        result = rawResults.stream().filter(oneFileName->{
            String[] fileNameArr = oneFileName.split("_");
            //check for length to account for cases for which services are not linked to username, but the transaction reference
            //matches the username input.
            if (fileNameArr.length==4 && fileNameArr[2].equals(username)){
                return true;
            }

            return false;
        }).collect(Collectors.toList());

        return result;
    }

    public List<String>  filterByServiceName(List<String> rawResults,String serviceName){
        List<String> result;

        result = rawResults.stream().filter(oneFileName->{
            String[] fileNameArr = oneFileName.split("_");
            if (fileNameArr[fileNameArr.length-1].split("\\.")[0].equals(serviceName)){
                return true;
            }

            return false;
        }).collect(Collectors.toList());

        return result;
    }

    public List<String>  filterByTransactionReference(List<String> rawResults,String txnReferenceNumber){
        List<String> result = new ArrayList<String>();

        result = rawResults.stream().filter(oneFileName->{
            String[] fileNameArr = oneFileName.split("_");
            if (fileNameArr[1].equals(txnReferenceNumber)){
                return true;
            }

            return false;
        }).collect(Collectors.toList());

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

    public String getSingleLogText(String envName, String fileName){
        String logsResults = null;
        String[] fileNameStringArr = fileName.split("T");
        String date =  fileNameStringArr[0];

        try {
            logsResults = logViewerDAO.getSingleLogText(envName, date, fileName);
        }
        catch (IOException ioe){
            ioe.printStackTrace();
        }
        return logsResults;
    }

    public void writeLogs(String request, String response, String txnReferenceNumber, String serviceName, String username, String envName) throws IOException {
        logViewerDAO.writeLogs(request,response,txnReferenceNumber,serviceName,username,envName);
    }
}
