/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice;

import com.metrolink.validatorservice.alarmsmanager.AlarmsManager;
import com.metrolink.validatorservice.alarmsmanager.IAlarmsManager;
import com.metrolink.validatorservice.bussinesvalidations.IndividualValidations;
import com.metrolink.validatorservice.controller.Controller;
import com.metrolink.validatorservice.db.controller.DatabaseController;
import com.metrolink.validatorservice.db.controller.IDatabaseController;
import com.metrolink.validatorservice.logger.DataLogger;
import com.metrolink.validatorservice.preferencesmanager.IPreferencesManager;
import com.metrolink.validatorservice.preferencesmanager.PreferencesManager;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.parser.ParseException;
import com.metrolink.validatorservice.bussinesvalidations.IIndividualValidations;


/**
 *
 * @author Gabriel Ortega
 */
public class Main {

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        test2();
    }
    
    
    //test validations iterator
    private static void test2(){
        try {

            String configFilePath = "resources/config.json";
            IPreferencesManager preferencesManager = new PreferencesManager(configFilePath);
            IAlarmsManager alarmsManager = new AlarmsManager();
            IIndividualValidations individualValidations = new IndividualValidations(alarmsManager);
            Controller controller = new Controller(individualValidations, preferencesManager);
            controller.performValidations();
            
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    //test preferences manager
    private void test1(){
   
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
