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
public interface IIndividualValidations {
    public boolean validation1(List<MovLectConsu> listaLecuras, int currentIndexToValidate);
    public boolean validation2(List<MovLectConsu> listaLecuras, int currentIndexToValidate);
    public boolean validation3(List<MovLectConsu> listaLecuras, int currentIndexToValidate);
    public boolean validation4(List<MovLectConsu> listaLecuras, int currentIndexToValidate);
    public boolean validation5(List<MovLectConsu> listaLecuras, int currentIndexToValidate);
    public boolean validation6(List<MovLectConsu> listaLecuras, int currentIndexToValidate);
}
