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
 * @author G 50-70
 */
@Embeddable
public class MovCalTouPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "NCOD_CAL_TOU")
    private int ncodCalTou;
    @Basic(optional = false)
    @Column(name = "NINI_FRANJA")
    private short niniFranja;
    @Basic(optional = false)
    @Column(name = "NFIN_FRANJA")
    private short nfinFranja;
    @Basic(optional = false)
    @Column(name = "VCTIPO_ENERGIA")
    private String vctipoEnergia;

    public MovCalTouPK() {
    }

    public MovCalTouPK(int ncodCalTou, short niniFranja, short nfinFranja, String vctipoEnergia) {
        this.ncodCalTou = ncodCalTou;
        this.niniFranja = niniFranja;
        this.nfinFranja = nfinFranja;
        this.vctipoEnergia = vctipoEnergia;
    }

    public int getNcodCalTou() {
        return ncodCalTou;
    }

    public void setNcodCalTou(int ncodCalTou) {
        this.ncodCalTou = ncodCalTou;
    }

    public short getNiniFranja() {
        return niniFranja;
    }

    public void setNiniFranja(short niniFranja) {
        this.niniFranja = niniFranja;
    }

    public short getNfinFranja() {
        return nfinFranja;
    }

    public void setNfinFranja(short nfinFranja) {
        this.nfinFranja = nfinFranja;
    }

    public String getVctipoEnergia() {
        return vctipoEnergia;
    }

    public void setVctipoEnergia(String vctipoEnergia) {
        this.vctipoEnergia = vctipoEnergia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) ncodCalTou;
        hash += (int) niniFranja;
        hash += (int) nfinFranja;
        hash += (vctipoEnergia != null ? vctipoEnergia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MovCalTouPK)) {
            return false;
        }
        MovCalTouPK other = (MovCalTouPK) object;
        if (this.ncodCalTou != other.ncodCalTou) {
            return false;
        }
        if (this.niniFranja != other.niniFranja) {
            return false;
        }
        if (this.nfinFranja != other.nfinFranja) {
            return false;
        }
        if ((this.vctipoEnergia == null && other.vctipoEnergia != null) || (this.vctipoEnergia != null && !this.vctipoEnergia.equals(other.vctipoEnergia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.metrolink.validatorservice.models.MovCalTouPK[ ncodCalTou=" + ncodCalTou + ", niniFranja=" + niniFranja + ", nfinFranja=" + nfinFranja + ", vctipoEnergia=" + vctipoEnergia + " ]";
    }
    
}
