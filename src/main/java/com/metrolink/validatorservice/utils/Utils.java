/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.utils;

import com.metrolink.validatorservice.models.MovSuministrosPK;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 * @author Gabriel Ortega
 */
public class Utils {

    public static Date addDays(Date date, int days) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }

    public static boolean doesColumnExist(String columnName, ResultSet rs) throws SQLException {
        ResultSetMetaData meta = rs.getMetaData();
        int numCol = meta.getColumnCount();
        for (int i = 1; i <= numCol; i++) {
            if (meta.getColumnName(i).equalsIgnoreCase(columnName)) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean esItinerarioValido(MovSuministrosPK movSuministrosPK, List<MovSuministrosPK> suministrosInvalidos) {
        boolean itinerarioValido = true;
        for (MovSuministrosPK suministrosInvalido : suministrosInvalidos) {
            String tipoEnergia = movSuministrosPK.getVctipoEnergia();
            int codProv = movSuministrosPK.getNcodProv();
            BigInteger nnisRad = movSuministrosPK.getNnisRad();
            if (tipoEnergia.equals(suministrosInvalido.getVctipoEnergia())
                    && codProv == suministrosInvalido.getNcodProv()
                    && nnisRad.compareTo(suministrosInvalido.getNnisRad()) == 0) {

                itinerarioValido = false;
            }
        }
        return itinerarioValido;
    }
}
