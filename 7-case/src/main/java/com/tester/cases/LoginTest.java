package com.tester.cases;


import com.tester.config.TestConfig;
import com.tester.model.InterfaceName;
import com.tester.model.LoginCase;
import com.tester.utils.ConfigFile;
import com.tester.utils.DatabaseUtil;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.*;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

public class LoginTest {

    @BeforeTest(groups = "loginTrue", description = "测试前准备")
    public void beforeTest() {
        TestConfig.loginUrl = ConfigFile.getUrl(InterfaceName.LOGIN);
        TestConfig.addUserUrl = ConfigFile.getUrl(InterfaceName.ADDUSER);
        TestConfig.getUserInfoUrl = ConfigFile.getUrl(InterfaceName.GETUSERINFO);
        TestConfig.getUserListUrl = ConfigFile.getUrl(InterfaceName.GETUSERLIST);
        TestConfig.updataUserInfoUrl = ConfigFile.getUrl(InterfaceName.UPDATAUSERINFO);

        TestConfig.httpClient = HttpClients.createDefault();
        TestConfig.cookieStore = new BasicCookieStore();   // 实例化cookie store
        //  设置cookie
        RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build();    // 全局请求
        TestConfig.httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).setDefaultCookieStore(TestConfig.cookieStore).build();

//        TestConfig.defaultHttpClient = new DefaultHttpClient();
    }


    @Test(groups = "loginTrue", description = "登陆成功")
    public void loginTrue() throws IOException {
        SqlSession session = DatabaseUtil.getSqlSession();
        LoginCase loginCase = session.selectOne("loginCase", 1); // 查询loginCase表第一条数据
        System.out.println(loginCase.toString());
        System.out.println(TestConfig.loginUrl);

        String actual = getResult(loginCase);
        Assert.assertEquals(loginCase.getExpected(), actual);
    }

    @Test(groups = "loginFail", description = "登陆失败")
    public void loginFail() throws IOException {
        SqlSession session = DatabaseUtil.getSqlSession();
        LoginCase loginCase = session.selectOne("loginCase", 2); // 查询loginCase表第二条数据
        System.out.println(loginCase.toString());
        System.out.println(TestConfig.loginUrl);

        String actual = getResult(loginCase);
        Assert.assertEquals(loginCase.getExpected(), actual);
    }

    // 获取数据，并发起请求
    private String getResult(LoginCase loginCase) throws IOException {
        HttpPost httpPost = new HttpPost(TestConfig.loginUrl);  // 传入测试地址
        // 传入参数
        JSONObject json = new JSONObject();
        json.put("userName", loginCase.getUserName()); // 获取loginCase表数据
        json.put("password", loginCase.getPassword());
        httpPost.setHeader("Content-Type", "application/json");  // 设置参数格式
        StringEntity param = new StringEntity(json.toString(), "utf-8");
        httpPost.setEntity(param);

        CloseableHttpResponse response = TestConfig.httpClient.execute(httpPost);  // 发起请求并返回响应
        String result = EntityUtils.toString(response.getEntity(), "utf-8"); // 转换响应参数

//        HttpResponse response = TestConfig.defaultHttpClient.execute(httpPost);
//        String result = EntityUtils.toString(response.getEntity(), "utf-8");
//        TestConfig.store = TestConfig.defaultHttpClient.getCookieStore();     // 4.2.3版本获取cookies

        return result;
    }
}
