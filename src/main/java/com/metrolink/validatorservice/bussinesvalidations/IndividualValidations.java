/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.bussinesvalidations;
import com.metrolink.validatorservice.alarmsmanager.AlarmsManager;
import com.metrolink.validatorservice.alarmsmanager.IAlarmsManager;
import com.metrolink.validatorservice.db.controller.ParametrosAdmin;
import com.metrolink.validatorservice.db.controller.ParametrosConf;
import com.metrolink.validatorservice.logger.DataLogger;
import com.metrolink.validatorservice.models.MConfVal;
import com.metrolink.validatorservice.models.MParametrosAdm;
import com.metrolink.validatorservice.models.MovLectConsu;
import com.metrolink.validatorservice.models.MovSuministros;
import com.metrolink.validatorservice.utils.Utils;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
    public boolean verificarCalendarioTOU(List<MovSuministros> itinerarios) throws Exception  { 
        
        boolean result = false;
      
        try {
            if(itinerarios.size() > 0){
                result = true;
                for (MovSuministros itinerario : itinerarios) {
                    if(!itinerario.isSuministroInvalidado()){                        
                        if (!itinerario.getMovLectConsuCollection().isEmpty() && itinerario.getVctipoVal().equals(MovSuministros.TIPO_CONSUMO)){                           
                            Integer calendarioTOU = itinerarios.get(0).getNcodCalTou().getNcodCalTou();
                            if(null == calendarioTOU || calendarioTOU < 1){                                
                                result = result && false;
                                alarmsManager.reportAlarm(itinerarios.get(0), AlarmsManager.CALENDARIO_TOU_VALIDATION_ERROR_CODE);
                            } else {                                
                                result = result && true;
                            }
                        }
                    }
                }
            }  
        
        } catch (Exception e) {
            String mensaje = "Error verificando Calendario TOU";
            DataLogger.Log(e, mensaje);
            throw new Exception(mensaje);
        }
        return result;
    }

    @Override
    public boolean verificarExistenciaDatos(List<MovSuministros> itinerarios) throws Exception {
        boolean result = false;       
        
        try {
            if(itinerarios.size() > 0){
                result = true;
                for (MovSuministros itinerario : itinerarios) {
                    if(!itinerario.isSuministroInvalidado()){
                        
                        if(itinerario.getMovLectConsuCollection().isEmpty() || !validarFechasLecturas(itinerario)){
                            alarmsManager.reportAlarm(itinerarios.get(0), AlarmsManager.EXISTENCIA_DE_DATOS_ERROR_CODE);
                            itinerario.setSuministroInvalidado(true);
                            result = result && false;
                        } else {
                            result = result && true;
                        }
                    } 
                }
            }
        } catch (Exception e) {
            String mensaje = "Error verificando existencia de datos";
            DataLogger.Log(e, mensaje);
            throw new Exception(mensaje);
        }
        return result;
    }

    
    @Override
    public boolean verificarCompletitudInformacion(List<MovSuministros> itinerarios) throws Exception {
        
        boolean result = false;
        try{
            Date fechaActual = new Date();
            MParametrosAdm parametrosAdmin = ParametrosAdmin.getParametrosAdmin();
            long toleranciaCompletitudLecturas = parametrosAdmin.getNvpCompletud();
            int toleranciaCompletitudConsumos = parametrosAdmin.getNtolCompletud();

            if(itinerarios.size() > 0){
                result = true;
                for (MovSuministros itinerario : itinerarios) {
                    if(!itinerario.isSuministroInvalidado()){
                        if (itinerario.getVctipoVal().equals(MovSuministros.TIPO_LECTURA)) {

                            Date maxFechaLectura = itinerario.getTsful();
                            long diffTime = fechaActual.getTime() - maxFechaLectura.getTime();
                            long daysDiff =  TimeUnit.DAYS.convert(diffTime, TimeUnit.MILLISECONDS);

                            System.out.println("Dias de diferencia: "+daysDiff);
                            if (daysDiff > toleranciaCompletitudLecturas) {
                                alarmsManager.reportAlarm(itinerario, AlarmsManager.COMPLETITUD_INFO_VALIDATION_ERROR_CODE);
                                itinerario.setSuministroInvalidado(true);
                                result = false;
                            } else {
                                result = true;
                            }
                        } else if (itinerario.getVctipoVal().equals(MovSuministros.TIPO_CONSUMO)){
                            Date fsy = fechaActual;
                            Date fla = itinerario.getTsfla(); //fecha ultima lectura reportada en perseo

                            if(fsy.compareTo(Utils.addDays(fla, toleranciaCompletitudConsumos)) > 0){
                                alarmsManager.reportAlarm(itinerario, AlarmsManager.COMPLETITUD_INFO_VALIDATION_ERROR_CODE);
                                itinerario.setSuministroInvalidado(true);
                                result = result && false;     
                            }else {
                                result = result && true;
                            }
                        }
                    }
                }        
            }
        } catch (Exception e) {
            String mensaje = "Error verificando completitud de información";
            DataLogger.Log(e, mensaje);
            throw new Exception(mensaje);
        }
        return result;
    }
    
    @Override
    public boolean comparacionLectuaDiaria(List<MovSuministros> itinerarios) throws Exception  {
        boolean result = false;        
        try {
            //TODO REPLACE THIS VALUES
            MConfVal parametrosConf = ParametrosConf.getParametrosConf();
            final double MAX_VALUE  = parametrosConf.getNranDiaMax().doubleValue();
            final double MIN_VALUE = parametrosConf.getNranDiaMin().doubleValue();            

            if(itinerarios.size() > 0){
                for (MovSuministros itinerario : itinerarios) {
                    if(!itinerario.isSuministroInvalidado()){                       
                        result = true;
                        if (!itinerario.getMovLectConsuCollection().isEmpty() && itinerario.getVctipoVal().equals(MovSuministros.TIPO_LECTURA)) {                            
                            List<MovLectConsu> listaLecturas = itinerario.getMovLectConsuCollection();                            
                            if (listaLecturas.size() >= 2) {
                                BigDecimal ultimaLectura =  listaLecturas.get(0).getNlectura();
                                BigDecimal penUltimaLectura =  listaLecturas.get(1).getNlectura();                              
                                BigDecimal vav = ultimaLectura.subtract(penUltimaLectura);                                                     
                                switch (vav.signum()) {
                                    case -1:
                                        alarmsManager.reportAlarm(itinerarios.get(0), AlarmsManager.DEVOLUCION_DE_REGISTRO_ERROR_CODE);
                                        result = result && false;
                                        break;
                                    case 0:
                                        alarmsManager.reportAlarm(itinerarios.get(0), AlarmsManager.LECTURA_REPETIDA_ERROR_CODE);
                                        result = result && false;
                                        break;
                                    case 1:
                                        double porcentaje = vav.doubleValue()/ultimaLectura.doubleValue();
                                        if(porcentaje < MIN_VALUE){
                                            alarmsManager.reportAlarm(itinerarios.get(0), AlarmsManager.INCREMENTO_MINIMO_NO_ESPERADO_ERROR_CODE);
                                            result = result && false;
                                        } else if (porcentaje > MAX_VALUE) {
                                            alarmsManager.reportAlarm(itinerarios.get(0), AlarmsManager.INCREMENTO_MAXIMO_NO_ESPERADO_ERROR_CODE);
                                            result = result && false;
                                        } else {
                                            //Lecturas cumplen condicion y se certifican
                                            itinerario.certificarLecturas();
                                            result = result && true;
                                        }   break;
                                    default:
                                        break;
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            String mensaje = "Error verificando comparación de lectura diaria";
            DataLogger.Log(e, mensaje);
            throw new Exception(mensaje);
        }
        return result;
    }

    @Override
    public boolean comparacionLectuaDiariaMensual(List<MovSuministros> itinerarios) throws Exception  {
        boolean result = false;
        try {
            
            MConfVal parametrosConf = ParametrosConf.getParametrosConf();
            final double MAX_VALUE  = parametrosConf.getNranMesMax().doubleValue();
            final double MIN_VALUE = parametrosConf.getNranMesMin().doubleValue();  

            if(itinerarios.size() > 0){
                result = true;
                for (MovSuministros itinerario : itinerarios) {

                    if(!itinerario.isSuministroInvalidado()){
                        if (!itinerario.getMovLectConsuCollection().isEmpty() && itinerario.getVctipoVal().equals(MovSuministros.TIPO_LECTURA)) {
                            List<MovLectConsu> listaLecturas = itinerario.getMovLectConsuCollection();
                            Integer ultimaLecturaReportadaEnOpen =  itinerario.getNulReportada();

                            if(null != ultimaLecturaReportadaEnOpen){
                                if (listaLecturas.size() >= 1) {
                                    Integer ultimaLectura =  listaLecturas.get(0).getNlectura().intValueExact();
                                    Integer vav = ultimaLectura - ultimaLecturaReportadaEnOpen;

                                    if (vav.intValue() < 0) {
                                        alarmsManager.reportAlarm(itinerarios.get(0), AlarmsManager.DEVOLUCION_DE_REGISTRO_MENSUAL_ERROR_CODE);
                                        result = result && false; 
                                    } else if (vav.intValue()  == 0){
                                        alarmsManager.reportAlarm(itinerarios.get(0), AlarmsManager.LECTURA_REPETIDA_MENSUAL_ERROR_CODE);
                                        result = result && false; 
                                    } else if(vav.intValue() > 0){
                                        double porcentaje = vav.doubleValue()/ultimaLectura.doubleValue();
                                        if(porcentaje < MIN_VALUE){
                                            alarmsManager.reportAlarm(itinerarios.get(0), AlarmsManager.INCREMENTO_MINIMO_NO_ESPERADO_MENSUAL_ERROR_CODE);
                                            result = result && false; 
                                        } else if(porcentaje > MAX_VALUE){
                                            alarmsManager.reportAlarm(itinerarios.get(0), AlarmsManager.INCREMENTO_MAXIMO_NO_ESPERADO_MENSUAL_ERROR_CODE);
                                            result = result && false; 
                                        } else {
                                            itinerario.certificarLecturas();
                                            result = result && true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            String mensaje = "Error verificando completitud de lectura diaria mensual";
            DataLogger.Log(e, mensaje);
            throw new Exception(mensaje);
        }
        return result;
    }

    @Override
    public boolean verificarPorcentajeMaximoSuperiorInferior(List<MovSuministros> itinerarios) throws Exception {
        boolean result = false;
        if(itinerarios.size() > 0){
            
            MConfVal parametrosConf = ParametrosConf.getParametrosConf();
            
            for (MovSuministros itinerario : itinerarios) {
                if(!itinerario.isSuministroInvalidado()){
                    if (!itinerario.getMovLectConsuCollection().isEmpty() && itinerario.getVctipoVal().equals(MovSuministros.TIPO_LECTURA)) {
                        result = true;
                        BigDecimal lecturaEsperadaMinima = parametrosConf.getNranDiaMin();
                        BigDecimal lecturaEsperadaMaxima = parametrosConf.getNranDiaMax();
                        BigDecimal ultimaLectura = itinerario.getMovLectConsuCollection().get(0).getNlectura();

                        if (ultimaLectura.compareTo(lecturaEsperadaMinima) < 0 || ultimaLectura.compareTo(lecturaEsperadaMaxima) > 0){
                            alarmsManager.reportAlarm(itinerarios.get(0), AlarmsManager.PORCENTAJE_MAXIMO_SUPERIOR_INFERIOR_ERROR_CODE);
                            result = result && false;
                        } else {
                            result = result && true;
                        }
                    } 
                }
            }
        }
        
        return result;
    }       

    private boolean validarFechasLecturas(MovSuministros itinerario) {
        boolean result = false;
        Date fechaActual = new Date();
        final int UN_DIA = 1;
        Date fechaUltimaLecturaReportada = itinerario.getTsful();
        fechaUltimaLecturaReportada = Utils.addDays(fechaUltimaLecturaReportada, UN_DIA);
        
        for (MovLectConsu lectura : itinerario.getMovLectConsuCollection()) {
            result = result || (lectura.getTsfechaLec().compareTo(fechaUltimaLecturaReportada) >= 0  && lectura.getTsfechaLec().compareTo(fechaActual) <= 0 );
        }
        
        return result;
    }
}
