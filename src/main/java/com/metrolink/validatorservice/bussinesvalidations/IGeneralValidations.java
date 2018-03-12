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
    
    public boolean verificarCompletitudInformacion(List<AgendaLectura> listaLecuras) throws Exception;
    
}
