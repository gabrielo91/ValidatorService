/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.alarmsmanager;

import com.metrolink.validatorservice.models.AgendaLectura;
import com.metrolink.validatorservice.models.MovAlarmas;
import com.metrolink.validatorservice.models.MovAlarmasPK;
import com.metrolink.validatorservice.models.MovSuministros;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Gabriel Ortega
 */
public class AlarmsManager implements IAlarmsManager{

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
    public void reportAlarm(MovSuministros suministro, int codigoAlarma) {
        System.out.println("REPORTANDO ALARMA ------------------------------------------------------------------ TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
        MovAlarmas alarm = createAlarm(suministro, codigoAlarma);
        AlarmsStack.getInstance().addAlarmToStack(alarm);
    }

    //TODO lo mas probable es qwue necesite otro dato de  suministros para sacar los datos de la alarma
    private MovAlarmas createAlarm(MovSuministros suministro, int codigoAlarma) {
        MovAlarmas alarm = new MovAlarmas();
        alarm.setDfechaVal(new Date());
         
        MovAlarmasPK alarmasPK = new MovAlarmasPK();
        alarmasPK.setNcodAlarma(codigoAlarma);
        alarmasPK.setNconsProceso(0);
        alarmasPK.setNnisRad(0);
        alarm.setMovAlarmasPK(alarmasPK);
        alarm.setNnic(suministro.getNnic().intValue());
        //TODO where this value does come from? ******
        alarm.setNperiodo(0);
        alarm.setNunicom(suministro.getNunicom().shortValue());
        alarm.setVcitinerario(suministro.getVcitinerario());
        alarm.setVcruta(suministro.getVcruta());
        alarm.setVctipoEnergia(suministro.getVctipoEnergia());
        
        return alarm;
    }

    @Override
    public void reportAlarm(AgendaLectura agenda, int codigoAlarma) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
