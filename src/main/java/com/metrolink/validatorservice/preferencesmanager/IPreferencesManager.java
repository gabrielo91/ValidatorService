/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.preferencesmanager;

/**
 * Interfaz que define la forma de obtener credenciales para el uso del servicio.
 * @author Gabriel Ortega
 */
public interface IPreferencesManager {
    String getUsername()throws Exception;
    String getPasword()throws Exception;
    String getServiceEndPoint()throws Exception;
    void setConfigFilePath(String configFilePath) throws Exception;
    String getConfigFilePath() throws Exception;
}
