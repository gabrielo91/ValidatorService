/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.models;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
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
@Table(name = "M_TIPO_TARIFAS")
@NamedQueries({
    @NamedQuery(name = "MTipoTarifas.findAll", query = "SELECT m FROM MTipoTarifas m")})
public class MTipoTarifas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "NCOD_TIPO_TARIFA")
    private Integer ncodTipoTarifa;
    @Column(name = "VCDES_TIPO_TARIFA")
    private String vcdesTipoTarifa;
    @Column(name = "LESTADO")
    private Short lestado;
    @OneToMany(mappedBy = "ncodTipoTarifa")
    private Collection<MTarifas> mTarifasCollection;

    public MTipoTarifas() {
    }

    public MTipoTarifas(Integer ncodTipoTarifa) {
        this.ncodTipoTarifa = ncodTipoTarifa;
    }

    public Integer getNcodTipoTarifa() {
        return ncodTipoTarifa;
    }

    public void setNcodTipoTarifa(Integer ncodTipoTarifa) {
        this.ncodTipoTarifa = ncodTipoTarifa;
    }

    public String getVcdesTipoTarifa() {
        return vcdesTipoTarifa;
    }

    public void setVcdesTipoTarifa(String vcdesTipoTarifa) {
        this.vcdesTipoTarifa = vcdesTipoTarifa;
    }

    public Short getLestado() {
        return lestado;
    }

    public void setLestado(Short lestado) {
        this.lestado = lestado;
    }

    public Collection<MTarifas> getMTarifasCollection() {
        return mTarifasCollection;
    }

    public void setMTarifasCollection(Collection<MTarifas> mTarifasCollection) {
        this.mTarifasCollection = mTarifasCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ncodTipoTarifa != null ? ncodTipoTarifa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MTipoTarifas)) {
            return false;
        }
        MTipoTarifas other = (MTipoTarifas) object;
        if ((this.ncodTipoTarifa == null && other.ncodTipoTarifa != null) || (this.ncodTipoTarifa != null && !this.ncodTipoTarifa.equals(other.ncodTipoTarifa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.metrolink.validatorservice.models.MTipoTarifas[ ncodTipoTarifa=" + ncodTipoTarifa + " ]";
    }
    
}
