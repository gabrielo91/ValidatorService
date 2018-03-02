/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.controller;

import com.metrolink.validatorservice.bussinesvalidations.IGeneralValidations;
import com.metrolink.validatorservice.db.controller.DatabaseController;
import com.metrolink.validatorservice.db.controller.IDatabaseController;
import com.metrolink.validatorservice.db.daos.DAOLecturas;
import com.metrolink.validatorservice.db.daos.IDAOLecturas;
import com.metrolink.validatorservice.models.MovLectConsu;
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
import java.text.SimpleDateFormat;
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
    private final IGeneralValidations generalValidationsClass;
    private IPreferencesManager preferencesManager;
    
    public Controller(IIndividualValidations idividualValidationsClass, IGeneralValidations generalValidationsClass,IPreferencesManager preferencesManager) {
        this.idividualValidationsClass = idividualValidationsClass;
        this.generalValidationsClass = generalValidationsClass;
        this.preferencesManager  = preferencesManager;
    }
     
    /**
     * This method gets the un validated readings and performs validations over them
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
        
        
        SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy");

        Date startingDate = dateformat.parse("01-01-2017");
        Date endingDate = dateformat.parse("01-01-2019");
        
        
        
        
        ArrayList<AgendaLectura> listaAgenda = daoAgendaLectura.listAgendaBetweenDates(startingDate, endingDate);
        System.out.println("EL TAMANO ES: "+listaAgenda.size());

    }
    
    private Date addDays(Date date, int days){
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }    
    
    /**
     * This method gets the un validated readings and performs validations over them
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws NoSuchMethodException 
     */
    public void performValidations2() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, Exception{
        
        IDatabaseController databaseController = new DatabaseController(preferencesManager);
        daoLecturas = new DAOLecturas(databaseController);
        ArrayList<MovLectConsu> listaLecturasValidar = daoLecturas.getLecturasNoValidadas();
        ReadingsStack.getInstance().setReadings(listaLecturasValidar);
                
        performGeneralValidations();      
       
       //un arraylisto con singleton donde agrege todos los datos que deben generar alarmas
        for (int i = 0; i <  ReadingsStack.getInstance().getReadings().size(); i++) {
            performIndividualValidations(i);
        }
    }

    /**
     * Iterate over each method declared in IIndividualValidations interface and uses it over each reading. 
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
            Method validation = validations.getMethod(bussinesValidation.getName(), List.class, int.class);
            validation.invoke(idividualValidationsClass, ReadingsStack.getInstance().getReadings(), indexToValidate);
        }
    }

    
    private void performGeneralValidations() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Class validations = generalValidationsClass.getClass();   
        
        for (Method bussinesValidation : IGeneralValidations.class.getMethods()) {
            System.out.println("La validacion a ejecutar es: "+ bussinesValidation.getName());
            Method validation = validations.getMethod(bussinesValidation.getName(), List.class);
            validation.invoke(generalValidationsClass, ReadingsStack.getInstance().getReadings());
        }
    }
}
