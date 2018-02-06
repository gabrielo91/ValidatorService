/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.bussinesvalidations;


import com.metrolink.validatorservice.alarmsmanager.AlarmsManager;
import com.metrolink.validatorservice.alarmsmanager.IAlarmsManager;
import com.metrolink.validatorservice.db.controller.IDatabaseController;
import com.metrolink.validatorservice.models.MCalTou;
import com.metrolink.validatorservice.models.MovLectConsu;
import com.metrolink.validatorservice.models.MovSuministros;
import java.util.ArrayList;
import javax.annotation.Resource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.any;
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
public class GeneralValidationsTest {
    
    @Mock
    IDatabaseController databaseMock;
    
    @Mock
    IAlarmsManager alarmsManager;
    
    GeneralValidations generalValidations;
    
    
    @Before
    public void setUp() throws Exception {
         MockitoAnnotations.initMocks(this);
         alarmsManager = mock(AlarmsManager.class);
         generalValidations = new GeneralValidations(alarmsManager);
         
         //Alarms Mockc createAlarm
         MovLectConsu lectura = new MovLectConsu();
         Mockito.doNothing().when(alarmsManager).reportAlarm(lectura, "");

         
    }

    @Test
    public void verificarCalendarioTOUTest() throws Exception{
        boolean result = false;
        Integer calTouNumber = 12345;
        ArrayList<MovLectConsu> listaLecturas = new ArrayList<>();
        MovLectConsu lectura = new MovLectConsu();
        MovSuministros movSuministros = new MovSuministros();
        MCalTou mCalTou = new MCalTou();
        mCalTou.setNcodCalTou(calTouNumber);
        movSuministros.setNcodCalTou(mCalTou);
        lectura.setMovSuministros(movSuministros);
        listaLecturas.add(lectura);
        
        result = generalValidations.verificarCalendarioTOU(listaLecturas);
        Assert.assertTrue(result);
    }
    
    @Test
    public void verificarCalendarioTOUTestFailAndAlarmSending() throws Exception{
        boolean result = false;
        Integer calTouNumber = -1;
        ArrayList<MovLectConsu> listaLecturas = new ArrayList<>();
        MovLectConsu lectura = new MovLectConsu();
        MovSuministros movSuministros = new MovSuministros();
        MCalTou mCalTou = new MCalTou();
        mCalTou.setNcodCalTou(calTouNumber);
        movSuministros.setNcodCalTou(mCalTou);
        lectura.setMovSuministros(movSuministros);
        listaLecturas.add(lectura);
        
        result = generalValidations.verificarCalendarioTOU(listaLecturas);
        Assert.assertFalse(result);
        verify(alarmsManager).reportAlarm(any(MovLectConsu.class), anyString());
    }
    
    
}
