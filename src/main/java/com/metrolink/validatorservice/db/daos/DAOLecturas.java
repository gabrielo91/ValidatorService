/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.db.daos;

import com.metrolink.validatorservice.models.DTOLecturas;
import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author Gabriel Ortega
 */
public class DAOLecturas implements IDAOLecturas {


    @Override
    public DTOLecturas getLecturaForUpdate(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<DTOLecturas> getLecturasNoValidadas() {
        ArrayList<DTOLecturas> lista = new ArrayList();
        DTOLecturas lectura = new DTOLecturas();
        lista.add(lectura);
        return lista;
    }
    
}
