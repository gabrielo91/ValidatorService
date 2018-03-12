/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.db.controller;

import com.metrolink.validatorservice.models.MParametrosAdm;

/**
 *
 * @author Gabriel Ortega
 */
public class ParametrosAdmin {
    
    private static MParametrosAdm instance;
    
    private ParametrosAdmin(){
    }
    
    public static void setParams(MParametrosAdm parametrosAdm){
        instance = parametrosAdm;
    }
    
    public static MParametrosAdm getParametrosAdmin() throws Exception{
        if (null != instance) {
            return instance;
        } else {
            throw new IllegalStateException ("getParametrosAdmin method must be called after setParams method has been called");
        }    
    }
    
}
