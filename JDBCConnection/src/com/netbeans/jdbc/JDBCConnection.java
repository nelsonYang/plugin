package com.netbeans.jdbc;

import com.netbeans.dao.DAOGenerate;
import com.netbeans.entity.TableEntity;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;


/**
 *
 * @author nelson
 */
public class JDBCConnection {
    /* 获取数据库连接的函数*/
    public static Connection getConnection(String connectionURL, String user, String password) {
        Connection con = null;  //创建用于连接数据库的Connection对象  
        try {
            Class.forName("com.mysql.jdbc.Driver");// 加载Mysql数据驱动  
            con = DriverManager.getConnection(connectionURL, user, password);// 创建数据连接  
        } catch (Exception e) {
            System.out.println("数据库连接失败" + e.getMessage());
        }
        return con; //返回所建立的数据库连接  
    }



    public static void main(String[] args) {
        String url = "jdbc:mysql://115.29.137.120:3306/appTreasure?zeroDateTimeBehavior=convertToNull";
        String user = "dianzhuan";
        String password = "654321";
        getConnection(url,user,password);
   //     List<TableEntity> tableList =DAOGenerate.generateDAO(url, user, password);
    }
}
