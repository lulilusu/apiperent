package com.tester.config;

import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;

public class TestConfig {
    public static String addUserUrl;
    public static String getUserInfoUrl;
    public static String getUserListUrl;
    public static String loginUrl;
    public static String updataUserInfoUrl;

    public static CloseableHttpClient httpClient;
    public static BasicCookieStore cookieStore;


}
