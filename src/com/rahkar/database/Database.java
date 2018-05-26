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
public class Database {
    private String username, password, dbname;

    public String getUsername() {
        return username;
    }

    @XmlAttribute(name = "username")
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    @XmlAttribute(name = "password")
    public void setPassword(String password) {
        this.password = password;
    }

    public String getDbname() {
        return dbname;
    }

    @XmlAttribute(name = "dbname")
    public void setDbname(String dbname) {
        this.dbname = dbname;
    }
    
}
