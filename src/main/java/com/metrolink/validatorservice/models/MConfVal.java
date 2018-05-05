/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.models;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author USUARIO
 */
@Entity
@Table(name = "M_CONF_VAL")
@NamedQueries({
    @NamedQuery(name = "MConfVal.findAll", query = "SELECT m FROM MConfVal m")})
public class MConfVal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "NCOD_AREA")
    private int ncodArea;
    @Basic(optional = false)
    @Column(name = "VTIPO_VAL")
    private String vtipoVal;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "NRAN_DIA_MIN")
    private BigDecimal nranDiaMin;
    @Column(name = "NRAN_DIA_MAX")
    private BigDecimal nranDiaMax;
    @Column(name = "NRAN_MES_MIN")
    private BigDecimal nranMesMin;
    @Column(name = "NRAN_MES_MAX")
    private BigDecimal nranMesMax;
    @Column(name = "NRAN_DES_MIN")
    private Short nranDesMin;
    @Column(name = "NRAN_DES_MAX")
    private Short nranDesMax;
    @Basic(optional = false)
    @Column(name = "NDES_CON_COE")
    private BigDecimal ndesConCoe;
    @Column(name = "LESTADO")
    private Short lestado;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;

    public MConfVal() {
    }

    public MConfVal(BigDecimal id) {
        this.id = id;
    }

    public MConfVal(BigDecimal id, int ncodArea, String vtipoVal, BigDecimal ndesConCoe) {
        this.id = id;
        this.ncodArea = ncodArea;
        this.vtipoVal = vtipoVal;
        this.ndesConCoe = ndesConCoe;
    }

    public int getNcodArea() {
        return ncodArea;
    }

    public void setNcodArea(int ncodArea) {
        this.ncodArea = ncodArea;
    }

    public String getVtipoVal() {
        return vtipoVal;
    }

    public void setVtipoVal(String vtipoVal) {
        this.vtipoVal = vtipoVal;
    }

    public BigDecimal getNranDiaMin() {
        return nranDiaMin;
    }

    public void setNranDiaMin(BigDecimal nranDiaMin) {
        this.nranDiaMin = nranDiaMin;
    }

    public BigDecimal getNranDiaMax() {
        return nranDiaMax;
    }

    public void setNranDiaMax(BigDecimal nranDiaMax) {
        this.nranDiaMax = nranDiaMax;
    }

    public BigDecimal getNranMesMin() {
        return nranMesMin;
    }

    public void setNranMesMin(BigDecimal nranMesMin) {
        this.nranMesMin = nranMesMin;
    }

    public BigDecimal getNranMesMax() {
        return nranMesMax;
    }

    public void setNranMesMax(BigDecimal nranMesMax) {
        this.nranMesMax = nranMesMax;
    }

    public Short getNranDesMin() {
        return nranDesMin;
    }

    public void setNranDesMin(Short nranDesMin) {
        this.nranDesMin = nranDesMin;
    }

    public Short getNranDesMax() {
        return nranDesMax;
    }

    public void setNranDesMax(Short nranDesMax) {
        this.nranDesMax = nranDesMax;
    }

    public BigDecimal getNdesConCoe() {
        return ndesConCoe;
    }

    public void setNdesConCoe(BigDecimal ndesConCoe) {
        this.ndesConCoe = ndesConCoe;
    }

    public Short getLestado() {
        return lestado;
    }

    public void setLestado(Short lestado) {
        this.lestado = lestado;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
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
        if (!(object instanceof MConfVal)) {
            return false;
        }
        MConfVal other = (MConfVal) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.metrolink.validatorservice.models.MConfVal[ id=" + id + " ]";
    }
    
}
