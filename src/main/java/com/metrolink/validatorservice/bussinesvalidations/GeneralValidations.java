/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.bussinesvalidations;

import com.metrolink.validatorservice.models.DTOLecturas;
import java.util.List;

/**
 *
 * @author Gabriel Ortega
 */
public class GeneralValidations implements IGeneralValidations{

    @Override
    public boolean validation1(List<DTOLecturas> listaLecuras) {
        System.out.println("com.metrolink.validatorservice.bussinesvalidations.GeneralValidations.validation1()");
        return true;
    }

    @Override
    public boolean validation2(List<DTOLecturas> listaLecuras) {
        System.out.println("com.metrolink.validatorservice.bussinesvalidations.GeneralValidations.validation2()");
        return true;
    }

    @Override
    public boolean validation3(List<DTOLecturas> listaLecuras) {
        System.out.println("com.metrolink.validatorservice.bussinesvalidations.GeneralValidations.validation3()");
        return true;
    }
    
}
