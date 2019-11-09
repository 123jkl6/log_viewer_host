package com.logviewer.logviewer.controller;

import com.logviewer.logviewer.exceptions.FailedOTPAuthenticationException;
import com.logviewer.logviewer.exceptions.MissingFieldException;
import com.logviewer.logviewer.exceptions.UnprocessableFieldException;
import com.logviewer.logviewer.logwriter.Logwriter;
import com.logviewer.logviewer.model.demo.*;
import com.logviewer.logviewer.service.AppService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;

@RestController
public class Controller {

    @Value("${env.name}")
    String envName;

    Logger logger = LoggerFactory.getLogger(Controller.class);

    @Autowired
    AppService appService;

    @GetMapping("ping")
    public String ping(@RequestBody CryptoRequest cryptoRequest) throws IOException {
        String txnReferenceNumber = null;
        if (cryptoRequest.getTxnReferenceNumber()==null){
            txnReferenceNumber = appService.generateTransactionRef();
            cryptoRequest.setTxnReferenceNumber(txnReferenceNumber);
        }
        String pingResponse = "pinged";
        logger.info(txnReferenceNumber+" ping() pinging logs service");
        logger.info(txnReferenceNumber+" RQ_JSON="+cryptoRequest!=null?cryptoRequest.toString():"");
        logger.info(txnReferenceNumber+" RS_JSON="+pingResponse);
        Logwriter.writeLogs(cryptoRequest.toString(),pingResponse.toString(), txnReferenceNumber,"ping",null,this.envName);
        return pingResponse;
    }

    @PostMapping("crypto/random")
    public RandomNumber getRandomNumber(@RequestBody CryptoRequest cryptoRequest) throws IOException {
        String txnReferenceNumber = cryptoRequest.getTxnReferenceNumber();
        logger.info(txnReferenceNumber+" getRandomNumber() ");
        RandomNumber rand = appService.getRandomNumber();
        rand.setResponseCode("0");
        logger.info(txnReferenceNumber+" getRandomNumber() done");
        logger.info(txnReferenceNumber+" RQ_JSON="+cryptoRequest.toString());
        logger.info(txnReferenceNumber+" RS_JSON="+rand.toString());
        Logwriter.writeLogs(cryptoRequest.toString(),rand.toString(), txnReferenceNumber,"getRandomNumber",null,this.envName);
        return rand;
    }

    @PostMapping("am/auth/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) throws MissingFieldException, UnprocessableFieldException, FailedOTPAuthenticationException, IOException {
        String txnReferenceNumber = loginRequest.getTxnReferenceNumber();
        String loginType = loginRequest.getLoginType();
        String serviceName = "";
        LoginResponse loginResponse = null;

        //Missing RequestBody is automatically handled.
        //validations
        logger.info(txnReferenceNumber + " Attempting login, performing validations");
        if (loginType==null || loginRequest.getUserCredentials()==null || txnReferenceNumber==null){
            logger.error(txnReferenceNumber + " There is a missing field.");
            logger.error(txnReferenceNumber + " RQ_JSON="+loginRequest.toString());
            MissingFieldException mfe = new MissingFieldException(txnReferenceNumber + " missing field");
            LogInfoBean lib = new LogInfoBean();
            lib.setTxnReferenceNumber(txnReferenceNumber);
            lib.setRequestString(loginRequest.toString());
            mfe.setLogInfoBean(lib);
            throw mfe;
        }
        String username = loginRequest.getUserCredentials().getUserId();
        logger.info(txnReferenceNumber + "Initial validations completed, proceeding with loginType " + loginType +" for " + username!=null?username:"");

        if (loginType.equals("AUTH001")) {
            serviceName = "login1FA";
            //performing validations for username and password field
            if (username==null){
                logger.error(txnReferenceNumber + " username is missing");
                logger.error(txnReferenceNumber + " RQ_JSON="+loginRequest.toString());
                MissingFieldException mfe = new MissingFieldException(txnReferenceNumber + "username is missing");
                LogInfoBean lib = new LogInfoBean();
                lib.setTxnReferenceNumber(txnReferenceNumber);
                lib.setServiceName(serviceName);
                lib.setRequestString(loginRequest.toString());
                mfe.setLogInfoBean(lib);
                throw mfe;
            }
            if (loginRequest.getUserCredentials().getEncryptedPin()==null){
                logger.error(txnReferenceNumber + " encryptedPin is missing");
                logger.error(txnReferenceNumber + " RQ_JSON="+loginRequest.toString());
                MissingFieldException mfe = new MissingFieldException(txnReferenceNumber + "enryptedPin is missing");
                LogInfoBean lib = new LogInfoBean();
                lib.setTxnReferenceNumber(txnReferenceNumber);
                lib.setUsername(username);
                lib.setServiceName(serviceName);
                lib.setRequestString(loginRequest.toString());
                mfe.setLogInfoBean(lib);
                throw mfe;
            }

            loginResponse = appService.login1FA(loginRequest);
        } else if (loginType.equals("AUTH004")) {
            serviceName = "login2FASMS";
            //2FA requires additional validations for certain otp-related fields
            logger.info(txnReferenceNumber + " additional validations for login2FASMS");
            if (loginRequest.getUserCredentials().getSmsOTPObject()==null){
                logger.error(txnReferenceNumber + " smsOTPObject is missing");
                logger.error(txnReferenceNumber + " RQ_JSON="+loginRequest.toString());
                MissingFieldException mfe = new MissingFieldException(txnReferenceNumber + " smsOTPObject is missing");
                LogInfoBean lib = new LogInfoBean();
                lib.setTxnReferenceNumber(txnReferenceNumber);
                lib.setUsername(username);
                lib.setServiceName(serviceName);
                lib.setRequestString(loginRequest.toString());
                mfe.setLogInfoBean(lib);
                throw mfe;
            }
            SMSOTPObject smsOTPObject = loginRequest.getUserCredentials().getSmsOTPObject();
            if (smsOTPObject.getOtp()==null || smsOTPObject.getOpaque()==null){
                logger.error(txnReferenceNumber + " otp or opaque is missing");
                logger.error(txnReferenceNumber + " RQ_JSON="+loginRequest.toString());
                MissingFieldException mfe = new MissingFieldException(txnReferenceNumber + " otp or opaque is missing");
                LogInfoBean lib = new LogInfoBean();
                lib.setTxnReferenceNumber(txnReferenceNumber);
                lib.setUsername(username);
                lib.setServiceName(serviceName);
                lib.setRequestString(loginRequest.toString());
                mfe.setLogInfoBean(lib);
                throw mfe;
            }

            logger.info(txnReferenceNumber + " additional validations for 2FA SMS is complete, proceeding with login2FASMS");
            loginResponse = appService.login2FASMS(loginRequest);
//            HttpClientErrorException error = HttpClientErrorException.create(HttpStatus.NOT_IMPLEMENTED,"Not implemented yet.", null,null,null);
//            throw error;
        } else if (loginType.equals("AUTH005")) {
            serviceName = "login2AToken";
            HttpClientErrorException error = HttpClientErrorException.create(HttpStatus.NOT_IMPLEMENTED,"Not implemented yet", null,null,null);
            throw error;
        } else {
            //not a recognised loginType
            HttpClientErrorException error = HttpClientErrorException.create(HttpStatus.UNPROCESSABLE_ENTITY,"Not a recognised loginType", null,null,null);
            throw error;
        }

        //if it reaches here, there is not issue, can return response accordingly
        logger.info(txnReferenceNumber + " " + loginType + " " + serviceName + " login() function is complete. ");
        logger.info(txnReferenceNumber + " RQ_JSON="+loginRequest.toString());
        logger.info(txnReferenceNumber + " RS_JSON="+loginResponse.toString());
        Logwriter.writeLogs(loginRequest.toString(),loginResponse.toString(),txnReferenceNumber,serviceName,username,envName);
        return loginResponse;
    }
}
