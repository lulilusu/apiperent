package com.tester.hello.server;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@Api(value = "/" , description = "所有get方法")
public class GetCookiesMethod {

    /**
     * 获取cookies
     * @param response
     * @return
     */
    @RequestMapping(value = "/getCookies" , method = RequestMethod.GET)
    @ApiOperation(value = "获取cookies值",httpMethod = "GET")
    public String getCookies(HttpServletResponse response){
        Cookie cookie = new Cookie("isOpen","true");
        response.addCookie(cookie);
        return "恭喜你获得cookie";
    }

    /**
     * 需要带cookies的get请求
     * @param request
     * @return
     */
    @RequestMapping(value = "/get/with/cookies", method = RequestMethod.GET)
    @ApiOperation(value = "需要cookies的get",httpMethod = "GET")
    public String getWithCookies(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
//        if (Objects.isNull(cookies)){
//            return "需要携带cookies";
//        }
        if (ObjectUtils.isEmpty(cookies)){
            return "需要携带cookies";
        }
        for (Cookie cookie : cookies){
            if (cookie.getName().equals("isOpen") && cookie.getValue().equals("true")){
                return "访问成功";
            }
        }
        return "需要携带cookies";
    }

    /**
     *  1.待参数的get请求
     * @param start
     * @param end
     * @return
     */
    @RequestMapping(value = "/get/with/param" , method = RequestMethod.GET)
    @ApiOperation(value = "待参数的get请求",httpMethod = "GET")
    public Map<String, Integer> getWithParam(@RequestParam String start, @RequestParam Integer end){
        Map<String, Integer> map = new HashMap<>();
        map.put("衣服", 399);
        map.put("鞋子", 299);
        map.put("电脑", 199);
        return map;
    }
    /**
     *  2.待参数的get请求
     * @param start
     * @param end
     * @return
     */
    @RequestMapping(value = "/get/with/param/{start}/{end}" , method = RequestMethod.GET)
    @ApiOperation(value = "待参数的get请求2",httpMethod = "GET")
    public Map<String, Integer> getWithParam2(@PathVariable String start, @PathVariable Integer end){
        Map<String, Integer> map = new HashMap<>();
        map.put("衣服", 359);
        map.put("鞋子", 299);
        map.put("电脑", 199);
        return map;
    }
}
