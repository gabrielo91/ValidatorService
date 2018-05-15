/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.models;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author G 50-70
 */
@Entity
@Table(name = "MOV_CAL_TOU")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MovCalTou.findAll", query = "SELECT m FROM MovCalTou m")})
public class MovCalTou implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MovCalTouPK movCalTouPK;
    @Basic(optional = false)
    @Column(name = "NCOD_TIPO_TARIFA")
    private int ncodTipoTarifa;
    @Basic(optional = false)
    @Column(name = "VCCODTCONSUMO")
    private String vccodtconsumo;
    @Column(name = "LESTADO")
    private Short lestado;

    public MovCalTou() {
    }

    public MovCalTou(MovCalTouPK movCalTouPK) {
        this.movCalTouPK = movCalTouPK;
    }

    public MovCalTou(MovCalTouPK movCalTouPK, int ncodTipoTarifa, String vccodtconsumo) {
        this.movCalTouPK = movCalTouPK;
        this.ncodTipoTarifa = ncodTipoTarifa;
        this.vccodtconsumo = vccodtconsumo;
    }

    public MovCalTou(int ncodCalTou, short niniFranja, short nfinFranja, String vctipoEnergia) {
        this.movCalTouPK = new MovCalTouPK(ncodCalTou, niniFranja, nfinFranja, vctipoEnergia);
    }

    public MovCalTouPK getMovCalTouPK() {
        return movCalTouPK;
    }

    public void setMovCalTouPK(MovCalTouPK movCalTouPK) {
        this.movCalTouPK = movCalTouPK;
    }

    public int getNcodTipoTarifa() {
        return ncodTipoTarifa;
    }

    public void setNcodTipoTarifa(int ncodTipoTarifa) {
        this.ncodTipoTarifa = ncodTipoTarifa;
    }

    public String getVccodtconsumo() {
        return vccodtconsumo;
    }

    public void setVccodtconsumo(String vccodtconsumo) {
        this.vccodtconsumo = vccodtconsumo;
    }

    public Short getLestado() {
        return lestado;
    }

    public void setLestado(Short lestado) {
        this.lestado = lestado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (movCalTouPK != null ? movCalTouPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MovCalTou)) {
            return false;
        }
        MovCalTou other = (MovCalTou) object;
        if ((this.movCalTouPK == null && other.movCalTouPK != null) || (this.movCalTouPK != null && !this.movCalTouPK.equals(other.movCalTouPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.metrolink.validatorservice.models.MovCalTou[ movCalTouPK=" + movCalTouPK + " ]";
    }
    
}
