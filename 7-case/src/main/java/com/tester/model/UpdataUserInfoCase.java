package com.tester.model;

import lombok.Data;

@Data
public class UpdataUserInfoCase {
    private int id;
    private int userId;
    private String userName;
    private String age;
    private String sex;
    private String permission;
    private String isDelete;
    private String expected;
}
