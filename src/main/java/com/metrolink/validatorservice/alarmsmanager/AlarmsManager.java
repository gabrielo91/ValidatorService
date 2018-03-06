/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.alarmsmanager;

import com.metrolink.validatorservice.models.AgendaLectura;
import com.metrolink.validatorservice.models.MovAlarmas;
import com.metrolink.validatorservice.models.MovAlarmasPK;
import com.metrolink.validatorservice.models.MovLectConsu;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Gabriel Ortega
 */
public class AlarmsManager implements IAlarmsManager{

    public final static String CALENDARIO_TOU_VALIDATION_ERROR_DESCRIPTION = "La lectura no contiene calendario TOU";
    public final static String EXISTENCIA_DATOS_VALIDATION_ERROR_DESCRIPTION = "No hay datos en el periodo selecionado";
    public final static Integer CALENDARIO_TOU_VALIDATION_ERROR_CODE = 0;
    public final static Short ACTIVE_ALARM = 1;
    public final static Short INACTIVE_ALARM = 0;
    

    
    public void saveAlarms(){
        ArrayList<MovAlarmas> listAlarmas =  AlarmsStack.getInstance().getAlarmsStack();
        //TODO save alarms using DAO
    }
    
    public void cleanAalrmsStack(){
         AlarmsStack.getInstance().cleanStack();
    }

    @Override
    public void reportAlarm(AgendaLectura suministro, String description) {
        MovAlarmas alarm = createAlarm(suministro, description);
        AlarmsStack.getInstance().addAlarmToStack(alarm);
    }

    //TODO lo mas probable es qwue necesite otro dato de  suministros para sacar los datos de la alarma
    private MovAlarmas createAlarm(AgendaLectura suministro, String description) {
        MovAlarmas alarm = new MovAlarmas();
        alarm.setDfechaVal(new Date());
         
        MovAlarmasPK alarmasPK = new MovAlarmasPK();
        alarmasPK.setNcodAlarma(CALENDARIO_TOU_VALIDATION_ERROR_CODE);
        alarmasPK.setNconsProceso(0);
        alarmasPK.setNnisRad(0);
        alarm.setMovAlarmasPK(alarmasPK);
        
//        alarm.setNnic(lecturaInformation.getNnic().intValue());
//        
//        //TODO where this value does come from? ******
//        alarm.setNperiodo(0);
//        alarm.setNunicom(lecturaInformation.getMovSuministros().getNunicom().shortValue());
//        alarm.setVcitinerario(lecturaInformation.getMovSuministros().getVcitinerario());
//        alarm.setVcruta(lecturaInformation.getMovSuministros().getVcruta());
//        alarm.setVctipoEnergia(lecturaInformation.getMovSuministros().getVctipoEnergia());
        
        return alarm;
    }
    
}
