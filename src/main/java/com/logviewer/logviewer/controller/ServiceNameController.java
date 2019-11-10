package com.logviewer.logviewer.controller;

import com.logviewer.logviewer.dao.LogViewerDAO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("logs")
public class ServiceNameController {

    @Autowired
    private LogViewerDAO logViewerDAO;

    @GetMapping("services")
    public List<String> getServiceNames(){
    List<String> serviceNames = logViewerDAO.getServiceNames();
        return serviceNames;
    }
}
