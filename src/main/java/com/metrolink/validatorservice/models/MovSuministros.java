/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.models;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Gabriel Ortega
 */
@Entity
@Table(name = "MOV_SUMINISTROS")
@NamedQueries({
    @NamedQuery(name = "MovSuministros.findAll", query = "SELECT m FROM MovSuministros m")})
public class MovSuministros implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MovSuministrosPK movSuministrosPK;
    @Column(name = "VCNUM_MED")
    private String vcnumMed;
    @Column(name = "VCTIPO_MED")
    private String vctipoMed;
    @Column(name = "VCTIPO_LEC")
    private String vctipoLec;
    @Column(name = "VCCENT_TEC")
    private String vccentTec;
    @Basic(optional = false)
    @Column(name = "TSFUL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tsful;
    @Basic(optional = false)
    @Column(name = "TSFLT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tsflt;
    @Column(name = "TSFLA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tsfla;
    @Column(name = "NNIC")
    private Integer nnic;
    @Column(name = "VCTIPO_VAL")
    private String vctipoVal;
    @Column(name = "LESTADO")
    private Short lestado;
    @Column(name = "VCNIF")
    private String vcnif;
    @Column(name = "NUNICOM")
    private BigInteger nunicom;
    @Column(name = "VCRUTA")
    private String vcruta;
    @Column(name = "VCITINERARIO")
    private String vcitinerario;
    @Column(name = "VCCICLO")
    private String vcciclo;
    @Column(name = "VCTIPO_ENERGIA")
    private String vctipoEnergia;
    @Column(name = "NNUM_REGS")
    private Short nnumRegs;
    @Column(name = "LBLOQUEADO")
    private Short lbloqueado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "movSuministros")
    private Collection<MovLectConsu> movLectConsuCollection = new ArrayList<MovLectConsu>();
    @JoinColumn(name = "NCOD_CAL_TOU", referencedColumnName = "NCOD_CAL_TOU")
    @ManyToOne
    private MCalTou ncodCalTou;
    @JoinColumn(name = "NCOD_JURISDICCION", referencedColumnName = "NCOD_JURISDICCION")
    @ManyToOne
    private MJurisdicciones ncodJurisdiccion;
    @JoinColumn(name = "VCCODMARCA", referencedColumnName = "VCCODMARCA")
    @ManyToOne
    private MMarcasmedidor vccodmarca;
    @JoinColumn(name = "NCOD_PROV", referencedColumnName = "NCOD_PROV", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private MProveedores mProveedores;
    @JoinColumn(name = "VCCODTARIFA", referencedColumnName = "VCCODTARIFA")
    @ManyToOne
    private MTarifas vccodtarifa;
    @JoinColumn(name = "VCCODTCONSUMO", referencedColumnName = "VCCODTCONSUMO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private MTipoconsumo mTipoconsumo;

    public MovSuministros() {
    }

    public MovSuministros(MovSuministrosPK movSuministrosPK) {
        this.movSuministrosPK = movSuministrosPK;
    }

    public MovSuministros(MovSuministrosPK movSuministrosPK, Date tsful, Date tsflt) {
        this.movSuministrosPK = movSuministrosPK;
        this.tsful = tsful;
        this.tsflt = tsflt;
    }

    public MovSuministros(int ncodProv, BigInteger nnisRad, String vccodtconsumo) {
        this.movSuministrosPK = new MovSuministrosPK(ncodProv, nnisRad, vccodtconsumo);
    }

    public MovSuministrosPK getMovSuministrosPK() {
        return movSuministrosPK;
    }

    public void setMovSuministrosPK(MovSuministrosPK movSuministrosPK) {
        this.movSuministrosPK = movSuministrosPK;
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

    public Date getTsful() {
        return tsful;
    }

    public void setTsful(Date tsful) {
        this.tsful = tsful;
    }

    public Date getTsflt() {
        return tsflt;
    }

    public void setTsflt(Date tsflt) {
        this.tsflt = tsflt;
    }

    public Date getTsfla() {
        return tsfla;
    }

    public void setTsfla(Date tsfla) {
        this.tsfla = tsfla;
    }

    public Integer getNnic() {
        return nnic;
    }

    public void setNnic(Integer nnic) {
        this.nnic = nnic;
    }

    public String getVctipoVal() {
        return vctipoVal;
    }

    public void setVctipoVal(String vctipoVal) {
        this.vctipoVal = vctipoVal;
    }

    public Short getLestado() {
        return lestado;
    }

    public void setLestado(Short lestado) {
        this.lestado = lestado;
    }

    public String getVcnif() {
        return vcnif;
    }

    public void setVcnif(String vcnif) {
        this.vcnif = vcnif;
    }

    public BigInteger getNunicom() {
        return nunicom;
    }

    public void setNunicom(BigInteger nunicom) {
        this.nunicom = nunicom;
    }

    public String getVcruta() {
        return vcruta;
    }

    public void setVcruta(String vcruta) {
        this.vcruta = vcruta;
    }

    public String getVcitinerario() {
        return vcitinerario;
    }

    public void setVcitinerario(String vcitinerario) {
        this.vcitinerario = vcitinerario;
    }

    public String getVcciclo() {
        return vcciclo;
    }

    public void setVcciclo(String vcciclo) {
        this.vcciclo = vcciclo;
    }

    public String getVctipoEnergia() {
        return vctipoEnergia;
    }

    public void setVctipoEnergia(String vctipoEnergia) {
        this.vctipoEnergia = vctipoEnergia;
    }

    public Short getNnumRegs() {
        return nnumRegs;
    }

    public void setNnumRegs(Short nnumRegs) {
        this.nnumRegs = nnumRegs;
    }

    public Short getLbloqueado() {
        return lbloqueado;
    }

    public void setLbloqueado(Short lbloqueado) {
        this.lbloqueado = lbloqueado;
    }

    public Collection<MovLectConsu> getMovLectConsuCollection() {
        return movLectConsuCollection;
    }

    public void setMovLectConsuCollection(Collection<MovLectConsu> movLectConsuCollection) {
        this.movLectConsuCollection = movLectConsuCollection;
    }

    public MCalTou getNcodCalTou() {
        return ncodCalTou;
    }

    public void setNcodCalTou(MCalTou ncodCalTou) {
        this.ncodCalTou = ncodCalTou;
    }

    public MJurisdicciones getNcodJurisdiccion() {
        return ncodJurisdiccion;
    }

    public void setNcodJurisdiccion(MJurisdicciones ncodJurisdiccion) {
        this.ncodJurisdiccion = ncodJurisdiccion;
    }

    public MMarcasmedidor getVccodmarca() {
        return vccodmarca;
    }

    public void setVccodmarca(MMarcasmedidor vccodmarca) {
        this.vccodmarca = vccodmarca;
    }

    public MProveedores getMProveedores() {
        return mProveedores;
    }

    public void setMProveedores(MProveedores mProveedores) {
        this.mProveedores = mProveedores;
    }

    public MTarifas getVccodtarifa() {
        return vccodtarifa;
    }

    public void setVccodtarifa(MTarifas vccodtarifa) {
        this.vccodtarifa = vccodtarifa;
    }

    public MTipoconsumo getMTipoconsumo() {
        return mTipoconsumo;
    }

    public void setMTipoconsumo(MTipoconsumo mTipoconsumo) {
        this.mTipoconsumo = mTipoconsumo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (movSuministrosPK != null ? movSuministrosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MovSuministros)) {
            return false;
        }
        MovSuministros other = (MovSuministros) object;
        if ((this.movSuministrosPK == null && other.movSuministrosPK != null) || (this.movSuministrosPK != null && !this.movSuministrosPK.equals(other.movSuministrosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.metrolink.validatorservice.models.MovSuministros[ movSuministrosPK=" + movSuministrosPK + " ]";
    }
    
}
