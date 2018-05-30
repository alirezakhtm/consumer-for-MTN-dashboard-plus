/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rahkar.logic;

import com.rahkar.database.CSVFileUploaded;
import com.rahkar.database.Content;
import com.rahkar.database.DBHandler;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author alirzea
 */
public class AppLogic {
    
    private DBHandler db = new DBHandler();
    private Timer guardTimer = new Timer();
    private static boolean bStartLogic = false;
    private static boolean bCheck = true;
    
    public AppLogic() {
        guardTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Thread threadLogic = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int hour = db.getHour();
                        int minute = db.getMinute();
                        Calendar cal = Calendar.getInstance();
                        int hourCurrent = Integer.parseInt(new SimpleDateFormat("HH").format(cal.getTime()));
                        int minuteCurrent = Integer.parseInt(new SimpleDateFormat("mm").format(cal.getTime()));
                        if(hour == hourCurrent && minute == minuteCurrent && !bStartLogic && bCheck){
                            bCheck = false;
                            System.out.println("[*] ["+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime())+"] INFO: Process have been started.");
                            bStartLogic = true;
                            DoProcess();
                            bStartLogic = false;
                        }
                        if(hour != hourCurrent && minute != minuteCurrent && bStartLogic && !bCheck){
                            bCheck = true;
                        }
                    }

                    private void DoProcess() {
                        String directory = db.getTargetDirectory();
                        db.open();
                        List<CSVFileUploaded> lstFiles = db.getPendingFile();
                        db.close();
                        for(CSVFileUploaded file : lstFiles){
                            System.out.println("[*] ["+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime())+"] INFO: File fetched -> " + directory + ((db.getOperationSys().equals("linux")) ? "/" : "\\") +file.getContentAddress());
                            if(file.getContentAddress().endsWith(".csv")){
                                String fileAddress = directory + ((db.getOperationSys().equals("linux")) ? "/" : "\\") + file.getContentAddress();
                                System.out.println("[*] ["+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime())+"] INFO: Set status file as \"Processing\".");
                                db.open();
                                db.setFileAsProcessing(file.getIndx());
                                db.close();
                                String serviceName = file.getServiceName();
                                try{
                                    System.out.println("[*] ["+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime())+"] INFO: Process File ...");
                                    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileAddress))));
                                    String line = "";
                                    int lineCount = 1;
                                    List<Content> lstContent = new ArrayList<>();
                                    while((line = br.readLine()) != null){
                                        if(lineCount != 1){
                                            String date = line.substring(0, line.indexOf(","));
                                            String serviceNameInFile = line.replace(date, "").substring(0, line.indexOf(","));
                                            String msg = line.replace(date, "").replace(serviceNameInFile, "").substring(2);
                                            
                                            Content con = new Content();
                                            con.setDate(date);
                                            con.setServiceName(serviceName);
                                            con.setMessage(msg);
                                            lstContent.add(con);
                                        }
                                        lineCount++;
                                    }
                                    System.out.println("[*] ["+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime())+"] INFO: Inserting to database.");
                                    db.open();
                                    db.InsertToDB(lstContent, serviceName);
                                    db.close();
                                    System.out.println("[*] ["+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime())+"] INFO: Set status file as \"Loaded\".");
                                    db.open();
                                    db.setFileAsLoaded(file.getIndx());
                                    db.close();

                                }catch(FileNotFoundException e){
                                    System.err.println("[*] ["+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime())+"] ERROR - AppLogic - 01 : " + e);
                                } catch (IOException ex) {
                                    System.err.println("[*] ["+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime())+"] ERROR - AppLogic - 02 : " + ex);
                                }
                            }else{
                                System.out.println("[*] ["+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime())+"] INFO: File is Bad format.");
                                db.open();
                                db.setFileAsBadFormat(file.getIndx());
                                db.close();
                            }
                        }
                        System.out.println("[*] ["+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime())+"] INFO: Finish processing file.");
                    }
                });
                threadLogic.start();
            }
        }, 0, 1000);
    }
    
    
    
}
