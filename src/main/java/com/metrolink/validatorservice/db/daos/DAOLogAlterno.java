/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.db.daos;

import com.metrolink.validatorservice.db.controller.IDatabaseController;
import com.metrolink.validatorservice.models.MovLectConsu;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

/**
 *
 * @author Gabriel Ortega
 */
public class DAOLogAlterno implements IDAOLogAlterno {

    private final IDatabaseController databaseController;

    public DAOLogAlterno(IDatabaseController databaseController) {
        this.databaseController = databaseController;
    }

    @Override
    public boolean insertLecturas(ArrayList<MovLectConsu> lecturas) throws Exception {

        boolean result = false;
        String sql = "INSERT INTO MOV_LECT_CONSU ("
                + "CONS, "
                + "VCTIPO_VAL, "
                + "VCNIC, "
                + "VCNISRAD, "
                + "VCNUM_MED, "
                + "VCCODMARCA, "
                + "VCTIPO_LEC, "
                + "DFEC_REAL, "
                + "NLECTURA, "
                + "NCONSUMO_ORI, "
                + "NCONSUMO_MOD, "
                + "NCOD_PROV, "
                + "VCTIPO_RECHAZO, "
                + "VCCODUSER, "
                + "VCPROGRAMA, "
                + "TSFECHA_TRAN, "
                + "NID_CAUSA_RECHAZO) VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)";

        try (Connection con = databaseController.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            
            for (MovLectConsu lectura : lecturas) {
                preparedStatement.setBigDecimal(1, lectura.getId());
                preparedStatement.setInt(2, lectura.getTipoLectura);
                preparedStatement.setString(3, lectura.getVcnic());
                preparedStatement.setString(4, lectura.getNnisRad());
                preparedStatement.setString(5, lectura.getVcnumMed());
                preparedStatement.setString(6, lectura.getVccodmarca());
                preparedStatement.setString(7, lectura.getVctipoLec());
                preparedStatement.setDate(8, new java.sql.Date(lectura.getTsfechaLec().getTime()));
                preparedStatement.setInt(9, lectura.getNlectura());
                preparedStatement.setBigDecimal(10, lectura.getNconsumoOri());
                preparedStatement.setBigDecimal(11, lectura.getNconsumoMod());
                preparedStatement.setInt(12, lectura.getNcodProv());
                preparedStatement.setString(13, lectura.getCausasRechazo().getVadescripcion());
                preparedStatement.setString(14, lectura.getVccoduser());
                preparedStatement.setString(15, lectura.getVcprograma());
                preparedStatement.setDate(16, new java.sql.Date(lectura.getTsfechaTran().getTime()));
                preparedStatement.setBigDecimal(17, lectura.getCausasRechazo().getNidCausaRechazo());
                
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
            result = true;

        }
        return result;
    }

}
