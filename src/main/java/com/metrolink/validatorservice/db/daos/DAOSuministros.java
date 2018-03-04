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
                preparedStatement.addBatch();
            }
            resultList  = preparedStatement.executeBatch();
            for (int i : resultList) {
                result = result && (i>=0);
            }
        } 
        
        return result;
    }
    
    public static MovSuministros createSuministrosEntity(ResultSet result) throws SQLException{
        MovSuministros suministro = new MovSuministros();
        
        MProveedores mProveedores = new MProveedores();
        mProveedores.setNcodProv(result.getInt("NCOD_PROV"));
        suministro.setMProveedores(mProveedores);
        
        MovSuministrosPK movSuministrosPK = new MovSuministrosPK();
        movSuministrosPK.setNnisRad(BigInteger.valueOf(result.getLong("NNIS_RAD")));
        movSuministrosPK.setNcodProv(result.getInt("NCOD_PROV"));
        movSuministrosPK.setVccodtconsumo(result.getString("VCCODTCONSUMO"));
        suministro.setMovSuministrosPK(movSuministrosPK);
        
        suministro.setVcnumMed(result.getString("VCNUM_MED"));
        suministro.setVctipoMed(result.getString("VCTIPO_MED")); 
        MMarcasmedidor mMarcasmedidor = new MMarcasmedidor();
        mMarcasmedidor.setVccodmarca(result.getString("VCCODMARCA"));
        suministro.setVccodmarca(mMarcasmedidor);
        
        suministro.setVctipoLec(result.getString("VCTIPO_LEC")); 
        suministro.setVccentTec(result.getString("VCCENT_TEC"));
        
        MCalTou mCalTou = new MCalTou();
        mCalTou.setNcodCalTou(result.getInt("NCOD_CAL_TOU"));
        suministro.setTsful(result.getTimestamp("TSFUL")); 
        suministro.setTsfla(result.getTimestamp("TSFLA")); 
        
        suministro.setNnic(result.getInt("NNIC"));
        MJurisdicciones mJurisdicciones = new MJurisdicciones();
        mJurisdicciones.setNcodJurisdiccion(result.getInt("NCOD_JURISDICCION"));
        suministro.setNcodJurisdiccion(mJurisdicciones);
        
        suministro.setVctipoVal(result.getString("VCTIPO_VAL"));
        suministro.setLestado(result.getShort("LESTADO")); 
        
        MTarifas mTarifas = new MTarifas();
        mTarifas.setVccodtarifa(result.getString("VCCODTARIFA"));
        suministro.setVcnif(result.getString("VCNIF")); 
        suministro.setNunicom(BigInteger.valueOf(result.getInt("NUNICOM"))); 
        suministro.setVcruta(result.getString("VCRUTA")); 
        suministro.setVcitinerario(result.getString("VCITINERARIO")); 
        suministro.setVcciclo(result.getString("VCCICLO")); 
        suministro.setVctipoEnergia(result.getString("VCTIPO_ENERGIA")); 
        suministro.setNnumRegs(result.getShort("NNUM_REGS")); 
        suministro.setLbloqueado(result.getShort("LBLOQUEADO")); 
        suministro.setTsflt(result.getTimestamp("TSFLT"));
        
        return suministro;
    }
    
}
