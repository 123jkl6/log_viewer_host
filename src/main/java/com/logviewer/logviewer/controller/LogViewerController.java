package com.logviewer.logviewer.controller;

import com.logviewer.logviewer.exceptions.UnprocessableFieldException;
import com.logviewer.logviewer.model.logviewer.FileListRequest;
import com.logviewer.logviewer.model.logviewer.OTPLogResponse;
import com.logviewer.logviewer.model.logviewer.SearchLogsRequest;
import com.logviewer.logviewer.service.LogViewerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("logs/{envName}")
public class LogViewerController {

    Logger logger = LoggerFactory.getLogger(LogViewerController.class);

    @Autowired
    private LogViewerService logViewerService;

    @GetMapping("ping")
    public String ping(@PathVariable("envName") String envName){
        logger.info("Pinging for environment +"+envName!=null?envName:"");
        return "pinged";
    }

    @GetMapping("today")
    public List<String> getAllLogsForToday(@PathVariable("envName") String envName){
        LocalDate currentDate = LocalDate.now();
        logger.info("Retrieving all logs available for " + currentDate);
        List<String> allLogsResults = logViewerService.getAllLogsForToday(envName);
        logger.info("Retrieved all logs for " + currentDate);
        return allLogsResults;
    }

    @PostMapping("search")
    public List<String> searchLogs(@PathVariable("envName") String envName, @RequestBody(required = false) SearchLogsRequest searchLogsRequest){
        List<String> results;

        //validate request body first, if no input given, retrieve all logs today, use getAllLogsForToday
        if (searchLogsRequest==null){
            results = logViewerService.getAllLogsForToday(envName);
        }
        else {
            logger.info(" SearchLogsRequest=" + searchLogsRequest.toString());
            results = logViewerService.searchLogs(envName, searchLogsRequest.getDate()==null?null:searchLogsRequest.getDate().toString().replaceAll("-",""), searchLogsRequest.getTxnReferenceNumber(), searchLogsRequest.getServiceName(), searchLogsRequest.getUsername());
        }
        logger.info(" Search logs response  : " + results);

        return results;
    }

    @GetMapping("{fileName}")
    public byte[] getSingleLogs(@PathVariable("envName") String envName, @PathVariable("fileName") String fileName){
        logger.info("Retrieving logs for "+fileName);
        byte[] logs = logViewerService.getSingleLog(envName,fileName);
        logger.info("Retrieved logs for "+fileName);
        return logs;
    }

    @PostMapping("otp")
    public List<OTPLogResponse> searchOTP(@PathVariable("envName") String envName, @RequestBody(required = false) SearchLogsRequest searchLogsRequest) throws UnprocessableFieldException {
        logger.info("Seaching for OTP");
        if (searchLogsRequest == null){
            searchLogsRequest= new SearchLogsRequest();
        }
        String opaque = searchLogsRequest.getOpaque();
        if (opaque!=null && opaque.length()!=4){
            throw new UnprocessableFieldException("If opaque is given, it has to be strictly 4 characters");
        }
        logger.info(" SearchLogsRequest=" + searchLogsRequest.toString());
        List<OTPLogResponse> results = logViewerService.searchOTP(envName,searchLogsRequest.getDate()==null?null:searchLogsRequest.getDate().toString().replaceAll("-",""), searchLogsRequest.getTxnReferenceNumber(), searchLogsRequest.getUsername(),searchLogsRequest.getOpaque());
        return results;
    }

    @GetMapping("otp")
    public String searchOtp(){
        return "By default, if above post mapping for otp search is implemented and get mapping is not used, spring will return \n" +
                "success response for get method of this path. Hence writing this message to suggest the use of post method to \n" +
                "retrieve otp logs. ";
    }

    @GetMapping("filelist")
    public List<String> searchFileList(@RequestBody FileListRequest fileListRequest, @PathVariable("envName") String envName){
        LocalDate dateInput = null;
        List<String> fileListResults = new ArrayList<>();
        if (fileListRequest.getDate()==null){
            dateInput = LocalDate.now();
        } else {
            dateInput = fileListRequest.getDate();
        }

        if (fileListRequest.isSso()){
            //based on above processing, dateInput will never be null
            List<String> tempList = logViewerService.getSSOLogs(envName,dateInput.toString().replaceAll("-",""));
            fileListResults.addAll(tempList);
        }

        return fileListResults;
    }
}
