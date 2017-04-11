package cn.com.hq.dao;

import java.sql.Connection;
import java.sql.DriverManager;
public class Dao {
	public static Connection getCommonConnection()
	{
		Connection connection = null;
        try {
            /*****填写数据库相关信息(请查找数据库详情页)*****/
            String databaseName = "iyGuNBWBMhcCdcfzHbfq";
            String host = "sqld.duapp.com";
            String port = "4050";
            String username = "09139cbfc993430eb70a2b855f340ca2"; //用户AK
            String password = "b25ffe99328a45e990547ae84746ae07"; //用户SK
            String driverName = "com.mysql.jdbc.Driver";
            String dbUrl = "jdbc:mysql://";
            String serverName = host + ":" + port + "/";
            String connName = dbUrl + serverName + databaseName;

            /******接着连接并选择数据库名为databaseName的服务器******/
            Class.forName(driverName);
            connection = DriverManager.getConnection(connName, username,
                    password);
            System.out.println("---connect DB success!!!");
            return connection;
            
        } catch (Exception e) {
        	 System.out.println("---connect DB failed!!!");
            e.printStackTrace();
            return null;
        }
	        
	}
		
}
