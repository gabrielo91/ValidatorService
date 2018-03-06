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
import com.metrolink.validatorservice.models.MovSuministros;
import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
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
    
    IndividualValidations individualValidations;
    
    private  ArrayList<AgendaLectura> createUniqueElementAgendaArray(){
        Integer calTouNumber = 12345;
        ArrayList<AgendaLectura> intinerarios = new ArrayList<>();
        AgendaLectura agenda = new AgendaLectura();
        MovSuministros movSuministros = new MovSuministros();
        MCalTou mCalTou = new MCalTou();
        mCalTou.setNcodCalTou(calTouNumber);
        movSuministros.setNcodCalTou(mCalTou);
        agenda.getListaSuministros().add(movSuministros);
        intinerarios.add(agenda);
        return intinerarios;
    }
    
    @Before
    public void setUp() throws Exception {
         MockitoAnnotations.initMocks(this);
         alarmsManager = mock(AlarmsManager.class);
         individualValidations = new IndividualValidations(alarmsManager);         
         AgendaLectura lectura = new AgendaLectura();
         Mockito.doNothing().when(alarmsManager).reportAlarm(lectura, 0);
         
    }

    @Test
    public void verificarCalendarioTOUTestSuccess() throws Exception{
        boolean result = false;        
        ArrayList<AgendaLectura> intineriarios = createUniqueElementAgendaArray();               
        result = individualValidations.verificarCalendarioTOU(intineriarios.get(0).getListaSuministros());
        Assert.assertTrue(result);
    }
    
    @Test
    public void verificarCalendarioTOUTestFailAndAlarmSending() throws Exception{
        boolean result = false;
        Integer calTouNumber = -1;
        ArrayList<AgendaLectura> intinerarios = createUniqueElementAgendaArray();  
        intinerarios.get(0).getListaSuministros().get(0).getNcodCalTou().setNcodCalTou(calTouNumber);      
        result = individualValidations.verificarCalendarioTOU(intinerarios.get(0).getListaSuministros());
        Assert.assertFalse(result);
        verify(alarmsManager).reportAlarm(any(MovSuministros.class), anyInt());
    }
    
    @Test
    public void verificarExistenciaDatosTestSucces() throws Exception{
        boolean result = false;
//        ArrayList<AgendaLectura> intinerarios = createUniqueElementAgendaArray();  
//        result = individualValidations.verificarExistenciaDatos(intinerarios.get(0).getListaSuministros().get(0));
//        Assert.assertTrue(result);
    }
    
  
    @Test
    public void verificarExistenciaDatosestFailAndAlarmSending() throws Exception{
        boolean result = false;
//        ArrayList<AgendaLectura> intinerarios = new ArrayList<>(); 
//        result = generalValidations.verificarExistenciaDatos(intinerarios);
//        Assert.assertFalse(result);
//        verify(alarmsManager).reportAlarm(any(AgendaLectura.class), anyString());
    }
    
}
