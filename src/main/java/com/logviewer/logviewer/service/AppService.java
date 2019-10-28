package com.logviewer.logviewer.service;

import com.logviewer.logviewer.model.RandomNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class AppService {
    Logger logger = LoggerFactory.getLogger(AppService.class);

    public RandomNumber getRandomNumber(){
        logger.info(AppService.class.toString()+" : getRandomNumber()");
        SecureRandom sr = new SecureRandom() ;
        Integer randomInt = sr.nextInt(1000000);
        String randomIntInHex =  Integer.toHexString(randomInt);
        RandomNumber rand = new RandomNumber();
        rand.setRandom(randomIntInHex);
        logger.info("random : "+randomIntInHex);
        return rand;
    }

    public String getPublicKey(){

        return "";
    }
}
