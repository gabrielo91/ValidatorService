/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.bussinesvalidations;

import com.metrolink.validatorservice.models.MovSuministros;
import com.metrolink.validatorservice.models.MovSuministrosPK;
import java.util.List;

/**
 *
 * @author Gabriel Ortega
 */
public interface IIndividualValidationsSCO {
    public boolean verificarDesviacionConsumo(List<MovSuministros> itinerariosSCO, List<MovSuministrosPK> suministrosInvalidos)  throws Exception;
}
