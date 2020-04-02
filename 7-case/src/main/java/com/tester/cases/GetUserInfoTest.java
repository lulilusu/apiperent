package com.tester.cases;

import com.tester.config.TestConfig;
import com.tester.model.GetUserInfoCase;
import com.tester.model.User;
import com.tester.utils.DatabaseUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetUserInfoTest {

    @Test(dependsOnGroups = "loginTrue" , description = "获取userId为1用户信息")
    public void getUserInfo() throws IOException {
        SqlSession session = DatabaseUtil.getSqlSession();
        GetUserInfoCase getUserInfoCase = session.selectOne("getUserInfoCase", 1);
        JSONObject json = new JSONObject();

        JSONArray josnResult = getJosnResult(getUserInfoCase);  // 获取实际结果  将id 为1 数据传入
        User user = session.selectOne(getUserInfoCase.getExpected(), getUserInfoCase); // 获取预期结果数据

        List userList = new ArrayList();
        userList.add(user);

        JSONArray expected = new JSONArray((userList));  //转换预期结果
        JSONArray actual = new JSONArray(josnResult.getString(0));  // 转换实际结果

        Assert.assertEquals(expected.toString(),actual.toString());
    }

    // 实际结果
    private JSONArray getJosnResult (GetUserInfoCase getUserInfoCase) throws IOException {
        HttpPost post = new HttpPost(TestConfig.getUserInfoUrl); // 设置请求URL
        JSONObject json = new JSONObject();
        post.setHeader("Content-Type" , "application/json"); // 设置post请求头部
        // 获取请求参数请求
        json.put("id", getUserInfoCase.getUserId());
        StringEntity entity = new StringEntity(json.toString(), "utf-8");
        post.setEntity(entity);

        // 设置请求cookie
        TestConfig.cookieStore = new BasicCookieStore();   // 创建cookie实例
        TestConfig.httpClient = HttpClients.custom().setDefaultCookieStore(TestConfig.cookieStore).build(); //设置cookie

        CloseableHttpResponse response = TestConfig.httpClient.execute(post);  // 发起请求并返回值
        String result = EntityUtils.toString(response.getEntity(), "utf-8"); // 转换返回值

        // 转为json数组
        List resultList = Arrays.asList(result);
        JSONArray jsonArray = new JSONArray(resultList);

        return jsonArray;
    }
}
