/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.db.daos;

import com.metrolink.validatorservice.db.controller.IDatabaseController;
import com.metrolink.validatorservice.models.MovAlarmas;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

/**
 *
 * @author Gabriel Ortega
 */
public class DAOAlarmas implements IDAOAlarmas {

    private final IDatabaseController databaseController;

    public DAOAlarmas(IDatabaseController databaseController) {
        this.databaseController = databaseController;
    }
    
    
    @Override
    public void insertAlarmas(ArrayList<MovAlarmas> alarmas, String processId) throws Exception {
        boolean result = false;
        int[] resultList;
        
        String sql = "INSERT INTO MOV_ALARMAS ("
                + " NUNICOM," 
                + " NCONS_PROCESO,"
                + " NPERICONS," 
                + " VCRUTA," 
                + " VCITINERARIO,"
                + " DFECHA_VAL,"
                + " NNIC,"
                + " NNIS_RAD," 
                + " NCOD_ALARMA," 
                + " VCTIPO_ENERGIA," 
                + " VCRESULTADO) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = databaseController.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
           
            for (MovAlarmas alarma : alarmas) {               
                preparedStatement.setShort(1, alarma.getNunicom());
                preparedStatement.setString(2, processId);
                preparedStatement.setInt(3, alarma.getNpericons());
                preparedStatement.setString(4, alarma.getVcruta());
                preparedStatement.setString(5, alarma.getVcitinerario());
                preparedStatement.setDate(6, new java.sql.Date(alarma.getDfechaVal().getTime()) );
                preparedStatement.setInt(7, alarma.getNnic());
                preparedStatement.setInt(8, alarma.getMovAlarmasPK().getNnisRad());
                preparedStatement.setInt(9, alarma.getMovAlarmasPK().getNcodAlarma());
                preparedStatement.setString(10, alarma.getVctipoEnergia());
                preparedStatement.setString(11, alarma.getVcresultado());
                
                preparedStatement.addBatch();
            }
            
            resultList  = preparedStatement.executeBatch();
            for (int i : resultList) {
                result = result && (i>=0);
            }
        }
    }
    
}
