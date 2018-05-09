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

/**
 *
 * @author Gabriel Ortega Rosero
 */
public class DAOMovProcessRegistry {

    private final IDatabaseController databaseController;
    
    public DAOMovProcessRegistry(IDatabaseController databaseController) {
        this.databaseController = databaseController;
    }

    public ArrayList<MovProcessRegistry> getProcessRgistry() throws Exception {
        ArrayList<MovProcessRegistry> movProcessRegistryList = new ArrayList<>();
        String sql = "SELECT * FROM (SELECT * FROM MOV_PROCESS_REGISTRY WHERE VCPROCESS_ID_PADRE IS NULL ORDER BY FECHA DESC) WHERE ROWNUM <2;";
        
        try (Connection con = databaseController.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
           
            ResultSet resultSet = preparedStatement.executeQuery();
            MovProcessRegistry movProcessRegistry;
            
            while (resultSet.next()) {
                movProcessRegistry = createMovProcessRegistryEntity(resultSet);
                movProcessRegistryList.add(movProcessRegistry);
            }
        
        }
        
        return movProcessRegistryList;
    }

    private MovProcessRegistry createMovProcessRegistryEntity(ResultSet resultSet) throws SQLException {
        MovProcessRegistry movProcessRegistry = new MovProcessRegistry();
        movProcessRegistry.setVcprocessId(resultSet.getString("VCPROCESS_ID"));
        return movProcessRegistry;
    }
    
}
