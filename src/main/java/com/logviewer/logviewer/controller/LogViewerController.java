package com.logviewer.logviewer.controller;

import com.logviewer.logviewer.service.LogViewerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("logs/{envName}")
public class LogViewerController {

    Logger logger = LoggerFactory.getLogger(LogViewerController.class);

    @Autowired
    private LogViewerService logViewerService;

    @GetMapping("ping")
    public String ping(){
        return "pinged";
    }

    @GetMapping("today")
    public List<String> getAllLogsForToday(@PathVariable("envName") String envName){
        return logViewerService.getAllLogsForToday(envName);
    }
    @GetMapping("{fileName}")
    public byte[] getSingleLogs(@PathVariable("envName") String envName, @PathVariable("fileName") String fileName){
        logger.info("Retrieving logs for "+fileName);
        return logViewerService.getSingleLog(envName,fileName);
    }
}
