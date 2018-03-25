/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.db.daos;

import com.metrolink.validatorservice.models.AgendaLectura;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Gabriel Ortega
 */
public interface IDAOAgendaLectura {
    ArrayList<AgendaLectura> listAgendaBetweenDates(Date startingDate, Date endingDate, int tipoConsulta) throws Exception;
}
