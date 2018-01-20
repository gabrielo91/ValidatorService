/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.controller;

import com.metrolink.validatorservice.alarmsmanager.IAlarmsManager;
import com.metrolink.validatorservice.bussinesvalidations.IValidations;
import com.metrolink.validatorservice.db.daos.DAOLecturas;
import com.metrolink.validatorservice.db.daos.IDAOLecturas;
import com.metrolink.validatorservice.models.DTOLecturas;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;


/**
 *
 * @author Gabriel Ortega
 */
public class Controller {
    
    
    private IDAOLecturas daoLecturas;
    private IAlarmsManager alarmsManager;
    private List<DTOLecturas> listaLecturasValidar; 
    private final IValidations validationsClass;

    public Controller(IValidations validationsClass) {
        this.validationsClass = validationsClass;
    }
        
    public void performValidations() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException{
        
        daoLecturas = new DAOLecturas();
        listaLecturasValidar = daoLecturas.getLecturasNoValidadas();

        for (int i = 0; i < listaLecturasValidar.size(); i++) {
            validateValue(i);
        }

    }

    private void validateValue(int indexToValidate) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        
        Class validations = validationsClass.getClass();   
        
        for (Method bussinesValidation : IValidations.class.getMethods()) {
            System.out.println("el nombre del metodo es: "+ bussinesValidation.getName());
            System.out.println("el nombre del los argumentos son: " + bussinesValidation.getParameterCount());
            Method validation = validations.getMethod(bussinesValidation.getName(), List.class, int.class);
            validation.invoke(validationsClass, listaLecturasValidar, indexToValidate);
        }
    }
}
