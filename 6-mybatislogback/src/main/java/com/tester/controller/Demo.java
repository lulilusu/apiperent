package com.tester.controller;

import com.tester.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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

    @RequestMapping(value = "/addUser" , method = RequestMethod.POST)
    @ApiOperation(value = "添加用户", httpMethod = "POST")
    public int addUser(@RequestBody User user){
        return template.insert("addUser" , user);
    }

    @RequestMapping(value = "/updataUser" , method = RequestMethod.POST)
    public int updataUser(@RequestBody User user){
        return template.update("updataUser" , user);
    }

    @RequestMapping(value = "/deleteUser" , method = RequestMethod.GET)
    public int deleteUser(@RequestParam int id){
        return template.delete("deleteUser" , id);
    }

}
