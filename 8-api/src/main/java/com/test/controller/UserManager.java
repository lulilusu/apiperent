package com.test.controller;

import com.test.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@Api
@RequestMapping
public class UserManager {

    @Autowired
    private SqlSessionTemplate template;

    @ApiOperation(value = "登录接口" ,httpMethod = "POST")
    @RequestMapping(value = "/login" , method = RequestMethod.POST)
    public boolean login(@RequestBody User user, HttpServletResponse response){
        int i = template.selectOne("login", user);
        Cookie cookie = new Cookie("login" , "true");
        response.addCookie(cookie);
        log.info("查看结果:" + i );
        if (i == 1){
            log.info("登录用户是:" + user.getUserName());
            return true;
        }
        return false;
    }

    @ApiOperation(value = "添加用户" ,httpMethod = "POST")
    @RequestMapping(value = "/addUser" , method = RequestMethod.POST)
    public boolean addUser(@RequestBody User user, HttpServletRequest request){
        boolean isCookie = verifyCookies(request);
        int result = 0;
        if (isCookie == true){
            result = template.insert("addUser", user);
        }
        if (result > 0){
            log.info("成功添加" + result + "用户");
            return true;
        }
        return false;
    }


    @ApiOperation(value = "获取用户信息(列表)" ,httpMethod = "POST")
    @RequestMapping(value = "/getUserInfo" , method = RequestMethod.POST)
    public List<User> getUserInfo(@RequestBody User user, HttpServletRequest request){
        boolean x = this.verifyCookies(request);
        if (x == true){
            List<User> list = template.selectList("getUserInfo", user);
            log.info("获取用户信息成功");
            return list;
        }else {
            log.info("获取用户信息失败");
            return null;
        }
    }

    @ApiOperation(value = "更新用户信息" ,httpMethod = "POST")
    @RequestMapping(value = "/updateUserInfo" , method = RequestMethod.POST)
    public int updateUserInfo(@RequestBody User user, HttpServletRequest request){
        boolean x = this.verifyCookies(request);
        int i = 0;
        if (x == true){
            i = template.update("updateUserInfo", user);
        }
        log.info("更新条数" + i);
        return i;
    }

    private boolean verifyCookies(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        boolean isCookie = false;
        if (Objects.isNull(cookies)){
            log.info("cookie为空");
            return isCookie = false;
        }
        for (Cookie cookie: cookies){
            if (cookie.getName().equals("login") && cookie.getValue().equals("true")){
                log.info("cookie 验证通过");
                return isCookie = true;
            }
        }
        return isCookie;
    }
}
