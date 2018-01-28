/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.alarmsmanager;

import com.metrolink.validatorservice.models.MovAlarmas;
import java.util.ArrayList;

/**
 *
 * @author Gabriel Ortega
 */
public class AlarmsManager implements IAlarmsManager{

    
    @Override
    public void reportAlarm(MovAlarmas alarma) {
        AlarmsStack.getInstance().addAlarmToStack(alarma);
    }
    
    public void saveAlarms(){
        ArrayList<MovAlarmas> listAlarmas =  AlarmsStack.getInstance().getAlarmsStack();
    }
    
    public void cleanAalrmsStack(){
         AlarmsStack.getInstance().cleanStack();
    }
    
}
