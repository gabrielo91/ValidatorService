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
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author USUARIO
 */
@Embeddable
public class AgendaLecturaPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "NPERICONS")
    private long npericons;
    @Basic(optional = false)
    @Column(name = "DFECHA_TEO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dfechaTeo;
    @Basic(optional = false)
    @Column(name = "VCPARAM")
    private String vcparam;

    public AgendaLecturaPK() {
    }

    public AgendaLecturaPK(long npericons, Date dfechaTeo, String vcparam) {
        this.npericons = npericons;
        this.dfechaTeo = dfechaTeo;
        this.vcparam = vcparam;
    }

    public long getNpericons() {
        return npericons;
    }

    public void setNpericons(long npericons) {
        this.npericons = npericons;
    }

    public Date getDfechaTeo() {
        return dfechaTeo;
    }

    public void setDfechaTeo(Date dfechaTeo) {
        this.dfechaTeo = dfechaTeo;
    }

    public String getVcparam() {
        return vcparam;
    }

    public void setVcparam(String vcparam) {
        this.vcparam = vcparam;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) npericons;
        hash += (dfechaTeo != null ? dfechaTeo.hashCode() : 0);
        hash += (vcparam != null ? vcparam.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AgendaLecturaPK)) {
            return false;
        }
        AgendaLecturaPK other = (AgendaLecturaPK) object;
        if (this.npericons != other.npericons) {
            return false;
        }
        if ((this.dfechaTeo == null && other.dfechaTeo != null) || (this.dfechaTeo != null && !this.dfechaTeo.equals(other.dfechaTeo))) {
            return false;
        }
        if ((this.vcparam == null && other.vcparam != null) || (this.vcparam != null && !this.vcparam.equals(other.vcparam))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.metrolink.validatorservice.models.AgendaLecturaPK[ npericons=" + npericons + ", dfechaTeo=" + dfechaTeo + ", vcparam=" + vcparam + " ]";
    }
    
}
