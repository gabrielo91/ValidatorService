/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.db.controller;

import java.sql.Connection;

/**
 *
 * @author Gabriel Ortega
 */
public interface IDatabaseController {
    Connection getConnection() throws Exception;;
}
