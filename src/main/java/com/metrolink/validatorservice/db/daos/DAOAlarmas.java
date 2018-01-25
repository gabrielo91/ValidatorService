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
    public void insertAlarma(MovAlarmas alarma) throws Exception {
        boolean result = false;
        String sql = "INSERT INTO MOV_ALARMAS ("
                + " NUNICOM" 
                + " NCONS_PROCESO"
                + " NPERIODO" 
                + " VCRUTA" 
                + " VCITINERARIO"
                + " DFECHA_VAL"
                + " NNIC"
                + " NNIS_RAD" 
                + " NCOD_ALARMA" 
                + " VCTIPO_ENERGIA) VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s)";

        try (Connection con = databaseController.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            
            //DEBE REVISARSE AQUI QUE NO HAYA UNA ALARMA REPORTADA YA CON EL MISMO ID
            preparedStatement.setShort(1, alarma.getNunicom());
            preparedStatement.setInt(2, alarma.getMovAlarmasPK().getNconsProceso());
            preparedStatement.setInt(3, alarma.getNperiodo());
            preparedStatement.setString(4, alarma.getVcruta());
            preparedStatement.setString(5, alarma.getVcitinerario());
            preparedStatement.setDate(6, new java.sql.Date(alarma.getDfechaVal().getTime()) );
            preparedStatement.setInt(7, alarma.getNnic());
            preparedStatement.setInt(8, alarma.getMovAlarmasPK().getNnisRad());
            preparedStatement.setInt(9, alarma.getMovAlarmasPK().getNcodAlarma());
            preparedStatement.setString(10, alarma.getVctipoEnergia());
            
            
            result = preparedStatement.executeUpdate()>0;
        }
    }
    
}
