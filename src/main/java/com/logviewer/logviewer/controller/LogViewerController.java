package com.logviewer.logviewer.controller;

import com.logviewer.logviewer.service.LogViewerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("logs")
public class LogViewerController {

    @Autowired
    private LogViewerService logViewerService;

    @GetMapping("ping")
    public String ping(){
        return "pinged";
    }

    @GetMapping("/{envName}/today")
    public List<String> getAllLogsForToday(@PathVariable("envName") String envName){
        return logViewerService.getAllLogsForToday(envName);
    }
}
