/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rahkar.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author alirzea
 */
public class ConfigHandler {
    private final String targetDirectory, fileFormat, dbUsername, dbPassword, dbName, operationSys;
    private final int hour, minute;

    public ConfigHandler() throws IOException, JAXBException{
        //InputStream input = this.getClass().getResource("/com/rahkar/database/config.xml").openStream();
        InputStream input = new FileInputStream(new File("config.xml"));
        JAXBContext context = JAXBContext.newInstance(Root.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        Root root = (Root) unmarshaller.unmarshal(input);
        targetDirectory = root.getSetting().getDirectory();
        fileFormat = root.getSetting().getFormat();
        dbUsername = root.getDatabase().getUsername();
        dbPassword = root.getDatabase().getPassword();
        dbName = root.getDatabase().getDbname();
        String[] time = root.getSetting().getTime().split(":");
        hour = Integer.parseInt(time[0]);
        minute = Integer.parseInt(time[1]);
        operationSys = root.getSetting().getOperationSys();
    }

    public String getTargetDirectory() {
        return targetDirectory;
    }

    public String getFileFormat() {
        return fileFormat;
    }

    public String getDbUsername() {
        return dbUsername;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public String getDbName() {
        return dbName;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public String getOperationSys() {
        return operationSys;
    }
    
    @Override
    public String toString() {
        return "targetDirectory: " + targetDirectory + "\nfileFormat: " +
                fileFormat + "\ndbUsername: " + dbUsername + "\n" +
                "dbPassword: " + dbPassword + "\ndbName: " + dbName +
                "\nhour: " + hour + "\nminute: " + minute +
                "\nOperation System: " + operationSys;
    }
    
    
}
