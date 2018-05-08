/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.models;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "MOV_ALARMAS")
@NamedQueries({
    @NamedQuery(name = "MovAlarmas.findAll", query = "SELECT m FROM MovAlarmas m")})
public class MovAlarmas implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MovAlarmasPK movAlarmasPK;
    @Column(name = "NUNICOM")
    private Short nunicom;
    @Column(name = "NPERICONS")
    private Integer npericons;
    @Column(name = "VCRUTA")
    private String vcruta;
    @Column(name = "VCITINERARIO")
    private String vcitinerario;
    @Column(name = "DFECHA_VAL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dfechaVal;
    @Column(name = "NNIC")
    private Integer nnic;
    @Basic(optional = false)
    @Column(name = "VCTIPO_ENERGIA")
    private String vctipoEnergia;
    @JoinColumn(name = "NCOD_ALARMA", referencedColumnName = "NCOD_ALARMA", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private MAlarmas mAlarmas;

    //Campo para almacenar los datos de los suministros que generaron alarmas y que por tanto no se deben validar
    private MovSuministrosPK movSuministrosPK;
    
    public MovAlarmas() {
    }

    public MovAlarmas(MovAlarmasPK movAlarmasPK) {
        this.movAlarmasPK = movAlarmasPK;
    }

    public MovAlarmas(MovAlarmasPK movAlarmasPK, String vctipoEnergia) {
        this.movAlarmasPK = movAlarmasPK;
        this.vctipoEnergia = vctipoEnergia;
    }

    public MovAlarmas(int nconsProceso, int nnisRad, int ncodAlarma) {
        this.movAlarmasPK = new MovAlarmasPK(nconsProceso, nnisRad, ncodAlarma);
    }

    public MovAlarmasPK getMovAlarmasPK() {
        return movAlarmasPK;
    }

    public void setMovAlarmasPK(MovAlarmasPK movAlarmasPK) {
        this.movAlarmasPK = movAlarmasPK;
    }

    public Short getNunicom() {
        return nunicom;
    }

    public void setNunicom(Short nunicom) {
        this.nunicom = nunicom;
    }

    public Integer getNpericons() {
        return npericons;
    }

    public void setNpericons(Integer npericons) {
        this.npericons = npericons;
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

    public Date getDfechaVal() {
        return dfechaVal;
    }

    public void setDfechaVal(Date dfechaVal) {
        this.dfechaVal = dfechaVal;
    }

    public Integer getNnic() {
        return nnic;
    }

    public void setNnic(Integer nnic) {
        this.nnic = nnic;
    }

    public String getVctipoEnergia() {
        return vctipoEnergia;
    }

    public void setVctipoEnergia(String vctipoEnergia) {
        this.vctipoEnergia = vctipoEnergia;
    }

    public MAlarmas getMAlarmas() {
        return mAlarmas;
    }

    public void setMAlarmas(MAlarmas mAlarmas) {
        this.mAlarmas = mAlarmas;
    }

    public MovSuministrosPK getMovSuministrosPK() {
        return movSuministrosPK;
    }

    public void setMovSuministrosPK(MovSuministrosPK movSuministrosPK) {
        this.movSuministrosPK = movSuministrosPK;
    }

    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (movAlarmasPK != null ? movAlarmasPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MovAlarmas)) {
            return false;
        }
        MovAlarmas other = (MovAlarmas) object;
        if ((this.movAlarmasPK == null && other.movAlarmasPK != null) || (this.movAlarmasPK != null && !this.movAlarmasPK.equals(other.movAlarmasPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.metrolink.validatorservice.models.MovAlarmas[ movAlarmasPK=" + movAlarmasPK + " ]";
    }
    
}
