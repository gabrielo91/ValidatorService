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
    private final int HORAS_DIA = 24;
    
    public GeneralValidations(IAlarmsManager alarmsManager){
        this.alarmsManager = alarmsManager;
    }

    //CUANDO SE CERTIFICAN LAS LECTURAS?? **************************************

    
    @Override
    public boolean verificarCompletitudInformacion(List<AgendaLectura> listAgenda) throws Exception {
        
        //Sobre este preguntar si la formula basta o en que momento se cuentan los registros
        
        boolean result = false;
        Date fechaActual = new Date();
        MParametrosAdm parametrosAdmin = ParametrosAdmin.getParametrosAdmin();
        long vpCompletitud = parametrosAdmin.getNvpCompletud();
        int toleranciaCompletitud = parametrosAdmin.getNtolCompletud();
        
        for (AgendaLectura agenda : listAgenda) {
            
            if (!agenda.getListaSuministros().isEmpty() && agenda.getListaSuministros().get(0).getVctipoVal().equals(MovSuministros.TIPO_LECTURA)) {
                
                Date maxFechaLectura = agenda.getAgendaLecturaPK().getDfechaTeo();
                long diffTime = fechaActual.getTime() - maxFechaLectura.getTime();
                long daysDiff =  TimeUnit.DAYS.convert(diffTime, TimeUnit.MILLISECONDS);
                
                System.out.println("Dias de diferencia: "+daysDiff);
                if (daysDiff > vpCompletitud) {
                    alarmsManager.reportAlarm(agenda.getListaSuministros().get(0), AlarmsManager.COMPLETITUD_INFO_VALIDATION_ERROR_CODE);
                    result = false;
                } else {
                    result = true;
                }
            
            } else if (!agenda.getListaSuministros().isEmpty() && agenda.getListaSuministros().get(0).getVctipoVal().equals(MovSuministros.TIPO_CONSUMO)){
                
                Date fsy = fechaActual;
                result = true;
                for (MovSuministros suministro : agenda.getListaSuministros()) {
                    Date ful = suministro.getTsful();//Fecha Ultima Lectura reportada en open SGC
                    Date flt = suministro.getTsflt();//fecha de lectura teorica
                    Date fla = suministro.getTsfla(); //fecha ultima lectura reportada en perseo
                    Short factorTiempoDiario = suministro.getNnumRegs();//NNUM_REGS
                    
                    long rangoDiasSistema = fla.getTime() - ful.getTime(); //Diferencia en milisegundos entre los dias
                    rangoDiasSistema = TimeUnit.DAYS.convert(rangoDiasSistema, TimeUnit.MILLISECONDS);
                    long qrr = rangoDiasSistema * HORAS_DIA * factorTiempoDiario.intValue();
                    
                    if(fla.compareTo(Utils.addDays(fsy, -toleranciaCompletitud)) > 0){
                        
                        long rangoDias = fsy.getTime() - ful.getTime();
                        rangoDias = TimeUnit.DAYS.convert(rangoDias, TimeUnit.MILLISECONDS)-toleranciaCompletitud;
                        long registrosEsperados = rangoDias * HORAS_DIA * factorTiempoDiario;
                        
                        if (registrosEsperados > qrr) {
                            alarmsManager.reportAlarm(agenda.getListaSuministros().get(0), AlarmsManager.COMPLETITUD_INFO_VALIDATION_ERROR_CODE);
                            suministro.invalidarLecturas();
                            result = result && false;        
                        } else {
                            result = result && true;
                        }
                    } else {
                        result = result && false;       
                    }
                } 
            } else {
                result = false;
            }
        }
        return result;
    }
    
}
