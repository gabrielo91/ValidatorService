/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.db.daos;

import com.metrolink.validatorservice.db.controller.IDatabaseController;
import com.metrolink.validatorservice.db.controller.ParametrosConf;
import com.metrolink.validatorservice.models.MConfVal;
import com.metrolink.validatorservice.models.MovSuministros;
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

    public DAOParametrosConf(IDatabaseController databaseController) {
        this.databaseController = databaseController;
    }

    @Override
    public MConfVal getParametrosConf(MovSuministros suministro) throws Exception {
        MConfVal ParametrosConf = new MConfVal();
        String sql = "SELECT DISTINCT MC.NCOD_AREA,MC.VCTIPO_VAL,MC.NRAN_DIA_MIN,MC.NRAN_DIA_MAX,MC.NRAN_MES_MIN,MC.NRAN_MES_MAX,MC.NRAN_DES_MIN,MC.NRAN_DES_MAX,MC.NDES_CON_COE,MC.LESTADO "
                + "FROM M_CONF_VAL MC "
                + "INNER JOIN M_JURISDICCIONES MJ ON MC.NCOD_AREA = MJ.NCOD_AREA "
                + "INNER JOIN MOV_SUMINISTROS MS ON MJ.NCOD_JURISDICCION = MS.NCOD_JURISDICCION "
                + "WHERE MJ.NCOD_JURISDICCION =? AND MC.VCTIPO_VAL=?";

        try (Connection con = databaseController.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(sql);           
            preparedStatement.setInt(1, suministro.getNcodJurisdiccion().getNcodJurisdiccion());
            preparedStatement.setString(2, suministro.getVctipoVal());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {               
                ParametrosConf = createParametrosConfEntity(resultSet);

            }
        }

        return ParametrosConf;
    }

    private MConfVal createParametrosConfEntity(ResultSet resultSet) throws SQLException {
        MConfVal mConfVal = new MConfVal();
        mConfVal.setNranDiaMax(resultSet.getBigDecimal("NRAN_DIA_MAX"));
        mConfVal.setNranDiaMin(resultSet.getBigDecimal("NRAN_DIA_MIN"));
        mConfVal.setNranMesMax(resultSet.getBigDecimal("NRAN_MES_MAX"));
        mConfVal.setNranMesMin(resultSet.getBigDecimal("NRAN_MES_MIN"));
        mConfVal.setNranDesMin(resultSet.getShort("NRAN_DES_MIN"));
        mConfVal.setNranDesMax(resultSet.getShort("NRAN_DES_MAX"));
        mConfVal.setNdesConCoe(resultSet.getBigDecimal("NDES_CON_COE"));

        return mConfVal;
    }

}
