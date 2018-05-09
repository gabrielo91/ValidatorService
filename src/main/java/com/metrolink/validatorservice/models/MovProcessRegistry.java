/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.models;

import java.io.Serializable;
import java.math.BigInteger;
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
 * @author USUARIO
 */
@Entity
@Table(name = "MOV_PROCESS_REGISTRY")
@NamedQueries({
    @NamedQuery(name = "MovProcessRegistry.findAll", query = "SELECT m FROM MovProcessRegistry m")})
public class MovProcessRegistry implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "VCPROCESS_ID")
    private String vcprocessId;
    @Basic(optional = false)
    @Column(name = "FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Column(name = "VCESTADO_TRANSACCION")
    private Short vcestadoTransaccion;
    @Column(name = "VCMENSAJE_ERROR")
    private String vcmensajeError;
    @Column(name = "VCPROCESS_ID_PADRE")
    private String vcprocessIdPadre;
    @Column(name = "ID_LECTURA")
    private BigInteger idLectura;

    public MovProcessRegistry() {
    }

    public MovProcessRegistry(String vcprocessId) {
        this.vcprocessId = vcprocessId;
    }

    public MovProcessRegistry(String vcprocessId, Date fecha) {
        this.vcprocessId = vcprocessId;
        this.fecha = fecha;
    }

    public String getVcprocessId() {
        return vcprocessId;
    }

    public void setVcprocessId(String vcprocessId) {
        this.vcprocessId = vcprocessId;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Short getVcestadoTransaccion() {
        return vcestadoTransaccion;
    }

    public void setVcestadoTransaccion(Short vcestadoTransaccion) {
        this.vcestadoTransaccion = vcestadoTransaccion;
    }

    public String getVcmensajeError() {
        return vcmensajeError;
    }

    public void setVcmensajeError(String vcmensajeError) {
        this.vcmensajeError = vcmensajeError;
    }

    public String getVcprocessIdPadre() {
        return vcprocessIdPadre;
    }

    public void setVcprocessIdPadre(String vcprocessIdPadre) {
        this.vcprocessIdPadre = vcprocessIdPadre;
    }

    public BigInteger getIdLectura() {
        return idLectura;
    }

    public void setIdLectura(BigInteger idLectura) {
        this.idLectura = idLectura;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (vcprocessId != null ? vcprocessId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MovProcessRegistry)) {
            return false;
        }
        MovProcessRegistry other = (MovProcessRegistry) object;
        if ((this.vcprocessId == null && other.vcprocessId != null) || (this.vcprocessId != null && !this.vcprocessId.equals(other.vcprocessId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.metrolink.validatorservice.models.MovProcessRegistry[ vcprocessId=" + vcprocessId + " ]";
    }
    
}
