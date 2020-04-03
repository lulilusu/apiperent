package com.tester.cases;

import com.sun.org.apache.regexp.internal.RE;
import com.tester.config.TestConfig;
import com.tester.model.GetUserListCase;
import com.tester.utils.DatabaseUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class GetUserListTest {

    @Test(dependsOnGroups = "loginTrue" , description = "获取用户性别为男的用户信息")
    public void getUserListInfo() throws IOException {
        SqlSession session = DatabaseUtil.getSqlSession();
        GetUserListCase getUserListCase = session.selectOne("getUserList", "man");

        JSONArray jsonResult = getJsonResult(getUserListCase);
        List list = session.selectList(getUserListCase.getExpected(), getUserListCase);

        JSONArray userListJson = new JSONArray(list);
        Assert.assertEquals(userListJson.length() , jsonResult.length());
        for (int i = 0; i < jsonResult.length(); i ++){
            JSONObject actual = (JSONObject)jsonResult.get(i);
            JSONObject expected = (JSONObject)userListJson.get(i);
            Assert.assertEquals(expected.toString(), actual.toString());
        }
        
    }


    private JSONArray getJsonResult (GetUserListCase getUserListCase) throws IOException {
        HttpPost post = new HttpPost(TestConfig.getUserListUrl);
        JSONObject json = new JSONObject();
        json.put("userName" , getUserListCase.getUserName());
        json.put("age", getUserListCase.getAge());
        json.put("sex", getUserListCase.getSex());

        post.setHeader("Content-Type" , "application/json");
        StringEntity entity = new StringEntity(json.toString(), "utf-8");
        post.setEntity(entity);

        CloseableHttpResponse response = TestConfig.httpClient.execute(post);
        String result = EntityUtils.toString(response.getEntity(), "utf-8");
        JSONArray array = new JSONArray(result);

        return array;
    }
}
