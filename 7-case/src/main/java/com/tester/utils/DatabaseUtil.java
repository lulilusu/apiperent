package com.tester.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

public class DatabaseUtil {
    public static SqlSession getSqlSession() throws IOException {
        Reader reader = Resources.getResourceAsReader("databaseConfig.xml");     // 获取配置文件
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession sqlSession = factory.openSession();  // SqlSession执行配置文件中sql语句
        return sqlSession;
    }

    public static void main(String[] args) throws IOException {
        SqlSession sqlSession = DatabaseUtil.getSqlSession();
        System.out.println(sqlSession.toString());
    }
}
