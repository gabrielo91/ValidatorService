/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.db.controller;

import com.metrolink.validatorservice.preferencesmanager.IPreferencesManager;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Gabriel Ortega
 */
public class DatabaseController implements IDatabaseController{

    private Connection connection;
    private IPreferencesManager preferencesManager;
    
    public DatabaseController (IPreferencesManager preferencesManager){
            this.preferencesManager = preferencesManager;
        }
    
    @Override
    public Connection getConnection() throws Exception {
             
        String url;
        String port;
        String username;
        String password;
        String driver;
        String connectionString;

        try {
            if(null == connection){
                
                driver = preferencesManager.getDBDriver();
                url = preferencesManager.getDBUrl();
                port = preferencesManager.getDBPort();
                username = preferencesManager.getDBUsername();
                password = preferencesManager.getDBPasword();                            
                Class.forName(driver);  
                connectionString = String.format("jdbc:oracle:thin:@%s:%s:xe", url, port);
                connection = DriverManager.getConnection(connectionString, username, password);   
            }
        } catch (Exception e) {
            throw new Exception("No se pudo obtener la conexiòn a la base de datos", e);
        } 
        return connection;
    }
    
}
