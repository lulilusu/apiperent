package com.tester.utils;

import com.tester.model.InterfaceName;

import java.util.Locale;
import java.util.ResourceBundle;

public class ConfigFile {

    static ResourceBundle bundle =ResourceBundle.getBundle("application", Locale.CHINA);  // 读取配置文件application.properties
    // 拼接接口地址
    public static String getUrl(InterfaceName interfaceName){
        String uri = "";
        if (interfaceName == InterfaceName.LOGIN){
            uri = bundle.getString("login.uri");
        }
        if (interfaceName == InterfaceName.ADDUSER){
            uri = bundle.getString("addUser.uri");
        }
        if (interfaceName == InterfaceName.UPDATAUSERINFO){
            uri = bundle.getString("updataUserInfo.uri");
        }
        if (interfaceName == InterfaceName.GETUSERLIST){
            uri = bundle.getString("getUserList.uri");
        }
        if (interfaceName == InterfaceName.GETUSERINFO){
            uri = bundle.getString("getUserInfo.uri");
        }
        String address = bundle.getString("test.url"); // 环境地址
        String url = address + uri;  // 最终测试地址
        return url;
    }
}
