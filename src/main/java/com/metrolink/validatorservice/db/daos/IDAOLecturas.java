/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.db.daos;

import com.metrolink.validatorservice.models.MovLectConsu;
import java.util.ArrayList;

/**
 *
 * @author Gabriel Ortega
 */
public interface IDAOLecturas {
    ArrayList<MovLectConsu> getLecturasNoValidadas()  throws Exception;
    MovLectConsu getLecturaForUpdate(String id)  throws Exception;
    boolean lockUnlockLecturas(ArrayList<MovLectConsu> listLecturas, short LockStatus) throws Exception;
}
