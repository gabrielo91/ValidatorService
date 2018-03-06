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
import com.metrolink.validatorservice.db.daos.IDAOAgendaLectura;
import com.metrolink.validatorservice.db.daos.IDAOParametrosAdmin;
import com.metrolink.validatorservice.models.AgendaLectura;
import com.metrolink.validatorservice.models.MParametrosAdm;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author Gabriel Ortega
 */
public class Controller {
    
    private IDAOLecturas daoLecturas;
    private IDAOParametrosAdmin daoParametrosAdmin;
    private IDAOAgendaLectura daoAgendaLectura;
    
    private final IIndividualValidations idividualValidationsClass;
    private IPreferencesManager preferencesManager;
    
    public Controller(IIndividualValidations idividualValidationsClass, IPreferencesManager preferencesManager) {
        this.idividualValidationsClass = idividualValidationsClass;
        this.preferencesManager  = preferencesManager;
    }
     
    /**
     * This method gets agenda values and performs validations over them
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws NoSuchMethodException 
     */
    public void performValidations() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, Exception{
        
        IDatabaseController databaseController = new DatabaseController(preferencesManager);
        daoParametrosAdmin = new DAOParametrosAdmin(databaseController);        
        MParametrosAdm parametrosAdm = daoParametrosAdmin.getParametrosAdm().get(0);
        short diasABuscar = parametrosAdm.getNdiasBusca();
        System.out.println("dias busca es: "+diasABuscar);
        daoLecturas = new DAOLecturas(databaseController);
        daoAgendaLectura = new DAOAgendaLectura(databaseController);
        Date startingDate = new Date();
        Date endingDate = addDays(startingDate, diasABuscar);

        ArrayList<AgendaLectura> intinerarios = daoAgendaLectura.listAgendaBetweenDates(startingDate, endingDate);
        AgendaStack.getInstance().setAgendaValues(intinerarios);
        
        int j = 0;
        for (AgendaLectura intinerario :  AgendaStack.getInstance().getIntinerarios()) {
            System.out.println("******************************* INTINERARIO: "+intinerario.getVcitinerario());
            for (int i = 0; i <  intinerario.getListaSuministros().size(); i++) {
                performValidations(j);
            }
            j++;
        }      
    }
    
    private Date addDays(Date date, int days){
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
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
    private void performValidations(int indexToValidate) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        
        Class validations = idividualValidationsClass.getClass();   
        for (Method bussinesValidation : IIndividualValidations.class.getMethods()) {
            System.out.println("La validacion a ejecutar es: "+ bussinesValidation.getName());
            Method validation = validations.getMethod(bussinesValidation.getName(), List.class);
            validation.invoke(idividualValidationsClass, AgendaStack.getInstance().getIntinerarios().get(indexToValidate).getListaSuministros());
        }
    }
}
