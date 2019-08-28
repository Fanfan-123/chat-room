package com.bittech.java.client.dao;

import com.bittech.java.client.entity.User;
import org.apache.commons.codec.digest.DigestUtils;

import javax.swing.*;
import java.sql.*;

/**
 * @Date: 2019-08-12 09:57
 * @Description:
 */
public class AccountDao extends BasedDao{
    // 用户注册
    //用户注册使用的是sql语句的增加语句：insert
    //要把用户的信息都填进去，可以定义一个User类，User类中只定义数据库中的属性，参数传入一个User类
    public boolean userReg(User user) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            String sql = "INSERT INTO user(username, password,brief) " +
                    " VALUES(?,?,?) ";
            statement = connection.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            //将传入的User类中的username，password，brief设为sql语句中要插入的值
            statement.setString(1,user.getUserName());
            //对密码进行加密
            statement.setString(2, DigestUtils.md5Hex(user.getPassword()));
            statement.setString(3,user.getBrief());
            //受影响的行数
            //返回1，则说明有一行数据插入成功
            int rows = statement.executeUpdate();
            if (rows == 1)
                return true;
        }catch (SQLException e) {
            //否则，就是插入失败
            System.err.println("用户注册失败");
            e.printStackTrace();
        }finally {
            //关闭资源
            closeResources(connection,statement);
        }
        return false;
    }

    //用户登录
    //用户登录的话就不需要整个User类，只需要知道username和password就好
    //登陆使用的是sql语句的select语句
    public User userLogin(String userName,String password) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            String sql = "SELECT * FROM user WHERE username = ? AND password = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1,userName);
            statement.setString(2,DigestUtils.md5Hex(password));
            //statement.executeQuery()：用来产生单个结果集
            resultSet = statement.executeQuery();
            //如果返回的结果集不为空，则登陆成功
            if (resultSet.next()) {
                User user = getUser(resultSet);
                return user;
            }
        }catch (SQLException e) {
            //否则，登陆失败
            System.err.println("用户登录失败");
            e.printStackTrace();
        }finally {
            closeResources(connection,statement,resultSet);
        }
        return null;
    }

    //通过ResultSet返回的一条结果集，得到该用户的各属性
    //返回一个user
    private User getUser(ResultSet resultSet) throws SQLException{
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setUserName(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        user.setBrief(resultSet.getString("brief"));
        return user;
    }
}
