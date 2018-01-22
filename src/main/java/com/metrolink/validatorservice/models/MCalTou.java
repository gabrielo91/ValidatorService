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
@Table(name = "M_CAL_TOU")
@NamedQueries({
    @NamedQuery(name = "MCalTou.findAll", query = "SELECT m FROM MCalTou m")})
public class MCalTou implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "NCOD_CAL_TOU")
    private Integer ncodCalTou;
    @Column(name = "VCDES_CAL_TOU")
    private String vcdesCalTou;
    @Column(name = "LESTADO")
    private Short lestado;
    @OneToMany(mappedBy = "ncodCalTou")
    private Collection<MovSuministros> movSuministrosCollection;

    public MCalTou() {
    }

    public MCalTou(Integer ncodCalTou) {
        this.ncodCalTou = ncodCalTou;
    }

    public Integer getNcodCalTou() {
        return ncodCalTou;
    }

    public void setNcodCalTou(Integer ncodCalTou) {
        this.ncodCalTou = ncodCalTou;
    }

    public String getVcdesCalTou() {
        return vcdesCalTou;
    }

    public void setVcdesCalTou(String vcdesCalTou) {
        this.vcdesCalTou = vcdesCalTou;
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
        hash += (ncodCalTou != null ? ncodCalTou.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MCalTou)) {
            return false;
        }
        MCalTou other = (MCalTou) object;
        if ((this.ncodCalTou == null && other.ncodCalTou != null) || (this.ncodCalTou != null && !this.ncodCalTou.equals(other.ncodCalTou))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.metrolink.validatorservice.models.MCalTou[ ncodCalTou=" + ncodCalTou + " ]";
    }
    
}
