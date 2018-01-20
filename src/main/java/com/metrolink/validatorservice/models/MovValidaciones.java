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
@Table(name = "MOV_VALIDACIONES")
@NamedQueries({
    @NamedQuery(name = "MovValidaciones.findAll", query = "SELECT m FROM MovValidaciones m")})
public class MovValidaciones implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "TSFEC_INICIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tsfecInicio;
    @Column(name = "TSFEC_FIN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tsfecFin;
    @Column(name = "NTOTAL_SUM")
    private Integer ntotalSum;
    @Column(name = "NTOTAL_NO_ALARMA")
    private Integer ntotalNoAlarma;
    @Column(name = "NTOTAL_ALARMA")
    private Integer ntotalAlarma;
    @Id
    @Basic(optional = false)
    @Column(name = "NCONS_PROCESO")
    private Integer nconsProceso;

    public MovValidaciones() {
    }

    public MovValidaciones(Integer nconsProceso) {
        this.nconsProceso = nconsProceso;
    }

    public Date getTsfecInicio() {
        return tsfecInicio;
    }

    public void setTsfecInicio(Date tsfecInicio) {
        this.tsfecInicio = tsfecInicio;
    }

    public Date getTsfecFin() {
        return tsfecFin;
    }

    public void setTsfecFin(Date tsfecFin) {
        this.tsfecFin = tsfecFin;
    }

    public Integer getNtotalSum() {
        return ntotalSum;
    }

    public void setNtotalSum(Integer ntotalSum) {
        this.ntotalSum = ntotalSum;
    }

    public Integer getNtotalNoAlarma() {
        return ntotalNoAlarma;
    }

    public void setNtotalNoAlarma(Integer ntotalNoAlarma) {
        this.ntotalNoAlarma = ntotalNoAlarma;
    }

    public Integer getNtotalAlarma() {
        return ntotalAlarma;
    }

    public void setNtotalAlarma(Integer ntotalAlarma) {
        this.ntotalAlarma = ntotalAlarma;
    }

    public Integer getNconsProceso() {
        return nconsProceso;
    }

    public void setNconsProceso(Integer nconsProceso) {
        this.nconsProceso = nconsProceso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nconsProceso != null ? nconsProceso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MovValidaciones)) {
            return false;
        }
        MovValidaciones other = (MovValidaciones) object;
        if ((this.nconsProceso == null && other.nconsProceso != null) || (this.nconsProceso != null && !this.nconsProceso.equals(other.nconsProceso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.metrolink.validatorservice.models.MovValidaciones[ nconsProceso=" + nconsProceso + " ]";
    }
    
}
