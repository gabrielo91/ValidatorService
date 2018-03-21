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
@Table(name = "M_AREAS")
@NamedQueries({
    @NamedQuery(name = "MAreas.findAll", query = "SELECT m FROM MAreas m")})
public class MAreas implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ncodArea")
    private Collection<MConfVal> mConfValCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "NCOD_AREA")
    private Integer ncodArea;
    @Column(name = "VCDES_AREA")
    private String vcdesArea;
    @Column(name = "LESTADO")
    private Short lestado;
    @OneToMany(mappedBy = "ncodArea")
    private Collection<MJurisdicciones> mJurisdiccionesCollection;

    public MAreas() {
    }

    public MAreas(Integer ncodArea) {
        this.ncodArea = ncodArea;
    }

    public Integer getNcodArea() {
        return ncodArea;
    }

    public void setNcodArea(Integer ncodArea) {
        this.ncodArea = ncodArea;
    }

    public String getVcdesArea() {
        return vcdesArea;
    }

    public void setVcdesArea(String vcdesArea) {
        this.vcdesArea = vcdesArea;
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
        hash += (ncodArea != null ? ncodArea.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MAreas)) {
            return false;
        }
        MAreas other = (MAreas) object;
        if ((this.ncodArea == null && other.ncodArea != null) || (this.ncodArea != null && !this.ncodArea.equals(other.ncodArea))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.metrolink.validatorservice.models.MAreas[ ncodArea=" + ncodArea + " ]";
    }

    public Collection<MConfVal> getMConfValCollection() {
        return mConfValCollection;
    }

    public void setMConfValCollection(Collection<MConfVal> mConfValCollection) {
        this.mConfValCollection = mConfValCollection;
    }
    
}
