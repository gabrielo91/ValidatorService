/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.bussinesvalidations;

import com.metrolink.validatorservice.models.MovRegsSco;
import com.metrolink.validatorservice.models.MovSuministros;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author Gabriel Ortega
 */
public class IndividualValidationsSCO implements IIndividualValidationsSCO {

    //Validar que los valores se esten pasando por referencia
    //para probar este metodo adicionar cinco lecturas  con una que tenga el vccoan > 0
    @Override
    public boolean verificarDesviacionConsumo(List<MovSuministros> itinerariosSCO) throws Exception {
        boolean result = false;
        if (itinerariosSCO.size() > 0) {
            result = true;
            int rangoMeses = 5;
            for (MovSuministros itinerario : itinerariosSCO) {

                if (!itinerario.getMovLectConsuCollection().isEmpty() && itinerario.getVctipoVal().equals(MovSuministros.TIPO_LECTURA)) {
                    List<MovRegsSco> movLectConsuAnalizables = itinerario.getMovRegsScoCollection().subList(0, rangoMeses - 1);

                    //#1.
                    validarAusenciaAnomalias(movLectConsuAnalizables);
                    if (movLectConsuAnalizables.size() >= rangoMeses) {
                        Collections.sort(movLectConsuAnalizables, sorterByDate()); // Se organiza por fechas en orden descendente
                        
                    }
                }

            }
        }
        return result;
    }
    
    private  Comparator<MovRegsSco> sorterByDate(){
        return new Comparator<MovRegsSco>() {
            @Override
            public int compare(MovRegsSco o1, MovRegsSco o2) {
                return o2.getMovRegsScoPK().getTsfeclet().compareTo(o1.getMovRegsScoPK().getTsfeclet());
            }
        };
    }

    private void validarAusenciaAnomalias(List<MovRegsSco> movLectConsuAnalizables) {
        List<MovRegsSco> movLectConsuAnalizablesLocal = new ArrayList<>(movLectConsuAnalizables);

        for (MovRegsSco movLectConsuAnalizable : movLectConsuAnalizablesLocal) {
            String codigoAnomalia = movLectConsuAnalizable.getVccoan();
            if (null != codigoAnomalia && !"".equals(codigoAnomalia) && !"0".equals(codigoAnomalia)) {
                movLectConsuAnalizables.remove(movLectConsuAnalizable);
            }
        }
    }
    
    

}
