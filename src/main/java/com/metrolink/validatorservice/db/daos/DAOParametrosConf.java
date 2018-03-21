/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.db.daos;

import com.metrolink.validatorservice.db.controller.IDatabaseController;
import com.metrolink.validatorservice.db.controller.ParametrosConf;
import com.metrolink.validatorservice.models.MConfVal;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Gabriel Ortega Rosero
 */
public class DAOParametrosConf implements IDAOParametrosConf {

    private final IDatabaseController databaseController;
        
    
    public DAOParametrosConf (IDatabaseController databaseController){
        this.databaseController = databaseController;
    }
    
    @Override
    public ArrayList<MConfVal> getParametrosConf() throws Exception {
        ArrayList<MConfVal> listaParametrosConf = new ArrayList();
        String sql  = "SELECT * FROM M_CONF_VAL";

        try (Connection con = databaseController.getConnection()){
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            MConfVal parametrosConf;
            
            while (resultSet.next()) {
                parametrosConf = createParametrosConfEntity(resultSet);
                listaParametrosConf.add(parametrosConf);
            }
        }
        ParametrosConf.setParamsConf(listaParametrosConf.get(0));
        return listaParametrosConf;
    }

    private MConfVal createParametrosConfEntity(ResultSet resultSet) throws SQLException {
        MConfVal mConfVal = new MConfVal();
        mConfVal.setNranDiaMax(resultSet.getBigDecimal("NRAN_DIA_MAX"));
        mConfVal.setNranDiaMin(resultSet.getBigDecimal("NRAN_DIA_MIN"));
        mConfVal.setNranMesMax(resultSet.getBigDecimal("NRAN_MES_MAX"));
        mConfVal.setNranMesMin(resultSet.getBigDecimal("NRAN_MES_MIN"));
        return mConfVal;
    }
    
}
