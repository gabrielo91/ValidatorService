/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.models;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "CAUSAS_RECHAZO")
@NamedQueries({
    @NamedQuery(name = "CausasRechazo.findAll", query = "SELECT c FROM CausasRechazo c")})
public class CausasRechazo implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "NID_CAUSA_RECHAZO")
    private BigDecimal nidCausaRechazo;
    @Column(name = "VADESCRIPCION")
    private String vadescripcion;
    @OneToMany(mappedBy = "nidCausaRechazo")
    private Collection<MovLogAlterno> movLogAlternoCollection;

    public CausasRechazo() {
    }

    public CausasRechazo(BigDecimal nidCausaRechazo) {
        this.nidCausaRechazo = nidCausaRechazo;
    }

    public BigDecimal getNidCausaRechazo() {
        return nidCausaRechazo;
    }

    public void setNidCausaRechazo(BigDecimal nidCausaRechazo) {
        this.nidCausaRechazo = nidCausaRechazo;
    }

    public String getVadescripcion() {
        return vadescripcion;
    }

    public void setVadescripcion(String vadescripcion) {
        this.vadescripcion = vadescripcion;
    }

    public Collection<MovLogAlterno> getMovLogAlternoCollection() {
        return movLogAlternoCollection;
    }

    public void setMovLogAlternoCollection(Collection<MovLogAlterno> movLogAlternoCollection) {
        this.movLogAlternoCollection = movLogAlternoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nidCausaRechazo != null ? nidCausaRechazo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CausasRechazo)) {
            return false;
        }
        CausasRechazo other = (CausasRechazo) object;
        if ((this.nidCausaRechazo == null && other.nidCausaRechazo != null) || (this.nidCausaRechazo != null && !this.nidCausaRechazo.equals(other.nidCausaRechazo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.metrolink.validatorservice.models.CausasRechazo[ nidCausaRechazo=" + nidCausaRechazo + " ]";
    }
    
}
