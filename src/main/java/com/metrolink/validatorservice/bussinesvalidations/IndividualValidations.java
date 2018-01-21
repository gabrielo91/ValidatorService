/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.bussinesvalidations;

import com.metrolink.validatorservice.models.MovLectConsu;
import java.util.List;

/**
 *
 * @author Gabriel Ortega
 */
public class IndividualValidations implements IIndividualValidations {

    //TODO check that every reading has not been invalidated 
    
    @Override
    public boolean validation1(List<MovLectConsu> listaLecuras, int currentIndexToValidate) {
        System.out.println("com.metrolink.validatorservice.bussinesvalidations.Validations.validation1()");
        return true;
    }

    @Override
    public boolean validation2(List<MovLectConsu> listaLecuras, int currentIndexToValidate) {
        System.out.println("com.metrolink.validatorservice.bussinesvalidations.Validations.validation2()");
        return true;
    }

    @Override
    public boolean validation3(List<MovLectConsu> listaLecuras, int currentIndexToValidate) {
        System.out.println("com.metrolink.validatorservice.bussinesvalidations.Validations.validation3()");
        return true;
    }

    @Override
    public boolean validation4(List<MovLectConsu> listaLecuras, int currentIndexToValidate) {
        System.out.println("com.metrolink.validatorservice.bussinesvalidations.Validations.validation4()");
        return true;
    }

    @Override
    public boolean validation5(List<MovLectConsu> listaLecuras, int currentIndexToValidate) {
        System.out.println("com.metrolink.validatorservice.bussinesvalidations.Validations.validation5()");
        return true;
    }
    
    @Override
    public boolean validation6(List<MovLectConsu> listaLecuras, int currentIndexToValidate) {
        System.out.println("ganamos");
        System.out.println("listaLecuras: "+listaLecuras.size());
        return true;
    }

    
}
