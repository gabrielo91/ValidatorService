/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.db.daos;

import com.metrolink.validatorservice.models.MovRegsSco;
import com.metrolink.validatorservice.models.MovRegsScoPK;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Gabriel Ortega
 */
public class DAORegsSco {
    
    public static MovRegsSco createMovLecConsuEntity(ResultSet resultSet) throws SQLException {
        
        MovRegsSco movRegsSco = new MovRegsSco();
        MovRegsScoPK movRegsScoPK = new MovRegsScoPK();
        movRegsScoPK.setNnisRad(BigInteger.valueOf(resultSet.getLong("NNIS_RAD")));
        movRegsScoPK.setTsfeclet(resultSet.getDate("TSFECLET"));
        movRegsScoPK.setVccodtconsumo(resultSet.getString("VCCODTCONSUMO"));
        movRegsSco.setMovRegsScoPK(movRegsScoPK);
        movRegsSco.setNconsumo(BigInteger.valueOf(resultSet.getLong("NCONSUMO")));
        movRegsSco.setNlec(BigInteger.valueOf(resultSet.getLong("NLEC")));
        movRegsSco.setVccoan(resultSet.getString("VCCOAN"));
        return movRegsSco;
    }
      
    
    
}
