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
import com.metrolink.validatorservice.db.controller.ParametrosAdmin;
import com.metrolink.validatorservice.db.controller.ParametrosConf;
import com.metrolink.validatorservice.db.daos.DAOMCalTou;
import com.metrolink.validatorservice.db.daos.DAOParametrosConf;
import com.metrolink.validatorservice.db.daos.DAORegsSco;
import com.metrolink.validatorservice.db.daos.DAOSuministros;
import com.metrolink.validatorservice.db.daos.IDAOLecturas;
import com.metrolink.validatorservice.db.daos.IDAOParametrosConf;
import com.metrolink.validatorservice.db.daos.IDAOSuministros;
import com.metrolink.validatorservice.logger.DataLogger;
import com.metrolink.validatorservice.models.MCalTou;
import com.metrolink.validatorservice.models.MConfVal;
import com.metrolink.validatorservice.models.MParametrosAdm;
import com.metrolink.validatorservice.models.MovCalTou;
import com.metrolink.validatorservice.models.MovLectConsu;
import com.metrolink.validatorservice.models.MovRegsSco;
import com.metrolink.validatorservice.models.MovSuministros;
import com.metrolink.validatorservice.preferencesmanager.IPreferencesManager;
import com.metrolink.validatorservice.utils.Utils;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Gabriel Ortega
 */
public class IndividualValidations implements IIndividualValidations {

    private IAlarmsManager alarmsManager;
    private IDAOSuministros suministrodao;
    private IDAOParametrosConf parametrosconfvaldao;

    public IndividualValidations(IAlarmsManager alarmsManager) {
        this.alarmsManager = alarmsManager;
    }

    @Override
    public boolean V1verificarCalendarioTOU(List<MovSuministros> itinerarios) throws Exception {
        boolean result = false;
        try {
            if (itinerarios.size() > 0) {
                result = true;
                for (MovSuministros itinerario : itinerarios) {
                    if (!itinerario.isSuministroInvalidado()) {
                        if (!itinerario.getMovLectConsuCollection().isEmpty() && itinerario.getVctipoVal().equals(MovSuministros.TIPO_CONSUMO)) {
                            IPreferencesManager preferencesManager = DataBaseManager.getInstance().getPreferencesManager();
                            IDatabaseController databaseController = new DatabaseController(preferencesManager);
                            MovCalTou calendario = new DAOMCalTou(databaseController).buscaCalendarioTou(itinerario);

                            suministrodao = new DAOSuministros(databaseController);
                            if (calendario.getMovCalTouPK() == null) {
                                result = result && false;
                                alarmsManager.reportAlarm(itinerario, AlarmsManager.CALENDARIO_TOU_VALIDATION_ERROR_CODE);
                            } else {
                                MCalTou caltou = new MCalTou(calendario.getMovCalTouPK().getNcodCalTou());
                                result = result && true;
                                itinerario.setNcodCalTou(caltou);
                                suministrodao.actualizaCalendarioTou(itinerario);
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
    public boolean V2verificarExistenciaDatos(List<MovSuministros> itinerarios) throws Exception {
        boolean result = false;
        try {
            if (itinerarios.size() > 0) {
                result = true;
                for (MovSuministros itinerario : itinerarios) {
                    if (!itinerario.isSuministroInvalidado()) {

                        if (itinerario.getMovLectConsuCollection().isEmpty() || !validarFechasLecturas(itinerario)) {
                            alarmsManager.reportAlarm(itinerario, AlarmsManager.EXISTENCIA_DE_DATOS_ERROR_CODE);
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
    public boolean V3verificarCompletitudInformacion(List<MovSuministros> itinerarios) throws Exception {
        boolean result = false;
        final int UN_DIA = 1;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date fechaActual = new Date();
            MParametrosAdm parametrosAdmin = ParametrosAdmin.getParametrosAdmin();
            long toleranciaCompletitudLecturas = parametrosAdmin.getNvpCompletud();
            int toleranciaCompletitudConsumos = parametrosAdmin.getNtolCompletud();

            if (itinerarios.size() > 0) {
                result = true;
                for (MovSuministros itinerario : itinerarios) {
                    if (!itinerario.isSuministroInvalidado()) {
                        if (itinerario.getVctipoVal().equals(MovSuministros.TIPO_LECTURA)) {
                            Date maxFechaLectura = itinerario.getTsful();
                            long diffTime = fechaActual.getTime() - maxFechaLectura.getTime();
                            long daysDiff = TimeUnit.DAYS.convert(diffTime, TimeUnit.MILLISECONDS);
                            if (daysDiff > toleranciaCompletitudLecturas) {
                                List<MovLectConsu> listaLecturas = itinerario.getMovLectConsuCollection();
                                BigDecimal ultimaLectura = listaLecturas.get(0).getNlectura();
                                String resultado = sdf.format(itinerario.getTsfla()) + "-" + ultimaLectura;
                                itinerario.setResultado(resultado);
                                alarmsManager.reportAlarm(itinerario, AlarmsManager.COMPLETITUD_INFO_VALIDATION_ERROR_CODE);
                                result = false;
                            } else {
                                result = true;
                            }
                        } else if (itinerario.getVctipoVal().equals(MovSuministros.TIPO_CONSUMO)) {
                            Date fsy = fechaActual;
                            fsy = Utils.addDays(fsy, -UN_DIA);
                            long fecactual = fsy.getTime();
                            Date ful = itinerario.getTsful(); //Fecha de la ultima lectura reportada a Open
                            ful = Utils.addDays(ful, UN_DIA);
                            long tsful = ful.getTime();
                            
                           /* if (fsy.compareTo(Utils.addDays(fla, toleranciaCompletitudConsumos)) > 0) {
                                alarmsManager.reportAlarm(itinerario, AlarmsManager.COMPLETITUD_INFO_VALIDATION_ERROR_CODE);
                                itinerario.setSuministroInvalidado(true);
                                result = result && false;
                            } else {
                                result = result && true;
                            }*/
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
    public boolean V4comparacionLectuaDiaria(List<MovSuministros> itinerarios) throws Exception {
        boolean result = false;
        String resultado = "";
        try {
            IPreferencesManager preferencesManager = DataBaseManager.getInstance().getPreferencesManager();
            IDatabaseController databaseController = new DatabaseController(preferencesManager);

            suministrodao = new DAOSuministros(databaseController);
            parametrosconfvaldao = new DAOParametrosConf(databaseController);

            if (itinerarios.size() > 0) {
                for (MovSuministros itinerario : itinerarios) {
                    if (!itinerario.isSuministroInvalidado()) {
                        result = true;
                        if (!itinerario.getMovLectConsuCollection().isEmpty() && itinerario.getVctipoVal().equals(MovSuministros.TIPO_LECTURA)) {                            
                            List<MovLectConsu> listaLecturas = itinerario.getMovLectConsuCollection();
                            if (listaLecturas.size() >= 2) {
                                MConfVal parametrosConf = parametrosconfvaldao.getParametrosConf(itinerario);
                                final double MAX_VALUE = parametrosConf.getNranDiaMax().doubleValue();
                                final double MIN_VALUE = parametrosConf.getNranDiaMin().doubleValue();                                
                                BigDecimal ultimaLectura = listaLecturas.get(0).getNlectura();
                                BigDecimal penUltimaLectura = listaLecturas.get(1).getNlectura();
                                BigDecimal vav = ultimaLectura.subtract(penUltimaLectura);
                                MovSuministros suministro = suministrodao.buscaFechaPerseoPenultimaSuministro();
                                resultado = suministro.getTsful().toString() + "-" + vav.multiply(BigDecimal.valueOf(itinerario.getNmulti().longValue()));
                                itinerario.setResultado(resultado);
                                switch (vav.signum()) {
                                    case -1:
                                        alarmsManager.reportAlarm(itinerario, AlarmsManager.DEVOLUCION_DE_REGISTRO_ERROR_CODE);
                                        result = result && false;
                                        break;
                                    case 0:
                                        alarmsManager.reportAlarm(itinerario, AlarmsManager.LECTURA_REPETIDA_ERROR_CODE);
                                        result = result && false;
                                        break;
                                    case 1:
                                        double porcentaje = vav.doubleValue() / ultimaLectura.doubleValue();
                                        if (porcentaje < MIN_VALUE) {
                                            alarmsManager.reportAlarm(itinerario, AlarmsManager.INCREMENTO_MINIMO_NO_ESPERADO_ERROR_CODE);
                                            result = result && false;
                                        } else if (porcentaje > MAX_VALUE) {
                                            alarmsManager.reportAlarm(itinerario, AlarmsManager.INCREMENTO_MAXIMO_NO_ESPERADO_ERROR_CODE);
                                            result = result && false;
                                        } else {
                                            //Lecturas cumplen condicion y se certifican
                                            itinerario.certificarLecturas();
                                            result = result && true;
                                        }
                                        break;
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
    public boolean V5comparacionLectuaDiariaMensual(List<MovSuministros> itinerarios) throws Exception {
        boolean result = false;
        String resultado = "";
        try {
            if (itinerarios.size() > 0) {
                result = true;
                for (MovSuministros itinerario : itinerarios) {
                    if (!itinerario.isSuministroInvalidado()) {
                        if (!itinerario.getMovLectConsuCollection().isEmpty() && itinerario.getVctipoVal().equals(MovSuministros.TIPO_LECTURA)) {
                            List<MovLectConsu> listaLecturas = itinerario.getMovLectConsuCollection();
                            Integer ultimaLecturaReportadaEnOpen = itinerario.getNulReportada();
                            if (null != ultimaLecturaReportadaEnOpen) {
                                if (listaLecturas.size() >= 1) {
                                    MConfVal parametrosConf = parametrosconfvaldao.getParametrosConf(itinerario);
                                    final double MAX_VALUE = parametrosConf.getNranDiaMax().doubleValue();
                                    final double MIN_VALUE = parametrosConf.getNranDiaMin().doubleValue();                                    
                                    Integer ultimaLectura = listaLecturas.get(0).getNlectura().intValueExact();
                                    Integer vav = ultimaLectura - ultimaLecturaReportadaEnOpen;
                                    MovSuministros suministro = suministrodao.buscaFechaOpenPenultimaLectura();
                                    resultado = suministro.getTsful() + "-" + (vav * itinerarios.get(0).getNmulti().longValue());
                                    itinerario.setResultado(resultado);
                                    if (vav.intValue() < 0) {
                                        alarmsManager.reportAlarm(itinerario, AlarmsManager.DEVOLUCION_DE_REGISTRO_MENSUAL_ERROR_CODE);
                                        result = result && false;
                                    } else if (vav.intValue() == 0) {
                                        alarmsManager.reportAlarm(itinerario, AlarmsManager.LECTURA_REPETIDA_MENSUAL_ERROR_CODE);
                                        result = result && false;
                                    } else if (vav.intValue() > 0) {
                                        double porcentaje = vav.doubleValue() / ultimaLectura.doubleValue();
                                        if (porcentaje < MIN_VALUE) {
                                            alarmsManager.reportAlarm(itinerario, AlarmsManager.INCREMENTO_MINIMO_NO_ESPERADO_MENSUAL_ERROR_CODE);
                                            result = result && false;
                                        } else if (porcentaje > MAX_VALUE) {
                                            alarmsManager.reportAlarm(itinerario, AlarmsManager.INCREMENTO_MAXIMO_NO_ESPERADO_MENSUAL_ERROR_CODE);
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
    public boolean V6verificarPorcentajeMaximoSuperiorInferior(List<MovSuministros> itinerarios) throws Exception {
        boolean result = false;
        String resultado = "";
        if (itinerarios.size() > 0) {
            for (MovSuministros itinerario : itinerarios) {
                if (!itinerario.isSuministroInvalidado()) {                    
                    if (!itinerario.getMovLectConsuCollection().isEmpty() && itinerario.getVctipoVal().equals(MovSuministros.TIPO_LECTURA)) {                       
                        IPreferencesManager preferencesManager = DataBaseManager.getInstance().getPreferencesManager();
                        IDatabaseController databaseController = new DatabaseController(preferencesManager);
                        ArrayList<MovRegsSco> movRegsScoAsociados = new DAORegsSco(databaseController).consultarMovRegScoAsociado(itinerario.getMovLectConsuCollection().get(0));

                        if (!movRegsScoAsociados.isEmpty()) {
                            result = true;
                            MovRegsSco movRegsSco = movRegsScoAsociados.get(0);
                            BigDecimal lecturaEsperadaMinima = movRegsSco.getNlemin();
                            BigDecimal lecturaEsperadaMaxima = movRegsSco.getNlemax();
                            BigDecimal ultimaLectura = itinerario.getMovLectConsuCollection().get(0).getNlectura();                           
                            resultado = lecturaEsperadaMaxima + "/" + lecturaEsperadaMinima;
                            itinerario.setResultado(resultado);
                            if (ultimaLectura.compareTo(lecturaEsperadaMinima) < 0 || ultimaLectura.compareTo(lecturaEsperadaMaxima) > 0) {                               
                                alarmsManager.reportAlarm(itinerario, AlarmsManager.PORCENTAJE_MAXIMO_SUPERIOR_INFERIOR_ERROR_CODE);
                                result = result && false;
                            } else {
                                result = result && true;
                            }
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
            result = result || (lectura.getTsfechaLec().compareTo(fechaUltimaLecturaReportada) >= 0 && lectura.getTsfechaLec().compareTo(fechaActual) <= 0);
        }

        return result;
    }
}
