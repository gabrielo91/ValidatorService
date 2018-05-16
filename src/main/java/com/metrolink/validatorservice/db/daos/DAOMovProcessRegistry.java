/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.db.daos;

import com.metrolink.validatorservice.db.controller.IDatabaseController;
import com.metrolink.validatorservice.models.MovProcessRegistry;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

/**
 *
 * @author Gabriel Ortega Rosero
 */
public class DAOMovProcessRegistry {

    private final IDatabaseController databaseController;
    
    public DAOMovProcessRegistry(IDatabaseController databaseController) {
        this.databaseController = databaseController;
    }

    public String getProcessRgistry() throws Exception {
        String processId = String.valueOf(UUID.randomUUID());
        final int TRANSACCION_EXITOSA = 1;
        
        String sql = "INSERT INTO MOV_PROCESS_REGISTRY  (VCPROCESS_ID, VCESTADO_TRANSACCION) VALUES (?,?)";
        
        try (Connection con = databaseController.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
           
            preparedStatement.setString(1, processId);
            preparedStatement.setInt(2, TRANSACCION_EXITOSA);
            int a = preparedStatement.executeUpdate();
            
            if (a <= 0){
                throw new Exception("No se pudo insertar la alarma");
            }

        
        }
        
        return processId;
    }

    private MovProcessRegistry createMovProcessRegistryEntity(ResultSet resultSet) throws SQLException {
        MovProcessRegistry movProcessRegistry = new MovProcessRegistry();
        movProcessRegistry.setVcprocessId(resultSet.getString("VCPROCESS_ID"));
        return movProcessRegistry;
    }
    
}
