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
public interface IValidations {
    public boolean validation1(List<DTOLecturas> listaLecuras, int currentIndexToValidate);
    public boolean validation2(List<DTOLecturas> listaLecuras, int currentIndexToValidate);
    public boolean validation3(List<DTOLecturas> listaLecuras, int currentIndexToValidate);
    public boolean validation4(List<DTOLecturas> listaLecuras, int currentIndexToValidate);
    public boolean validation5(List<DTOLecturas> listaLecuras, int currentIndexToValidate);
    public boolean validation6(List<DTOLecturas> listaLecuras, int currentIndexToValidate);
}
