/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.db.daos;

import com.metrolink.validatorservice.models.MConfVal;
import com.metrolink.validatorservice.models.MovSuministros;
import java.util.ArrayList;

/**
 *
 * @author USUARIO
 */
public interface IDAOParametrosConf {
    public MConfVal getParametrosConf(MovSuministros suministro) throws Exception;
}
