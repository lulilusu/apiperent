package com.tester.cases;

import com.tester.config.TestConfig;
import com.tester.model.AddUserCase;
import com.tester.model.User;
import com.tester.utils.DatabaseUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class AddUserTest {

    // 获取数据，请求接口，获取响应
    public String getResult(AddUserCase addUserCase) throws IOException {
        HttpPost httpPost = new HttpPost(TestConfig.addUserUrl);
        httpPost.setHeader("content-type","application/json");
        JSONObject json = new JSONObject();
        json.put("userName",addUserCase.getUserName());
        json.put("password",addUserCase.getPassword());
        json.put("age",addUserCase.getAge());
        json.put("sex",addUserCase.getSex());
        json.put("permission",addUserCase.getPermission());
        json.put("isDelete",addUserCase.getIsDelete());
        json.put("expected",addUserCase.getExpected());
        StringEntity param = new StringEntity(json.toString(), "utf-8");
        httpPost.setEntity(param);

//        TestConfig.defaultHttpClient.setCookieStore(TestConfig.store);
//        HttpResponse response = TestConfig.defaultHttpClient.execute(post);

        CloseableHttpResponse response = TestConfig.httpClient.execute(httpPost);
        String result = EntityUtils.toString(response.getEntity(), "utf-8");
        return result;
    }



    @Test(dependsOnGroups = "loginTrue" , description = "添加用户")
    public void addUser() throws IOException {
        SqlSession session= DatabaseUtil.getSqlSession();
        AddUserCase addUserCase = session.selectOne("addUserCase", 1);
        String actual = getResult(addUserCase);

        User user = session.selectOne("addUser", addUserCase);
        // 断言
        Assert.assertEquals(addUserCase.getExpected() , actual);
    }
}
