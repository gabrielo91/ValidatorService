/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice;

import com.metrolink.validatorservice.db.controller.DatabaseController;
import com.metrolink.validatorservice.db.controller.IDatabaseController;
import com.metrolink.validatorservice.logger.DataLogger;
import com.metrolink.validatorservice.preferencesmanager.IPreferencesManager;
import com.metrolink.validatorservice.preferencesmanager.PreferencesManager;
import java.io.IOException;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Gabriel Ortega
 */
public class Main {

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            System.out.println("Empezamos");
            String configFilePath = "resources/config.json";
            IPreferencesManager preferencesManager = new PreferencesManager(configFilePath);
            IDatabaseController databaseController = new DatabaseController(preferencesManager);
            Connection con = databaseController.getConnection();
            System.out.println("Funcionamos: "+ databaseController);
            int a = 4/0;
            
        } catch (IOException ex) {      
            DataLogger.Log(ex, "Error leyendo el archivo de configuracion");
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "Error leyendo el archivo de configuracion", ex);             
        } catch (ParseException ex) {
            DataLogger.Log(ex, "Error analizando el archivo de configuracion");
        } catch (Exception ex) {
            DataLogger.Log(ex, "Error en la ejecuciòn de la función principal");
        }
    }
    
}
