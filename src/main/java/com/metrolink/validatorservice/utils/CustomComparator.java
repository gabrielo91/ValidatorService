/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.utils;

import java.lang.reflect.Method;
import java.util.Comparator;

/**
 *
 * @author Gabriel Ortega Rosero
 */
public class CustomComparator implements Comparator<Method> {

    @Override
    public int compare(Method o1, Method o2) {
        return o1.getName().compareTo(o2.getName());
    }
    
}
