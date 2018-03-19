/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.controller;

import com.metrolink.validatorservice.db.controller.DatabaseController;
import com.metrolink.validatorservice.db.controller.IDatabaseController;
import com.metrolink.validatorservice.db.daos.DAOLecturas;
import com.metrolink.validatorservice.db.daos.IDAOLecturas;
import com.metrolink.validatorservice.preferencesmanager.IPreferencesManager;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import com.metrolink.validatorservice.bussinesvalidations.IIndividualValidations;
import com.metrolink.validatorservice.db.daos.DAOAgendaLectura;
import com.metrolink.validatorservice.db.daos.DAOParametrosAdmin;
import com.metrolink.validatorservice.db.daos.DAOSuministros;
import com.metrolink.validatorservice.db.daos.IDAOAgendaLectura;
import com.metrolink.validatorservice.db.daos.IDAOParametrosAdmin;
import com.metrolink.validatorservice.db.daos.IDAOSuministros;
import com.metrolink.validatorservice.models.AgendaLectura;
import com.metrolink.validatorservice.models.MParametrosAdm;
import com.metrolink.validatorservice.models.MovSuministros;
import com.metrolink.validatorservice.utils.Utils;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Gabriel Ortega
 */
public class Controller {
    
    private IDAOLecturas daoLecturas;
    private IDAOParametrosAdmin daoParametrosAdmin;
    private IDAOAgendaLectura daoAgendaLectura;
    private IDAOSuministros dAOSuministros;
    
    private final IIndividualValidations idividualValidationsClass;
    private IPreferencesManager preferencesManager;
    
    public Controller(IIndividualValidations idividualValidationsClass, IPreferencesManager preferencesManager) {
        this.idividualValidationsClass = idividualValidationsClass;
        this.preferencesManager  = preferencesManager;
    }
    
    public void startValidationProcess() throws Exception{
        ArrayList<AgendaLectura> intinerarios = getValuesForChecking();
        System.out.println("El tamano es: : "+intinerarios.size());
        AgendaStack.getInstance().setAgendaValues(intinerarios);
        
        //Remember: element in index is the newest
        
        int i=0;
        for (AgendaLectura intinerario : AgendaStack.getInstance().getIntinerarios()) {
            i++;
            System.out.println("i: "+ i +"La fecha es: "+intinerario.getAgendaLecturaPK().getDfechaTeo() + " vdtcodconsumo: "+intinerario.getAgendaLecturaPK().getVcparam());
            
        }
        
        //lockUnlockSuministros(DAOSuministros.BLOQUEADO);
        performValidations();
        //updateLecturas()
        //lockUnlockSuministros(DAOSuministros.DESBLOQUEADO);
        //saveAlarmas()
    }
    
    
    /**
     * This method gets the needed value to analyze
     * @return 
     */
    public ArrayList<AgendaLectura> getValuesForChecking() throws Exception{
        
        ArrayList<AgendaLectura> intinerarios = null;
        IDatabaseController databaseController = new DatabaseController(preferencesManager);
        daoParametrosAdmin = new DAOParametrosAdmin(databaseController);        
        MParametrosAdm parametrosAdm = daoParametrosAdmin.getParametrosAdm().get(0);
        short diasABuscar = parametrosAdm.getNdiasBusca();
        System.out.println("dias busca es: "+diasABuscar);
        daoLecturas = new DAOLecturas(databaseController);
        daoAgendaLectura = new DAOAgendaLectura(databaseController);
        Date startingDate = new Date();
        Date endingDate = Utils.addDays(startingDate, diasABuscar);//addDays
        //intinerarios = daoAgendaLectura.listAgendaBetweenDates(startingDate, endingDate);
        intinerarios = daoAgendaLectura.listAgendaBetweenDates(Utils.addDays(startingDate, -8), Utils.addDays(endingDate, -8));
        return intinerarios;
    }
    
    
    
    
    /**
     * This method gets agenda values and performs validations over them
     * 
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws NoSuchMethodException 
     */
    public void performValidations() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, Exception{
        int j = 0;
        for (AgendaLectura intinerario :  AgendaStack.getInstance().getIntinerarios()) {
            System.out.println("******************************* INTINERARIO: "+intinerario.getVcitinerario());
            for (int i = 0; i <  intinerario.getListaSuministros().size(); i++) {
                performIndividualValidations(j);
            }
            j++;
        }  
    }
    
    
    /**
     * Iterate over each method declared in IIndividualValidations interface and uses it over each suministros object. 
     * It uses the full array in order to perfom validations which uses previous values
     * @param indexToValidate
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws NoSuchMethodException 
     */
    private void performIndividualValidations(int indexToValidate) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        
        Class validations = idividualValidationsClass.getClass();   
        for (Method bussinesValidation : IIndividualValidations.class.getMethods()) {
            System.out.println("La validacion a ejecutar es: "+ bussinesValidation.getName());
            Method validation = validations.getMethod(bussinesValidation.getName(), List.class);
            validation.invoke(idividualValidationsClass, AgendaStack.getInstance().getIntinerarios().get(indexToValidate).getListaSuministros());
        }
    }

    private void lockUnlockSuministros(final short state) throws Exception {
        IDatabaseController databaseController = new DatabaseController(preferencesManager);
        daoParametrosAdmin = new DAOParametrosAdmin(databaseController);
        dAOSuministros = new DAOSuministros(databaseController);
        daoLecturas = new DAOLecturas(databaseController);
        
        for (AgendaLectura intinerario : AgendaStack.getInstance().getIntinerarios()) {

            dAOSuministros.lockUnlockSuministros(intinerario.getListaSuministros(), state);
            for (MovSuministros suministro : intinerario.getListaSuministros()) {
                daoLecturas.lockUnlockLecturas(suministro.getMovLectConsuCollection(), state);
            }
        }
    }
}
