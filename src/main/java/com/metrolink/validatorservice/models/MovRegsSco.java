/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.models;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author USUARIO
 */
@Entity
@Table(name = "MOV_REGS_SCO")
@NamedQueries({
    @NamedQuery(name = "MovRegsSco.findAll", query = "SELECT m FROM MovRegsSco m")})
public class MovRegsSco implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MovRegsScoPK movRegsScoPK;
    @Column(name = "NLEC")
    private BigInteger nlec;
    @Column(name = "VCCOAN")
    private String vccoan;
    @Column(name = "NCONSUMO")
    private BigInteger nconsumo;

    public MovRegsSco() {
    }

    public MovRegsSco(MovRegsScoPK movRegsScoPK) {
        this.movRegsScoPK = movRegsScoPK;
    }

    public MovRegsSco(BigInteger nnisRad, Date tsfeclet, String vccodtconsumo) {
        this.movRegsScoPK = new MovRegsScoPK(nnisRad, tsfeclet, vccodtconsumo);
    }

    public MovRegsScoPK getMovRegsScoPK() {
        return movRegsScoPK;
    }

    public void setMovRegsScoPK(MovRegsScoPK movRegsScoPK) {
        this.movRegsScoPK = movRegsScoPK;
    }

    public BigInteger getNlec() {
        return nlec;
    }

    public void setNlec(BigInteger nlec) {
        this.nlec = nlec;
    }

    public String getVccoan() {
        return vccoan;
    }

    public void setVccoan(String vccoan) {
        this.vccoan = vccoan;
    }

    public BigInteger getNconsumo() {
        return nconsumo;
    }

    public void setNconsumo(BigInteger nconsumo) {
        this.nconsumo = nconsumo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (movRegsScoPK != null ? movRegsScoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MovRegsSco)) {
            return false;
        }
        MovRegsSco other = (MovRegsSco) object;
        if ((this.movRegsScoPK == null && other.movRegsScoPK != null) || (this.movRegsScoPK != null && !this.movRegsScoPK.equals(other.movRegsScoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.metrolink.validatorservice.models.MovRegsSco[ movRegsScoPK=" + movRegsScoPK + " ]";
    }
    
}
