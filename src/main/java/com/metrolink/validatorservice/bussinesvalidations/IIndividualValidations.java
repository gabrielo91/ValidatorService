/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.bussinesvalidations;

import com.metrolink.validatorservice.models.AgendaLectura;
import com.metrolink.validatorservice.models.MovSuministros;
import java.util.List;

/**
 *
 * @author Gabriel Ortega
 */
public interface IIndividualValidations {
    
    public boolean verificarCalendarioTOU(List<MovSuministros> itinerarios)  throws Exception;
    public boolean verificarExistenciaDatos(List<MovSuministros> itinerarios)  throws Exception;
    public boolean verificarCompletitudInformacion(List<MovSuministros> listaLecuras) throws Exception;
    public boolean comparacionLectuaDiaria(List<MovSuministros> itinerarios) throws Exception;
    public boolean comparacionLectuaDiariaMensual(List<MovSuministros> itinerarios)  throws Exception;
    public boolean verificarPorcentajeMaximoSuperiorInferior(List<MovSuministros> itinerarios)  throws Exception;
    public boolean verificarDesviacionConsumo(List<MovSuministros> itinerarios)  throws Exception;
    
}
