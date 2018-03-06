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
import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
/**
 *
 * @author Gabriel Ortega
 */
@PrepareForTest({IAlarmsManager.class})
public class GeneralValidationsTest {
    
    @Mock
    IDatabaseController databaseMock;
    
    @Mock
    IAlarmsManager alarmsManager;
    
    GeneralValidations generalValidations;
    
    private  ArrayList<AgendaLectura> createUniqueElementAgendaArray(){
        Integer calTouNumber = 12345;
        ArrayList<AgendaLectura> intinerarios = new ArrayList<>();
//        AgendaLectura lectura = new AgendaLectura();
//        MovSuministros movSuministros = new MovSuministros();
//        MCalTou mCalTou = new MCalTou();
//        mCalTou.setNcodCalTou(calTouNumber);
//        movSuministros.setNcodCalTou(mCalTou);
//        intinerarios.setMovSuministros(movSuministros);
//        intinerarios.add(lectura);
        return intinerarios;
    }
    
    @Before
    public void setUp() throws Exception {
         MockitoAnnotations.initMocks(this);
         alarmsManager = mock(AlarmsManager.class);
         generalValidations = new GeneralValidations(alarmsManager);         
         //Alarms Mockc createAlarm
         AgendaLectura lectura = new AgendaLectura();
         //Mockito.doNothing().when(alarmsManager).reportAlarm(lectura, "");
         
    }

    @Test
    public void verificarCalendarioTOUTestSuccess() throws Exception{
        boolean result = false;        
        ArrayList<AgendaLectura> intineriarios = createUniqueElementAgendaArray();               
        //result = generalValidations.verificarCalendarioTOU(intinerarios);
        Assert.assertTrue(result);
    }
    
    @Test
    public void verificarCalendarioTOUTestFailAndAlarmSending() throws Exception{
        boolean result = false;
        Integer calTouNumber = -1;
        ArrayList<AgendaLectura> intinerarios = createUniqueElementAgendaArray();  
        //intinerarios.get(0).getMovSuministros().getNcodCalTou().setNcodCalTou(calTouNumber);      
        result = generalValidations.verificarCalendarioTOU(intinerarios);
        Assert.assertFalse(result);
        verify(alarmsManager).reportAlarm(any(AgendaLectura.class), anyString());
    }
    
    @Test
    public void verificarExistenciaDatosTestSucces() throws Exception{
        boolean result = false;
        ArrayList<AgendaLectura> intinerarios = createUniqueElementAgendaArray();  
        result = generalValidations.verificarExistenciaDatos(intinerarios);
        Assert.assertTrue(result);
    }
    
  
    @Test
    public void verificarExistenciaDatosestFailAndAlarmSending() throws Exception{
        boolean result = false;
        ArrayList<AgendaLectura> intinerarios = new ArrayList<>(); 
        result = generalValidations.verificarExistenciaDatos(intinerarios);
        Assert.assertFalse(result);
        verify(alarmsManager).reportAlarm(any(AgendaLectura.class), anyString());
    }
    
}
