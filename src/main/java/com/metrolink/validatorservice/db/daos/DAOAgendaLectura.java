/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.db.daos;

import com.metrolink.validatorservice.db.controller.IDatabaseController;
import com.metrolink.validatorservice.models.AgendaLectura;
import com.metrolink.validatorservice.models.AgendaLecturaPK;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Gabriel Ortega Rosero
 */
public class DAOAgendaLectura implements IDAOAgendaLectura {

    private final IDatabaseController databaseController;

    public DAOAgendaLectura(IDatabaseController databaseController) {
        this.databaseController = databaseController;
    }
    
    
    @Override
    public ArrayList<AgendaLectura> listAgenda() throws Exception {
        ArrayList<AgendaLectura>  listAgenda = new ArrayList<>();
        String sql = "SELECT * FROM AGENDA_LECTURA";
        
        try (Connection con = databaseController.getConnection()){
            
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            ResultSet result = preparedStatement.executeQuery();
            AgendaLectura agendaLectura;
            
            while (result.next()) {
                agendaLectura = createAgendaEntity(result);
                listAgenda.add(agendaLectura);
            }
            
        } catch (Exception ex) {
            throw new Exception("Error getting agenda data", ex);
        }
        
        
        return listAgenda;
    }

    private AgendaLectura createAgendaEntity(ResultSet result) throws SQLException {
        
        AgendaLectura agendaLectura = new AgendaLectura();
        
        AgendaLecturaPK agendaLecturaPK = new AgendaLecturaPK();
        agendaLecturaPK.setDfecha(result.getDate("DFECHA"));
        agendaLecturaPK.setNpericons(result.getInt("NPERICONS"));
        agendaLecturaPK.setVcparam(result.getString("VCPARAM"));
        
        agendaLectura.setAgendaLecturaPK(agendaLecturaPK);
        agendaLectura.setNunicom(result.getShort("NUNICOM"));
        agendaLectura.setVcciclo(result.getString("VCCICLO"));
        agendaLectura.setVcitinerario(result.getString("VCITINERARIO"));
        agendaLectura.setVcruta(result.getString("VCITINERARIO"));
        
        
        return agendaLectura;
    }
    
}
