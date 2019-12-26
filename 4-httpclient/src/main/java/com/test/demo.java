package com.test;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * Hello world!
 */
public class demo {

    @Test
    public void get(){
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        String resp = null;
        String url ="http://www.baidu.com";
        HttpGet get = new HttpGet(url);
        try {
            CloseableHttpResponse response = httpClient.execute(get);
//            InputStream content = response.getEntity().getContent();
//            String res = IOUtils.toString(content, "utf-8");
//            System.out.println(res);

            resp = EntityUtils.toString(response.getEntity(), "utf-8");
            System.out.println(resp);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
