package com.logviewer.logviewer.controller;

import com.logviewer.logviewer.logwriter.Logwriter;
import com.logviewer.logviewer.model.CryptoRequest;
import com.logviewer.logviewer.model.RandomNumber;
import com.logviewer.logviewer.service.AppService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class Controller {

    @Value("${env.name}")
    String envName;

    Logger logger = LoggerFactory.getLogger(Controller.class);

    @Autowired
    AppService appService;

    @GetMapping("ping")
    public String ping(){
        return "pinged";
    }

    @PostMapping("crypto/random")
    public RandomNumber getRandomNumber(@RequestBody CryptoRequest cryptoRequest) throws IOException {
        RandomNumber rand = appService.getRandomNumber();
        rand.setResponseCode("0");
        logger.info("RQ_JSON="+cryptoRequest.toString());
        logger.info("RS_JSON="+rand.toString());
        Logwriter.writeLogs(cryptoRequest.toString(),rand.toString(),"getRandomNumber",null,this.envName);
        return rand;
    }
}
