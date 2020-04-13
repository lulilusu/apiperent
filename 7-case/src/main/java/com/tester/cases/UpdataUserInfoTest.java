package com.tester.cases;

import com.tester.config.TestConfig;
import com.tester.model.UpdataUserInfoCase;
import com.tester.model.User;
import com.tester.utils.DatabaseUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;


public class UpdataUserInfoTest {


    @Test(dependsOnGroups = "loginTrue", description = "更新信息")
    public void updataUserInfo() throws Exception {
        SqlSession session = DatabaseUtil.getSqlSession();
        UpdataUserInfoCase updataUserInfoCase = session.selectOne("updateUserInfoCase", 2);

        int result = getResult(updataUserInfoCase);
        Thread.sleep(3000);

        User user = session.selectOne(updataUserInfoCase.getExpected(), updataUserInfoCase);
        Assert.assertNull(user);
        Assert.assertNull(result);
    }

    @Test(dependsOnGroups = "loginTrue", description = "删除信息")
    public void deleteUser() throws Exception {
        SqlSession session = DatabaseUtil.getSqlSession();
        UpdataUserInfoCase updataUserInfoCase = session.selectOne("updateUserInfoCase", 1);

        int result = getResult(updataUserInfoCase);
        Thread.sleep(3000);

        User user = session.selectOne(updataUserInfoCase.getExpected(), updataUserInfoCase);
        Assert.assertNull(user);
        Assert.assertNull(result);
    }

    private int getResult(UpdataUserInfoCase updataUserInfoCase) throws Exception {
        HttpPost post = new HttpPost(TestConfig.updataUserInfoUrl);
        post.setHeader("Content-Type", "application/json");
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("id", updataUserInfoCase.getUserId());  // 接口字段名是user表id，所以这里取值updataUserInfoCase表userId
        jsonParam.put("userName", updataUserInfoCase.getUserName());
        jsonParam.put("sex", updataUserInfoCase.getSex());
        jsonParam.put("age", updataUserInfoCase.getAge());
        jsonParam.put("permission", updataUserInfoCase.getPermission());
        jsonParam.put("isDelete", updataUserInfoCase.getIsDelete());

        StringEntity entity = new StringEntity(jsonParam.toString());
        post.setEntity(entity);

        CloseableHttpResponse response = TestConfig.httpClient.execute(post);
        String result = EntityUtils.toString(response.getEntity(), "utf-8");

        return Integer.parseInt(result);   //将结果返回为整数
    }
}
