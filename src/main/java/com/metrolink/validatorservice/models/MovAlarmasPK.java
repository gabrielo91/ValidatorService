/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.models;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author USUARIO
 */
@Embeddable
public class MovAlarmasPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "NCONS_PROCESO")
    private int nconsProceso;
    @Basic(optional = false)
    @Column(name = "NNIS_RAD")
    private int nnisRad;
    @Basic(optional = false)
    @Column(name = "NCOD_ALARMA")
    private int ncodAlarma;

    public MovAlarmasPK() {
    }

    public MovAlarmasPK(int nconsProceso, int nnisRad, int ncodAlarma) {
        this.nconsProceso = nconsProceso;
        this.nnisRad = nnisRad;
        this.ncodAlarma = ncodAlarma;
    }

    public int getNconsProceso() {
        return nconsProceso;
    }

    public void setNconsProceso(int nconsProceso) {
        this.nconsProceso = nconsProceso;
    }

    public int getNnisRad() {
        return nnisRad;
    }

    public void setNnisRad(int nnisRad) {
        this.nnisRad = nnisRad;
    }

    public int getNcodAlarma() {
        return ncodAlarma;
    }

    public void setNcodAlarma(int ncodAlarma) {
        this.ncodAlarma = ncodAlarma;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) nconsProceso;
        hash += (int) nnisRad;
        hash += (int) ncodAlarma;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MovAlarmasPK)) {
            return false;
        }
        MovAlarmasPK other = (MovAlarmasPK) object;
        if (this.nconsProceso != other.nconsProceso) {
            return false;
        }
        if (this.nnisRad != other.nnisRad) {
            return false;
        }
        if (this.ncodAlarma != other.ncodAlarma) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.metrolink.validatorservice.models.MovAlarmasPK[ nconsProceso=" + nconsProceso + ", nnisRad=" + nnisRad + ", ncodAlarma=" + ncodAlarma + " ]";
    }
    
}
