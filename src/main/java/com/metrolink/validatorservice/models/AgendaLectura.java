/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.models;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author USUARIO
 */
@Entity
@Table(name = "AGENDA_LECTURA")
@NamedQueries({
    @NamedQuery(name = "AgendaLectura.findAll", query = "SELECT a FROM AgendaLectura a")})
public class AgendaLectura implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AgendaLecturaPK agendaLecturaPK;
    @Column(name = "NUNICOM")
    private Short nunicom;
    @Column(name = "VCCICLO")
    private String vcciclo;
    @Column(name = "VCRUTA")
    private String vcruta;
    @Column(name = "VCITINERARIO")
    private String vcitinerario;

    public AgendaLectura() {
    }

    public AgendaLectura(AgendaLecturaPK agendaLecturaPK) {
        this.agendaLecturaPK = agendaLecturaPK;
    }

    public AgendaLectura(long npericons, Date dfecha, String vcparam) {
        this.agendaLecturaPK = new AgendaLecturaPK(npericons, dfecha, vcparam);
    }

    public AgendaLecturaPK getAgendaLecturaPK() {
        return agendaLecturaPK;
    }

    public void setAgendaLecturaPK(AgendaLecturaPK agendaLecturaPK) {
        this.agendaLecturaPK = agendaLecturaPK;
    }

    public Short getNunicom() {
        return nunicom;
    }

    public void setNunicom(Short nunicom) {
        this.nunicom = nunicom;
    }

    public String getVcciclo() {
        return vcciclo;
    }

    public void setVcciclo(String vcciclo) {
        this.vcciclo = vcciclo;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (agendaLecturaPK != null ? agendaLecturaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AgendaLectura)) {
            return false;
        }
        AgendaLectura other = (AgendaLectura) object;
        if ((this.agendaLecturaPK == null && other.agendaLecturaPK != null) || (this.agendaLecturaPK != null && !this.agendaLecturaPK.equals(other.agendaLecturaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.metrolink.validatorservice.models.AgendaLectura[ agendaLecturaPK=" + agendaLecturaPK + " ]";
    }
    
}
