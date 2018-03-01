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
public interface IGeneralValidations {
    
    public boolean verificarCalendarioTOU(List<MovLectConsu> listaLecuras);
    public boolean verificarExistenciaDatos(List<MovLectConsu> listaLecuras);
    public boolean verificarCompletitudInformacionLecturas(List<MovLectConsu> listaLecuras);
    public boolean verificarCompletitudInformacionConsumos(List<MovLectConsu> listaLecuras);
    
}
