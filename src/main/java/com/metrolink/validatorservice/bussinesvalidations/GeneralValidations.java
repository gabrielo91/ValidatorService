/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.bussinesvalidations;

import com.metrolink.validatorservice.alarmsmanager.AlarmsManager;
import com.metrolink.validatorservice.alarmsmanager.IAlarmsManager;
import com.metrolink.validatorservice.db.controller.ParametrosAdmin;
import com.metrolink.validatorservice.models.AgendaLectura;
import com.metrolink.validatorservice.models.MParametrosAdm;
import com.metrolink.validatorservice.models.MovSuministros;
import com.metrolink.validatorservice.utils.Utils;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Gabriel Ortega
 */
public class GeneralValidations implements IGeneralValidations{
    
    private IAlarmsManager alarmsManager;
    
    public GeneralValidations(IAlarmsManager alarmsManager){
        this.alarmsManager = alarmsManager;
    }



    
    @Override
    public boolean verificarCompletitudInformacion(List<AgendaLectura> listAgenda) throws Exception {
        
        //Sobre este preguntar si la formula basta o en que momento se cuentan los registros
        
        boolean result = true;
        Date fechaActual = new Date();
        MParametrosAdm parametrosAdmin = ParametrosAdmin.getParametrosAdmin();
        long vpCompletitud = parametrosAdmin.getNvpCompletud();
        long toleranciaCompletitud = parametrosAdmin.getNtolCompletud();
        
        for (AgendaLectura agenda : listAgenda) {                    
            if (!agenda.getListaSuministros().isEmpty() && agenda.getListaSuministros().get(0).getVctipoVal().equals(MovSuministros.TIPO_LECTURA)) {
                Date maxFechaLectura = agenda.getAgendaLecturaPK().getDfechaTeo();
                long diffTime = fechaActual.getTime() - maxFechaLectura.getTime();
                long daysDiff =  TimeUnit.DAYS.convert(diffTime, TimeUnit.MILLISECONDS);
                System.out.println("Dias de diferencia: "+daysDiff);
                if (daysDiff > vpCompletitud) {
                    alarmsManager.reportAlarm(agenda.getListaSuministros().get(0), AlarmsManager.COMPLETITUD_INFO_VALIDATION_ERROR_CODE);
                }
            
            } else if (!agenda.getListaSuministros().isEmpty() && agenda.getListaSuministros().get(0).getVctipoVal().equals(MovSuministros.TIPO_CONSUMO)){
                
                Date fsy = fechaActual;
                
                for (MovSuministros suministro : agenda.getListaSuministros()) {
                    
                    Date ful = suministro.getTsful();//Fecha Ultima Lectura reportada en open SGC
                    Date flt = suministro.getTsflt();//fecha de lectura teorica
                    Date fla = suministro.getTsfla(); //fecha ultima lectura reportada en perseo
                    
                    long rangoDiasSistema = fla.getTime() - ful.getTime();
                    rangoDiasSistema = TimeUnit.DAYS.convert(rangoDiasSistema, TimeUnit.MILLISECONDS)-toleranciaCompletitud;
                    long qrr = rangoDiasSistema*24;
                    
                    if(fla.compareTo(Utils.addDays(fsy, -1)) >= 0){
                        long rangoDias = fsy.getTime() - ful.getTime();
                        rangoDias = TimeUnit.DAYS.convert(rangoDias, TimeUnit.MILLISECONDS)-toleranciaCompletitud;
                        long rangoHoras = rangoDias*24;
                                                        
                        if(rangoHoras > qrr){
                            alarmsManager.reportAlarm(agenda.getListaSuministros().get(0), AlarmsManager.COMPLETITUD_INFO_VALIDATION_ERROR_CODE);
                        }
                    } else {
                        alarmsManager.reportAlarm(agenda.getListaSuministros().get(0), AlarmsManager.COMPLETITUD_INFO_VALIDATION_ERROR_CODE);
                    }
                }
                
            }
        }
        
        return result;
    }
    
}
