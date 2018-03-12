/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.bussinesvalidations;


import com.metrolink.validatorservice.alarmsmanager.AlarmsManager;
import com.metrolink.validatorservice.alarmsmanager.IAlarmsManager;
import com.metrolink.validatorservice.db.controller.IDatabaseController;
import com.metrolink.validatorservice.models.AgendaLectura;
import com.metrolink.validatorservice.models.MCalTou;
import com.metrolink.validatorservice.models.MovLectConsu;
import com.metrolink.validatorservice.models.MovSuministros;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
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
    
    @Mock
    IDatabaseController databaseMock;
    
    @Mock
    IAlarmsManager alarmsManager;
    
    IIndividualValidations individualValidations;
    IGeneralValidations generalValidations;
    
    
    private  ArrayList<AgendaLectura> createUniqueElementAgendaArray() throws ParseException{
        Integer calTouNumber = 12345;
        ArrayList<AgendaLectura> intinerarios = new ArrayList<>();
        AgendaLectura agenda = new AgendaLectura();
        MovSuministros movSuministros = new MovSuministros();
        MCalTou mCalTou = new MCalTou();
        mCalTou.setNcodCalTou(calTouNumber);
        movSuministros.setNcodCalTou(mCalTou);
        movSuministros.setVctipoVal(MovSuministros.TIPO_LECTURA);
        
        MovLectConsu movLectConsu = new MovLectConsu();
        movLectConsu.setId(BigDecimal.ONE);
        movLectConsu.setTsfechaLec(new SimpleDateFormat("yyyy-MM-dd").parse("2018-03-10"));
        movLectConsu.setNconsumoOri(BigDecimal.valueOf(160));
        movSuministros.getMovLectConsuCollection().add(movLectConsu);
        
        MovLectConsu movLectConsu2 = new MovLectConsu();
        movLectConsu2.setId(BigDecimal.valueOf(2));
        movLectConsu2.setTsfechaLec(new SimpleDateFormat("yyyy-MM-dd").parse("2018-03-09"));
        movLectConsu2.setNconsumoOri(BigDecimal.valueOf(100));
        movSuministros.getMovLectConsuCollection().add(movLectConsu2);
        
        agenda.getListaSuministros().add(movSuministros);

        intinerarios.add(agenda);
        return intinerarios;
    }
    
    @Before
    public void setUp() throws Exception {
         MockitoAnnotations.initMocks(this);
         alarmsManager = mock(AlarmsManager.class);
         individualValidations = new IndividualValidations(alarmsManager);   
         generalValidations = new GeneralValidations(alarmsManager);
         AgendaLectura lectura = new AgendaLectura();
         Mockito.doNothing().when(alarmsManager).reportAlarm(lectura, 0);
    }

    @Test
    public void verificarCalendarioTOUTestExitoso() throws Exception{
        boolean result = false;        
        ArrayList<AgendaLectura> itinerarios = createUniqueElementAgendaArray();               
        result = individualValidations.verificarCalendarioTOU(itinerarios.get(0).getListaSuministros());
        Assert.assertTrue(result);
    }
    
    @Test
    public void verificarCalendarioTOUFallidoYEnvioAlarma() throws Exception{
        boolean result = false;
        Integer calTouNumber = -1;
        ArrayList<AgendaLectura> itinerarios = createUniqueElementAgendaArray();  
        itinerarios.get(0).getListaSuministros().get(0).getNcodCalTou().setNcodCalTou(calTouNumber);      
        result = individualValidations.verificarCalendarioTOU(itinerarios.get(0).getListaSuministros());
        Assert.assertFalse(result);
        verify(alarmsManager).reportAlarm(any(MovSuministros.class), anyInt());
    }
    
    @Test
    public void verificarExistenciaDatosExitoso() throws Exception{
        boolean result = false;
//        ArrayList<AgendaLectura> intinerarios = createUniqueElementAgendaArray();  
//        result = individualValidations.verificarExistenciaDatos(intinerarios.get(0).getListaSuministros().get(0));
//        Assert.assertTrue(result);
    }
    
  
    @Test
    public void verificarExistenciaDatosFallidoYEnvioAlarma() throws Exception{
        boolean result = false;
//        ArrayList<AgendaLectura> intinerarios = new ArrayList<>(); 
//        result = generalValidations.verificarExistenciaDatos(intinerarios);
//        Assert.assertFalse(result);
//        verify(alarmsManager).reportAlarm(any(AgendaLectura.class), anyString());
    }
    
    @Test
    public void verificarCompletitudInformacionExitoso() throws Exception {
        boolean result = true;
    }
    
    @Test
    public void verificarCompletitudInformacionFallidoYEnvioAlarma() throws Exception {
        boolean result = false;
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
        itinerarios.get(0).getListaSuministros().get(0).getMovLectConsuCollection().get(0).setNconsumoOri(BigDecimal.valueOf(200));
        itinerarios.get(0).getListaSuministros().get(0).getMovLectConsuCollection().get(1).setNconsumoOri(BigDecimal.valueOf(300));
        boolean result = individualValidations.comparacionLectuaDiaria(itinerarios.get(0).getListaSuministros());
        Assert.assertFalse(result);
        verify(alarmsManager).reportAlarm(itinerarios.get(0).getListaSuministros().get(0), AlarmsManager.DEVOLUCION_DE_REGISTRO_ERROR_CODE);
    }
    
    @Test
    public void comparacionLectuaDiariaFallidoYEnvioAlarmaLecturaRepetida() throws Exception {
        //Ocurre cuando el valor de la ultima lectura es igual al de la penultima
        ArrayList<AgendaLectura> itinerarios = createUniqueElementAgendaArray();  
        itinerarios.get(0).getListaSuministros().get(0).getMovLectConsuCollection().get(0).setNconsumoOri(BigDecimal.ONE);
        itinerarios.get(0).getListaSuministros().get(0).getMovLectConsuCollection().get(1).setNconsumoOri(BigDecimal.ONE);
        boolean result = individualValidations.comparacionLectuaDiaria(itinerarios.get(0).getListaSuministros());
        Assert.assertFalse(result);
        verify(alarmsManager).reportAlarm(itinerarios.get(0).getListaSuministros().get(0), AlarmsManager.LECTURA_REPETIDA_ERROR_CODE);
        
    }
    
    @Test
    public void comparacionLectuaDiariaFallidoYEnvioAlarmaIncrementoMinimoNoEsperado() throws Exception {
        //Ocurre cuando el valor de la ultima lectura menos el de la penultima es menor a valor minimo esperado
        ArrayList<AgendaLectura> itinerarios = createUniqueElementAgendaArray();  
        itinerarios.get(0).getListaSuministros().get(0).getMovLectConsuCollection().get(0).setNconsumoOri(BigDecimal.valueOf(120));
        itinerarios.get(0).getListaSuministros().get(0).getMovLectConsuCollection().get(1).setNconsumoOri(BigDecimal.valueOf(100));
        boolean result  = individualValidations.comparacionLectuaDiaria(itinerarios.get(0).getListaSuministros());
        Assert.assertFalse(result);
        //verify(alarmsManager).reportAlarm(itinerarios.get(0).getListaSuministros().get(0), AlarmsManager.INCREMENTO_MINIMO_NO_ESPERADO_ERROR_CODE);
    }
    
     @Test
    public void comparacionLectuaDiariaFallidoYEnvioAlarmaIncrementoMaximoNoEsperado() throws Exception {
        //Ocurre cuando el valor de la ultima lectura menos el de la penultima es mayor a valor máximo esperado
        ArrayList<AgendaLectura> itinerarios = createUniqueElementAgendaArray();  
        itinerarios.get(0).getListaSuministros().get(0).getMovLectConsuCollection().get(0).setNconsumoOri(BigDecimal.valueOf(1100));
        itinerarios.get(0).getListaSuministros().get(0).getMovLectConsuCollection().get(1).setNconsumoOri(BigDecimal.valueOf(100));
        boolean result = individualValidations.comparacionLectuaDiaria(itinerarios.get(0).getListaSuministros());
        Assert.assertFalse(result);
        verify(alarmsManager).reportAlarm(itinerarios.get(0).getListaSuministros().get(0), AlarmsManager.INCREMENTO_MAXIMO_NO_ESPERADO_ERROR_CODE);
    }
    
    
   
    
    
}
