/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rahkar.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBException;

/**
 *
 * @author alirzea
 */
public class DBHandler {
    private String username, password, url, dbname, targetDirectory, operationSys;
    private int hour, minute;
    private Connection conn;
    private Statement stm;
    private ResultSet rst;
    
    public DBHandler() {
        try{
            ConfigHandler config = new ConfigHandler();
            username = config.getDbUsername();
            password = config.getDbPassword();
            dbname = config.getDbName();
            url = "jdbc:mysql://localhost:3306/"+dbname+"?useSSL=false&useUnicode=yes&characterEncoding=UTF-8";
            hour = config.getHour();
            minute = config.getMinute();
            targetDirectory = config.getTargetDirectory();
            operationSys = config.getOperationSys();
        }catch(IOException | JAXBException e){
            System.err.println("[*] ERROR - DBHandler/Constructor : " + e);
        }
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getUrl() {
        return url;
    }

    public String getDbname() {
        return dbname;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public String getTargetDirectory() {
        return targetDirectory;
    }

    public String getOperationSys() {
        return operationSys;
    }

    public void setOperationSys(String operationSys) {
        this.operationSys = operationSys;
    }
    
    public void open(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = (Connection)DriverManager.getConnection(url, username, password);
        }catch(SQLException | ClassNotFoundException e){
            System.err.println("[*] ERROR - DBHandler/open : " + e);
        }
    }
    
    public void close(){
        try{
            if(!conn.isClosed()) conn.close();
        }catch(SQLException e){
            System.err.println("[*] ERROR - DBHandler/close : " + e);
        }
    }
    
    public boolean InsertToDB(List<Content> lst, String serviceName){
        try{
            String query_truncate = "truncate `"+dbname+"`.`tbl_message_"+serviceName+"`";
            stm = conn.createStatement();
            stm.execute(query_truncate);
            this.close();
            this.open();
            String query = "INSERT INTO `"+dbname+"`.`tbl_message_"+serviceName+"`(`dateSend`,`successfullPaymentMessage`,`failedPaymentMessage`) VALUES\n";
            for(Content cont : lst){
                query += "('"+cont.getDate().replace(" ", "")+"','"+cont.getMessage()+"',''),\n";
            }
            query = query.substring(0, query.lastIndexOf(","));
            stm = conn.createStatement();
            stm.execute(query);
            return true;
        }catch(SQLException e){
            System.err.println("[*] ERROR - DBHandler/InsertToDB : " + e);
            return false;
        }
    }
    
    public List<CSVFileUploaded> getPendingFile(){
        List<CSVFileUploaded> lstAns = new ArrayList<>();
        try{
            String query = "select * from `"+dbname+"`.`tbl_content_file` where `status`='1'";
            stm = conn.createStatement();
            rst = stm.executeQuery(query);
            while(rst.next()){
                CSVFileUploaded csvFileUpload = new CSVFileUploaded();
                csvFileUpload.setIndx(rst.getInt("indx"));
                csvFileUpload.setContentAddress(rst.getString("file_address"));
                csvFileUpload.setServiceName(rst.getString("service_name"));
                lstAns.add(csvFileUpload);
            }
        }catch(SQLException e){
            System.err.println("[*] ERROR - DBHandler/getPendingFile : " + e);
        }
        return lstAns;
    }
    
    public void setFileAsProcessing(int indx){
        try{
            String query = "UPDATE `"+dbname+"`.`tbl_content_file` SET `status` = '2' WHERE `indx` = '"+indx+"'";
            stm = conn.createStatement();
            stm.execute(query);
        }catch(SQLException e){
            System.err.println("[*] ERROR - DBHandler/setFileAsProcessing : " + e);
        }
    }
    
    public void setFileAsLoaded(int indx){
        try{
            String query = "UPDATE `"+dbname+"`.`tbl_content_file` SET `status` = '3' WHERE `indx` = '"+indx+"'";
            stm = conn.createStatement();
            stm.execute(query);
        }catch(SQLException e){
            System.err.println("[*] ERROR - DBHandler/setFileAsLoaded : " + e);
        }
    }
    
    public void setFileAsBadFormat(int indx){
        try{
            String query = "UPDATE `"+dbname+"`.`tbl_content_file` SET `status` = '4' WHERE `indx` = '"+indx+"'";
            stm = conn.createStatement();
            stm.execute(query);
        }catch(SQLException e){
            System.err.println("[*] ERROR - DBHandler/setFileAsBadFormat : " + e);
        }
    }
    
    
}
