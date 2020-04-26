package com.test;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

public class ExtenedDemo {

    @Test
   public void test1(){
       Assert.assertEquals(1,1);
   }
    @Test
    public void test2(){
        Assert.assertEquals(1,3);
    }
    @Test
    public void test3(){
        Assert.assertEquals(1,1);
    }

    public void reportLog(){
        Reporter.log("自己的日志");
        throw new RuntimeException("自己的异常");
    }
}
