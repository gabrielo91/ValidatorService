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
@Table(name = "M_PROVEEDORES")
@NamedQueries({
    @NamedQuery(name = "MProveedores.findAll", query = "SELECT m FROM MProveedores m")})
public class MProveedores implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "VCCOD_PROV")
    private Integer vccodProv;
    @Basic(optional = false)
    @Column(name = "VCDESCRIPCION")
    private String vcdescripcion;
    @Column(name = "VCNIT")
    private String vcnit;
    @Column(name = "VCNUM_CONTRATO")
    private String vcnumContrato;
    @Column(name = "VCREPLEGAL")
    private String vcreplegal;
    @Column(name = "VCDIR")
    private String vcdir;
    @Column(name = "LESTADO")
    private Short lestado;
    @Column(name = "VCUSERNAME")
    private String vcusername;
    @Column(name = "VCPASSWORD")
    private String vcpassword;
    @Column(name = "VCVIS_ORIGEN")
    private String vcvisOrigen;
    @Column(name = "VCPROCESO")
    private String vcproceso;
    @Column(name = "DBCONEXION")
    private String dbconexion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mProveedores")
    private Collection<MovSuministros> movSuministrosCollection;
    @OneToMany(mappedBy = "ncodProv")
    private Collection<MJurisdicciones> mJurisdiccionesCollection;

    public MProveedores() {
    }

    public MProveedores(Integer vccodProv) {
        this.vccodProv = vccodProv;
    }

    public MProveedores(Integer vccodProv, String vcdescripcion) {
        this.vccodProv = vccodProv;
        this.vcdescripcion = vcdescripcion;
    }

    public Integer getVccodProv() {
        return vccodProv;
    }

    public void setVccodProv(Integer vccodProv) {
        this.vccodProv = vccodProv;
    }

    public String getVcdescripcion() {
        return vcdescripcion;
    }

    public void setVcdescripcion(String vcdescripcion) {
        this.vcdescripcion = vcdescripcion;
    }

    public String getVcnit() {
        return vcnit;
    }

    public void setVcnit(String vcnit) {
        this.vcnit = vcnit;
    }

    public String getVcnumContrato() {
        return vcnumContrato;
    }

    public void setVcnumContrato(String vcnumContrato) {
        this.vcnumContrato = vcnumContrato;
    }

    public String getVcreplegal() {
        return vcreplegal;
    }

    public void setVcreplegal(String vcreplegal) {
        this.vcreplegal = vcreplegal;
    }

    public String getVcdir() {
        return vcdir;
    }

    public void setVcdir(String vcdir) {
        this.vcdir = vcdir;
    }

    public Short getLestado() {
        return lestado;
    }

    public void setLestado(Short lestado) {
        this.lestado = lestado;
    }

    public String getVcusername() {
        return vcusername;
    }

    public void setVcusername(String vcusername) {
        this.vcusername = vcusername;
    }

    public String getVcpassword() {
        return vcpassword;
    }

    public void setVcpassword(String vcpassword) {
        this.vcpassword = vcpassword;
    }

    public String getVcvisOrigen() {
        return vcvisOrigen;
    }

    public void setVcvisOrigen(String vcvisOrigen) {
        this.vcvisOrigen = vcvisOrigen;
    }

    public String getVcproceso() {
        return vcproceso;
    }

    public void setVcproceso(String vcproceso) {
        this.vcproceso = vcproceso;
    }

    public String getDbconexion() {
        return dbconexion;
    }

    public void setDbconexion(String dbconexion) {
        this.dbconexion = dbconexion;
    }

    public Collection<MovSuministros> getMovSuministrosCollection() {
        return movSuministrosCollection;
    }

    public void setMovSuministrosCollection(Collection<MovSuministros> movSuministrosCollection) {
        this.movSuministrosCollection = movSuministrosCollection;
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
        hash += (vccodProv != null ? vccodProv.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MProveedores)) {
            return false;
        }
        MProveedores other = (MProveedores) object;
        if ((this.vccodProv == null && other.vccodProv != null) || (this.vccodProv != null && !this.vccodProv.equals(other.vccodProv))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.metrolink.validatorservice.models.MProveedores[ vccodProv=" + vccodProv + " ]";
    }
    
}