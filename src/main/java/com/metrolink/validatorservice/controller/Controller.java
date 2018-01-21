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

/**
 *
 * @author Gabriel Ortega
 */
public class Controller {
    
    private IDAOLecturas daoLecturas;
    private List<MovLectConsu> listaLecturasValidar; 
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
        daoLecturas = new DAOLecturas(databaseController);
        listaLecturasValidar = daoLecturas.getLecturasNoValidadas();
        
        performGeneralValidations();
       
        for (int i = 0; i < listaLecturasValidar.size(); i++) {
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
            validation.invoke(idividualValidationsClass, listaLecturasValidar, indexToValidate);
        }
    }

    
    private void performGeneralValidations() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Class validations = generalValidationsClass.getClass();   
        
        for (Method bussinesValidation : IGeneralValidations.class.getMethods()) {
            System.out.println("La validacion a ejecutar es: "+ bussinesValidation.getName());
            Method validation = validations.getMethod(bussinesValidation.getName(), List.class);
            validation.invoke(generalValidationsClass, listaLecturasValidar);
        }
    }
}
