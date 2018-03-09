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
import com.metrolink.validatorservice.models.MTipoconsumo;
import com.metrolink.validatorservice.models.MovLectConsu;
import com.metrolink.validatorservice.models.MovSuministros;
import com.metrolink.validatorservice.models.MovSuministrosPK;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Gabriel Ortega
 */
public class DAOSuministros implements IDAOSuministros{

    private final IDatabaseController databaseController;
    public static final short BLOQUEADO = 1;
    public static final short DESBLOQUEADO = 0;

    public DAOSuministros(IDatabaseController databaseController) {
        this.databaseController = databaseController;
    }
    
    
    @Override
    public boolean lockUnlockSuministros(ArrayList<MovSuministros> listSuministros, short lockStatus) throws Exception {
        boolean result =  true;
        int [] resultList;
        String sql = "UPDATE MOV_SUMINISTROS SET LBLOQUEADO = ? WHERE NCOD_PROV = ? AND NNIS_RAD = ? AND VCCODTCONSUMO = ?";
        
        try (Connection con = databaseController.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            
            for (MovSuministros suministro : listSuministros) {
                preparedStatement.setShort(1, lockStatus);
                preparedStatement.setInt(2, suministro.getMovSuministrosPK().getNcodProv());
                preparedStatement.setLong(3, suministro.getMovSuministrosPK().getNnisRad().longValue());
                preparedStatement.setString(4, suministro.getMovSuministrosPK().getVccodtconsumo());
                preparedStatement.addBatch();
            }
            resultList  = preparedStatement.executeBatch();
            for (int i : resultList) {
                result = result && (i>=0);
            }
        } 
        
        return result;
    }
    
    public static MovSuministros createMovSuministrosEntity(ResultSet resultSet) throws SQLException {
        MovSuministros movSuministros = new MovSuministros();

        MovSuministrosPK movSuministrosPK = new MovSuministrosPK();
        movSuministrosPK.setNcodProv(resultSet.getInt("NCOD_PROV"));
        movSuministrosPK.setNnisRad(BigInteger.valueOf(resultSet.getLong("NNIS_RAD")));
        movSuministrosPK.setVccodtconsumo(resultSet.getString("VCCODTCONSUMO"));
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
        movSuministros.setVctipoEnergia(resultSet.getString("VCTIPO_ENERGIA"));
        
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
        
        MTipoconsumo mTipoconsumo = new MTipoconsumo();
        mTipoconsumo.setVccodtconsumo(resultSet.getString("VCCODTCONSUMO"));
        movSuministros.setMTipoconsumo(mTipoconsumo);
        
        MovLectConsu lectConsu = DAOLecturas.createMovLecConsuEntity(resultSet);

        if (null != lectConsu.getId()) {
            movSuministros.getMovLectConsuCollection().add(lectConsu);
        }       
        
        return movSuministros;
    }
    
}
