package com.tester.hello.server;

import com.tester.hello.bean.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@Api(value = "/", description = "全部的POST方法")
public class PostCookiesMethod {

    private Cookie cookie;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation(value = "登录获取cookies", httpMethod = "POST")
    public String login(@RequestParam(value = "userName", required = true) String userName, @RequestParam(value = "passWord", required = true) String passWord
            , HttpServletResponse response) {
        Cookie cookie = new Cookie("isLogin", "true");
        response.addCookie(cookie);
        if (userName.equals("admin") && passWord.equals("12345")) {
            return "登录成功";
        }
        return "密码或用户名错误";
    }

    @RequestMapping(value = "/getUserList", method = RequestMethod.POST)
    @ApiOperation(value = "获取用户列表", httpMethod = "POST")
    public String getUserList(HttpServletRequest request, @RequestBody User u) {
        Cookie[] cookies = request.getCookies();
        User user = null;

        for (Cookie c : cookies) {
            if (c.getName().equals("isLogin") && c.getValue().equals("true")
                    ) {   // 验证cookies是否合法
                user = new User();
                user.setName("lisi");
                user.setAge("18");
                user.setSex("man");
                return user.toString();
            }
        }
        return "参数不合法";
    }
}
