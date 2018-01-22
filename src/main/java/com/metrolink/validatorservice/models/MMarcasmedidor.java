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
@Table(name = "M_MARCASMEDIDOR")
@NamedQueries({
    @NamedQuery(name = "MMarcasmedidor.findAll", query = "SELECT m FROM MMarcasmedidor m")})
public class MMarcasmedidor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "VCCODMARCA")
    private String vccodmarca;
    @Column(name = "VCDESMARCA")
    private String vcdesmarca;
    @Column(name = "LESTADO")
    private Short lestado;
    @OneToMany(mappedBy = "vccodmarca")
    private Collection<MovSuministros> movSuministrosCollection;

    public MMarcasmedidor() {
    }

    public MMarcasmedidor(String vccodmarca) {
        this.vccodmarca = vccodmarca;
    }

    public String getVccodmarca() {
        return vccodmarca;
    }

    public void setVccodmarca(String vccodmarca) {
        this.vccodmarca = vccodmarca;
    }

    public String getVcdesmarca() {
        return vcdesmarca;
    }

    public void setVcdesmarca(String vcdesmarca) {
        this.vcdesmarca = vcdesmarca;
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
        hash += (vccodmarca != null ? vccodmarca.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MMarcasmedidor)) {
            return false;
        }
        MMarcasmedidor other = (MMarcasmedidor) object;
        if ((this.vccodmarca == null && other.vccodmarca != null) || (this.vccodmarca != null && !this.vccodmarca.equals(other.vccodmarca))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.metrolink.validatorservice.models.MMarcasmedidor[ vccodmarca=" + vccodmarca + " ]";
    }
    
}
