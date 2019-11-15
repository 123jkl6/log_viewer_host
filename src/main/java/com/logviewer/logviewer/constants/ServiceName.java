package com.logviewer.logviewer.constants;

import com.google.common.collect.ImmutableList;

import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ServiceName {

    public static final ImmutableList<String> SERVICE_NAMES;

    public static final String GET_RANDOM_NUMBER;
    public static final String PING;
    public static final String LOGIN_1FA;
    public static final String GENERATE_OTP;
    public static final String LOGIN_2FA_SMS;
    public static final String LOGIN_2FA_TOKEN;

    static {
        List<String> serviceNames = new ArrayList<String>();
        ClassPathResource cl = new ClassPathResource("service_names");

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(cl.getURL().openStream()))) {
            Stream<String> stream = br.lines();
            serviceNames = stream.collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        SERVICE_NAMES = ImmutableList.<String>builder()
                .addAll(serviceNames)
                .build();
        GET_RANDOM_NUMBER = SERVICE_NAMES.get(0);
        PING = SERVICE_NAMES.get(1);
        LOGIN_1FA = SERVICE_NAMES.get(2);
        GENERATE_OTP = SERVICE_NAMES.get(3);
        LOGIN_2FA_SMS = SERVICE_NAMES.get(4);
        LOGIN_2FA_TOKEN=  SERVICE_NAMES.get(5);
    }
}
