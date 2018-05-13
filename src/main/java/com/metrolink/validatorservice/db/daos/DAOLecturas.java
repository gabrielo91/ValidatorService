/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.db.daos;

import com.metrolink.validatorservice.db.controller.IDatabaseController;
import com.metrolink.validatorservice.models.MovLectConsu;
import java.math.BigDecimal;
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
public class DAOLecturas implements IDAOLecturas {

    static final int LECTURA_NO_VALIDADA = 0;
    static final int LECTURA_VALIDA = 1;
    static final int LECTURA_NO_VALIDA = 2;
    public static final short BLOQUEADO = 1;
    public static final short DESBLOQUEADO = 0;

    private final IDatabaseController databaseController;

    public DAOLecturas(IDatabaseController databaseController) {
        this.databaseController = databaseController;
    }

    @Override
    public MovLectConsu getLecturaForUpdate(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<MovLectConsu> getLecturasNoValidadas() throws Exception {

        ArrayList<MovLectConsu> lecturas = new ArrayList();
        String sql = "SELECT MLC.*, MS.* FROM MOV_LECT_CONSU MLC "
                + "JOIN MOV_SUMINISTROS MS "
                + "ON MLC.NCOD_PROV = MS.NCOD_PROV "
                + "AND  MLC.NNIS_RAD = MS.NNIS_RAD "
                + "AND  MLC.VCTIPO_LEC = MS.VCTIPO_LEC "
                + "AND MLC.LCERTIFICADA = ?";

        try (Connection con = databaseController.getConnection()) {

            System.out.println("sql: "+sql);
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, LECTURA_NO_VALIDADA);
            ResultSet resultSet = preparedStatement.executeQuery();
            MovLectConsu lectura;

            while (resultSet.next()) {
                lectura = createMovLecConsuEntity(resultSet);
                lecturas.add(lectura);

            }

            System.out.println("el tamano es: " + lecturas.size());

        } catch (Exception ex) {
            throw new Exception("Error getting readings", ex);
        }

        return lecturas;
    }

    public static MovLectConsu createMovLecConsuEntity(ResultSet resultSet) throws SQLException {
        MovLectConsu lectura = new MovLectConsu();
        lectura.setId(resultSet.getBigDecimal("ID"));
        lectura.setNnisRad(BigInteger.valueOf(resultSet.getLong("NNIS_RAD")));
        lectura.setNcodProv(resultSet.getInt("NCOD_PROV"));
        lectura.setVccodtconsumo(resultSet.getString("VCCODTCONSUMO"));
        lectura.setNnic(BigInteger.valueOf(resultSet.getLong("NNIC")));
        lectura.setLbloqueado(resultSet.getShort("LBLOQUEADO"));
        lectura.setLcertificada(resultSet.getShort("LCERTIFICADA"));
        lectura.setLenviado(resultSet.getShort("LENVIADO"));
        lectura.setLgenAlarma(resultSet.getShort("LGEN_ALARMA"));
        lectura.setNconsProceso(resultSet.getInt("NCONS_PROCESO"));
        lectura.setNconsumoMod(resultSet.getBigDecimal("NCONSUMO_MOD"));
        lectura.setNconsumoOri(resultSet.getBigDecimal("NCONSUMO_ORI"));
        lectura.setNlectura(BigDecimal.valueOf(resultSet.getInt("NLECTURA")));
        lectura.setTsfechaLec(resultSet.getDate("TSFECHA_LEC"));
        lectura.setTsfechaTran(resultSet.getDate("TSFECHA_TRAN"));
        lectura.setVccodmarca(resultSet.getString("VCCODMARCA"));
        lectura.setVccoduser(resultSet.getString("VCCODUSER"));
        lectura.setVcnumMed(resultSet.getString("VCNUM_MED"));
        lectura.setVcprograma(resultSet.getString("VCPROGRAMA"));
        lectura.setVctipoLec(resultSet.getString("VCTIPO_LEC"));
        lectura.setVctipoMed(resultSet.getString("VCTIPO_MED"));
        return lectura;
    }

    

    @Override
    public boolean lockUnlockLecturas(ArrayList<MovLectConsu> listLecturas, short lockStatus) throws Exception {
        boolean result =  true;
        int [] resultList;
        String sql = "UPDATE MOV_LECT_CONSU SET LBLOQUEADO = ? WHERE ID = ?";
        
        try (Connection con = databaseController.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            
            for (MovLectConsu lectura : listLecturas) {
                preparedStatement.setShort(1, lockStatus);
                preparedStatement.setLong(2, lectura.getId().longValue());
                preparedStatement.addBatch();
            }
            resultList  = preparedStatement.executeBatch();
            for (int i : resultList) {
                result = result && (i>=0);
            }
        } 
        return result;
    }
}
