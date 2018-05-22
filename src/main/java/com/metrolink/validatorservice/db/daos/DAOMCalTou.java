/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.db.daos;

import com.metrolink.validatorservice.db.controller.IDatabaseController;
import com.metrolink.validatorservice.logger.DataLogger;
import com.metrolink.validatorservice.models.MovCalTou;
import com.metrolink.validatorservice.models.MovCalTouPK;
import com.metrolink.validatorservice.models.MovSuministros;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Camilo Ramirez
 */
public class DAOMCalTou {

    private final IDatabaseController databaseController;

    public DAOMCalTou(IDatabaseController databaseController) {
        this.databaseController = databaseController;
    }

    public MovCalTou buscaCalendarioTou(MovSuministros suministro) throws Exception {
        MovCalTou calendario = new MovCalTou();

        String sql = "SELECT * FROM MOV_CAL_TOU WHERE NCOD_TIPO_TARIFA in (select NCOD_TIPO_TARIFA from m_tarifas where VCCODTARIFA = ?) AND VCCODTCONSUMO = ?";

        try (Connection con = databaseController.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(sql);            
            preparedStatement.setString(1, suministro.getVccodtarifa().getVccodtarifa());
            preparedStatement.setString(2, suministro.getVccodtconsumo());

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                MovCalTouPK calendarioPK = new MovCalTouPK();
                calendarioPK.setNcodCalTou(resultSet.getInt("NCOD_CAL_TOU"));
                calendarioPK.setNfinFranja(resultSet.getShort("NINI_FRANJA"));
                calendarioPK.setNiniFranja(resultSet.getShort("NFIN_FRANJA"));
                calendarioPK.setVctipoEnergia(resultSet.getString("VCTIPO_ENERGIA"));
                calendario.setMovCalTouPK(calendarioPK);
                calendario.setNcodTipoTarifa(resultSet.getInt("NCOD_TIPO_TARIFA"));
                calendario.setVccodtconsumo(resultSet.getString("VCCODTCONSUMO"));
                calendario.setLestado(resultSet.getShort("LESTADO"));

            }
        }catch (Exception e) {
            String mensaje = "Error actualizando Calendario del Suministro";
            DataLogger.Log(e, mensaje);
            throw new Exception(mensaje);
        }
        return calendario;
    }
}
