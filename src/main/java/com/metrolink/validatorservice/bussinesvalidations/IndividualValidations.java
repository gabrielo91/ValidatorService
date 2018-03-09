/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.bussinesvalidations;
import com.metrolink.validatorservice.alarmsmanager.AlarmsManager;
import com.metrolink.validatorservice.alarmsmanager.IAlarmsManager;
import com.metrolink.validatorservice.models.MovLectConsu;
import com.metrolink.validatorservice.models.MovSuministros;
import java.math.BigDecimal;
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
        boolean result = true;
        
        Integer calendarioTOU = intinerarios.get(0).getNcodCalTou().getNcodCalTou();
        if(null == calendarioTOU || calendarioTOU < 1){
            result = false;
            alarmsManager.reportAlarm(intinerarios.get(0), AlarmsManager.CALENDARIO_TOU_VALIDATION_ERROR_CODE);
        }       
               
        return result;
    }

    @Override
    public boolean verificarExistenciaDatos(List<MovSuministros> intinerarios) {
        return true;
    }

    @Override
    public boolean comparacionLectuaDiaria(List<MovSuministros> intinerarios) {
        System.out.println("El total de intinerarios es: " + intinerarios.size());
        int i = 1;
        System.out.println("empezando "+intinerarios.size());
        for (MovSuministros intinerario : intinerarios) {
            List<MovLectConsu> listaLecturas = intinerario.getMovLectConsuCollection();
            System.out.println("--------------   El tamano de lecturas es: " + listaLecturas.size());
            if (listaLecturas.size() > 0) {
                BigDecimal ultimaLectura =  listaLecturas.get(0).getNconsumoOri();
                System.out.println("cuenta "+i);
                System.out.println("Fecha lect: "+listaLecturas.get(0).getTsfechaLec());
                i++;
            }
        }
        
        return true;
    }
       
}
