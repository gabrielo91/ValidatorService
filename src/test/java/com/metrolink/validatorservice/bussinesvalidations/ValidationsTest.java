/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.bussinesvalidations;


import com.metrolink.validatorservice.alarmsmanager.AlarmsManager;
import com.metrolink.validatorservice.alarmsmanager.IAlarmsManager;
import com.metrolink.validatorservice.db.controller.DatabaseController;
import com.metrolink.validatorservice.db.controller.IDatabaseController;
import com.metrolink.validatorservice.db.daos.DAOParametrosAdmin;
import com.metrolink.validatorservice.db.daos.DAOParametrosConf;
import com.metrolink.validatorservice.db.daos.IDAOParametrosAdmin;
import com.metrolink.validatorservice.db.daos.IDAOParametrosConf;
import com.metrolink.validatorservice.models.AgendaLectura;
import com.metrolink.validatorservice.models.AgendaLecturaPK;
import com.metrolink.validatorservice.models.MCalTou;
import com.metrolink.validatorservice.models.MConfVal;
import com.metrolink.validatorservice.models.MParametrosAdm;
import com.metrolink.validatorservice.models.MovLectConsu;
import com.metrolink.validatorservice.models.MovSuministros;
import com.metrolink.validatorservice.preferencesmanager.IPreferencesManager;
import com.metrolink.validatorservice.preferencesmanager.PreferencesManager;
import com.metrolink.validatorservice.utils.Utils;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
/**
 *
 * @author Gabriel Ortega
 */
@PrepareForTest({IAlarmsManager.class})
public class ValidationsTest {
    
    final static String DATE_FORMAT_YYYY_MM_DD="yyyy-MM-dd";
    final static String DATE_FORMAT_YYYY_MM_DD_HH_MM="yyyy-MM-dd HH:mm";
    final int UN_DIA = 1;
    
    @Mock
    IDatabaseController databaseMock;
    
    @Mock
    IAlarmsManager alarmsManager;
    
    IIndividualValidations individualValidations;
    MParametrosAdm parametrosAdm;
    MConfVal parametrosConf;
    
    
    private  ArrayList<AgendaLectura> createUniqueElementAgendaArray() throws ParseException{
        Integer calTouNumber = 1235456;
        ArrayList<AgendaLectura> intinerarios = new ArrayList<>();
        AgendaLectura agenda = new AgendaLectura();
        
        AgendaLecturaPK agendaLecturaPK = new AgendaLecturaPK();
        agendaLecturaPK.setDfechaTeo(new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD_HH_MM).parse("2018-03-10 12:00"));
        
        MovSuministros movSuministros = new MovSuministros();
        MCalTou mCalTou = new MCalTou();
        mCalTou.setNcodCalTou(calTouNumber);
        movSuministros.setNcodCalTou(mCalTou);
        movSuministros.setVctipoVal(MovSuministros.TIPO_LECTURA);
        
        MovLectConsu movLectConsu = new MovLectConsu();
        movLectConsu.setId(BigDecimal.ONE);
        movLectConsu.setTsfechaLec(new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD).parse("2018-03-10"));
        movLectConsu.setNlectura(BigDecimal.valueOf(160));
        movSuministros.getMovLectConsuCollection().add(movLectConsu);
        
        MovLectConsu movLectConsu2 = new MovLectConsu();
        movLectConsu2.setId(BigDecimal.valueOf(2));
        movLectConsu2.setTsfechaLec(new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD).parse("2018-03-09"));
        movLectConsu2.setNlectura(BigDecimal.valueOf(68));
        movSuministros.getMovLectConsuCollection().add(movLectConsu2);
        
        movSuministros.setNnumRegs((short)1);
        Date ful = new Date();//Fecha Ultima Lectura reportada en open SGC, no importa en las formulas
        movSuministros.setTsful(ful);
        
        agenda.getListaSuministros().add(movSuministros);
        agenda.setAgendaLecturaPK(agendaLecturaPK);
        intinerarios.add(agenda);
        return intinerarios;
    }
    
    @Before
    public void setUp() throws Exception {
         MockitoAnnotations.initMocks(this);
         alarmsManager = mock(AlarmsManager.class);
         individualValidations = new IndividualValidations(alarmsManager);   
         loadAdminParamsFromDB();
         loadConfParamsFromDB();
         AgendaLectura lectura = new AgendaLectura();
         Mockito.doNothing().when(alarmsManager).reportAlarm(lectura, 0);
    }

    @Test
    public void verificarCalendarioTOUTestExitoso() throws Exception{
        boolean result = false;        
        ArrayList<AgendaLectura> itinerarios = createUniqueElementAgendaArray();      
        itinerarios.get(0).getListaSuministros().get(0).setVctipoVal(MovSuministros.TIPO_CONSUMO);
        result = individualValidations.verificarCalendarioTOU(itinerarios.get(0).getListaSuministros());
        Assert.assertTrue(result);
    }
    
    @Test
    public void verificarCalendarioTOUFallidoYEnvioAlarma() throws Exception{
        boolean result = false;
        Integer calTouNumber = -1;
        ArrayList<AgendaLectura> itinerarios = createUniqueElementAgendaArray();  
        itinerarios.get(0).getListaSuministros().get(0).setVctipoVal(MovSuministros.TIPO_CONSUMO);
        itinerarios.get(0).getListaSuministros().get(0).getNcodCalTou().setNcodCalTou(calTouNumber);      
        result = individualValidations.verificarCalendarioTOU(itinerarios.get(0).getListaSuministros());
        Assert.assertFalse(result);
        verify(alarmsManager).reportAlarm(itinerarios.get(0).getListaSuministros().get(0), AlarmsManager.CALENDARIO_TOU_VALIDATION_ERROR_CODE);
    }
    
    @Test
    public void verificarExistenciaDatosLecturasExitoso() throws Exception{
        ArrayList<AgendaLectura> itinerarios = createUniqueElementAgendaArray(); 
        itinerarios.get(0).getListaSuministros().get(0).getMovLectConsuCollection().get(0).setTsfechaLec(new Date());
        itinerarios.get(0).getListaSuministros().get(0).getMovLectConsuCollection().get(1).setTsfechaLec(new Date());
        Date tsfulSimulada = Utils.addDays(new Date(), - UN_DIA*5);
        itinerarios.get(0).getListaSuministros().get(0).setTsful(tsfulSimulada);
        boolean result = individualValidations.verificarExistenciaDatos(itinerarios.get(0).getListaSuministros());
        Assert.assertTrue(result);
    }
    
    @Test
    public void verificarExistenciaDatosConsumosExitoso() throws Exception{
        ArrayList<AgendaLectura> itinerarios = createUniqueElementAgendaArray();  
        itinerarios.get(0).getListaSuministros().get(0).getMovLectConsuCollection().get(0).setTsfechaLec(new Date());
        itinerarios.get(0).getListaSuministros().get(0).getMovLectConsuCollection().get(1).setTsfechaLec(new Date());
        Date tsfulSimulada = Utils.addDays(new Date(), - UN_DIA*5);
        itinerarios.get(0).getListaSuministros().get(0).setTsful(tsfulSimulada);
        itinerarios.get(0).getListaSuministros().get(0).setVctipoVal(MovSuministros.TIPO_CONSUMO);
        boolean result = individualValidations.verificarExistenciaDatos(itinerarios.get(0).getListaSuministros());
        Assert.assertTrue(result);
    }
    
  
    @Test
    public void verificarExistenciaDatosFallidoYEnvioAlarma() throws Exception{
        boolean result;
        ArrayList<AgendaLectura> itinerarios = createUniqueElementAgendaArray();   
        itinerarios.get(0).getListaSuministros().get(0).getMovLectConsuCollection().clear();
        result = individualValidations.verificarExistenciaDatos(itinerarios.get(0).getListaSuministros());
        Assert.assertFalse(result);
        verify(alarmsManager).reportAlarm(itinerarios.get(0).getListaSuministros().get(0), AlarmsManager.EXISTENCIA_DE_DATOS_ERROR_CODE);
        Assert.assertTrue(itinerarios.get(0).getListaSuministros().get(0).isSuministroInvalidado());
    }
    
    @Test
    public void verificarCompletitudInformacionLecturasExitoso() throws Exception {
        boolean result; 
        int diasDiferenciaMinimaPermitidaLectura = parametrosAdm.getNvpCompletud();
        Date testingDateFLA = Utils.addDays(new Date(), -diasDiferenciaMinimaPermitidaLectura);
        ArrayList<AgendaLectura> itinerarios = createUniqueElementAgendaArray();       
        itinerarios.get(0).getListaSuministros().get(0).setTsfla(testingDateFLA);
        itinerarios.get(0).getListaSuministros().get(0).setVctipoVal(MovSuministros.TIPO_LECTURA);
        result = individualValidations.verificarCompletitudInformacion(itinerarios.get(0).getListaSuministros());
        Assert.assertTrue(result);
    }
    
    @Test
    public void verificarCompletitudInformacionLecturasFallidoYEnvioAlarmaCompletitud() throws Exception {
        boolean result; 
        final int diasDiferenciaMinimaPermitidaLecturas = parametrosAdm.getNvpCompletud();
        Date testingDateFul = Utils.addDays(new Date(), - (diasDiferenciaMinimaPermitidaLecturas + UN_DIA));
        ArrayList<AgendaLectura> itinerarios = createUniqueElementAgendaArray();       
        itinerarios.get(0).getListaSuministros().get(0).setTsful(testingDateFul);
        itinerarios.get(0).getListaSuministros().get(0).setVctipoVal(MovSuministros.TIPO_LECTURA);
        result = individualValidations.verificarCompletitudInformacion(itinerarios.get(0).getListaSuministros());
        Assert.assertFalse(result);
    }
    
    @Test
    public void verificarCompletitudInformacionConsumosExitoso() throws Exception {
        boolean result; 
        final int diasDiferenciaMinimaPermitida = parametrosAdm.getNtolCompletud();
        Date testingDateFLA = Utils.addDays(new Date(), + (diasDiferenciaMinimaPermitida + UN_DIA));
        ArrayList<AgendaLectura> itinerarios = createUniqueElementAgendaArray();  
        itinerarios.get(0).getListaSuministros().get(0).setTsfla(testingDateFLA);
        itinerarios.get(0).getListaSuministros().get(0).setVctipoVal(MovSuministros.TIPO_CONSUMO);
        result = individualValidations.verificarCompletitudInformacion(itinerarios.get(0).getListaSuministros());
        Assert.assertTrue(result);
    }
    
    @Test
    public void verificarCompletitudInformacionConsumosFallidoYEnvioAlarmaCompletitud() throws Exception {
        boolean result; 
        final int diasDiferenciaMinimaPermitida = parametrosAdm.getNtolCompletud();
        Date testingDateFLA = Utils.addDays(new Date(), - diasDiferenciaMinimaPermitida - 2 * UN_DIA);
        ArrayList<AgendaLectura> itinerarios = createUniqueElementAgendaArray();  
        itinerarios.get(0).getListaSuministros().get(0).setTsfla(testingDateFLA);
        itinerarios.get(0).getListaSuministros().get(0).setVctipoVal(MovSuministros.TIPO_CONSUMO);
        result = individualValidations.verificarCompletitudInformacion(itinerarios.get(0).getListaSuministros());
        Assert.assertFalse(result);
        verify(alarmsManager).reportAlarm(itinerarios.get(0).getListaSuministros().get(0), AlarmsManager.COMPLETITUD_INFO_VALIDATION_ERROR_CODE);
        Assert.assertTrue(itinerarios.get(0).getListaSuministros().get(0).isSuministroInvalidado()); //Validar que se invalidó el suministro correctamente
    }
    
    
    @Test
    public void comparacionLectuaDiariaExitoso() throws Exception {
        ArrayList<AgendaLectura> itinerarios = createUniqueElementAgendaArray();  
        boolean result = individualValidations.comparacionLectuaDiaria(itinerarios.get(0).getListaSuministros());
        Assert.assertTrue(result);
    }
    
    @Test
    public void comparacionLectuaDiariaFallidoYEnvioAlarmaDevolucionRegistro() throws Exception {
        //Ocurre cuando el valor de la ultima lectura es menor al de la penultima
        ArrayList<AgendaLectura> itinerarios = createUniqueElementAgendaArray();  
        itinerarios.get(0).getListaSuministros().get(0).getMovLectConsuCollection().get(0).setNlectura(BigDecimal.valueOf(200));
        itinerarios.get(0).getListaSuministros().get(0).getMovLectConsuCollection().get(1).setNlectura(BigDecimal.valueOf(300));
        boolean result = individualValidations.comparacionLectuaDiaria(itinerarios.get(0).getListaSuministros());
        Assert.assertFalse(result);
        verify(alarmsManager).reportAlarm(itinerarios.get(0).getListaSuministros().get(0), AlarmsManager.DEVOLUCION_DE_REGISTRO_ERROR_CODE);
    }
    
    @Test
    public void comparacionLectuaDiariaFallidoYEnvioAlarmaLecturaRepetida() throws Exception {
        //Ocurre cuando el valor de la ultima lectura es igual al de la penultima
        ArrayList<AgendaLectura> itinerarios = createUniqueElementAgendaArray();  
        itinerarios.get(0).getListaSuministros().get(0).getMovLectConsuCollection().get(0).setNlectura(BigDecimal.ONE);
        itinerarios.get(0).getListaSuministros().get(0).getMovLectConsuCollection().get(1).setNlectura(BigDecimal.ONE);
        boolean result = individualValidations.comparacionLectuaDiaria(itinerarios.get(0).getListaSuministros());
        Assert.assertFalse(result);
        verify(alarmsManager).reportAlarm(itinerarios.get(0).getListaSuministros().get(0), AlarmsManager.LECTURA_REPETIDA_ERROR_CODE);
    }
    
    @Test
    public void comparacionLectuaDiariaFallidoYEnvioAlarmaIncrementoMinimoNoEsperado() throws Exception {
        //Ocurre cuando el valor de la ultima lectura menos el de la penultima es menor a valor minimo esperado
        ArrayList<AgendaLectura> itinerarios = createUniqueElementAgendaArray();  
        itinerarios.get(0).getListaSuministros().get(0).getMovLectConsuCollection().get(0).setNlectura(BigDecimal.valueOf(120));
        itinerarios.get(0).getListaSuministros().get(0).getMovLectConsuCollection().get(1).setNlectura(BigDecimal.valueOf(100));
        boolean result  = individualValidations.comparacionLectuaDiaria(itinerarios.get(0).getListaSuministros());
        Assert.assertFalse(result);
        verify(alarmsManager).reportAlarm(itinerarios.get(0).getListaSuministros().get(0), AlarmsManager.INCREMENTO_MINIMO_NO_ESPERADO_ERROR_CODE);
    }
    
     @Test
    public void comparacionLectuaDiariaFallidoYEnvioAlarmaIncrementoMaximoNoEsperado() throws Exception {
        //Ocurre cuando el valor de la ultima lectura menos el de la penultima es mayor a valor máximo esperado
        ArrayList<AgendaLectura> itinerarios = createUniqueElementAgendaArray();  
        itinerarios.get(0).getListaSuministros().get(0).getMovLectConsuCollection().get(0).setNlectura(BigDecimal.valueOf(1100));
        itinerarios.get(0).getListaSuministros().get(0).getMovLectConsuCollection().get(1).setNlectura(BigDecimal.valueOf(100));
        boolean result = individualValidations.comparacionLectuaDiaria(itinerarios.get(0).getListaSuministros());
        Assert.assertFalse(result);
        verify(alarmsManager).reportAlarm(itinerarios.get(0).getListaSuministros().get(0), AlarmsManager.INCREMENTO_MAXIMO_NO_ESPERADO_ERROR_CODE);
    }
    
    
    @Test
    public void comparacionLectuaDiariaMensualExitoso() throws Exception {
        ArrayList<AgendaLectura> itinerarios = createUniqueElementAgendaArray();  
        itinerarios.get(0).getListaSuministros().get(0).setNulReportada(70);
        boolean result = individualValidations.comparacionLectuaDiariaMensual(itinerarios.get(0).getListaSuministros());
        Assert.assertTrue(result);
    }
    
    @Test
    public void comparacionLectuaDiariaMensualFallidoYEnvioAlarmaDevolucionRegistro() throws Exception {
        //Ocurre cuando el valor de la ultima lectura es menor al de la penultima
        ArrayList<AgendaLectura> itinerarios = createUniqueElementAgendaArray();  
        itinerarios.get(0).getListaSuministros().get(0).getMovLectConsuCollection().get(0).setNlectura(BigDecimal.valueOf(200));
        itinerarios.get(0).getListaSuministros().get(0).setNulReportada(300);
        boolean result = individualValidations.comparacionLectuaDiariaMensual(itinerarios.get(0).getListaSuministros());
        Assert.assertFalse(result);
        verify(alarmsManager).reportAlarm(itinerarios.get(0).getListaSuministros().get(0), AlarmsManager.DEVOLUCION_DE_REGISTRO_MENSUAL_ERROR_CODE);
    }
    
    @Test
    public void comparacionLectuaDiariaMensualFallidoYEnvioAlarmaLecturaRepetida() throws Exception {
        //Ocurre cuando el valor de la ultima lectura es igual al de la penultima
        ArrayList<AgendaLectura> itinerarios = createUniqueElementAgendaArray();  
        itinerarios.get(0).getListaSuministros().get(0).getMovLectConsuCollection().get(0).setNlectura(BigDecimal.ONE);
        itinerarios.get(0).getListaSuministros().get(0).setNulReportada(1);
        boolean result = individualValidations.comparacionLectuaDiariaMensual(itinerarios.get(0).getListaSuministros());
        Assert.assertFalse(result);
        verify(alarmsManager).reportAlarm(itinerarios.get(0).getListaSuministros().get(0), AlarmsManager.LECTURA_REPETIDA_MENSUAL_ERROR_CODE);
    }
    
    @Test
    public void comparacionLectuaDiariaMensualFallidoYEnvioAlarmaIncrementoMinimoNoEsperado() throws Exception {
        //Ocurre cuando el valor de la ultima lectura menos el de la penultima es menor a valor minimo esperado
        ArrayList<AgendaLectura> itinerarios = createUniqueElementAgendaArray();  
        itinerarios.get(0).getListaSuministros().get(0).getMovLectConsuCollection().get(0).setNlectura(BigDecimal.valueOf(120));
        itinerarios.get(0).getListaSuministros().get(0).setNulReportada(100);
        boolean result  = individualValidations.comparacionLectuaDiariaMensual(itinerarios.get(0).getListaSuministros());
        Assert.assertFalse(result);
        verify(alarmsManager).reportAlarm(itinerarios.get(0).getListaSuministros().get(0), AlarmsManager.INCREMENTO_MINIMO_NO_ESPERADO_MENSUAL_ERROR_CODE);
    }
    
    /**
     * Este test no funciona si el porcentaje máximo permitido es 1
     * @throws Exception 
     */
    @Test
    public void comparacionLectuaDiariaMensualFallidoYEnvioAlarmaIncrementoMaximoNoEsperado() throws Exception {
        //Ocurre cuando el valor de la ultima lectura menos el de la penultima es mayor a valor máximo esperado
        ArrayList<AgendaLectura> itinerarios = createUniqueElementAgendaArray();  
        itinerarios.get(0).getListaSuministros().get(0).getMovLectConsuCollection().get(0).setNlectura(BigDecimal.valueOf(2000));
        itinerarios.get(0).getListaSuministros().get(0).setNulReportada(50);
        boolean result = individualValidations.comparacionLectuaDiariaMensual(itinerarios.get(0).getListaSuministros());
        Assert.assertFalse(result);
        verify(alarmsManager).reportAlarm(itinerarios.get(0).getListaSuministros().get(0), AlarmsManager.INCREMENTO_MAXIMO_NO_ESPERADO_MENSUAL_ERROR_CODE);
    }
    
//    @Test
//    public void validacionLecturaPorcentajeMaximoSuperiorInferiorExitoso() throws Exception {
//        BigDecimal valorMinimoAceptado = parametrosConf.getNranDiaMin().add(BigDecimal.ONE);
//        ArrayList<AgendaLectura> itinerarios = createUniqueElementAgendaArray();  
//        itinerarios.get(0).getListaSuministros().get(0).setNulReportada(valorMinimoAceptado.intValue());
//        itinerarios.get(0).getListaSuministros().get(0).setVctipoVal(MovSuministros.TIPO_LECTURA);
//        itinerarios.get(0).getListaSuministros().get(0).getMovLectConsuCollection().get(0).setNlectura(valorMinimoAceptado);
//        itinerarios.get(0).getListaSuministros().get(0).getMovLectConsuCollection().get(1).setNlectura(valorMinimoAceptado);
//        boolean result = individualValidations.verificarPorcentajeMaximoSuperiorInferior(itinerarios.get(0).getListaSuministros());
//        Assert.assertTrue(result);
//    }
//    
//    @Test
//    public void validacionLecturaPorcentajeMaximoSuperiorInferiorFallidoYEnvioAlarmas() throws Exception {
//        BigDecimal valorMinimoAceptado = parametrosConf.getNranDiaMax().add(BigDecimal.ONE);
//        ArrayList<AgendaLectura> itinerarios = createUniqueElementAgendaArray();  
//        itinerarios.get(0).getListaSuministros().get(0).setNulReportada(valorMinimoAceptado.intValue());
//        itinerarios.get(0).getListaSuministros().get(0).setVctipoVal(MovSuministros.TIPO_LECTURA);
//        itinerarios.get(0).getListaSuministros().get(0).getMovLectConsuCollection().get(0).setNlectura(valorMinimoAceptado);
//        itinerarios.get(0).getListaSuministros().get(0).getMovLectConsuCollection().get(1).setNlectura(valorMinimoAceptado);
//        boolean result = individualValidations.verificarPorcentajeMaximoSuperiorInferior(itinerarios.get(0).getListaSuministros());
//        Assert.assertFalse(result);
//        verify(alarmsManager).reportAlarm(itinerarios.get(0).getListaSuministros().get(0), AlarmsManager.PORCENTAJE_MAXIMO_SUPERIOR_INFERIOR_ERROR_CODE);
//    }

    private void loadAdminParamsFromDB() throws IOException, FileNotFoundException, org.json.simple.parser.ParseException, Exception {
        String configFilePath = "resources/config.json";
        IPreferencesManager preferencesManager = new PreferencesManager(configFilePath);
        IDatabaseController databaseController = new DatabaseController(preferencesManager);
        IDAOParametrosAdmin daoParametrosAdmin = new DAOParametrosAdmin(databaseController);        
        parametrosAdm = daoParametrosAdmin.getParametrosAdm().get(0);
    }

    private void loadConfParamsFromDB() throws IOException, FileNotFoundException, org.json.simple.parser.ParseException, Exception {
        String configFilePath = "resources/config.json";
        IPreferencesManager preferencesManager = new PreferencesManager(configFilePath);
        IDatabaseController databaseController = new DatabaseController(preferencesManager);
        IDAOParametrosConf daoParametrosConf = new DAOParametrosConf(databaseController);
        parametrosConf = daoParametrosConf.getParametrosConf().get(0);//necesaria para inicialziar los valores
    }
    
    
}
