/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.controller;

import com.metrolink.validatorservice.models.MovLectConsu;
import java.util.ArrayList;

/**
 * Singleton stack for readings managment. In this way all process will share the same readings array
 * @author Gabriel Ortega
 */
public class ReadingsStack {
    private static ReadingsStack instance;
    private static ArrayList<MovLectConsu> readings;

    private ReadingsStack() {
    }
    
    public static ReadingsStack getInstance(){
        if(null == instance){
           instance = new ReadingsStack();
           readings = new ArrayList<>();
        }
        return instance;
    }
    
    public static void setReadings(ArrayList<MovLectConsu> readingsList) throws IllegalStateException  {
        if(null != readings){
            readings.addAll(readingsList);
        }else{
            throw new IllegalStateException ("getInstance method must be called before use setReadings() method");
        }
        
    }
    
    public static synchronized ArrayList<MovLectConsu> getReadings() throws IllegalStateException  {
        if(null != readings){
            return readings;
        }else{
            throw new IllegalStateException ("getInstance method must be called before use getReadings() method");
        }
    }
    
    public static void cleanStack() throws IllegalStateException  {
        if(null != readings){
            readings.clear();
            readings = new ArrayList<>();
        }else{
            throw new IllegalStateException ("getInstance method must be called before use cleanStack() method");
        }
    }
    
}
