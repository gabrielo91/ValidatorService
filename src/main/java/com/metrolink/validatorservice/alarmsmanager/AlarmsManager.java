/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.alarmsmanager;

import com.metrolink.validatorservice.db.controller.IDatabaseController;
import com.metrolink.validatorservice.db.daos.DAOAlarmas;
import com.metrolink.validatorservice.db.daos.IDAOAlarmas;
import com.metrolink.validatorservice.models.AgendaLectura;
import com.metrolink.validatorservice.models.MovAlarmas;
import com.metrolink.validatorservice.models.MovAlarmasPK;
import com.metrolink.validatorservice.models.MovSuministros;
import com.metrolink.validatorservice.models.MovSuministrosPK;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Gabriel Ortega
 */
public class AlarmsManager implements IAlarmsManager{

    public final static Integer CALENDARIO_TOU_VALIDATION_ERROR_CODE = 0;
    public final static Integer COMPLETITUD_INFO_VALIDATION_ERROR_CODE = 1;
    public final static Integer DEVOLUCION_DE_REGISTRO_ERROR_CODE = 2;
    public final static Integer LECTURA_REPETIDA_ERROR_CODE = 3;
    public final static Integer INCREMENTO_MINIMO_NO_ESPERADO_ERROR_CODE = 4;
    public final static Integer INCREMENTO_MAXIMO_NO_ESPERADO_ERROR_CODE = 5;
    public final static Integer EXISTENCIA_DE_DATOS_ERROR_CODE = 6;
    public final static Integer DEVOLUCION_DE_REGISTRO_MENSUAL_ERROR_CODE = 7;
    public final static Integer LECTURA_REPETIDA_MENSUAL_ERROR_CODE = 8;
    public final static Integer INCREMENTO_MINIMO_NO_ESPERADO_MENSUAL_ERROR_CODE = 9;
    public final static Integer INCREMENTO_MAXIMO_NO_ESPERADO_MENSUAL_ERROR_CODE = 10;
    public final static Integer PORCENTAJE_MAXIMO_SUPERIOR_INFERIOR_ERROR_CODE = 11;
    public final static Integer DESVIACION_DE_CONSUMO_ERROR_CODE = 12;
    
    public final static Short ACTIVE_ALARM = 1;
    public final static Short INACTIVE_ALARM = 0;
    
    
    public static void saveAlarms(IDatabaseController databaseController) throws Exception{
        ArrayList<MovAlarmas> listAlarmas =  AlarmsStack.getInstance().getAlarmsStack();
        IDAOAlarmas daoAlarmas = new DAOAlarmas(databaseController);
        daoAlarmas.insertAlarmas(listAlarmas);
    }
    
    public void cleanAalrmsStack(){
         AlarmsStack.getInstance().cleanStack();
    }

    @Override
    public void reportAlarm(MovSuministros suministro, int codigoAlarma) {
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
        
        //Se almacenan los datos del suminsitro que genera la alarma
        alarm.setMovSuministrosPK(suministro.getMovSuministrosPK());
        
        return alarm;
    }

    @Override
    public void reportAlarm(AgendaLectura agenda, int codigoAlarma) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
