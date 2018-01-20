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
@Table(name = "M_PARAMETROS_ADM")
@NamedQueries({
    @NamedQuery(name = "MParametrosAdm.findAll", query = "SELECT m FROM MParametrosAdm m")})
public class MParametrosAdm implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "NDIAS_BUSCA")
    private Short ndiasBusca;
    @Column(name = "NDIAS_ANT")
    private Short ndiasAnt;
    @Column(name = "NDIAS_ASIG_ANO")
    private Short ndiasAsigAno;
    @Column(name = "VCSERVIDOR_EMAIL")
    private String vcservidorEmail;
    @Column(name = "LREQ_AUTENTIC")
    private Short lreqAutentic;
    @Column(name = "VCUSER")
    private String vcuser;
    @Column(name = "VCPASS_CORREO")
    private String vcpassCorreo;
    @Column(name = "VCEMAIL_REMIT")
    private String vcemailRemit;
    @Column(name = "VCEMAIL_ENVIO")
    private String vcemailEnvio;
    @Column(name = "VCRUTA_ARCHIVO")
    private String vcrutaArchivo;
    @Column(name = "NVP_COMPLETUD")
    private Short nvpCompletud;
    @Basic(optional = false)
    @Column(name = "NTOL_COMPLETUD")
    private short ntolCompletud;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "NID_PARAMETRO")
    private BigDecimal nidParametro;

    public MParametrosAdm() {
    }

    public MParametrosAdm(BigDecimal nidParametro) {
        this.nidParametro = nidParametro;
    }

    public MParametrosAdm(BigDecimal nidParametro, short ntolCompletud) {
        this.nidParametro = nidParametro;
        this.ntolCompletud = ntolCompletud;
    }

    public Short getNdiasBusca() {
        return ndiasBusca;
    }

    public void setNdiasBusca(Short ndiasBusca) {
        this.ndiasBusca = ndiasBusca;
    }

    public Short getNdiasAnt() {
        return ndiasAnt;
    }

    public void setNdiasAnt(Short ndiasAnt) {
        this.ndiasAnt = ndiasAnt;
    }

    public Short getNdiasAsigAno() {
        return ndiasAsigAno;
    }

    public void setNdiasAsigAno(Short ndiasAsigAno) {
        this.ndiasAsigAno = ndiasAsigAno;
    }

    public String getVcservidorEmail() {
        return vcservidorEmail;
    }

    public void setVcservidorEmail(String vcservidorEmail) {
        this.vcservidorEmail = vcservidorEmail;
    }

    public Short getLreqAutentic() {
        return lreqAutentic;
    }

    public void setLreqAutentic(Short lreqAutentic) {
        this.lreqAutentic = lreqAutentic;
    }

    public String getVcuser() {
        return vcuser;
    }

    public void setVcuser(String vcuser) {
        this.vcuser = vcuser;
    }

    public String getVcpassCorreo() {
        return vcpassCorreo;
    }

    public void setVcpassCorreo(String vcpassCorreo) {
        this.vcpassCorreo = vcpassCorreo;
    }

    public String getVcemailRemit() {
        return vcemailRemit;
    }

    public void setVcemailRemit(String vcemailRemit) {
        this.vcemailRemit = vcemailRemit;
    }

    public String getVcemailEnvio() {
        return vcemailEnvio;
    }

    public void setVcemailEnvio(String vcemailEnvio) {
        this.vcemailEnvio = vcemailEnvio;
    }

    public String getVcrutaArchivo() {
        return vcrutaArchivo;
    }

    public void setVcrutaArchivo(String vcrutaArchivo) {
        this.vcrutaArchivo = vcrutaArchivo;
    }

    public Short getNvpCompletud() {
        return nvpCompletud;
    }

    public void setNvpCompletud(Short nvpCompletud) {
        this.nvpCompletud = nvpCompletud;
    }

    public short getNtolCompletud() {
        return ntolCompletud;
    }

    public void setNtolCompletud(short ntolCompletud) {
        this.ntolCompletud = ntolCompletud;
    }

    public BigDecimal getNidParametro() {
        return nidParametro;
    }

    public void setNidParametro(BigDecimal nidParametro) {
        this.nidParametro = nidParametro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nidParametro != null ? nidParametro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MParametrosAdm)) {
            return false;
        }
        MParametrosAdm other = (MParametrosAdm) object;
        if ((this.nidParametro == null && other.nidParametro != null) || (this.nidParametro != null && !this.nidParametro.equals(other.nidParametro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.metrolink.validatorservice.models.MParametrosAdm[ nidParametro=" + nidParametro + " ]";
    }
    
}
