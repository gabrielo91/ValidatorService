/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.bussinesvalidations;
import com.metrolink.validatorservice.alarmsmanager.AlarmsManager;
import com.metrolink.validatorservice.alarmsmanager.IAlarmsManager;
import com.metrolink.validatorservice.models.MovLectConsu;
import com.metrolink.validatorservice.models.MovSuministros;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Gabriel Ortega
 */
public class IndividualValidations implements IIndividualValidations {

    private IAlarmsManager alarmsManager;
    public IndividualValidations(IAlarmsManager alarmsManager){
        this.alarmsManager = alarmsManager;
    }

    @Override
    public boolean verificarCalendarioTOU(List<MovSuministros> intinerarios) {        
        boolean result = false;
        if(intinerarios.size() > 0){
            result = true;
            for (MovSuministros intinerario : intinerarios) {
                if (!intinerario.getMovLectConsuCollection().isEmpty() && intinerario.getVctipoVal().equals(MovSuministros.TIPO_LECTURA)){
                    Integer calendarioTOU = intinerarios.get(0).getNcodCalTou().getNcodCalTou();
                    if(null == calendarioTOU || calendarioTOU < 1){
                        result = result && false;
                        alarmsManager.reportAlarm(intinerarios.get(0), AlarmsManager.CALENDARIO_TOU_VALIDATION_ERROR_CODE);
                    } else {
                        result = result && true;
                    }
                }
            }
        }       
        return result;
    }

    @Override
    public boolean verificarExistenciaDatos(List<MovSuministros> intinerarios) {
        boolean result = false;
        if(intinerarios.size() > 0){
            result = true;
            for (MovSuministros intinerario : intinerarios) {
                if(intinerario.getMovLectConsuCollection().size() == 0){
                    alarmsManager.reportAlarm(intinerarios.get(0), AlarmsManager.EXISTENCIA_DE_DATOS_ERROR_CODE);
                    result = result && false;
                } else {
                    result = result && true;
                }
            }
        }
        return result;
    }

    
    @Override
    public boolean comparacionLectuaDiaria(List<MovSuministros> intinerarios) {
        boolean result = false;
        final double MAX_VALUE = 0.9;
        final double MIN_VALUE = 0.2;
        
        int i = 1;
        System.out.println("empezando "+intinerarios.size());
        if(intinerarios.size() > 0){
            result = true;
            for (MovSuministros intinerario : intinerarios) {
                if (!intinerario.getMovLectConsuCollection().isEmpty() && intinerario.getVctipoVal().equals(MovSuministros.TIPO_LECTURA)) {
                    List<MovLectConsu> listaLecturas = intinerario.getMovLectConsuCollection();
                    if (listaLecturas.size() >= 2) {
                        BigDecimal ultimaLectura =  listaLecturas.get(0).getNlectura();
                        BigDecimal penUltimaLectura =  listaLecturas.get(1).getNlectura();
                        BigDecimal vav = ultimaLectura.subtract(penUltimaLectura);                         

                        if (vav.signum() == -1) { //Si el valor es negativo
                            alarmsManager.reportAlarm(intinerarios.get(0), AlarmsManager.DEVOLUCION_DE_REGISTRO_ERROR_CODE);
                            result = result && false; 
                        } else if(vav.signum() == 0){
                            alarmsManager.reportAlarm(intinerarios.get(0), AlarmsManager.LECTURA_REPETIDA_ERROR_CODE);
                            result = result && false;
                        } else if(vav.signum() == 1){
                            double porcentaje = vav.doubleValue()/ultimaLectura.doubleValue();

                            if(porcentaje < MIN_VALUE){
                                alarmsManager.reportAlarm(intinerarios.get(0), AlarmsManager.INCREMENTO_MINIMO_NO_ESPERADO_ERROR_CODE);
                                result = result && false;
                            } else if (porcentaje > MAX_VALUE) {
                                alarmsManager.reportAlarm(intinerarios.get(0), AlarmsManager.INCREMENTO_MAXIMO_NO_ESPERADO_ERROR_CODE);
                                result = result && false;
                            } else {
                                //Lecturas cumplen condicion y se certifican
                                intinerario.certificarLecturas();
                                result = result && true;
                            }
                        }

                        System.out.println("cuenta "+i);
                        System.out.println("Fecha lect: "+listaLecturas.get(0).getTsfechaLec());
                        i++;
                    }
                }
            }
        }
        return result;
    }

    @Override
    public boolean comparacionLectuaDiariaMensual(List<MovSuministros> intinerarios) {
        boolean result = false;
        final double MAX_VALUE = 0.9;
        final double MIN_VALUE = 0.2;
        
        if(intinerarios.size() > 0){
            result = true;
            for (MovSuministros intinerario : intinerarios) {
                if (!intinerario.getMovLectConsuCollection().isEmpty() && intinerario.getVctipoVal().equals(MovSuministros.TIPO_LECTURA)) {
                    List<MovLectConsu> listaLecturas = intinerario.getMovLectConsuCollection();
                    Integer ultimaLecturaReportadaEnOpen =  intinerario.getNulReportada();
                    
                    if(null != ultimaLecturaReportadaEnOpen){
                        if (listaLecturas.size() >= 1) {
                            Integer ultimaLectura =  listaLecturas.get(0).getNlectura().intValueExact();
                            Integer vav = ultimaLectura - ultimaLecturaReportadaEnOpen;
                            
                            if (vav.intValue() < 0) {
                                alarmsManager.reportAlarm(intinerarios.get(0), AlarmsManager.DEVOLUCION_DE_REGISTRO_MENSUAL_ERROR_CODE);
                                result = result && false; 
                            } else if (vav.intValue()  == 0){
                                alarmsManager.reportAlarm(intinerarios.get(0), AlarmsManager.LECTURA_REPETIDA_MENSUAL_ERROR_CODE);
                                result = result && false; 
                            } else if(vav.intValue() > 0){
                                double porcentaje = vav.doubleValue()/ultimaLectura.doubleValue();
                                if(porcentaje < MIN_VALUE){
                                    alarmsManager.reportAlarm(intinerarios.get(0), AlarmsManager.INCREMENTO_MINIMO_NO_ESPERADO_MENSUAL_ERROR_CODE);
                                    result = result && false; 
                                } else if(porcentaje > MAX_VALUE){
                                    alarmsManager.reportAlarm(intinerarios.get(0), AlarmsManager.INCREMENTO_MAXIMO_NO_ESPERADO_MENSUAL_ERROR_CODE);
                                    result = result && false; 
                                } else {
                                    intinerario.certificarLecturas();
                                    result = result && true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return result;
    }
       
}
