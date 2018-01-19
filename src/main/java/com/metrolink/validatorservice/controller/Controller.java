/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.controller;

import com.metrolink.validatorservice.alarmsmanager.IAlarmsManager;
import com.metrolink.validatorservice.db.daos.DAOLecturas;
import com.metrolink.validatorservice.db.daos.IDAOLecturas;
import com.metrolink.validatorservice.models.DTOLecturas;
import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author Gabriel Ortega
 */
public class Controller {
    
    
    IDAOLecturas daoLecturas;
    IAlarmsManager alarmsManager;
        
    public void performValidations(Date startDate, Date endDate){
        
        daoLecturas = new DAOLecturas();
        ArrayList<DTOLecturas> listaLecturasValdar = daoLecturas.getLecturasBetweenDates(startDate, endDate);
        
        for (DTOLecturas dTOLecturas : listaLecturasValdar) {
            realizarValidaciones(dTOLecturas);
        }
    }

    private void realizarValidaciones(DTOLecturas dTOLecturas) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
