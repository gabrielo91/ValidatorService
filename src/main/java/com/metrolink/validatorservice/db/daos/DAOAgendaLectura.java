/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.db.daos;

import com.metrolink.validatorservice.db.controller.IDatabaseController;
import com.metrolink.validatorservice.models.AgendaLectura;
import com.metrolink.validatorservice.models.AgendaLecturaPK;
import com.metrolink.validatorservice.models.MovLectConsu;
import com.metrolink.validatorservice.models.MovSuministros;
import com.metrolink.validatorservice.models.MovSuministrosPK;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

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
    public ArrayList<AgendaLectura> listAgendaBetweenDates(Date startingDate, Date endingDate) throws Exception {
        System.out.println("DATES ARE: "+startingDate.toString() + " AND "+endingDate.toString());
        
        ArrayList<AgendaLectura> listAgenda = new ArrayList<>();
        String sql = "SELECT  AL.*,  MSUM.*, MLEC.*  FROM AGENDA_LECTURA AL  \n"
                + "LEFT OUTER JOIN MOV_SUMINISTROS MSUM\n"
                + "ON AL.NUNICOM = MSUM.NUNICOM \n"
                + "AND AL.VCITINERARIO = MSUM.VCITINERARIO \n"
                + "AND AL.VCRUTA = MSUM.VCRUTA\n"
                + "LEFT JOIN MOV_LECT_CONSU MLEC\n"
                + "ON MLEC.NCOD_PROV = MSUM.NCOD_PROV\n"
                + "AND MLEC.NNIC = MSUM.NNIC\n"
                + "AND MLEC.NNIS_RAD = MSUM.NNIS_RAD \n"
                + "WHERE AL.DFECHA_TEO BETWEEN ? AND ?\n"
                + "ORDER BY AL.VCPARAM, AL.DFECHA_TEO, AL.NPERICONS";

        try (Connection con = databaseController.getConnection()) {
            
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setDate(1, new java.sql.Date(startingDate.getTime()));
            preparedStatement.setDate(2, new java.sql.Date(endingDate.getTime()));
            ResultSet result = preparedStatement.executeQuery();
            listAgenda = mapRows(result);

        } catch (Exception ex) {
            throw new Exception("Error getting agenda data", ex);
        }

        return listAgenda;
    }

    private ArrayList<AgendaLectura> mapRows(ResultSet result) throws SQLException {
        
        //Agenda
        ArrayList<AgendaLectura> listAgenda = new ArrayList<>();
        AgendaLecturaPK agendaLecturaPKPrev = null;
        long npericonsPrev;
        Date dfechaTeoPrev;
        String vcparamPrev;

        //Suministros
        MovSuministrosPK movSuministrosPKPrev = null;
        int ncodProvPrev; 
        BigInteger nnisRadPrev; 
        String vccodtconsumoPrev;        
        AgendaLectura agendaLectura = new AgendaLectura();
        
        while (result.next()) {
            long npericonsCurr = result.getLong("NPERICONS");;
            Date dfechaTeoCurr = result.getDate("DFECHA_TEO");;
            String vcparamCurr = result.getString("VCPARAM");
            AgendaLecturaPK agendaLecturaPKCurr = new AgendaLecturaPK(npericonsCurr, dfechaTeoCurr, vcparamCurr);

 
            int ncodProvCurr; 
            BigInteger nnisRadCurr; 
            String vccodtconsumoCurr;
            MovSuministrosPK suministrosPKCurr;
            MovSuministros suministro;
            
            //Iterates over each distinc row of AgendaLectura
            if(null == agendaLecturaPKPrev || !agendaLecturaPKPrev.equals(agendaLecturaPKCurr)){
               
                agendaLectura = createAgendaEntity(result);    
                suministro = null;
                suministrosPKCurr = null;
                movSuministrosPKPrev = null;
                
            } else if (agendaLecturaPKPrev.equals(agendaLecturaPKCurr)){
                
                ncodProvCurr = result.getInt("NCOD_PROV"); 
                nnisRadCurr = result.getBigDecimal("NNIS_RAD").toBigInteger(); 
                vccodtconsumoCurr = result.getString("VCCODTCONSUMO"); 
                suministrosPKCurr = new MovSuministrosPK(ncodProvCurr, nnisRadCurr, vccodtconsumoCurr);               
                
                //Iterates over each distinc row of Suministros
                if (null == movSuministrosPKPrev || !movSuministrosPKPrev.equals(suministrosPKCurr)){
                    suministro = DAOSuministros.createMovSuministrosEntity(result);
                    agendaLectura.getListaSuministros().add(suministro);
                } else if (movSuministrosPKPrev.equals(suministrosPKCurr)){
                                        
                    MovLectConsu movLectConsu = DAOLecturas.createMovLecConsuEntity(result);   
                    int lastElementIndex = agendaLectura.getListaSuministros().size()-1;
                    agendaLectura.getListaSuministros().get(lastElementIndex).getMovLectConsuCollection().add(movLectConsu);
                
                }

                ncodProvPrev = ncodProvCurr; 
                nnisRadPrev = nnisRadCurr; 
                vccodtconsumoPrev = vccodtconsumoCurr;
                movSuministrosPKPrev = new MovSuministrosPK(ncodProvPrev, nnisRadPrev, vccodtconsumoPrev);                
            }
            
            npericonsPrev = npericonsCurr;
            dfechaTeoPrev = new Date(dfechaTeoCurr.getTime());
            vcparamPrev = vcparamCurr;
            agendaLecturaPKPrev = new AgendaLecturaPK(npericonsPrev, dfechaTeoPrev, vcparamPrev);
            listAgenda.add(agendaLectura);
        }
        return listAgenda;
    }

    private AgendaLectura createAgendaEntity(ResultSet result) throws SQLException {

        AgendaLectura agendaLectura = new AgendaLectura();
        AgendaLecturaPK agendaLecturaPK = new AgendaLecturaPK();
        agendaLecturaPK.setDfechaTeo(result.getDate("DFECHA_TEO"));
        agendaLecturaPK.setNpericons(result.getInt("NPERICONS"));
        agendaLecturaPK.setVcparam(result.getString("VCPARAM"));
        agendaLectura.setAgendaLecturaPK(agendaLecturaPK);
        agendaLectura.setNunicom(result.getShort("NUNICOM"));
        agendaLectura.setVcciclo(result.getString("VCCICLO"));
        agendaLectura.setVcitinerario(result.getString("VCITINERARIO"));
        agendaLectura.setVcruta(result.getString("VCRUTA"));
        MovSuministros movSuministros = DAOSuministros.createMovSuministrosEntity(result);
        agendaLectura.getListaSuministros().add(movSuministros);
        return agendaLectura;
    }


}
