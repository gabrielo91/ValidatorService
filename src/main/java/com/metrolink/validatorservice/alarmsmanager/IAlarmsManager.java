/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.alarmsmanager;

import com.metrolink.validatorservice.models.MovAlarmas;
import com.metrolink.validatorservice.models.MovLectConsu;

/**
 *
 * @author Gabriel Ortega
 */
public interface IAlarmsManager {
    
    public void reportAlarm(MovLectConsu lectura, String description);
}
