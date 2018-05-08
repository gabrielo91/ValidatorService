/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice;

import com.metrolink.validatorservice.alarmsmanager.AlarmsManager;
import com.metrolink.validatorservice.alarmsmanager.IAlarmsManager;
import com.metrolink.validatorservice.bussinesvalidations.IndividualValidationsSCO;
import com.metrolink.validatorservice.bussinesvalidations.IndividualValidations;
import com.metrolink.validatorservice.controller.Controller;
import com.metrolink.validatorservice.logger.DataLogger;
import com.metrolink.validatorservice.preferencesmanager.IPreferencesManager;
import com.metrolink.validatorservice.preferencesmanager.PreferencesManager;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.parser.ParseException;
import com.metrolink.validatorservice.bussinesvalidations.IIndividualValidations;
import com.metrolink.validatorservice.bussinesvalidations.IIndividualValidationsSCO;
import com.metrolink.validatorservice.db.controller.DataBaseManager;


/**
 *
 * @author Gabriel Ortega
 */
public class Main {

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        run();
    }
    
    private static void run(){
        try {

            String configFilePath = "resources/config.json";
            DataBaseManager.getInstance().setPreferencesManager(configFilePath); //set gloabl db access object
            IPreferencesManager preferencesManager = DataBaseManager.getInstance().getPreferencesManager();
            IAlarmsManager alarmsManager = new AlarmsManager();
            IIndividualValidations individualValidations = new IndividualValidations(alarmsManager);
            IIndividualValidationsSCO generalValidations = new IndividualValidationsSCO(alarmsManager);
            Controller controller = new Controller(individualValidations, generalValidations, preferencesManager);
            controller.startValidationProcess();
            
        } catch (IllegalAccessException ex) {
            DataLogger.Log(ex, ex.getMessage());
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            DataLogger.Log(ex, ex.getMessage());
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            DataLogger.Log(ex, ex.getMessage());
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            DataLogger.Log(ex, ex.getMessage());
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException ex) {
            DataLogger.Log(ex, ex.getMessage());
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            DataLogger.Log(ex, ex.getMessage());
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            DataLogger.Log(ex, ex.getMessage());
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            DataLogger.Log(ex, ex.getMessage());
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
}
