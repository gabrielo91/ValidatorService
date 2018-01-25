/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.db.daos;

import com.metrolink.validatorservice.models.MovAlarmas;

/**
 *
 * @author Gabriel Ortega
 */
public interface IDAOAlarmas {
    void insertAlarma(MovAlarmas alarma)throws Exception;
}
