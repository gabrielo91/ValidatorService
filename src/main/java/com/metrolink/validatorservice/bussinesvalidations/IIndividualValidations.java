/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.bussinesvalidations;

import com.metrolink.validatorservice.models.AgendaLectura;
import java.util.List;

/**
 *
 * @author Gabriel Ortega
 */
public interface IIndividualValidations {
    public boolean validation1(List<AgendaLectura> listaLecuras, int currentIndexToValidate);
    public boolean validation2(List<AgendaLectura> listaLecuras, int currentIndexToValidate);
    public boolean validation3(List<AgendaLectura> listaLecuras, int currentIndexToValidate);
    public boolean validation4(List<AgendaLectura> listaLecuras, int currentIndexToValidate);
    public boolean validation5(List<AgendaLectura> listaLecuras, int currentIndexToValidate);
    public boolean validation6(List<AgendaLectura> listaLecuras, int currentIndexToValidate);
}
