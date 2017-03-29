package cn.com.hq.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Dao {
	public static Connection getCommonConnection()
	{
		Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        String sql = null;
        try {
            /*****填写数据库相关信息(请查找数据库详情页)*****/
            String databaseName = "iyGuNBWBMhcCdcfzHbfq";
            String host = "sqld.duapp.com";
            String port = "4050";
            String username = "AK"; //用户AK
            String password = "SK"; //用户SK
            String driverName = "com.mysql.jdbc.Driver";
            String dbUrl = "jdbc:mysql://";
            String serverName = host + ":" + port + "/";
            String connName = dbUrl + serverName + databaseName;

            /******接着连接并选择数据库名为databaseName的服务器******/
            Class.forName(driverName);
            connection = DriverManager.getConnection(connName, username,
                    password);
            return connection;
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
	        
	}
		
}
