/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Gabriel Ortega
 */
@Entity
@Table(name = "MOV_LECT_CONSU")
@NamedQueries({
    @NamedQuery(name = "MovLectConsu.findAll", query = "SELECT m FROM MovLectConsu m")})
public class MovLectConsu implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "NCOD_PROV")
    private int ncodProv;
    @Basic(optional = false)
    @Column(name = "NNIS_RAD")
    private String nnisRad;
    @Basic(optional = false)
    @Column(name = "TSFECHA_LEC")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tsfechaLec;
    @Column(name = "VCNUM_MED")
    private String vcnumMed;
    @Column(name = "VCTIPO_MED")
    private String vctipoMed;
    @Column(name = "VCCODMARCA")
    private String vccodmarca;
    @Column(name = "VCTIPO_LEC")
    private String vctipoLec;
    @Column(name = "NLECTURA")
    private Integer nlectura;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "NCONSUMO_ORI")
    private BigDecimal nconsumoOri;
    @Column(name = "NCONSUMO_MOD")
    private BigDecimal nconsumoMod;
    @Column(name = "LBLOQUEADO")
    private Short lbloqueado;
    @Column(name = "LENVIADO")
    private Short lenviado;
    @Column(name = "VCCODUSER")
    private String vccoduser;
    @Column(name = "VCPROGRAMA")
    private String vcprograma;
    @Column(name = "TSFECHA_TRAN")
    private String tsfechaTran;
    @Column(name = "LGEN_ALARMA")
    private Short lgenAlarma;
    @Column(name = "LCERTIFICADA")
    private Short lcertificada;
    @Basic(optional = false)
    @Column(name = "NCONS_PROCESO")
    private int nconsProceso;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "VCNIC")
    private String vcnic;

    public MovLectConsu() {
    }

    public MovLectConsu(BigDecimal id) {
        this.id = id;
    }

    public MovLectConsu(BigDecimal id, int ncodProv, String nnisRad, Date tsfechaLec, int nconsProceso) {
        this.id = id;
        this.ncodProv = ncodProv;
        this.nnisRad = nnisRad;
        this.tsfechaLec = tsfechaLec;
        this.nconsProceso = nconsProceso;
    }

    public int getNcodProv() {
        return ncodProv;
    }

    public void setNcodProv(int ncodProv) {
        this.ncodProv = ncodProv;
    }

    public String getNnisRad() {
        return nnisRad;
    }

    public void setNnisRad(String nnisRad) {
        this.nnisRad = nnisRad;
    }

    public Date getTsfechaLec() {
        return tsfechaLec;
    }

    public void setTsfechaLec(Date tsfechaLec) {
        this.tsfechaLec = tsfechaLec;
    }

    public String getVcnumMed() {
        return vcnumMed;
    }

    public void setVcnumMed(String vcnumMed) {
        this.vcnumMed = vcnumMed;
    }

    public String getVctipoMed() {
        return vctipoMed;
    }

    public void setVctipoMed(String vctipoMed) {
        this.vctipoMed = vctipoMed;
    }

    public String getVccodmarca() {
        return vccodmarca;
    }

    public void setVccodmarca(String vccodmarca) {
        this.vccodmarca = vccodmarca;
    }

    public String getVctipoLec() {
        return vctipoLec;
    }

    public void setVctipoLec(String vctipoLec) {
        this.vctipoLec = vctipoLec;
    }

    public Integer getNlectura() {
        return nlectura;
    }

    public void setNlectura(Integer nlectura) {
        this.nlectura = nlectura;
    }

    public BigDecimal getNconsumoOri() {
        return nconsumoOri;
    }

    public void setNconsumoOri(BigDecimal nconsumoOri) {
        this.nconsumoOri = nconsumoOri;
    }

    public BigDecimal getNconsumoMod() {
        return nconsumoMod;
    }

    public void setNconsumoMod(BigDecimal nconsumoMod) {
        this.nconsumoMod = nconsumoMod;
    }

    public Short getLbloqueado() {
        return lbloqueado;
    }

    public void setLbloqueado(Short lbloqueado) {
        this.lbloqueado = lbloqueado;
    }

    public Short getLenviado() {
        return lenviado;
    }

    public void setLenviado(Short lenviado) {
        this.lenviado = lenviado;
    }

    public String getVccoduser() {
        return vccoduser;
    }

    public void setVccoduser(String vccoduser) {
        this.vccoduser = vccoduser;
    }

    public String getVcprograma() {
        return vcprograma;
    }

    public void setVcprograma(String vcprograma) {
        this.vcprograma = vcprograma;
    }

    public String getTsfechaTran() {
        return tsfechaTran;
    }

    public void setTsfechaTran(String tsfechaTran) {
        this.tsfechaTran = tsfechaTran;
    }

    public Short getLgenAlarma() {
        return lgenAlarma;
    }

    public void setLgenAlarma(Short lgenAlarma) {
        this.lgenAlarma = lgenAlarma;
    }

    public Short getLcertificada() {
        return lcertificada;
    }

    public void setLcertificada(Short lcertificada) {
        this.lcertificada = lcertificada;
    }

    public int getNconsProceso() {
        return nconsProceso;
    }

    public void setNconsProceso(int nconsProceso) {
        this.nconsProceso = nconsProceso;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getVcnic() {
        return vcnic;
    }

    public void setVcnic(String vcnic) {
        this.vcnic = vcnic;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MovLectConsu)) {
            return false;
        }
        MovLectConsu other = (MovLectConsu) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.metrolink.validatorservice.models.MovLectConsu[ id=" + id + " ]";
    }
    
}
