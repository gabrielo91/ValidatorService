/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.bussinesvalidations;
import com.metrolink.validatorservice.models.MCalTou;
import com.metrolink.validatorservice.models.MovSuministros;
import java.util.List;

/**
 *
 * @author Gabriel Ortega
 */
public class IndividualValidations implements IIndividualValidations {

    //TODO check that every reading has not been invalidated 

    @Override
    public boolean verificarCalendarioTOU(List<MovSuministros> intinerarios) {
        System.out.println("El total de intinerarios es: " + intinerarios.size());
        boolean result = false;
        MCalTou calTou = intinerarios.get(0).getNcodCalTou();
        result = null != calTou;
        return result;
    }
       
}
