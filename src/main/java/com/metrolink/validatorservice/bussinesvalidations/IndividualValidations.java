/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.bussinesvalidations;
import com.metrolink.validatorservice.alarmsmanager.AlarmsManager;
import com.metrolink.validatorservice.alarmsmanager.IAlarmsManager;
import com.metrolink.validatorservice.models.MovSuministros;
import java.util.List;

/**
 *
 * @author Gabriel Ortega
 */
public class IndividualValidations implements IIndividualValidations {

    private IAlarmsManager alarmsManager;
    public IndividualValidations(IAlarmsManager alarmsManager){
        this.alarmsManager = alarmsManager;
    }

    @Override
    public boolean verificarCalendarioTOU(List<MovSuministros> intinerarios) {
        System.out.println("El total de intinerarios es: " + intinerarios.size());
        boolean result = true;
        
        Integer calendarioTOU = intinerarios.get(0).getNcodCalTou().getNcodCalTou();
        System.out.println("Calendario tou es: "+calendarioTOU);
        if(null == calendarioTOU || calendarioTOU < 1){
            result = false;
            alarmsManager.reportAlarm(intinerarios.get(0), AlarmsManager.CALENDARIO_TOU_VALIDATION_ERROR_CODE);
        }       
               
        return result;
    }
       
}
