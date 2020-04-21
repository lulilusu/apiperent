package com.tester.config;

import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.CloseableHttpClient;

public class TestConfig {
    public static String addUserUrl;
    public static String getUserInfoUrl;
    public static String getUserListUrl;
    public static String loginUrl;
    public static String updateUserInfoUrl;

    public static CloseableHttpClient httpClient;
    public static CookieStore cookieStore;

    // httpclient4.2.3版本
//    public static DefaultHttpClient defaultHttpClient;
//    public static CookieStore store;

}
