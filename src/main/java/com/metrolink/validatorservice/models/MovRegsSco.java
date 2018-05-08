/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author G 50-70
 */
@Entity
@Table(name = "MOV_REGS_SCO")
@NamedQueries({
    @NamedQuery(name = "MovRegsSco.findAll", query = "SELECT m FROM MovRegsSco m")})
public class MovRegsSco implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MovRegsScoPK movRegsScoPK;
    @Column(name = "NLEC")
    private BigInteger nlec;
    @Column(name = "VCCOAN")
    private String vccoan;
    @Column(name = "NCONSUMO")
    private BigInteger nconsumo;
    @Column(name = "VCNUM_MED")
    private String vcnumMed;
    @Column(name = "VCTIPO_MED")
    private String vctipoMed;
    @Column(name = "VCCODMARCA")
    private String vccodmarca;
    @Column(name = "VCTIPO_LEC")
    private String vctipoLec;
    @Column(name = "VCCENT_TEC")
    private String vccentTec;
    @Column(name = "VCTIPO_VAL")
    private String vctipoVal;
    @Column(name = "VCCODTARIFA")
    private String vccodtarifa;
    @Column(name = "NREG")
    private BigInteger nreg;
    @Column(name = "VCDESTCONSUMO")
    private String vcdestconsumo;
    @Column(name = "DFLEAN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dflean;
    @Column(name = "NLEAN")
    private BigDecimal nlean;
    @Column(name = "NLEMAX")
    private BigDecimal nlemax;
    @Column(name = "NLEMIN")
    private BigDecimal nlemin;
    @Column(name = "NNUMRUE")
    private BigInteger nnumrue;
    @Column(name = "NMULTI")
    private BigInteger nmulti;
    @Column(name = "DFEC_FACT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dfecFact;
    @Column(name = "LCORREGIDA_FACY")
    private Short lcorregidaFacy;
    @Column(name = "VCCODTCONSUMO_FACT")
    private String vccodtconsumoFact;
    @Column(name = "NLEC_FACT")
    private BigInteger nlecFact;
    @Column(name = "VCTIPO_LEC_FACT")
    private String vctipoLecFact;
    @Column(name = "VCDESC_LEC_FACT")
    private String vcdescLecFact;
    @Column(name = "NCONSU_FACT")
    private BigInteger nconsuFact;
    @Column(name = "VCCOAN_FACT")
    private String vccoanFact;
    @Column(name = "DFECRES_ANO_FACT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dfecresAnoFact;
    @Column(name = "VCDESRES_ANO_FACT")
    private String vcdesresAnoFact;
    @Column(name = "NNIC")
    private BigInteger nnic;
    @Column(name = "NPERIODO")
    private BigInteger nperiodo;
    @Column(name = "NCOD_COMENT")
    private Integer ncodComent;

    public MovRegsSco() {
    }

    public MovRegsSco(MovRegsScoPK movRegsScoPK) {
        this.movRegsScoPK = movRegsScoPK;
    }

    public MovRegsSco(BigInteger nnisRad, Date tsfeclet, String vccodtconsumo) {
        this.movRegsScoPK = new MovRegsScoPK(nnisRad, tsfeclet, vccodtconsumo);
    }

    public MovRegsScoPK getMovRegsScoPK() {
        return movRegsScoPK;
    }

    public void setMovRegsScoPK(MovRegsScoPK movRegsScoPK) {
        this.movRegsScoPK = movRegsScoPK;
    }

    public BigInteger getNlec() {
        return nlec;
    }

    public void setNlec(BigInteger nlec) {
        this.nlec = nlec;
    }

    public String getVccoan() {
        return vccoan;
    }

    public void setVccoan(String vccoan) {
        this.vccoan = vccoan;
    }

    public BigInteger getNconsumo() {
        return nconsumo;
    }

    public void setNconsumo(BigInteger nconsumo) {
        this.nconsumo = nconsumo;
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

    public String getVccentTec() {
        return vccentTec;
    }

    public void setVccentTec(String vccentTec) {
        this.vccentTec = vccentTec;
    }

    public String getVctipoVal() {
        return vctipoVal;
    }

    public void setVctipoVal(String vctipoVal) {
        this.vctipoVal = vctipoVal;
    }

    public String getVccodtarifa() {
        return vccodtarifa;
    }

    public void setVccodtarifa(String vccodtarifa) {
        this.vccodtarifa = vccodtarifa;
    }

    public BigInteger getNreg() {
        return nreg;
    }

    public void setNreg(BigInteger nreg) {
        this.nreg = nreg;
    }

    public String getVcdestconsumo() {
        return vcdestconsumo;
    }

    public void setVcdestconsumo(String vcdestconsumo) {
        this.vcdestconsumo = vcdestconsumo;
    }

    public Date getDflean() {
        return dflean;
    }

    public void setDflean(Date dflean) {
        this.dflean = dflean;
    }

    public BigDecimal getNlean() {
        return nlean;
    }

    public void setNlean(BigDecimal nlean) {
        this.nlean = nlean;
    }

    public BigDecimal getNlemax() {
        return nlemax;
    }

    public void setNlemax(BigDecimal nlemax) {
        this.nlemax = nlemax;
    }

    public BigDecimal getNlemin() {
        return nlemin;
    }

    public void setNlemin(BigDecimal nlemin) {
        this.nlemin = nlemin;
    }

    public BigInteger getNnumrue() {
        return nnumrue;
    }

    public void setNnumrue(BigInteger nnumrue) {
        this.nnumrue = nnumrue;
    }

    public BigInteger getNmulti() {
        return nmulti;
    }

    public void setNmulti(BigInteger nmulti) {
        this.nmulti = nmulti;
    }

    public Date getDfecFact() {
        return dfecFact;
    }

    public void setDfecFact(Date dfecFact) {
        this.dfecFact = dfecFact;
    }

    public Short getLcorregidaFacy() {
        return lcorregidaFacy;
    }

    public void setLcorregidaFacy(Short lcorregidaFacy) {
        this.lcorregidaFacy = lcorregidaFacy;
    }

    public String getVccodtconsumoFact() {
        return vccodtconsumoFact;
    }

    public void setVccodtconsumoFact(String vccodtconsumoFact) {
        this.vccodtconsumoFact = vccodtconsumoFact;
    }

    public BigInteger getNlecFact() {
        return nlecFact;
    }

    public void setNlecFact(BigInteger nlecFact) {
        this.nlecFact = nlecFact;
    }

    public String getVctipoLecFact() {
        return vctipoLecFact;
    }

    public void setVctipoLecFact(String vctipoLecFact) {
        this.vctipoLecFact = vctipoLecFact;
    }

    public String getVcdescLecFact() {
        return vcdescLecFact;
    }

    public void setVcdescLecFact(String vcdescLecFact) {
        this.vcdescLecFact = vcdescLecFact;
    }

    public BigInteger getNconsuFact() {
        return nconsuFact;
    }

    public void setNconsuFact(BigInteger nconsuFact) {
        this.nconsuFact = nconsuFact;
    }

    public String getVccoanFact() {
        return vccoanFact;
    }

    public void setVccoanFact(String vccoanFact) {
        this.vccoanFact = vccoanFact;
    }

    public Date getDfecresAnoFact() {
        return dfecresAnoFact;
    }

    public void setDfecresAnoFact(Date dfecresAnoFact) {
        this.dfecresAnoFact = dfecresAnoFact;
    }

    public String getVcdesresAnoFact() {
        return vcdesresAnoFact;
    }

    public void setVcdesresAnoFact(String vcdesresAnoFact) {
        this.vcdesresAnoFact = vcdesresAnoFact;
    }

    public BigInteger getNnic() {
        return nnic;
    }

    public void setNnic(BigInteger nnic) {
        this.nnic = nnic;
    }

    public BigInteger getNperiodo() {
        return nperiodo;
    }

    public void setNperiodo(BigInteger nperiodo) {
        this.nperiodo = nperiodo;
    }

    public Integer getNcodComent() {
        return ncodComent;
    }

    public void setNcodComent(Integer ncodComent) {
        this.ncodComent = ncodComent;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (movRegsScoPK != null ? movRegsScoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MovRegsSco)) {
            return false;
        }
        MovRegsSco other = (MovRegsSco) object;
        if ((this.movRegsScoPK == null && other.movRegsScoPK != null) || (this.movRegsScoPK != null && !this.movRegsScoPK.equals(other.movRegsScoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.metrolink.validatorservice.models.MovRegsSco[ movRegsScoPK=" + movRegsScoPK + " ]";
    }
    
}
