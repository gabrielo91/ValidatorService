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
        
    public final static String USERNAME = "username";
    public final static String PASSWORD = "password";
    public final static String CREDENTIALS = "credentials";
    public final static String SERVICE = "service";
    public final static String SERVICE_ENDPOINT = "service-endpoint";

    private String configFilePath;
    private String username;
    private String password;
    private String serviceEndPoint;
   
    public PreferencesManager(String configFilePath) throws FileNotFoundException, IOException, ParseException {
        this.configFilePath = configFilePath;
        JSONParser parser = new JSONParser();
        Object object = parser.parse(new FileReader(configFilePath));
        JSONObject configData = (JSONObject) object;
        JSONObject credentials = (JSONObject) configData.get(CREDENTIALS);
        JSONObject serviceInfo = (JSONObject) configData.get(SERVICE);
        username = (String) credentials.get(USERNAME);
        password = (String) credentials.get(PASSWORD);
        serviceEndPoint = (String) serviceInfo.get(SERVICE_ENDPOINT);
    }
    
    @Override
    public String getUsername() throws Exception {
        return this.username;
    }

    @Override
    public String getPasword() throws Exception {
        return this.password;
    }

    @Override
    public String getServiceEndPoint() throws Exception {
        return this.serviceEndPoint;
    }

    @Override
    public void setConfigFilePath(String configFilePath) throws Exception {
        this.configFilePath = configFilePath;
    }

    @Override
    public String getConfigFilePath() throws Exception {
        return configFilePath;
    }
    
}
