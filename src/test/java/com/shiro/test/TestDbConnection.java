package com.shiro.test;

import com.mysql.jdbc.Driver;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


/**
 * Created by user on 2018/4/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-*.xml")
public class TestDbConnection {

    /** 测试数据库连接*/
    @Test public void testDbConnection() throws IOException, SQLException, ClassNotFoundException {
        String driverName = "com.mysql.jdbc.Driver";
        String userName = "cobra";
        String password = "Z&Tc1234567890";
        String url = "jdbc:mysql://192.168.6.163:3306/shiro?useUnicode=true&characterEncoding=UTF-8";
        Class.forName(driverName);//加载驱动
        Connection connection = DriverManager.getConnection(url,userName,password);
        PreparedStatement statement = connection.prepareStatement("show databases");
        boolean flag = statement.execute();
        connection.close();

        Assert.assertTrue(flag);
    }
}
