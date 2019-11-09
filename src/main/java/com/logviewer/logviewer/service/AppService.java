package com.logviewer.logviewer.service;

import com.logviewer.logviewer.exceptions.FailedOTPAuthenticationException;
import com.logviewer.logviewer.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    public String generateTransactionRef(){
        SecureRandom sr = new SecureRandom();
        Long txnRef = sr.nextLong();
        return txnRef.toString();
    }

    public LoginResponse login1FA(LoginRequest loginRequest){
        //stubbed login 1FA impl
        LoginResponse loginResponse = new LoginResponse();

        //set userinfo details
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(loginRequest.getUserCredentials().getUserId());
        userInfo.setCin("O3848168");
        userInfo.setCinSuffix("00");
        userInfo.setTokenSysGenId("H999999999");
        userInfo.setOtpInStatus("0");
        userInfo.setLastLoginDate(LocalDate.now());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String lastLoginTime = LocalDateTime.now().format(formatter);
        userInfo.setLastLoginInTime(lastLoginTime);

        //create iamToken
        IAMToken iamToken = new IAMToken();
        iamToken.setSessionId(generateTransactionRef());
        iamToken.setUserId(loginRequest.getUserCredentials().getUserId());

        //consolidating response
        loginResponse.setResponseCode("0");
        loginResponse.setIamToken(iamToken);
        loginResponse.setUserInfo(userInfo);

        return loginResponse;
    }

    public LoginResponse login2FASMS(LoginRequest loginRequest) throws FailedOTPAuthenticationException {
        //stubbed login 2FA impl
        String otp = loginRequest.getUserCredentials().getSmsOTPObject().getOtp();
        //accept only 1 otp for now
        if (!otp.equals("111111")){
            throw new FailedOTPAuthenticationException("Invalid OTP.");
        }

        LoginResponse loginResponse = new LoginResponse();

        //create iamToken
        IAMToken iamToken = new IAMToken();
        iamToken.setSessionId(generateTransactionRef());
        iamToken.setUserId(loginRequest.getUserCredentials().getUserId());

        //consolidating response
        loginResponse.setResponseCode("0");
        loginResponse.setIamToken(iamToken);

        return loginResponse;
    }
    public LoginResponse login2AToken(LoginRequest loginRequest){


        return null;
    }

    public String getPublicKey(){

        return "";
    }
}
