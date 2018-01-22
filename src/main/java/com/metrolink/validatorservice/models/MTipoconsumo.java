/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.models;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author USUARIO
 */
@Entity
@Table(name = "M_TIPOCONSUMO")
@NamedQueries({
    @NamedQuery(name = "MTipoconsumo.findAll", query = "SELECT m FROM MTipoconsumo m")})
public class MTipoconsumo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "VCCODTCONSUMO")
    private String vccodtconsumo;
    @Column(name = "VCDESTCONSUMO")
    private String vcdestconsumo;
    @Column(name = "LESTADO")
    private Short lestado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mTipoconsumo")
    private Collection<MovSuministros> movSuministrosCollection;

    public MTipoconsumo() {
    }

    public MTipoconsumo(String vccodtconsumo) {
        this.vccodtconsumo = vccodtconsumo;
    }

    public String getVccodtconsumo() {
        return vccodtconsumo;
    }

    public void setVccodtconsumo(String vccodtconsumo) {
        this.vccodtconsumo = vccodtconsumo;
    }

    public String getVcdestconsumo() {
        return vcdestconsumo;
    }

    public void setVcdestconsumo(String vcdestconsumo) {
        this.vcdestconsumo = vcdestconsumo;
    }

    public Short getLestado() {
        return lestado;
    }

    public void setLestado(Short lestado) {
        this.lestado = lestado;
    }

    public Collection<MovSuministros> getMovSuministrosCollection() {
        return movSuministrosCollection;
    }

    public void setMovSuministrosCollection(Collection<MovSuministros> movSuministrosCollection) {
        this.movSuministrosCollection = movSuministrosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (vccodtconsumo != null ? vccodtconsumo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MTipoconsumo)) {
            return false;
        }
        MTipoconsumo other = (MTipoconsumo) object;
        if ((this.vccodtconsumo == null && other.vccodtconsumo != null) || (this.vccodtconsumo != null && !this.vccodtconsumo.equals(other.vccodtconsumo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.metrolink.validatorservice.models.MTipoconsumo[ vccodtconsumo=" + vccodtconsumo + " ]";
    }
    
}
