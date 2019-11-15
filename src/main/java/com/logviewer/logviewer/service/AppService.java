package com.logviewer.logviewer.service;

import com.logviewer.logviewer.exceptions.FailedOTPAuthenticationException;
import com.logviewer.logviewer.model.demo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppService {

    Logger logger = LoggerFactory.getLogger(AppService.class);

    private final String alphanumericStore = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

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
            FailedOTPAuthenticationException foae = new FailedOTPAuthenticationException("Invalid OTP.");
            LogInfoBean lib= new LogInfoBean();
            lib.setTxnReferenceNumber(loginRequest.getTxnReferenceNumber());
            lib.setServiceName("login2FASMS");
            lib.setUsername(loginRequest.getUserCredentials().getUserId());
            lib.setRequestString(loginRequest.toString());
            foae.setLogInfoBean(lib);
            throw foae;
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

    public OTPResponse generateOTP(LoginRequest loginRequest){
        String txnReferenceNumber = loginRequest.getTxnReferenceNumber();
        OTPResponse otpResponse = new OTPResponse();
        SecureRandom sr = new SecureRandom();
        List<Integer> otpList = sr.ints(6,0,9).boxed().collect(Collectors.toList());
        List<Integer> opaqueList = sr.ints(10,0,35).boxed().collect(Collectors.toList());

        logger.info(txnReferenceNumber + " OTP list" + otpList);
        logger.info(txnReferenceNumber + "opaque list" + opaqueList);

        StringBuilder otpBuilder = new StringBuilder();

        for(Integer oneDigit : otpList){
            otpBuilder.append(oneDigit);
        }

        StringBuilder opaqaueBuilder = new StringBuilder();

        for (Integer oneDigit : opaqueList){
            opaqaueBuilder.append(this.alphanumericStore.charAt(oneDigit));
        }

        //create new SMSOTPObject
        SMSOTPObject smsotpObject = new SMSOTPObject();
        smsotpObject.setOtp(otpBuilder.toString());
        smsotpObject.setOpaque(opaqaueBuilder.toString());

        //set OTPResponse
        otpResponse.setResponseCode("0");
        otpResponse.setSmsOTPObject(smsotpObject);

        return otpResponse;
    }

    public String getPublicKey(){

        return "";
    }
}
