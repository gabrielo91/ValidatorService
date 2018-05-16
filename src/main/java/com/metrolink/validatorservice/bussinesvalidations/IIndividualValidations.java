/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.bussinesvalidations;

import com.metrolink.validatorservice.models.MovSuministros;
import java.util.List;

/**
 *
 * @author Gabriel Ortega
 */
public interface IIndividualValidations {
    
    public boolean V1verificarCalendarioTOU(List<MovSuministros> itinerarios)  throws Exception;
    public boolean V2verificarExistenciaDatos(List<MovSuministros> itinerarios)  throws Exception;
    public boolean V3verificarCompletitudInformacion(List<MovSuministros> listaLecuras) throws Exception;
    public boolean V4comparacionLectuaDiaria(List<MovSuministros> itinerarios) throws Exception;
    public boolean V5comparacionLectuaDiariaMensual(List<MovSuministros> itinerarios)  throws Exception;
    public boolean V6verificarPorcentajeMaximoSuperiorInferior(List<MovSuministros> itinerarios)  throws Exception;
    
}
