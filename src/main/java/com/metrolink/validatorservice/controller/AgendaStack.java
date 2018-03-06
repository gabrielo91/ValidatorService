/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.controller;

import com.metrolink.validatorservice.models.AgendaLectura;
import java.util.ArrayList;

/**
 * Singleton stack for readings managment. In this way all process will share the same agenda values array
 * @author Gabriel Ortega
 */
public class AgendaStack {
    private static AgendaStack instance;
    private static ArrayList<AgendaLectura> intinerarios;

    private AgendaStack() {
    }
    
    public static AgendaStack getInstance(){
        if(null == instance){
           instance = new AgendaStack();
           intinerarios = new ArrayList<>();
        }
        return instance;
    }
    
    public static void setAgendaValues(ArrayList<AgendaLectura> agendaList) throws IllegalStateException  {
        if(null != intinerarios){
            intinerarios.addAll(agendaList);
        }else{
            throw new IllegalStateException ("getInstance method must be called before using setAgendaValues() method");
        }
        
    }
    
    public static synchronized ArrayList<AgendaLectura> getIntinerarios() throws IllegalStateException  {
        if(null != intinerarios){
            return intinerarios;
        }else{
            throw new IllegalStateException ("getInstance method must be called before using getIntinerarios() method");
        }
    }
    
    public static void cleanStack() throws IllegalStateException  {
        if(null != intinerarios){
            intinerarios.clear();
            intinerarios = new ArrayList<>();
        }else{
            throw new IllegalStateException ("getInstance method must be called before using cleanStack() method");
        }
    }
    
}
