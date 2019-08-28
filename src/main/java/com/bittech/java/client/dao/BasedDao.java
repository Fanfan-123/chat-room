package com.bittech.java.client.dao;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.bittech.java.util.CommUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @Date: 2019-08-12 09:50
 * @Description:封装基础dao操作,获取数据源、连接、关闭资源等
 */
public class BasedDao {
    //1. 获取连接池对象（即获取资源）
    //因为操作的基础是先获取数据源，所以使用static块，在类加载的时候执行
    private static DruidDataSource dataSource;
    static {
        Properties properties = CommUtils.
                loadProperties("datasource.properties");
        try {
            //获取连接池对象
            dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            //如果抛出异常，则获取连接池对象失败
            System.err.println("数据源加载失败");
            e.printStackTrace();
        }
    }
//2. 连接数据库
    //dataSource是一个连接池对象，使用连接池对象和数据库建立连接
    protected DruidPooledConnection getConnection() {
        try {
            return (DruidPooledConnection) dataSource.getPooledConnection();
        } catch (SQLException e) {
            System.err.println("数据库连接获取失败");
            e.printStackTrace();
        }
        return null;
    }
//3. 关闭连接
    //Collection：是客户端与数据库进行交互
    //Statement：是建立了到特定数据库的连接之后，就可用该连接发送 SQL 语句。Statement对象用 Connection 的法createStatement 创建
    //ResultSet：查询数据库时，返回的是一个二维的结果集，我们需要用到ResultSet来遍历结果集，获取每一行的数据

    /*关闭连接有两种
    1. 对于数据库的增删改，只需要Collection，Statement就可以
    2. 对于数据库的查询，需要Collection，Statement，ResultSet（ResultSet用来返回结果集）
     */
    protected void closeResources(Connection connection,
                             Statement statement) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    protected void closeResources(Connection connection,
                                  Statement statement,
                                  ResultSet resultSet) {
        closeResources(connection,statement);
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
