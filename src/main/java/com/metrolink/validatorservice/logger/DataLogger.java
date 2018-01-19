/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.logger;
import com.metrolink.validatorservice.preferencesmanager.IPreferencesManager;
import com.metrolink.validatorservice.preferencesmanager.PreferencesManager;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.parser.ParseException;
/**
 *
 * @author Gabriel Ortega
 */
public class DataLogger {
       
    public static final String CONFIGURATION_FILE_PATH = "resources/config.json";
    
    public static void Log(Exception e, String message){
        
        try {
            IPreferencesManager preferencesManager = new PreferencesManager(CONFIGURATION_FILE_PATH);
            String eventTime = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss").format( Calendar.getInstance().getTime());
            String date = new SimpleDateFormat("MM_dd_yyyy").format( Calendar.getInstance().getTime());
            //String fileName = String.format("%s\\%s.txt", preferencesManager.getLogsPath(), date);                        
            String fileName = String.format("%s\\ValidatorLogs.txt", preferencesManager.getLogsPath());                        
            File file = new File(fileName);
            
            if (!file.exists()) {
                file.getParentFile().mkdirs();;
            }                      
            
            try(FileWriter  fileWritter = new FileWriter(fileName,true);){
                BufferedWriter out = new BufferedWriter(fileWritter);
                PrintWriter printWriter = new PrintWriter(out, true);
                printWriter.write("\n"+eventTime +" -------------------------------------------------------------\n");
                printWriter.write(eventTime + " Log message: "+message + "\n");
                e.printStackTrace(printWriter);                
            }       
                       
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(DataLogger.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            java.util.logging.Logger.getLogger(DataLogger.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(DataLogger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
