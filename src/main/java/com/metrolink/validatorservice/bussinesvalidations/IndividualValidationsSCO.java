/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.bussinesvalidations;

import com.metrolink.validatorservice.alarmsmanager.AlarmsManager;
import com.metrolink.validatorservice.alarmsmanager.IAlarmsManager;
import com.metrolink.validatorservice.logger.DataLogger;
import com.metrolink.validatorservice.models.MovRegsSco;
import com.metrolink.validatorservice.models.MovSuministros;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.DoubleStream;

/**
 *
 * @author Gabriel Ortega
 */
public class IndividualValidationsSCO implements IIndividualValidationsSCO {

    
    private IAlarmsManager alarmsManager;
    public IndividualValidationsSCO(IAlarmsManager alarmsManager){
        this.alarmsManager = alarmsManager;
    }
    
    
    //para probar este metodo adicionar cinco lecturas  con una que tenga el vccoan > 0
    @Override
    public boolean verificarDesviacionConsumo(List<MovSuministros> itinerariosSCO) throws Exception {
        boolean result = false;
        try {
            if (itinerariosSCO.size() > 0) {
                result = true;

                //TODO REEMPLAZAR VALORES
                final int RANGO_MESES = 4;
                final double COEFICIENTE_VARIACION_MAX = 0.3;

                for (MovSuministros itinerario : itinerariosSCO) {

                    if (!itinerario.getMovRegsScoCollection().isEmpty() && itinerario.getVctipoVal().equals(MovSuministros.TIPO_LECTURA)) {
                        List<MovRegsSco> movLectConsuAnalizables = itinerario.getMovRegsScoCollection().subList(0, RANGO_MESES);
                        validarAusenciaDeAnomalias(movLectConsuAnalizables);
                        if (movLectConsuAnalizables.size() >= RANGO_MESES) {
                            Collections.sort(movLectConsuAnalizables, sorterByDate()); // Se organiza por fechas en orden descendente

                            double[] diferencias = obtenerDiferenciasLecturas(itinerario.getMovRegsScoCollection());
                            double desviacionEstandar = calcularDesviacionEstandar(diferencias);
                            double promedio = (DoubleStream.of(diferencias).sum()) / diferencias.length;
                            double coeficienteVariacion = desviacionEstandar/Math.abs(promedio);

                            if(coeficienteVariacion > COEFICIENTE_VARIACION_MAX){
                                alarmsManager.reportAlarm(itinerariosSCO.get(0), AlarmsManager.DESVIACION_DE_CONSUMO_ERROR_CODE);
                                result = result && false;
                            } else {
                                result = result && true;
                            }
                        } else {
                            result = result && false;
                        }
                    } else { //Analisis para consumo

                            List<MovRegsSco> movLectConsuAnalizables = itinerario.getMovRegsScoCollection().subList(0, RANGO_MESES);
                            validarAusenciaDeAnomalias(movLectConsuAnalizables);
                            if (movLectConsuAnalizables.size() >= RANGO_MESES) {
                                Collections.sort(movLectConsuAnalizables, sorterByDate()); // Se organiza por fechas en orden descendente

                                double[] valoresConsumo = obtenerConsumos(itinerario.getMovRegsScoCollection());
                                double desviacionEstandar = calcularDesviacionEstandar(valoresConsumo);
                                double promedio = (DoubleStream.of(valoresConsumo).sum()) / valoresConsumo.length;
                                double coeficienteVariacion = desviacionEstandar/Math.abs(promedio);

                                if(coeficienteVariacion > COEFICIENTE_VARIACION_MAX){
                                    alarmsManager.reportAlarm(itinerariosSCO.get(0), AlarmsManager.DESVIACION_DE_CONSUMO_ERROR_CODE);
                                    result = result && false;
                                } else {
                                    result = result && true;
                                }
                            } else {
                                result = result && false;
                            }
                    }

                }
            }
        } catch (Exception e) {
            String mensaje = "Error verificando desviación de consumo";
            DataLogger.Log(e, mensaje);
            throw new Exception(mensaje);
        }
        return result;
    }

    private Comparator<MovRegsSco> sorterByDate() {
        return new Comparator<MovRegsSco>() {
            @Override
            public int compare(MovRegsSco o1, MovRegsSco o2) {
                return o2.getMovRegsScoPK().getTsfeclet().compareTo(o1.getMovRegsScoPK().getTsfeclet());
            }
        };
    }

    private void validarAusenciaDeAnomalias(List<MovRegsSco> movLectConsuAnalizables) {
        List<MovRegsSco> movLectConsuAnalizablesLocal = new ArrayList<>(movLectConsuAnalizables);

        for (MovRegsSco movLectConsuAnalizable : movLectConsuAnalizablesLocal) {
            String codigoAnomalia = movLectConsuAnalizable.getVccoan();
            if (null != codigoAnomalia && !"".equals(codigoAnomalia) && !"0".equals(codigoAnomalia)) {
                movLectConsuAnalizables.remove(movLectConsuAnalizable);
            }
        }
    }

    private double[] obtenerDiferenciasLecturas(ArrayList<MovRegsSco> movRegsScoCollection) {
        double[] diferencias = new double[movRegsScoCollection.size()-1];
        for (int i = 0; i < movRegsScoCollection.size()-1; i++) {
            diferencias[i] = movRegsScoCollection.get(i).getNlec().longValue() - movRegsScoCollection.get(i+1).getNlec().longValue();
        }
        return diferencias;
    }
    
    private double[] obtenerConsumos(ArrayList<MovRegsSco> movRegsScoCollection) {
        double[] consumos = new double[movRegsScoCollection.size()];
        for (int i = 0; i < movRegsScoCollection.size(); i++) {
            consumos[i] = movRegsScoCollection.get(i).getNconsumo().longValue();
        }
        return consumos;
    }

    private double calcularDesviacionEstandar(double[] diferencias) {
        double promedio = (DoubleStream.of(diferencias).sum()) / diferencias.length;
        float sumatoria = 0.0f;

        for (int i = 0; i < diferencias.length; i++) {
            sumatoria += Math.pow(diferencias[i] - promedio, 2);
        }

        return Math.sqrt(sumatoria / (double) diferencias.length);
    }

}
