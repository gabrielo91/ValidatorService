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
public interface IGeneralValidations {
    
    public boolean verificarCalendarioTOU(List<AgendaLectura> listaLecuras);
    public boolean verificarExistenciaDatos(List<AgendaLectura> listaLecuras);
    public boolean verificarCompletitudInformacionLecturas(List<AgendaLectura> listaLecuras);
    public boolean verificarCompletitudInformacionConsumos(List<AgendaLectura> listaLecuras);
    
}
