/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.db.daos;

import com.metrolink.validatorservice.models.MovSuministros;
import java.util.ArrayList;

/**
 *
 * @author Gabriel Ortega
 */
public interface IDAOSuministros {
    boolean lockUnlockSuministros(ArrayList<MovSuministros> listSuministros, short lockStatus) throws Exception;    
    boolean actualizaCalendarioTou(MovSuministros suministro) throws Exception;
}
