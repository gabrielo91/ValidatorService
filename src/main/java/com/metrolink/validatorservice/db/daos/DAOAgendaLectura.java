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
import com.metrolink.validatorservice.models.MovRegsSco;
import com.metrolink.validatorservice.models.MovSuministros;
import com.metrolink.validatorservice.models.MovSuministrosPK;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Gabriel Ortega Rosero
 */
public class DAOAgendaLectura implements IDAOAgendaLectura {

    public final static int CONSULTA_MOV_LECT_CONSU = 0;
    public final static int CONSULTA_MOV_REGS_SCO = 1;
    private final IDatabaseController databaseController;

    public DAOAgendaLectura(IDatabaseController databaseController) {
        this.databaseController = databaseController;
    }

    /**
     * Retorna un arreglo den agendas con los suministros y lecturas asociadas.
     *
     * @param startingDate
     * @param endingDate
     * @param tipoConsulta
     * @return
     * @throws Exception
     */
    @Override
    public ArrayList<AgendaLectura> listAgendaBetweenDates(Date startingDate, Date endingDate, int tipoConsulta) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("DATES ARE: " + sdf.format(startingDate.getTime()) + " AND " + sdf.format(endingDate.getTime()));

        ArrayList<AgendaLectura> listAgenda = new ArrayList<>();
        String sqlMovLectConsumo = "SELECT  AL.*,  MSUM.*, MLEC.*  FROM AGENDA_LECTURA AL  \n"
                + "LEFT OUTER JOIN MOV_SUMINISTROS MSUM\n"
                + "ON AL.NUNICOM = MSUM.NUNICOM \n"
                + "AND AL.VCITINERARIO = MSUM.VCITINERARIO \n"
                + "AND AL.VCRUTA = MSUM.VCRUTA\n"
                + "AND AL.VCCICLO = MSUM.VCCICLO\n"
                + "LEFT JOIN MOV_LECT_CONSU MLEC\n"
                + "ON ((MLEC.NNIC = MSUM.NNIC AND MLEC.NNIS_RAD IS NULL) \n"
                + "OR (MLEC.NNIS_RAD = MSUM.NNIS_RAD AND MLEC.NNIC IS NULL)\n"
                + "OR (MLEC.NNIC = MSUM.NNIC AND MLEC.NNIS_RAD = MSUM.NNIS_RAD))\n"
                + "WHERE AL.DFECHA_TEO BETWEEN to_date(?,'dd/MM/yyyy') AND to_date(?,'dd/MM/yyyy')\n"
                + "ORDER BY AL.DFECHA_TEO, AL.VCPARAM, AL.NPERICONS";

        String sqlMovRegsSco = "SELECT  AL.*,  MSUM.*, MRSCO.*  FROM AGENDA_LECTURA AL "
                + "LEFT OUTER JOIN MOV_SUMINISTROS MSUM "
                + "ON AL.NUNICOM = MSUM.NUNICOM "
                + "AND AL.VCITINERARIO = MSUM.VCITINERARIO "
                + "AND AL.VCRUTA = MSUM.VCRUTA "
                + "LEFT JOIN MOV_REGS_SCO MRSCO "
                + "ON MRSCO.NNIS_RAD = MSUM.NNIS_RAD "
                + "WHERE AL.DFECHA_TEO BETWEEN to_date(?,'dd/MM/yyyy') AND to_date(?,'dd/MM/yyyy') "
                + "ORDER BY AL.DFECHA_TEO, AL.VCPARAM,  AL.NPERICONS";

        try (Connection con = databaseController.getConnection()) {

            PreparedStatement preparedStatement;
            ResultSet result = null;
            System.out.println("********************* tipo consulta = > " + tipoConsulta);
            if (tipoConsulta == CONSULTA_MOV_LECT_CONSU) {
                preparedStatement = con.prepareStatement(sqlMovLectConsumo);
                preparedStatement.setString(1, sdf.format(startingDate.getTime()));
                preparedStatement.setString(2, sdf.format(endingDate.getTime()));
                result = preparedStatement.executeQuery();
                listAgenda = mapRows(result, CONSULTA_MOV_LECT_CONSU);

            } else {
                System.out.println("********************* ENTRO EN ELSE " + tipoConsulta);
                preparedStatement = con.prepareStatement(sqlMovRegsSco);
                preparedStatement.setString(1, sdf.format(startingDate.getTime()));
                preparedStatement.setString(2, sdf.format(endingDate.getTime()));
                result = preparedStatement.executeQuery();
                listAgenda = mapRows(result, CONSULTA_MOV_REGS_SCO);

            }

        } catch (Exception ex) {
            throw new Exception("Error getting agenda data", ex);
        }

        return listAgenda;
    }

    private ArrayList<AgendaLectura> mapRows(ResultSet result, int tipoConsulta) throws SQLException {

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

        int contador = 0;

        while (result.next()) {
            contador = contador + 1;
            long npericonsCurr = result.getLong("NPERICONS");;
            Date dfechaTeoCurr = result.getDate("DFECHA_TEO");;
            String vcparamCurr = result.getString("VCPARAM");
            AgendaLecturaPK agendaLecturaPKCurr = new AgendaLecturaPK(npericonsCurr, dfechaTeoCurr, vcparamCurr);
            //System.err.println("VCINTINERARIO ES: "+result.getString("VCITINERARIO"));

            int ncodProvCurr = -1;
            BigInteger nnisRadCurr = null;
            String vccodtconsumoCurr = null;
            MovSuministrosPK suministrosPKCurr = null;
            MovSuministros suministro;

            if (result.getBigDecimal("NNIS_RAD") != null) {
                ncodProvCurr = result.getInt("NCOD_PROV");
                nnisRadCurr = result.getBigDecimal("NNIS_RAD").toBigInteger();
                vccodtconsumoCurr = result.getString("VCTIPO_ENERGIA");
                suministrosPKCurr = new MovSuministrosPK(ncodProvCurr, nnisRadCurr, vccodtconsumoCurr);
            }

            //Iterates over each distinc row of AgendaLectura
            if (null == agendaLecturaPKPrev || !agendaLecturaPKPrev.equals(agendaLecturaPKCurr)) {
                agendaLectura = createAgendaEntity(result);
                suministro = null;
                suministrosPKCurr = null;
                movSuministrosPKPrev = null;
                listAgenda.add(agendaLectura);

            } else if (agendaLecturaPKPrev.equals(agendaLecturaPKCurr)) {

                //Iterates over each distinc row of Suministros
                if (null == movSuministrosPKPrev || !movSuministrosPKPrev.equals(suministrosPKCurr)) {
                    suministro = DAOSuministros.createMovSuministrosEntity(result);
                    agendaLectura.getListaSuministros().add(suministro);

                } else if (movSuministrosPKPrev.equals(suministrosPKCurr)) {
                    System.out.println("MAPROWS TIPO CONSULTA " + tipoConsulta);
                    int lastElementIndex = agendaLectura.getListaSuministros().size() - 1;
                    if (tipoConsulta == CONSULTA_MOV_LECT_CONSU) {
                        MovLectConsu movLectConsu = DAOLecturas.createMovLecConsuEntity(result);
                        agendaLectura.getListaSuministros().get(lastElementIndex).getMovLectConsuCollection().add(movLectConsu);
                    } else {
                        System.out.println("Entro en else maprows " + tipoConsulta);
                        MovRegsSco movRegsSco = DAORegsSco.createMovLecConsuEntity(result);
                        agendaLectura.getListaSuministros().get(lastElementIndex).getMovRegsScoCollection().add(movRegsSco);
                    }

                }

                ncodProvPrev = ncodProvCurr;
                nnisRadPrev = nnisRadCurr;
                vccodtconsumoPrev = vccodtconsumoCurr;
                movSuministrosPKPrev = new MovSuministrosPK(ncodProvPrev, nnisRadPrev, vccodtconsumoPrev);
            }

            ncodProvPrev = ncodProvCurr;
            nnisRadPrev = nnisRadCurr;
            vccodtconsumoPrev = vccodtconsumoCurr;
            movSuministrosPKPrev = new MovSuministrosPK(ncodProvPrev, nnisRadPrev, vccodtconsumoPrev);

            npericonsPrev = npericonsCurr;
            dfechaTeoPrev = new Date(dfechaTeoCurr.getTime());
            vcparamPrev = vcparamCurr;
            agendaLecturaPKPrev = new AgendaLecturaPK(npericonsPrev, dfechaTeoPrev, vcparamPrev);

        }
        System.out.println("contador es: " + contador);
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

        if (null != movSuministros && movSuministros.getMovSuministrosPK().getNcodProv() >= 0
                && null != movSuministros.getMovSuministrosPK().getNnisRad()
                && null != movSuministros.getMovSuministrosPK().getVctipoEnergia()) {
            agendaLectura.getListaSuministros().add(movSuministros);
        }

        return agendaLectura;
    }

    public ArrayList<AgendaLectura> getAgendaAsociada(MovSuministros movSuministros) throws Exception {
        ArrayList<AgendaLectura> listaAgendas = new ArrayList<>();
        String sql = "SELECT * FROM AGENDA_LECTURA WHERE VCITINERARIO = ? AND VCRUTA = ? AND VCCICLO = ? AND NUNICOM = ?";

        try (Connection con = databaseController.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(sql);

            preparedStatement.setString(1, movSuministros.getVcitinerario());
            preparedStatement.setString(2, movSuministros.getVcruta());
            preparedStatement.setString(3, movSuministros.getVcciclo());
            preparedStatement.setInt(4, movSuministros.getNunicom().intValue());
            ResultSet resultSet = preparedStatement.executeQuery();

            AgendaLectura agenda;

            while (resultSet.next()) {
                agenda = createAgendaEntity(resultSet);
                listaAgendas.add(agenda);
            }

        }

        return listaAgendas;
    }

}
