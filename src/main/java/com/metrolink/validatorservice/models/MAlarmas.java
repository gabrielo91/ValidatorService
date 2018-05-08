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
@Table(name = "M_ALARMAS")
@NamedQueries({
    @NamedQuery(name = "MAlarmas.findAll", query = "SELECT m FROM MAlarmas m")})
public class MAlarmas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "NCOD_ALARMA")
    private Integer ncodAlarma;
    @Column(name = "VCDES_ALARMA")
    private String vcdesAlarma;
    @Column(name = "LESTADO")
    private Short lestado;
    @Column(name = "NCOD_TIPO_ALARMA")
    private Integer ncodTipoAlarma;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mAlarmas")
    private Collection<MovAlarmas> movAlarmasCollection;

    public MAlarmas() {
    }

    public MAlarmas(Integer ncodAlarma) {
        this.ncodAlarma = ncodAlarma;
    }

    public Integer getNcodAlarma() {
        return ncodAlarma;
    }

    public void setNcodAlarma(Integer ncodAlarma) {
        this.ncodAlarma = ncodAlarma;
    }

    public String getVcdesAlarma() {
        return vcdesAlarma;
    }

    public void setVcdesAlarma(String vcdesAlarma) {
        this.vcdesAlarma = vcdesAlarma;
    }

    public Short getLestado() {
        return lestado;
    }

    public void setLestado(Short lestado) {
        this.lestado = lestado;
    }

    public Integer getNcodTipoAlarma() {
        return ncodTipoAlarma;
    }

    public void setNcodTipoAlarma(Integer ncodTipoAlarma) {
        this.ncodTipoAlarma = ncodTipoAlarma;
    }

    public Collection<MovAlarmas> getMovAlarmasCollection() {
        return movAlarmasCollection;
    }

    public void setMovAlarmasCollection(Collection<MovAlarmas> movAlarmasCollection) {
        this.movAlarmasCollection = movAlarmasCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ncodAlarma != null ? ncodAlarma.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MAlarmas)) {
            return false;
        }
        MAlarmas other = (MAlarmas) object;
        if ((this.ncodAlarma == null && other.ncodAlarma != null) || (this.ncodAlarma != null && !this.ncodAlarma.equals(other.ncodAlarma))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.metrolink.validatorservice.models.MAlarmas[ ncodAlarma=" + ncodAlarma + " ]";
    }
    
}
