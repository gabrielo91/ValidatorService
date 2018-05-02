/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.models;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author G 50-70
 */
@Embeddable
public class MovRegsScoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "NNIS_RAD")
    private BigInteger nnisRad;
    @Basic(optional = false)
    @Column(name = "TSFECLET")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tsfeclet;
    @Basic(optional = false)
    @Column(name = "VCCODTCONSUMO")
    private String vccodtconsumo;

    public MovRegsScoPK() {
    }

    public MovRegsScoPK(BigInteger nnisRad, Date tsfeclet, String vccodtconsumo) {
        this.nnisRad = nnisRad;
        this.tsfeclet = tsfeclet;
        this.vccodtconsumo = vccodtconsumo;
    }

    public BigInteger getNnisRad() {
        return nnisRad;
    }

    public void setNnisRad(BigInteger nnisRad) {
        this.nnisRad = nnisRad;
    }

    public Date getTsfeclet() {
        return tsfeclet;
    }

    public void setTsfeclet(Date tsfeclet) {
        this.tsfeclet = tsfeclet;
    }

    public String getVccodtconsumo() {
        return vccodtconsumo;
    }

    public void setVccodtconsumo(String vccodtconsumo) {
        this.vccodtconsumo = vccodtconsumo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nnisRad != null ? nnisRad.hashCode() : 0);
        hash += (tsfeclet != null ? tsfeclet.hashCode() : 0);
        hash += (vccodtconsumo != null ? vccodtconsumo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MovRegsScoPK)) {
            return false;
        }
        MovRegsScoPK other = (MovRegsScoPK) object;
        if ((this.nnisRad == null && other.nnisRad != null) || (this.nnisRad != null && !this.nnisRad.equals(other.nnisRad))) {
            return false;
        }
        if ((this.tsfeclet == null && other.tsfeclet != null) || (this.tsfeclet != null && !this.tsfeclet.equals(other.tsfeclet))) {
            return false;
        }
        if ((this.vccodtconsumo == null && other.vccodtconsumo != null) || (this.vccodtconsumo != null && !this.vccodtconsumo.equals(other.vccodtconsumo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.metrolink.validatorservice.models.MovRegsScoPK[ nnisRad=" + nnisRad + ", tsfeclet=" + tsfeclet + ", vccodtconsumo=" + vccodtconsumo + " ]";
    }
    
}
