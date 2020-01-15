package com.tester.cases;


import com.tester.config.TestConfig;
import com.tester.model.InterfaceName;
import com.tester.model.LoginCase;
import com.tester.utils.ConfigFile;
import com.tester.utils.DatabaseUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

public class LoginTest {

    @BeforeTest(groups = "loginTrue" , description = "测试前准备")
    public void beforeTest(){
        TestConfig.loginUrl = ConfigFile.getUrl(InterfaceName.LOGIN);
        TestConfig.addUserUrl = ConfigFile.getUrl(InterfaceName.ADDUSER);
        TestConfig.getUserInfoUrl = ConfigFile.getUrl(InterfaceName.GETUSERINFO);
        TestConfig.getUserListUrl = ConfigFile.getUrl(InterfaceName.GETUSERLIST);
        TestConfig.updataUserInfoUrl = ConfigFile.getUrl(InterfaceName.UPDATAUSERINFO);

        TestConfig.httpClient = HttpClientBuilder.create().build();
        TestConfig.cookieStore = new BasicCookieStore();
    }

    // 获取数据，并发起请求
    private String getResult(LoginCase loginCase) throws IOException {
        HttpPost httpPost = new HttpPost(TestConfig.loginUrl);  // 传入测试地址
        httpPost.setHeader("Content-Type" , "application/json");

        // 传入参数
        JSONObject json = new JSONObject();
        json.put("userName",loginCase.getUserName()); // 获取loginCase表数据
        json.put("password",loginCase.getPassword());
        StringEntity param = new StringEntity(json.toString(),"utf-8");
        httpPost.setEntity(param);

        CloseableHttpResponse response = TestConfig.httpClient.execute(httpPost);  // 发起请求并返回响应
        String result = EntityUtils.toString(response.getEntity(), "utf-8"); // 转换响应参数

        return result;
    }

    @Test(groups = "loginTrue" , description = "登陆成功")
    public void loginTure() throws IOException {
        SqlSession session = DatabaseUtil.getSqlSession();
        LoginCase loginCase = session.selectOne("loginCase", 1); // 查询loginCase表第一条数据

        String actual = getResult(loginCase);
        Assert.assertEquals(loginCase.getExpected(),actual);
    }

    @Test(groups = "loginFail" , description = "登陆失败")
    public void loginFail() throws IOException {
        SqlSession session = DatabaseUtil.getSqlSession();
        LoginCase loginCase = session.selectOne("loginCase", 2); // 查询loginCase表第一条数据

        String actual = getResult(loginCase);
        Assert.assertEquals(loginCase.getExpected(),actual);
    }

}
