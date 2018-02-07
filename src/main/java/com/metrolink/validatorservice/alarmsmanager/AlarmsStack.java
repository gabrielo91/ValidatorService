/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.alarmsmanager;

import com.metrolink.validatorservice.models.MovAlarmas;
import java.util.ArrayList;

/**
 * Singleton on charge of managing the alarms stack. In this way the program can iterate over such stack in order 
 * to avoid sending repeated alarms corresponding to the same discarded alarm
 * @author Gabriel Ortega
 */
public class AlarmsStack {
    
    private static AlarmsStack instance;
    private static ArrayList<MovAlarmas> listAlarmas;
    
    private AlarmsStack(){
    }
    
    public static AlarmsStack getInstance(){
        if (null == instance){
            instance = new AlarmsStack();
            listAlarmas = new ArrayList<>();
        }
        return instance;
    }
    
    public static void addAlarmToStack(MovAlarmas alarma){
        if(null != listAlarmas){
            listAlarmas.add(alarma);
        }else{
            throw new IllegalStateException ("getInstance method must be called before using addAlarmToStack() method");
        }
    }
    
    public static ArrayList<MovAlarmas> getAlarmsStack(){
        if(null != listAlarmas){
            return listAlarmas;
        } else{
            throw new IllegalStateException ("getInstance method must be called before using getAlarmsStack() method");
        }
    }
    
    public static void cleanStack(){
        if(null != listAlarmas){
            listAlarmas.clear();
            listAlarmas = null;
        } else{
            throw new IllegalStateException ("getInstance method must be called before using cleanStack() method");
        }
        
        
        
    }
    
}
