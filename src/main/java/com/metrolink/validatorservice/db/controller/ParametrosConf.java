/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.db.controller;

import com.metrolink.validatorservice.models.MConfVal;

/**
 *
 * @author Gabriel Ortega
 */
public class ParametrosConf {
    private static MConfVal instance;
    
    private ParametrosConf(){
    }
    
    public static void setParamsConf(MConfVal parametrosAdm){
        instance = parametrosAdm;
    }
    
    public static MConfVal getParametrosConf() throws Exception{
        if (null != instance) {
            return instance;
        } else {
            throw new IllegalStateException ("getParametrosConf method must be called after setParamsConf method has been called");
        }    
    }
}
