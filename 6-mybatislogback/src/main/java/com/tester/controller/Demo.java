package com.tester.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Api(value = "/" , description = "mybatisdemo")
public class Demo {
    @Autowired
    private SqlSessionTemplate template;

    @RequestMapping(value = "/getUserCount" , method = RequestMethod.GET)
    @ApiOperation(value = "获取用户数" , httpMethod = "Get")
    public int getUserCount(){
        return template.selectOne("getUserCount");
    }

}
