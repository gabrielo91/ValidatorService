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
@Table(name = "M_DELEGACIONES")
@NamedQueries({
    @NamedQuery(name = "MDelegaciones.findAll", query = "SELECT m FROM MDelegaciones m")})
public class MDelegaciones implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "NCOD_DELEGACION")
    private Integer ncodDelegacion;
    @Column(name = "VCDES_DELEGACION")
    private String vcdesDelegacion;
    @Column(name = "LESTADO")
    private Short lestado;
    @OneToMany(mappedBy = "ncodDelegacion")
    private Collection<MJurisdicciones> mJurisdiccionesCollection;

    public MDelegaciones() {
    }

    public MDelegaciones(Integer ncodDelegacion) {
        this.ncodDelegacion = ncodDelegacion;
    }

    public Integer getNcodDelegacion() {
        return ncodDelegacion;
    }

    public void setNcodDelegacion(Integer ncodDelegacion) {
        this.ncodDelegacion = ncodDelegacion;
    }

    public String getVcdesDelegacion() {
        return vcdesDelegacion;
    }

    public void setVcdesDelegacion(String vcdesDelegacion) {
        this.vcdesDelegacion = vcdesDelegacion;
    }

    public Short getLestado() {
        return lestado;
    }

    public void setLestado(Short lestado) {
        this.lestado = lestado;
    }

    public Collection<MJurisdicciones> getMJurisdiccionesCollection() {
        return mJurisdiccionesCollection;
    }

    public void setMJurisdiccionesCollection(Collection<MJurisdicciones> mJurisdiccionesCollection) {
        this.mJurisdiccionesCollection = mJurisdiccionesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ncodDelegacion != null ? ncodDelegacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MDelegaciones)) {
            return false;
        }
        MDelegaciones other = (MDelegaciones) object;
        if ((this.ncodDelegacion == null && other.ncodDelegacion != null) || (this.ncodDelegacion != null && !this.ncodDelegacion.equals(other.ncodDelegacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.metrolink.validatorservice.models.MDelegaciones[ ncodDelegacion=" + ncodDelegacion + " ]";
    }
    
}
