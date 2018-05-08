/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.db.controller;

import com.metrolink.validatorservice.preferencesmanager.IPreferencesManager;
import com.metrolink.validatorservice.preferencesmanager.PreferencesManager;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.json.simple.parser.ParseException;

/**
 * Class for keeping the access object to database in one single file
 * @author Gabriel Ortega Rosero
 */
public class DataBaseManager {

    private static DataBaseManager instace;
    
    private DataBaseManager(){
    }
    
    private static IPreferencesManager preferencesManager;

    
    public static DataBaseManager getInstance(){
        if (null == instace){
            instace = new DataBaseManager();
        }
        return instace;
    }
    
    public void setPreferencesManager(String configFilePath) throws IOException, FileNotFoundException, ParseException{
        this.preferencesManager = new PreferencesManager(configFilePath);
    }
    
    public IPreferencesManager getPreferencesManager(){
        if(null != preferencesManager){
            return preferencesManager;
        }else {
            throw new IllegalStateException ("setPreferencesManager method must be called before using getPreferencesManager() method");
        }        
    }
    
}
