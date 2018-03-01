/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.db.daos;

import com.metrolink.validatorservice.db.controller.IDatabaseController;
import com.metrolink.validatorservice.models.MParametrosAdm;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Gabriel Ortega
 */
public class DAOParametrosAdmin implements IDAOParametrosAdmin {

    
    private final IDatabaseController databaseController;
    
    public DAOParametrosAdmin (IDatabaseController databaseController){
        this.databaseController = databaseController;
    }
    
    @Override
    public ArrayList<MParametrosAdm> getParametrosAdm() throws Exception {
        
        ArrayList<MParametrosAdm> listaParametrosAdms = new ArrayList();
        String sql  = "SELECT * FROM M_PARAMETROS_ADM";

        try (Connection con = databaseController.getConnection()){
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            MParametrosAdm parametrosAdm;
            
            while (resultSet.next()) {
                parametrosAdm = createParametrosAdminEntity(resultSet);
                listaParametrosAdms.add(parametrosAdm);
            }
        }
        
        return listaParametrosAdms;
    }

    private MParametrosAdm createParametrosAdminEntity(ResultSet resultSet) throws SQLException {
        MParametrosAdm parametrosAdm = new MParametrosAdm();
        
        parametrosAdm.setLreqAutentic(resultSet.getShort("LREQ_AUTENTIC"));
        parametrosAdm.setNdiasAnt(resultSet.getShort("NDIAS_ANT"));
        parametrosAdm.setNdiasAsigAno(resultSet.getShort("NDIAS_ASIG_ANO"));
        parametrosAdm.setNidParametro(resultSet.getBigDecimal("NID_PARAMETRO"));
        parametrosAdm.setNtolCompletud(resultSet.getShort("NTOL_COMPLETUD"));
        parametrosAdm.setNvpCompletud(resultSet.getShort("NVP_COMPLETUD"));
        parametrosAdm.setVcemailEnvio(resultSet.getString("VCEMAIL_ENVIO"));
        parametrosAdm.setVcemailRemit(resultSet.getString("VCEMAIL_REMIT"));
        parametrosAdm.setVcpassCorreo(resultSet.getString("VCPASS_CORREO"));
        parametrosAdm.setVcrutaArchivo(resultSet.getString("VCRUTA_ARCHIVO"));
        parametrosAdm.setVcservidorEmail(resultSet.getString("VCSERVIDOR_EMAIL"));
        parametrosAdm.setVcuser(resultSet.getString("VCUSER"));
        parametrosAdm.setNdiasBusca(resultSet.getShort("NDIAS_BUSCA"));

        return parametrosAdm;
    }
    
}
