package com.logviewer.logviewer.controller;

import com.logviewer.logviewer.model.logviewer.SearchLogsRequest;
import com.logviewer.logviewer.service.LogViewerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

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

    @GetMapping("search")
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

        return results;
    }

    @GetMapping("{fileName}")
    public byte[] getSingleLogs(@PathVariable("envName") String envName, @PathVariable("fileName") String fileName){
        logger.info("Retrieving logs for "+fileName);
        byte[] logs = logViewerService.getSingleLog(envName,fileName);
        logger.info("Retrieved logs for "+fileName);
        return logs;
    }
}
