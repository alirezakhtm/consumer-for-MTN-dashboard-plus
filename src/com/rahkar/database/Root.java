/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rahkar.database;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
/**
 *
 * @author alirzea
 */
@XmlRootElement(name = "root")
public class Root {
    private Setting setting;
    private Database database;

    public Setting getSetting() {
        return setting;
    }

    @XmlElement(name = "setting")
    public void setSetting(Setting setting) {
        this.setting = setting;
    }

    public Database getDatabase() {
        return database;
    }

    @XmlElement(name = "database")
    public void setDatabase(Database database) {
        this.database = database;
    }
    
}
