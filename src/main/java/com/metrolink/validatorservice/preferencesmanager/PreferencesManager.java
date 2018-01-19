/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.preferencesmanager;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Implementación de la interfaz IPreferencesManager para la carga de preferncias y credenciales
 * @author Gabriel Ortega
 */
public class PreferencesManager implements IPreferencesManager{
        
    public final static String DB_USERNAME = "username";
    public final static String DB_PASSWORD = "password";
    public final static String DB_CONFIGURATION = "db_params";
    public final static String DB_URL = "url";
    public final static String DB_PORT = "port";
    public final static String DB_DRIVER = "driver";
    public final static String LOGS_PATH = "logs_path";
    

    private String configFilePath;
    private String dbUsername;
    private String dbPassword;
    private String dbUrl;
    private String dbPort;
    private String dbDriver;
    private String logsPath;
    
    public PreferencesManager(String configFilePath) throws FileNotFoundException, IOException, ParseException {
        this.configFilePath = configFilePath;
        JSONParser parser = new JSONParser();
        Object object = parser.parse(new FileReader(configFilePath));
        JSONObject configData = (JSONObject) object;
        JSONObject dbConfiguration = (JSONObject) configData.get(DB_CONFIGURATION);
        dbUsername = (String) dbConfiguration.get(DB_USERNAME);
        dbPassword = (String) dbConfiguration.get(DB_PASSWORD);
        dbUrl = (String) dbConfiguration.get(DB_URL);
        dbPort = (String) dbConfiguration.get(DB_PORT);
        dbDriver = (String) dbConfiguration.get(DB_DRIVER);
        logsPath = (String) configData.get(LOGS_PATH);
    }
    
    @Override
    public String getDBUsername() throws Exception {
        return this.dbUsername;
    }

    @Override
    public String getDBPasword() throws Exception {
        return this.dbPassword;
    }

    @Override
    public String getDBUrl() throws Exception {
        return this.dbUrl;
    }
    
    @Override
    public String getDBPort() throws Exception {
        return this.dbPort;
    }
    
    @Override
    public String getDBDriver() throws Exception {
        return this.dbDriver;
    }
    
    @Override
    public String getLogsPath() throws Exception {
        return logsPath;
    }
}
