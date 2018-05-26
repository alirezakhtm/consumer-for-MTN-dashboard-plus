/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rahkar.database;

import javax.xml.bind.annotation.XmlAttribute;

/**
 *
 * @author alirzea
 */
public class Setting {
    private String time;
    private String directory;
    private String format;

    public String getTime() {
        return time;
    }

    @XmlAttribute(name = "time")
    public void setTime(String time) {
        this.time = time;
    }

    public String getDirectory() {
        return directory;
    }

    @XmlAttribute(name = "directory")
    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public String getFormat() {
        return format;
    }

    @XmlAttribute(name = "format")
    public void setFormat(String format) {
        this.format = format;
    }
    
}
