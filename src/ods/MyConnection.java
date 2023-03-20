/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ods;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

public class MyConnection {

    public Connection getConn() {
        Connection conn = null;
        try {
            PropertiesParameterConfig parameterConfig = PropertiesParameterConfig.getPropertiesParameterObject();
            HashMap hashMap = parameterConfig.getHashMap();
            String DB_IP = (String) hashMap.get("DB_IP");
            String DB_Port = (String) hashMap.get("DB_PORT");
            String DB_User_Name = (String) hashMap.get("DB_USER");
            String DB_Password = (String) hashMap.get("DB_PASS");
            String DB_SID = (String) hashMap.get("DB_SID");

            try {

                Class.forName("oracle.jdbc.driver.OracleDriver");
                String connectionURL = "jdbc:oracle:thin:@" + DB_IP + ":" + DB_Port + "/" + DB_SID;
                System.out.println("Try to Connecting with -->"+connectionURL);
                conn = DriverManager.getConnection(connectionURL, DB_User_Name, DB_Password);
                if (conn != null) {
                	System.out.println("Connected succesfully!!!");
                }
            } catch (ClassNotFoundException ex) {
                System.err.println(ex.getMessage());
            }
        } catch (SQLException ex) {
        	System.err.println("getConn-->"+ex.getMessage());
        }
        return conn;
    }
    public Connection getConn2() {
        Connection conn = null;
        try {
            PropertiesParameterConfig parameterConfig = PropertiesParameterConfig.getPropertiesParameterObject();
            HashMap hashMap = parameterConfig.getHashMap();
            String DB_IP2 = (String) hashMap.get("DB_IP2");
            String DB_Port2 = (String) hashMap.get("DB_PORT2");
            String DB_User_Name2 = (String) hashMap.get("DB_USER2");
            String DB_Password2 = (String) hashMap.get("DB_PASS2");
            String DB_SID2 = (String) hashMap.get("DB_SID2");

            try {

                Class.forName("oracle.jdbc.driver.OracleDriver");
                String connectionURL = "jdbc:oracle:thin:@" + DB_IP2 + ":" + DB_Port2 + "/" + DB_SID2;
                System.out.println("Try to Connecting with -->"+connectionURL);
                conn = DriverManager.getConnection(connectionURL, DB_User_Name2, DB_Password2);
                if (conn != null) {
                	System.out.println("Connected succesfully!!!");
                }
            } catch (ClassNotFoundException ex) {
            	System.err.println(ex.getMessage());
            }
        } catch (SQLException ex) {
        	System.err.println("getConn2--> "+ex.getMessage());
        }
        return conn;
    }
}
