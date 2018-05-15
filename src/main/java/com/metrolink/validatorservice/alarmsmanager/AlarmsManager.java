/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.alarmsmanager;

import com.metrolink.validatorservice.db.controller.DataBaseManager;
import com.metrolink.validatorservice.db.controller.DatabaseController;
import com.metrolink.validatorservice.db.controller.IDatabaseController;
import com.metrolink.validatorservice.db.daos.DAOAgendaLectura;
import com.metrolink.validatorservice.db.daos.DAOAlarmas;
import com.metrolink.validatorservice.db.daos.DAORegsSco;
import com.metrolink.validatorservice.db.daos.IDAOAlarmas;
import com.metrolink.validatorservice.models.AgendaLectura;
import com.metrolink.validatorservice.models.MovAlarmas;
import com.metrolink.validatorservice.models.MovAlarmasPK;
import com.metrolink.validatorservice.models.MovRegsSco;
import com.metrolink.validatorservice.models.MovSuministros;
import com.metrolink.validatorservice.preferencesmanager.IPreferencesManager;
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
    
    
    public static void saveAlarms(IDatabaseController databaseController, String processId) throws Exception{
        ArrayList<MovAlarmas> listAlarmas =  AlarmsStack.getInstance().getAlarmsStack();
        IDAOAlarmas daoAlarmas = new DAOAlarmas(databaseController);
        daoAlarmas.insertAlarmas(listAlarmas, processId);
    }
    
    public void cleanAalrmsStack(){
         AlarmsStack.getInstance().cleanStack();
    }

    @Override
    public void reportAlarm(MovSuministros suministro, int codigoAlarma) throws Exception {
        MovAlarmas alarm = createAlarm(suministro, codigoAlarma);
        AlarmsStack.getInstance().addAlarmToStack(alarm);
    }

    private MovAlarmas createAlarm(MovSuministros suministro, int codigoAlarma) throws Exception {
        
        IPreferencesManager preferencesManager = DataBaseManager.getInstance().getPreferencesManager();
        IDatabaseController databaseController = new DatabaseController(preferencesManager);
        ArrayList<AgendaLectura> agendaAsociada = new DAOAgendaLectura(databaseController).getAgendaAsociada(suministro);
        MovAlarmas alarm = new MovAlarmas();
        
        if(!agendaAsociada.isEmpty()){
            
            alarm.setDfechaVal(new Date());

            MovAlarmasPK alarmasPK = new MovAlarmasPK();
            alarmasPK.setNcodAlarma(codigoAlarma);
            System.out.println("NISRAD EN CREAR ALARMAS  ===============> "+suministro.getMovSuministrosPK().getNnisRad().intValue());
            alarmasPK.setNnisRad(suministro.getMovSuministrosPK().getNnisRad().intValue());
            alarm.setMovAlarmasPK(alarmasPK);
            alarm.setNnic(suministro.getNnic());
            alarm.setNpericons((int)agendaAsociada.get(0).getAgendaLecturaPK().getNpericons());
            alarm.setNunicom(suministro.getNunicom().shortValue());
            alarm.setVcitinerario(suministro.getVcitinerario());
            alarm.setVcruta(suministro.getVcruta());
            alarm.setVctipoEnergia(suministro.getMovSuministrosPK().getVctipoEnergia());

            //Se almacenan los datos del suminsitro que genera la alarma
            alarm.setMovSuministrosPK(suministro.getMovSuministrosPK());
        } else {
             throw new Exception("Error creating alarm");
        }
        
        
        
        return alarm;
    }

    @Override
    public void reportAlarm(AgendaLectura agenda, int codigoAlarma)  throws Exception{
        MovAlarmas alarm = createAlarm(agenda.getListaSuministros().get(0), codigoAlarma);
        AlarmsStack.getInstance().addAlarmToStack(alarm);
    }
}
