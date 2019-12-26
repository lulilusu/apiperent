package com.utils;


import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class HttpClientDemo {

    CloseableHttpClient httpClient = null;
    BasicCookieStore cookieStore = null;
    private String url = null;
    private ResourceBundle bundle = null;

    @BeforeTest
    public void httpGet() {
        bundle = ResourceBundle.getBundle("application", Locale.CHINA); // 获取配置文件
        url = bundle.getString("test.url"); // 获取环境域名
        RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build();// 设置cookies全局请求
        cookieStore = new BasicCookieStore();  // 创建cookies实例
        httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).setDefaultCookieStore(cookieStore).build();  // 创建httpclient并设置cookies

    }

    @Test
    public void getCookies() throws IOException {
        String result = null;
        HttpGet httpGet = new HttpGet(url + bundle.getString("cookies.uri"));  //拼接接口地址并发送get请求
        CloseableHttpResponse response = httpClient.execute(httpGet);
        result = EntityUtils.toString(response.getEntity());
        System.out.println(result);

        // 获取cookies信息
        List<Cookie> cookies = cookieStore.getCookies();
        for (Cookie cookie : cookies) {
            System.out.println(cookie.getName() + "="+  cookie.getValue());
        }
    }

    @Test
    public void sendWithCookieGet() throws IOException {
        HttpGet httpGet = new HttpGet(url +bundle.getString("test.with.cookies.get"));
        CloseableHttpResponse response = httpClient.execute(httpGet);
        String s = EntityUtils.toString(response.getEntity());
        System.out.println(s);

    }

    @Test
    public void sendWithCookiePost() throws IOException {
        HttpPost httppost = new HttpPost(url +bundle.getString("test.with.cookies.post"));
        httppost.setHeader("Content-Type","application/json;charset=utf-8");

        // 设置参数
        JSONObject param = new JSONObject();
        param.put("name","dandan");
        param.put("sex","12");
        // 传入参数
        StringEntity entity = new StringEntity(param.toString());
        httppost.setEntity(entity);
        // 发送请求
        CloseableHttpResponse response = httpClient.execute(httppost);
        String result = EntityUtils.toString(response.getEntity(), "utf-8");
        String status = response.getStatusLine().toString();
        System.out.println(result + "\n" + status );

        JSONObject res = new JSONObject(result);
        String message = res.getString("message");
        System.out.println(message);

    }

}
