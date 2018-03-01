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
                + "AND  MLC.VCCODTCONSUMO = MS.VCCODTCONSUMO "
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

    private MovLectConsu createMovLecConsuEntity(ResultSet resultSet) throws SQLException {
        MovLectConsu lectura = new MovLectConsu();

        lectura.setId(resultSet.getBigDecimal("ID"));
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

        MovSuministros movSuministros = createMovSuministrosEntity(resultSet);
        lectura.setMovSuministros(movSuministros);
        return lectura;
    }

    private MovSuministros createMovSuministrosEntity(ResultSet resultSet) throws SQLException {
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

        return movSuministros;
    }
}
