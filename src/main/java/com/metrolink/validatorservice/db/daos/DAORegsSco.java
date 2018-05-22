/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.db.daos;

import com.metrolink.validatorservice.db.controller.IDatabaseController;
import com.metrolink.validatorservice.models.MovLectConsu;
import com.metrolink.validatorservice.models.MovRegsSco;
import com.metrolink.validatorservice.models.MovRegsScoPK;
import com.metrolink.validatorservice.models.MovSuministros;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 *
 * @author Gabriel Ortega
 */
public class DAORegsSco {

    private final IDatabaseController databaseController;

    public DAORegsSco(IDatabaseController databaseController) {
        this.databaseController = databaseController;
    }

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
        movRegsSco.setNperiodo(BigInteger.valueOf(resultSet.getLong("NPERIODO")));
        movRegsSco.setNlemin(resultSet.getBigDecimal("NLEMIN"));
        movRegsSco.setNlemax(resultSet.getBigDecimal("NLEMAX"));
        return movRegsSco;
    }

    public ArrayList<MovRegsSco> consultarMovRegScoAsociado(MovLectConsu movLectConsu) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        ArrayList<MovRegsSco> movRegsScoList = new ArrayList<>();
        String sql = "SELECT * FROM MOV_REGS_SCO WHERE NNIS_RAD = ? and TSFECLET = '" + sdf.format(new java.sql.Timestamp(movLectConsu.getTsfechaLec().getTime())) +  "' and VCTIPO_LEC = ?";

        try (Connection con = databaseController.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(sql);            
            preparedStatement.setInt(1, movLectConsu.getNnisRad().intValue());
            //preparedStatement.setString(2, sdf.format(new java.sql.Timestamp(movLectConsu.getTsfechaLec().getTime())));
            preparedStatement.setString(2, movLectConsu.getVctipoLec());
            ResultSet resultSet = preparedStatement.executeQuery();
            
            MovRegsSco movRegsSco;
            
            while (resultSet.next()) {
                movRegsSco = createMovLecConsuEntity(resultSet);
                movRegsScoList.add(movRegsSco);
            }

        } 
        return movRegsScoList;
    }

    /**
     * Consulta el mov reg suministro asociado al suministro pasado como paràmetro
     * @param suministro
     * @return
     * @throws Exception 
     */
    public ArrayList<MovRegsSco> consultarMovRegScoAsociado(MovSuministros suministro) throws Exception {
        ArrayList<MovRegsSco> movRegsScoList = new ArrayList<>();
        String sql = "SELECT * FROM MOV_REGS_SCO WHERE NNIS_RAD = ? and VCTIPO_LEC = ?";

        try (Connection con = databaseController.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            //NNIS_RAD y VCTIPO_LEC.            
            preparedStatement.setInt(1, suministro.getMovSuministrosPK().getNnisRad().intValue());
            preparedStatement.setString(2, suministro.getVctipoLec());
            ResultSet resultSet = preparedStatement.executeQuery();
            
            MovRegsSco movRegsSco;
            
            while (resultSet.next()) {
                movRegsSco = createMovLecConsuEntity(resultSet);
                movRegsScoList.add(movRegsSco);
            }

        } 
        return movRegsScoList;
    }

}
