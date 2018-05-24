/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.bussinesvalidations;

import com.metrolink.validatorservice.alarmsmanager.AlarmsManager;
import com.metrolink.validatorservice.alarmsmanager.IAlarmsManager;
import com.metrolink.validatorservice.db.controller.DataBaseManager;
import com.metrolink.validatorservice.db.controller.DatabaseController;
import com.metrolink.validatorservice.db.controller.IDatabaseController;
import com.metrolink.validatorservice.db.controller.ParametrosConf;
import com.metrolink.validatorservice.db.daos.DAOAgendaLectura;
import com.metrolink.validatorservice.db.daos.DAOParametrosAdmin;
import com.metrolink.validatorservice.db.daos.DAOParametrosConf;
import com.metrolink.validatorservice.db.daos.IDAOAgendaLectura;
import com.metrolink.validatorservice.db.daos.IDAOParametrosAdmin;
import com.metrolink.validatorservice.db.daos.IDAOParametrosConf;
import com.metrolink.validatorservice.logger.DataLogger;
import com.metrolink.validatorservice.models.AgendaLectura;
import com.metrolink.validatorservice.models.MConfVal;
import com.metrolink.validatorservice.models.MParametrosAdm;
import com.metrolink.validatorservice.models.MovRegsSco;
import com.metrolink.validatorservice.models.MovSuministros;
import com.metrolink.validatorservice.models.MovSuministrosPK;
import com.metrolink.validatorservice.preferencesmanager.IPreferencesManager;
import com.metrolink.validatorservice.utils.Utils;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.DoubleStream;

/**
 *
 * @author Gabriel Ortega
 */
public class IndividualValidationsSCO implements IIndividualValidationsSCO {

    private IAlarmsManager alarmsManager;
    private IDAOParametrosConf parametrosconfvaldao;
    private IDAOAgendaLectura daoAgendaLectura;
    private IDAOParametrosAdmin daoParametrosAdmin;

    public IndividualValidationsSCO(IAlarmsManager alarmsManager) {
        this.alarmsManager = alarmsManager;
    }

    //para probar este metodo adicionar cinco lecturas  con una que tenga el vccoan > 0
    @Override
    public boolean verificarDesviacionConsumo(List<MovSuministros> itinerariosSCO, List<MovSuministrosPK> suministrosInvalidos) throws Exception {
        System.out.println("La validacion a ejecutar es: V7verificarDesviacionConsumo");
        IPreferencesManager preferencesManager = DataBaseManager.getInstance().getPreferencesManager();
        IDatabaseController databaseController = new DatabaseController(preferencesManager);
        boolean result = false;
        parametrosconfvaldao = new DAOParametrosConf(databaseController);
        try {
            if (itinerariosSCO.size() > 0) {
                result = true;

                for (MovSuministros itinerario : itinerariosSCO) {
                    if (Utils.esItinerarioValido(itinerario.getMovSuministrosPK(), suministrosInvalidos)) {
                        if (!itinerario.getMovRegsScoCollection().isEmpty() && itinerario.getVctipoVal().equals(MovSuministros.TIPO_LECTURA)) {
                            MConfVal parametrosConf = parametrosconfvaldao.getParametrosConf(itinerario);
                            final int RANGO_MESES_MAXIMO = parametrosConf.getNranDesMax().intValue();
                            final int RANGO_MESES_MINIMO = parametrosConf.getNranDesMin().intValue();
                            final double COEFICIENTE_VARIACION_MAX = parametrosConf.getNdesConCoe().doubleValue();

                            System.out.println("********************* RANGO_MESES_MAXIMO " + RANGO_MESES_MAXIMO);
                            System.out.println("********************* RANGO_MESES_MINIMO " + RANGO_MESES_MINIMO);
                            System.out.println("********************* COEFICIENTE_VARIACION_MAX " + COEFICIENTE_VARIACION_MAX);
                            System.out.println("********************* getMovRegsScoCollection " + itinerario.getMovRegsScoCollection().subList(0, RANGO_MESES_MAXIMO > itinerario.getMovRegsScoCollection().size() ? itinerario.getMovRegsScoCollection().size() : RANGO_MESES_MAXIMO));
                            List<MovRegsSco> movLectConsuAnalizables = itinerario.getMovRegsScoCollection().subList(0, RANGO_MESES_MAXIMO > itinerario.getMovRegsScoCollection().size() ? itinerario.getMovRegsScoCollection().size() : RANGO_MESES_MAXIMO);
                            getMovLectConsuAnalizables(RANGO_MESES_MAXIMO, itinerario.getTsfla(), movLectConsuAnalizables);
                            
                            validarAusenciaDeAnomalias(movLectConsuAnalizables);
                            if (movLectConsuAnalizables.size() >= RANGO_MESES_MINIMO) {
                                Collections.sort(movLectConsuAnalizables, sorterByDate()); // Se organiza por fechas en orden descendente

                                double[] diferencias = obtenerDiferenciasLecturas(itinerario.getMovRegsScoCollection());
                                double desviacionEstandar = calcularDesviacionEstandar(diferencias);
                                double promedio = (DoubleStream.of(diferencias).sum()) / diferencias.length;
                                double coeficienteVariacion = desviacionEstandar / Math.abs(promedio);

                                if (coeficienteVariacion > COEFICIENTE_VARIACION_MAX) {
                                    alarmsManager.reportAlarm(itinerario, AlarmsManager.DESVIACION_DE_CONSUMO_ERROR_CODE);
                                    result = result && false;
                                } else {
                                    result = result && true;
                                }
                            } else {
                                result = result && false;
                            }
                        }
                        /*else if(!itinerario.getMovLectConsuCollection().isEmpty()){ //Analisis para consumo
                             MConfVal parametrosConf = parametrosconfvaldao.getParametrosConf(itinerario);
                            final int RANGO_MESES_MAXIMO = parametrosConf.getNranDesMax().intValue();
                            final int RANGO_MESES_MINIMO = parametrosConf.getNranDesMin().intValue();
                            final double COEFICIENTE_VARIACION_MAX = parametrosConf.getNdesConCoe().doubleValue();
                            List<MovRegsSco> movLectConsuAnalizables = itinerario.getMovRegsScoCollection().subList(0, RANGO_MESES_MAXIMO);
                            validarAusenciaDeAnomalias(movLectConsuAnalizables);
                            if (movLectConsuAnalizables.size() >= RANGO_MESES_MINIMO) {
                                Collections.sort(movLectConsuAnalizables, sorterByDate()); // Se organiza por fechas en orden descendente

                                double[] valoresConsumo = obtenerConsumos(itinerario.getMovRegsScoCollection());
                                double desviacionEstandar = calcularDesviacionEstandar(valoresConsumo);
                                double promedio = (DoubleStream.of(valoresConsumo).sum()) / valoresConsumo.length;
                                double coeficienteVariacion = desviacionEstandar / Math.abs(promedio);

                                if (coeficienteVariacion > COEFICIENTE_VARIACION_MAX) {
                                    alarmsManager.reportAlarm(itinerario, AlarmsManager.DESVIACION_DE_CONSUMO_ERROR_CODE);
                                    result = result && false;
                                } else {
                                    result = result && true;
                                }
                            } else {
                                result = result && false;
                            }
                        }*/
                    } else {
                        result = result && false;
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
        List<BigInteger> periodosanomalos = new ArrayList<>();
        
        for (MovRegsSco movLectConsuAnalizable : movLectConsuAnalizablesLocal) {
                      
            String codigoAnomalia = movLectConsuAnalizable.getVccoan();
            if (null != codigoAnomalia && !"".equals(codigoAnomalia)) {   
                periodosanomalos.add(movLectConsuAnalizable.getNperiodo());
            }            
        }
        
        for (MovRegsSco movLectConsuAnalizable : movLectConsuAnalizablesLocal) {
                      
            if (periodosanomalos.isEmpty()) {
                if (Utils.arrayContains(periodosanomalos, movLectConsuAnalizable.getNperiodo())) {
                    movLectConsuAnalizables.remove(movLectConsuAnalizable);
                }
            }          
        }
        
        
        

    }

    private double[] obtenerDiferenciasLecturas(ArrayList<MovRegsSco> movRegsScoCollection) {
        double[] diferencias = new double[movRegsScoCollection.size() - 1];
        for (int i = 0; i < movRegsScoCollection.size() - 1; i++) {
            diferencias[i] = movRegsScoCollection.get(i).getNlec().longValue() - movRegsScoCollection.get(i + 1).getNlec().longValue();
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

    private void getMovLectConsuAnalizables(final int rangoMeses, Date fechaUltimaLecturaReportadaPerseo, List<MovRegsSco> movLectConsuAnalizables) {
        
        if (!movLectConsuAnalizables.isEmpty()) {
            
            List<MovRegsSco> movLectConsuAnalizablesCopy = new ArrayList<>(movLectConsuAnalizables);
            
            for (MovRegsSco movLectConsuAnalizable : movLectConsuAnalizablesCopy) {
                Date tsFechaLect = movLectConsuAnalizable.getMovRegsScoPK().getTsfeclet();
                fechaUltimaLecturaReportadaPerseo = Utils.addMonths(fechaUltimaLecturaReportadaPerseo, -rangoMeses);
                if(tsFechaLect.before(fechaUltimaLecturaReportadaPerseo)){
                    movLectConsuAnalizables.remove(movLectConsuAnalizable);
                }
            }
        }
    }

}
