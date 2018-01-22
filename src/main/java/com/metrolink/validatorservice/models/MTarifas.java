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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author USUARIO
 */
@Entity
@Table(name = "M_TARIFAS")
@NamedQueries({
    @NamedQuery(name = "MTarifas.findAll", query = "SELECT m FROM MTarifas m")})
public class MTarifas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "VCCODTARIFA")
    private String vccodtarifa;
    @Column(name = "VCDESTARIFA")
    private String vcdestarifa;
    @Column(name = "LESTADO")
    private Short lestado;
    @JoinColumn(name = "NCOD_TIPO_TARIFA", referencedColumnName = "NCOD_TIPO_TARIFA")
    @ManyToOne
    private MTipoTarifas ncodTipoTarifa;
    @OneToMany(mappedBy = "vccodtarifa")
    private Collection<MovSuministros> movSuministrosCollection;

    public MTarifas() {
    }

    public MTarifas(String vccodtarifa) {
        this.vccodtarifa = vccodtarifa;
    }

    public String getVccodtarifa() {
        return vccodtarifa;
    }

    public void setVccodtarifa(String vccodtarifa) {
        this.vccodtarifa = vccodtarifa;
    }

    public String getVcdestarifa() {
        return vcdestarifa;
    }

    public void setVcdestarifa(String vcdestarifa) {
        this.vcdestarifa = vcdestarifa;
    }

    public Short getLestado() {
        return lestado;
    }

    public void setLestado(Short lestado) {
        this.lestado = lestado;
    }

    public MTipoTarifas getNcodTipoTarifa() {
        return ncodTipoTarifa;
    }

    public void setNcodTipoTarifa(MTipoTarifas ncodTipoTarifa) {
        this.ncodTipoTarifa = ncodTipoTarifa;
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
        hash += (vccodtarifa != null ? vccodtarifa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MTarifas)) {
            return false;
        }
        MTarifas other = (MTarifas) object;
        if ((this.vccodtarifa == null && other.vccodtarifa != null) || (this.vccodtarifa != null && !this.vccodtarifa.equals(other.vccodtarifa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.metrolink.validatorservice.models.MTarifas[ vccodtarifa=" + vccodtarifa + " ]";
    }
    
}
