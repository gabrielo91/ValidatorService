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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author USUARIO
 */
@Entity
@Table(name = "MOV_LOG_ALTERNO")
@NamedQueries({
    @NamedQuery(name = "MovLogAlterno.findAll", query = "SELECT m FROM MovLogAlterno m")})
public class MovLogAlterno implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CONS")
    private Long cons;
    @Column(name = "VCTIPO_VAL")
    private String vctipoVal;
    @Column(name = "VCNIC")
    private String vcnic;
    @Column(name = "VCNISRAD")
    private String vcnisrad;
    @Column(name = "VCNUM_MED")
    private String vcnumMed;
    @Column(name = "VCCODMARCA")
    private String vccodmarca;
    @Column(name = "VCTIPO_LEC")
    private String vctipoLec;
    @Column(name = "DFEC_REAL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dfecReal;
    @Column(name = "NLECTURA")
    private Integer nlectura;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "NCONSUMO_ORI")
    private BigDecimal nconsumoOri;
    @Column(name = "NCONSUMO_MOD")
    private BigDecimal nconsumoMod;
    @Column(name = "NCOD_PROV")
    private Integer ncodProv;
    @Column(name = "VCTIPO_RECHAZO")
    private String vctipoRechazo;
    @Column(name = "VCCODUSER")
    private String vccoduser;
    @Column(name = "VCPROGRAMA")
    private String vcprograma;
    @Column(name = "TSFECHA_TRAN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tsfechaTran;
    @JoinColumn(name = "NID_CAUSA_RECHAZO", referencedColumnName = "NID_CAUSA_RECHAZO")
    @ManyToOne
    private CausasRechazo nidCausaRechazo;

    public MovLogAlterno() {
    }

    public MovLogAlterno(Long cons) {
        this.cons = cons;
    }

    public Long getCons() {
        return cons;
    }

    public void setCons(Long cons) {
        this.cons = cons;
    }

    public String getVctipoVal() {
        return vctipoVal;
    }

    public void setVctipoVal(String vctipoVal) {
        this.vctipoVal = vctipoVal;
    }

    public String getVcnic() {
        return vcnic;
    }

    public void setVcnic(String vcnic) {
        this.vcnic = vcnic;
    }

    public String getVcnisrad() {
        return vcnisrad;
    }

    public void setVcnisrad(String vcnisrad) {
        this.vcnisrad = vcnisrad;
    }

    public String getVcnumMed() {
        return vcnumMed;
    }

    public void setVcnumMed(String vcnumMed) {
        this.vcnumMed = vcnumMed;
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

    public Date getDfecReal() {
        return dfecReal;
    }

    public void setDfecReal(Date dfecReal) {
        this.dfecReal = dfecReal;
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

    public Integer getNcodProv() {
        return ncodProv;
    }

    public void setNcodProv(Integer ncodProv) {
        this.ncodProv = ncodProv;
    }

    public String getVctipoRechazo() {
        return vctipoRechazo;
    }

    public void setVctipoRechazo(String vctipoRechazo) {
        this.vctipoRechazo = vctipoRechazo;
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

    public Date getTsfechaTran() {
        return tsfechaTran;
    }

    public void setTsfechaTran(Date tsfechaTran) {
        this.tsfechaTran = tsfechaTran;
    }

    public CausasRechazo getNidCausaRechazo() {
        return nidCausaRechazo;
    }

    public void setNidCausaRechazo(CausasRechazo nidCausaRechazo) {
        this.nidCausaRechazo = nidCausaRechazo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cons != null ? cons.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MovLogAlterno)) {
            return false;
        }
        MovLogAlterno other = (MovLogAlterno) object;
        if ((this.cons == null && other.cons != null) || (this.cons != null && !this.cons.equals(other.cons))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.metrolink.validatorservice.models.MovLogAlterno[ cons=" + cons + " ]";
    }
    
}
