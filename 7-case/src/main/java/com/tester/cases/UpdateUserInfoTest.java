package com.tester.cases;

import com.tester.config.TestConfig;
import com.tester.model.UpdateUserInfoCase;
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


public class UpdateUserInfoTest {


    @Test(dependsOnGroups = "loginTrue", description = "更新信息")
    public void updataUserInfo() throws Exception {
        SqlSession session = DatabaseUtil.getSqlSession();
        UpdateUserInfoCase updateUserInfoCase = session.selectOne("updateUserInfoCase", 2);

        int result = getResult(updateUserInfoCase);
        Thread.sleep(3000);

        User user = session.selectOne(updateUserInfoCase.getExpected(), updateUserInfoCase);
        Assert.assertNull(user);
        Assert.assertNull(result);
    }

    @Test(dependsOnGroups = "loginTrue", description = "删除信息")
    public void deleteUser() throws Exception {
        SqlSession session = DatabaseUtil.getSqlSession();
        UpdateUserInfoCase updateUserInfoCase = session.selectOne("updateUserInfoCase", 1);

        int result = getResult(updateUserInfoCase);
        Thread.sleep(3000);

        User user = session.selectOne(updateUserInfoCase.getExpected(), updateUserInfoCase);
        Assert.assertNull(user);
        Assert.assertNull(result);
    }

    private int getResult(UpdateUserInfoCase updateUserInfoCase) throws Exception {
        HttpPost post = new HttpPost(TestConfig.updataUserInfoUrl);
        post.setHeader("Content-Type", "application/json");
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("id", updateUserInfoCase.getUserId());  // 接口字段名是user表id，所以这里取值updataUserInfoCase表userId
        jsonParam.put("userName", updateUserInfoCase.getUserName());
        jsonParam.put("sex", updateUserInfoCase.getSex());
        jsonParam.put("age", updateUserInfoCase.getAge());
        jsonParam.put("permission", updateUserInfoCase.getPermission());
        jsonParam.put("isDelete", updateUserInfoCase.getIsDelete());

        StringEntity entity = new StringEntity(jsonParam.toString());
        post.setEntity(entity);

        CloseableHttpResponse response = TestConfig.httpClient.execute(post);
        String result = EntityUtils.toString(response.getEntity(), "utf-8");

        return Integer.parseInt(result);   //将结果返回为整数
    }
}
