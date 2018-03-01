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
@Table(name = "M_JURISDICCIONES")
@NamedQueries({
    @NamedQuery(name = "MJurisdicciones.findAll", query = "SELECT m FROM MJurisdicciones m")})
public class MJurisdicciones implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "NCOD_JURISDICCION")
    private Integer ncodJurisdiccion;
    @Column(name = "VCDES_JURISDICCION")
    private String vcdesJurisdiccion;
    @Column(name = "LESTADO")
    private Short lestado;
    @OneToMany(mappedBy = "ncodJurisdiccion")
    private Collection<MovSuministros> movSuministrosCollection;
    @JoinColumn(name = "NCOD_AREA", referencedColumnName = "NCOD_AREA")
    @ManyToOne
    private MAreas ncodArea;
    @JoinColumn(name = "NCOD_DELEGACION", referencedColumnName = "NCOD_DELEGACION")
    @ManyToOne
    private MDelegaciones ncodDelegacion;
    @JoinColumn(name = "NCOD_PROV", referencedColumnName = "NCOD_PROV")
    @ManyToOne
    private MProveedores ncodProv;

    public MJurisdicciones() {
    }

    public MJurisdicciones(Integer ncodJurisdiccion) {
        this.ncodJurisdiccion = ncodJurisdiccion;
    }

    public Integer getNcodJurisdiccion() {
        return ncodJurisdiccion;
    }

    public void setNcodJurisdiccion(Integer ncodJurisdiccion) {
        this.ncodJurisdiccion = ncodJurisdiccion;
    }

    public String getVcdesJurisdiccion() {
        return vcdesJurisdiccion;
    }

    public void setVcdesJurisdiccion(String vcdesJurisdiccion) {
        this.vcdesJurisdiccion = vcdesJurisdiccion;
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

    public MAreas getNcodArea() {
        return ncodArea;
    }

    public void setNcodArea(MAreas ncodArea) {
        this.ncodArea = ncodArea;
    }

    public MDelegaciones getNcodDelegacion() {
        return ncodDelegacion;
    }

    public void setNcodDelegacion(MDelegaciones ncodDelegacion) {
        this.ncodDelegacion = ncodDelegacion;
    }

    public MProveedores getNcodProv() {
        return ncodProv;
    }

    public void setNcodProv(MProveedores ncodProv) {
        this.ncodProv = ncodProv;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ncodJurisdiccion != null ? ncodJurisdiccion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MJurisdicciones)) {
            return false;
        }
        MJurisdicciones other = (MJurisdicciones) object;
        if ((this.ncodJurisdiccion == null && other.ncodJurisdiccion != null) || (this.ncodJurisdiccion != null && !this.ncodJurisdiccion.equals(other.ncodJurisdiccion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.metrolink.validatorservice.models.MJurisdicciones[ ncodJurisdiccion=" + ncodJurisdiccion + " ]";
    }
    
}
