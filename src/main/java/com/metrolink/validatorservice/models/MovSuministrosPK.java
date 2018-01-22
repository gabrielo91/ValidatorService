/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.models;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author USUARIO
 */
@Embeddable
public class MovSuministrosPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "NCOD_PROV")
    private int ncodProv;
    @Basic(optional = false)
    @Column(name = "NNIS_RAD")
    private BigInteger nnisRad;
    @Basic(optional = false)
    @Column(name = "VCCODTCONSUMO")
    private String vccodtconsumo;

    public MovSuministrosPK() {
    }

    public MovSuministrosPK(int ncodProv, BigInteger nnisRad, String vccodtconsumo) {
        this.ncodProv = ncodProv;
        this.nnisRad = nnisRad;
        this.vccodtconsumo = vccodtconsumo;
    }

    public int getNcodProv() {
        return ncodProv;
    }

    public void setNcodProv(int ncodProv) {
        this.ncodProv = ncodProv;
    }

    public BigInteger getNnisRad() {
        return nnisRad;
    }

    public void setNnisRad(BigInteger nnisRad) {
        this.nnisRad = nnisRad;
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
        hash += (int) ncodProv;
        hash += (nnisRad != null ? nnisRad.hashCode() : 0);
        hash += (vccodtconsumo != null ? vccodtconsumo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MovSuministrosPK)) {
            return false;
        }
        MovSuministrosPK other = (MovSuministrosPK) object;
        if (this.ncodProv != other.ncodProv) {
            return false;
        }
        if ((this.nnisRad == null && other.nnisRad != null) || (this.nnisRad != null && !this.nnisRad.equals(other.nnisRad))) {
            return false;
        }
        if ((this.vccodtconsumo == null && other.vccodtconsumo != null) || (this.vccodtconsumo != null && !this.vccodtconsumo.equals(other.vccodtconsumo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.metrolink.validatorservice.models.MovSuministrosPK[ ncodProv=" + ncodProv + ", nnisRad=" + nnisRad + ", vccodtconsumo=" + vccodtconsumo + " ]";
    }
    
}
