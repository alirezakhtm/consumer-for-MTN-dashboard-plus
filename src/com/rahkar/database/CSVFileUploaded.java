/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rahkar.database;

import java.util.List;

/**
 *
 * @author alirzea
 */
public class CSVFileUploaded {
    int indx;
    private String ContentAddress;
    private String serviceName;

    public String getContentAddress() {
        return ContentAddress;
    }

    public void setContentAddress(String ContentAddress) {
        this.ContentAddress = ContentAddress;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public int getIndx() {
        return indx;
    }

    public void setIndx(int indx) {
        this.indx = indx;
    }
    
}
