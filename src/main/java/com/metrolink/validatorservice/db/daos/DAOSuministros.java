/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.db.daos;

import com.metrolink.validatorservice.db.controller.IDatabaseController;
import com.metrolink.validatorservice.models.MCalTou;
import com.metrolink.validatorservice.models.MJurisdicciones;
import com.metrolink.validatorservice.models.MMarcasmedidor;
import com.metrolink.validatorservice.models.MProveedores;
import com.metrolink.validatorservice.models.MTarifas;
import com.metrolink.validatorservice.models.MovLectConsu;
import com.metrolink.validatorservice.models.MovRegsSco;
import com.metrolink.validatorservice.models.MovSuministros;
import com.metrolink.validatorservice.models.MovSuministrosPK;
import com.metrolink.validatorservice.utils.Utils;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Gabriel Ortega
 */
public class DAOSuministros implements IDAOSuministros {

    private final IDatabaseController databaseController;
    public static final short BLOQUEADO = 1;
    public static final short DESBLOQUEADO = 0;

    public DAOSuministros(IDatabaseController databaseController) {
        this.databaseController = databaseController;
    }

    @Override
    public boolean lockUnlockSuministros(ArrayList<MovSuministros> listSuministros, short lockStatus) throws Exception {
        boolean result = true;
        int[] resultList;
        String sql = "UPDATE MOV_SUMINISTROS SET LBLOQUEADO = ? WHERE NCOD_PROV = ? AND NNIS_RAD = ? AND VCTIPO_ENERGIA = ?";

        try (Connection con = databaseController.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(sql);

            for (MovSuministros suministro : listSuministros) {
                preparedStatement.setShort(1, lockStatus);
                preparedStatement.setInt(2, suministro.getMovSuministrosPK().getNcodProv());
                preparedStatement.setLong(3, suministro.getMovSuministrosPK().getNnisRad().longValue());
                preparedStatement.setString(4, suministro.getMovSuministrosPK().getVctipoEnergia());
                preparedStatement.addBatch();
            }
            resultList = preparedStatement.executeBatch();
            for (int i : resultList) {
                result = result && (i >= 0);
            }
        }

        return result;
    }

    public static MovSuministros createMovSuministrosEntity(ResultSet resultSet) throws SQLException {
        MovSuministros movSuministros = null;

        if (Utils.doesColumnExist("NCOD_PROV", resultSet)) {
            movSuministros = new MovSuministros();
            MovSuministrosPK movSuministrosPK = new MovSuministrosPK();
            movSuministrosPK.setNcodProv(resultSet.getInt("NCOD_PROV"));
            movSuministrosPK.setNnisRad(BigInteger.valueOf(resultSet.getLong("NNIS_RAD")));
            movSuministrosPK.setVctipoEnergia(resultSet.getString("VCTIPO_ENERGIA"));
            movSuministros.setMovSuministrosPK(movSuministrosPK);

            movSuministros.setVcnumMed(resultSet.getString("VCNUM_MED"));
            movSuministros.setVctipoMed(resultSet.getString("VCTIPO_MED"));
            movSuministros.setVctipoLec(resultSet.getString("VCTIPO_LEC"));
            movSuministros.setVccentTec(resultSet.getString("VCCENT_TEC"));
            movSuministros.setTsful(resultSet.getDate("TSFUL"));
            movSuministros.setTsflt(resultSet.getDate("TSFLT"));
            movSuministros.setTsfla(resultSet.getDate("TSFLA"));
            movSuministros.setNnic(resultSet.getInt("NNIC"));
            movSuministros.setVctipoVal(resultSet.getString("VCTIPO_VAL"));
            movSuministros.setLestado(resultSet.getShort("LESTADO"));
            movSuministros.setVcnif(resultSet.getString("LESTADO"));
            movSuministros.setNunicom(BigInteger.valueOf(resultSet.getInt("NUNICOM")));
            movSuministros.setVcruta(resultSet.getString("VCRUTA"));
            movSuministros.setVcitinerario(resultSet.getString("VCITINERARIO"));
            movSuministros.setVcciclo(resultSet.getString("VCCICLO"));
            movSuministros.setNmulti(BigInteger.valueOf(resultSet.getInt("NMULTI")));
            movSuministros.setNulReportada(resultSet.getInt("NUL_REPORTADA"));

            MCalTou ncodCalTou = new MCalTou();
            ncodCalTou.setNcodCalTou(resultSet.getInt("NCOD_CAL_TOU"));
            movSuministros.setNcodCalTou(ncodCalTou);

            MJurisdicciones ncodJurisdiccion = new MJurisdicciones();
            ncodJurisdiccion.setNcodJurisdiccion(resultSet.getInt("NCOD_JURISDICCION"));
            movSuministros.setNcodJurisdiccion(ncodJurisdiccion);

            MMarcasmedidor vccodmarca = new MMarcasmedidor();
            vccodmarca.setVccodmarca(resultSet.getString("VCCODMARCA"));
            movSuministros.setVccodmarca(vccodmarca);

            MProveedores mProveedores = new MProveedores();
            mProveedores.setNcodProv(resultSet.getInt("NCOD_PROV"));
            movSuministros.setMProveedores(mProveedores);

            MTarifas vccodtarifa = new MTarifas();
            vccodtarifa.setVccodtarifa(resultSet.getString("VCCODTARIFA"));
            movSuministros.setVccodtarifa(vccodtarifa);

            movSuministros.setVccodtconsumo(resultSet.getString("VCCODTCONSUMO"));

            if (Utils.doesColumnExist("VCCOAN", resultSet)) { // Es una consulta de MOV_REGS_SCO
                MovRegsSco movRegsSco = DAORegsSco.createMovLecConsuEntity(resultSet);
                if (null != movRegsSco.getMovRegsScoPK() && null != movRegsSco.getMovRegsScoPK().getNnisRad()) {
                    movSuministros.getMovRegsScoCollection().add(movRegsSco);
                }
            }

            if (Utils.doesColumnExist("NLECTURA", resultSet)) {// Es una consulta de MOV_LECT_CONSU
                MovLectConsu lectConsu = DAOLecturas.createMovLecConsuEntity(resultSet);
                if (null != lectConsu.getId()) {
                    movSuministros.getMovLectConsuCollection().add(lectConsu);
                }
            }

        }
        return movSuministros;
    }

    @Override
    public boolean actualizaCalendarioTou(MovSuministros suministro) throws Exception {

        String sql = "UPDATE MOV_SUMINISTROS SET NCOD_CAL_TOU = ? WHERE NCOD_PROV = ? AND NNIS_RAD = ? AND VCTIPO_ENERGIA = ?";
        int result = 0;
        boolean resultado = false;
        try (Connection con = databaseController.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(sql);           
            preparedStatement.setInt(1, suministro.getNcodCalTou().getNcodCalTou());
            preparedStatement.setInt(2, suministro.getMovSuministrosPK().getNcodProv());
            preparedStatement.setLong(3, suministro.getMovSuministrosPK().getNnisRad().longValue());
            preparedStatement.setString(4, suministro.getMovSuministrosPK().getVctipoEnergia());
            result = preparedStatement.executeUpdate();

            if (result == 1) {
                resultado = true;
            }
        }

        return resultado;
    }

    @Override
    public MovSuministros buscaFechaPerseoPenultimaSuministro() throws Exception {
        MovSuministros suministro = new MovSuministros();
        String sql = "SELECT MAX(TSFLA) FECHA FROM MOV_SUMINISTROS WHERE TSFLA < (SELECT MAX(TSFLA) FROM MOV_SUMINISTROS)";

        try (Connection con = databaseController.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                suministro.setTsful(resultSet.getDate("FECHA"));
            }
        }

        return suministro;
    }
    
    @Override
    public MovSuministros buscaFechaOpenPenultimaLectura() throws Exception {
        MovSuministros suministro = new MovSuministros();
        String sql = "SELECT MAX(TSFUL) FECHA FROM MOV_SUMINISTROS WHERE TSFUL < (SELECT MAX(TSFUL) FROM MOV_SUMINISTROS)";

        try (Connection con = databaseController.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                suministro.setTsful(resultSet.getDate("FECHA"));
            }
        }

        return suministro;
    }

}
