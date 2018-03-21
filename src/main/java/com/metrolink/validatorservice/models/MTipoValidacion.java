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
@Table(name = "M_TIPO_VALIDACION")
@NamedQueries({
    @NamedQuery(name = "MTipoValidacion.findAll", query = "SELECT m FROM MTipoValidacion m")})
public class MTipoValidacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "VCTIPO_VAL")
    private String vctipoVal;
    @Column(name = "VCNOMTIPO_VALI")
    private String vcnomtipoVali;
    @Column(name = "LESTADO")
    private Short lestado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vtipoVal")
    private Collection<MConfVal> mConfValCollection;

    public MTipoValidacion() {
    }

    public MTipoValidacion(String vctipoVal) {
        this.vctipoVal = vctipoVal;
    }

    public String getVctipoVal() {
        return vctipoVal;
    }

    public void setVctipoVal(String vctipoVal) {
        this.vctipoVal = vctipoVal;
    }

    public String getVcnomtipoVali() {
        return vcnomtipoVali;
    }

    public void setVcnomtipoVali(String vcnomtipoVali) {
        this.vcnomtipoVali = vcnomtipoVali;
    }

    public Short getLestado() {
        return lestado;
    }

    public void setLestado(Short lestado) {
        this.lestado = lestado;
    }

    public Collection<MConfVal> getMConfValCollection() {
        return mConfValCollection;
    }

    public void setMConfValCollection(Collection<MConfVal> mConfValCollection) {
        this.mConfValCollection = mConfValCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (vctipoVal != null ? vctipoVal.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MTipoValidacion)) {
            return false;
        }
        MTipoValidacion other = (MTipoValidacion) object;
        if ((this.vctipoVal == null && other.vctipoVal != null) || (this.vctipoVal != null && !this.vctipoVal.equals(other.vctipoVal))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.metrolink.validatorservice.models.MTipoValidacion[ vctipoVal=" + vctipoVal + " ]";
    }
    
}
