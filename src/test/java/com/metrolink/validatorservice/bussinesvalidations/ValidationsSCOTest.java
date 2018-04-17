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
import com.metrolink.validatorservice.models.MovRegsSco;
import com.metrolink.validatorservice.models.MovRegsScoPK;
import com.metrolink.validatorservice.models.MovSuministros;
import com.metrolink.validatorservice.models.MovSuministrosPK;
import com.metrolink.validatorservice.preferencesmanager.IPreferencesManager;
import com.metrolink.validatorservice.preferencesmanager.PreferencesManager;
import com.metrolink.validatorservice.utils.Utils;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
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
public class ValidationsSCOTest {
    
    final static String DATE_FORMAT_YYYY_MM_DD="yyyy-MM-dd";
    final static String DATE_FORMAT_YYYY_MM_DD_HH_MM="yyyy-MM-dd HH:mm";
    final long VALOR_PARA_FORZAR_DESVIACION= 300;
    
    @Mock
    IDatabaseController databaseMock;
    
    @Mock
    IAlarmsManager alarmsManager;
    
    IIndividualValidationsSCO individualValidationsSCO;
    MParametrosAdm parametrosAdm;
    MConfVal parametrosConf;
    ArrayList<MovSuministrosPK> itinerariosInvalidos;
    
    private  ArrayList<AgendaLectura> createUniqueElementAgendaArray() throws ParseException{
        ArrayList<AgendaLectura> intinerarios = new ArrayList<>();
        AgendaLectura agenda = new AgendaLectura();
        
        AgendaLecturaPK agendaLecturaPK = new AgendaLecturaPK();
        agendaLecturaPK.setDfechaTeo(new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD_HH_MM).parse("2018-03-10 12:00"));
        agenda.setAgendaLecturaPK(agendaLecturaPK);
        
        MovSuministros movSuministros = new MovSuministros();
        MovSuministrosPK movSuministrosPK = new MovSuministrosPK();
        movSuministrosPK.setNcodProv(123);
        movSuministrosPK.setVctipoEnergia("TEST");
        movSuministrosPK.setNnisRad(BigInteger.valueOf(123123));
        movSuministros.setMovSuministrosPK(movSuministrosPK);
        
        MCalTou mCalTou = new MCalTou();
        movSuministros.setNcodCalTou(mCalTou);
        movSuministros.setVctipoVal(MovSuministros.TIPO_LECTURA);
        
        MovRegsSco movRegsSco1 = new MovRegsSco();
        MovRegsScoPK movRegsScoPK1 = new MovRegsScoPK();
        movRegsScoPK1.setTsfeclet(Utils.addDays(new Date(), 0));
        movRegsSco1.setMovRegsScoPK(movRegsScoPK1);
        movRegsSco1.setNconsumo(BigInteger.valueOf(100));
        movRegsSco1.setNlec(BigInteger.valueOf(10));
        movSuministros.getMovRegsScoCollection().add(movRegsSco1);
        
        MovRegsSco movRegsSco2 = new MovRegsSco();
        MovRegsScoPK movRegsScoPK2 = new MovRegsScoPK();
        movRegsScoPK2.setTsfeclet(Utils.addDays(new Date(), 1));
        movRegsSco2.setMovRegsScoPK(movRegsScoPK2);
        movRegsSco2.setNconsumo(BigInteger.valueOf(100));
        movRegsSco2.setNlec(BigInteger.valueOf(20));
        movSuministros.getMovRegsScoCollection().add(movRegsSco2);
        
        MovRegsSco movRegsSco3 = new MovRegsSco();
        MovRegsScoPK movRegsScoPK3 = new MovRegsScoPK();
        movRegsScoPK3.setTsfeclet(Utils.addDays(new Date(), 2));
        movRegsSco3.setMovRegsScoPK(movRegsScoPK3);
        movRegsSco3.setNconsumo(BigInteger.valueOf(120));
        movRegsSco3.setNlec(BigInteger.valueOf(30));
        movSuministros.getMovRegsScoCollection().add(movRegsSco3);
        
        MovRegsSco movRegsSco4 = new MovRegsSco();
        MovRegsScoPK movRegsScoPK4 = new MovRegsScoPK();
        movRegsScoPK4.setTsfeclet(Utils.addDays(new Date(), 3));
        movRegsSco4.setMovRegsScoPK(movRegsScoPK4);
        movRegsSco4.setNconsumo(BigInteger.valueOf(140));
        movRegsSco4.setNlec(BigInteger.valueOf(35));
        movSuministros.getMovRegsScoCollection().add(movRegsSco4);
        
        agenda.getListaSuministros().add(movSuministros);
        intinerarios.add(agenda);
        
        //los datos ingresados de consumo tienen un coeficiente de variacion de 0.14
        //los datos ingresados de lectura tienen un coeficiente de variacion de 0.28
        
        return intinerarios;
    }
    
    @Before
    public void setUp() throws Exception {
         MockitoAnnotations.initMocks(this);
         alarmsManager = mock(AlarmsManager.class);
         individualValidationsSCO = new IndividualValidationsSCO(alarmsManager);  
         itinerariosInvalidos = new ArrayList<>();
         loadAdminParamsFromDB();
         loadConfParamsFromDB();
         AgendaLectura lectura = new AgendaLectura();
         Mockito.doNothing().when(alarmsManager).reportAlarm(lectura, 0);
    }
    
    
    
    @Test
    public void validacionDesviacionLecturasExitoso() throws ParseException, Exception{
        ArrayList<AgendaLectura> itinerarios = createUniqueElementAgendaArray(); 
        boolean result = individualValidationsSCO.verificarDesviacionConsumo(itinerarios.get(0).getListaSuministros(), itinerariosInvalidos);
        Assert.assertTrue(result);
    }
            
    @Test
    public void validacionDesviacionConsumoExitoso() throws ParseException, Exception{
        ArrayList<AgendaLectura> itinerarios = createUniqueElementAgendaArray(); 
        itinerarios.get(0).getListaSuministros().get(0).setVctipoVal(MovSuministros.TIPO_CONSUMO);
        boolean result = individualValidationsSCO.verificarDesviacionConsumo(itinerarios.get(0).getListaSuministros(), itinerariosInvalidos);
        Assert.assertTrue(result);
    }
    
    @Test
    public void validacionDesviacionLecturasFallidaPorCodigoAnomalia() throws ParseException, Exception{
        ArrayList<AgendaLectura> itinerarios = createUniqueElementAgendaArray(); 
        itinerarios.get(0).getListaSuministros().get(0).getMovRegsScoCollection().get(0).setVccoan("ASDWA");
        boolean result = individualValidationsSCO.verificarDesviacionConsumo(itinerarios.get(0).getListaSuministros(), itinerariosInvalidos);
        Assert.assertFalse(result);
    }
    
    @Test
    public void validacionDesviacionConsumoFallidaPorCodigoAnomalia() throws ParseException, Exception{
        ArrayList<AgendaLectura> itinerarios = createUniqueElementAgendaArray(); 
        itinerarios.get(0).getListaSuministros().get(0).setVctipoVal(MovSuministros.TIPO_CONSUMO);
        itinerarios.get(0).getListaSuministros().get(0).getMovRegsScoCollection().get(0).setVccoan("ASDWA");
        boolean result = individualValidationsSCO.verificarDesviacionConsumo(itinerarios.get(0).getListaSuministros(), itinerariosInvalidos);
        Assert.assertFalse(result);;
    }
    
    @Test
    public void validacionDesviacionLecturasFallidaYEnvioAlarma() throws ParseException, Exception{
        ArrayList<AgendaLectura> itinerarios = createUniqueElementAgendaArray(); 
        itinerarios.get(0).getListaSuministros().get(0).getMovRegsScoCollection().get(0).setNlec(BigInteger.valueOf(VALOR_PARA_FORZAR_DESVIACION));
        boolean result = individualValidationsSCO.verificarDesviacionConsumo(itinerarios.get(0).getListaSuministros(), itinerariosInvalidos);
        Assert.assertFalse(result);
        verify(alarmsManager).reportAlarm(itinerarios.get(0).getListaSuministros().get(0), AlarmsManager.DESVIACION_DE_CONSUMO_ERROR_CODE);
    }
    
    @Test
    public void validacionDesviacionConsumoYEnvioAlarma() throws ParseException, Exception{
        ArrayList<AgendaLectura> itinerarios = createUniqueElementAgendaArray(); 
        itinerarios.get(0).getListaSuministros().get(0).setVctipoVal(MovSuministros.TIPO_CONSUMO);
        itinerarios.get(0).getListaSuministros().get(0).getMovRegsScoCollection().get(0).setNconsumo(BigInteger.valueOf(VALOR_PARA_FORZAR_DESVIACION));
        boolean result = individualValidationsSCO.verificarDesviacionConsumo(itinerarios.get(0).getListaSuministros(), itinerariosInvalidos);
        Assert.assertFalse(result);
        verify(alarmsManager).reportAlarm(itinerarios.get(0).getListaSuministros().get(0), AlarmsManager.DESVIACION_DE_CONSUMO_ERROR_CODE);
    
    }
    
    @Test
    public void validacionDesviacionSuministroInvalido() throws ParseException, Exception{
        ArrayList<AgendaLectura> itinerarios = createUniqueElementAgendaArray(); 
        itinerariosInvalidos.add(itinerarios.get(0).getListaSuministros().get(0).getMovSuministrosPK());
        boolean result = individualValidationsSCO.verificarDesviacionConsumo(itinerarios.get(0).getListaSuministros(), itinerariosInvalidos);
        Assert.assertFalse(result);
    }
    
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
