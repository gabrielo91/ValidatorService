/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.bussinesvalidations;

import com.metrolink.validatorservice.alarmsmanager.AlarmsManager;
import com.metrolink.validatorservice.alarmsmanager.IAlarmsManager;
import com.metrolink.validatorservice.models.MovLectConsu;
import java.util.List;

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
    public boolean validation1(List<MovLectConsu> listaLecuras) {
        System.out.println("com.metrolink.validatorservice.bussinesvalidations.GeneralValidations.validation1()");
        return true;
    }

    @Override
    public boolean validation2(List<MovLectConsu> listaLecuras) {
        System.out.println("com.metrolink.validatorservice.bussinesvalidations.GeneralValidations.validation2()");
        return true;
    }

    @Override
    public boolean validation3(List<MovLectConsu> listaLecuras) {
        System.out.println("com.metrolink.validatorservice.bussinesvalidations.GeneralValidations.validation3()");
        return true;
    }

    @Override
    public boolean verificarCalendarioTOU(List<MovLectConsu> listaLecuras) {
        boolean result = true;
        Integer calendarioTOU = listaLecuras.get(0).getMovSuministros().getNcodCalTou().getNcodCalTou();
        
        if(null == calendarioTOU || calendarioTOU < 1){
            result = false;
            alarmsManager.reportAlarm(listaLecuras.get(0), AlarmsManager.CALENDARIO_TOU_VALIDATION_ERROR_DESCRIPTION);
        }  
        
        return result;
    }

    /**
     * TODO: Check how data is passed to this methos in order to  accomplish with the validation requirement
     * @param listaLecuras
     * @return 
     */
    @Override
    public boolean verificarExistenciaDatos(List<MovLectConsu> listaLecuras) {
        boolean result = true;
        if (listaLecuras.size() < 1) {
            result = false;
            //Esto debe recibir como argumento los datos que se estan trabajando
            alarmsManager.reportAlarm(new MovLectConsu(), AlarmsManager.EXISTENCIA_DATOS_VALIDATION_ERROR_DESCRIPTION);
        }
        return result;
    }
    
}
