/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metrolink.validatorservice.models;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
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
@Table(name = "MOV_SUMINISTROS")
@NamedQueries({
    @NamedQuery(name = "MovSuministros.findAll", query = "SELECT m FROM MovSuministros m")})
public class MovSuministros implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String TIPO_LECTURA = "1";
    public static final String TIPO_CONSUMO = "2";
    public static final short LECTURA_CERTIFICADA = 1;
    public static final short LECTURA_INVALIDA = 0;

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
    @Column(name = "NNUM_REGS")
    private Short nnumRegs;
    @Column(name = "LBLOQUEADO")
    private Short lbloqueado;
    @Column(name = "NUL_REPORTADA")
    private Integer nulReportada;
    @Column(name = "NREG")
    private BigInteger nreg;
    @Column(name = "NORDEN")
    private BigInteger norden;
    @Column(name = "VCCODCALLE")
    private String vccodcalle;
    @Column(name = "VCPTA")
    private String vcpta;
    @Column(name = "VCDUP")
    private String vcdup;
    @Column(name = "VCPISO_MED")
    private String vcpisoMed;
    @Column(name = "VCCGV")
    private String vccgv;
    @Column(name = "VCACC_PTO")
    private String vcaccPto;
    @Column(name = "VCID_SERV")
    private String vcidServ;
    @Column(name = "VCCODTCONSUMO")
    private String vccodtconsumo;
    @Column(name = "VCDESTCONSUMO")
    private String vcdestconsumo;
    @Column(name = "VCPISO_SERV")
    private String vcpisoServ;
    @Column(name = "VCNOMBRE")
    private String vcnombre;
    @Column(name = "NNUMRUE")
    private BigInteger nnumrue;
    @Column(name = "VCDESTARIFA")
    private String vcdestarifa;
    @Column(name = "VCCOD_DEP")
    private String vccodDep;
    @Column(name = "VCNOM_DEP")
    private String vcnomDep;
    @Column(name = "VCCOD_MUN")
    private String vccodMun;
    @Column(name = "VCNOM_MUN")
    private String vcnomMun;
    @Column(name = "VCCOD_LOC")
    private String vccodLoc;
    @Column(name = "VCNOM_LOC")
    private String vcnomLoc;
    @Column(name = "VCDIR")
    private String vcdir;
    @Column(name = "VCSECMOD_CON")
    private String vcsecmodCon;
    @Column(name = "VCREF_DIR")
    private String vcrefDir;
    @Column(name = "VCTIP_ASOC")
    private String vctipAsoc;
    @Column(name = "VCDES_ASOC")
    private String vcdesAsoc;
    @Column(name = "NMULTI")
    private BigInteger nmulti;
    @Column(name = "NUM_PADRON")
    private String numPadron;
    @Column(name = "VCCOD_TSERV")
    private String vccodTserv;
    @Column(name = "VCDES_TSERV")
    private String vcdesTserv;
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
    private ArrayList<MovRegsSco> movRegsScoCollection = new ArrayList<>();
    private ArrayList<MovLectConsu> movLectConsuCollection = new ArrayList<MovLectConsu>();
    private boolean suministroInvalidado;

    public MovSuministros(MovSuministrosPK movSuministrosPK, String vcnumMed, String vctipoMed, String vctipoLec, String vccentTec, Date tsful, Date tsflt, Date tsfla, Integer nnic, String vctipoVal, Short lestado, String vcnif, BigInteger nunicom, String vcruta, String vcitinerario, String vcciclo, Short nnumRegs, Short lbloqueado, Integer nulReportada, BigInteger nreg, BigInteger norden, String vccodcalle, String vcpta, String vcdup, String vcpisoMed, String vccgv, String vcaccPto, String vcidServ, String vccodtconsumo, String vcdestconsumo, String vcpisoServ, String vcnombre, BigInteger nnumrue, String vcdestarifa, String vccodDep, String vcnomDep, String vccodMun, String vcnomMun, String vccodLoc, String vcnomLoc, String vcdir, String vcsecmodCon, String vcrefDir, String vctipAsoc, String vcdesAsoc, BigInteger nmulti, String numPadron, String vccodTserv, String vcdesTserv, MCalTou ncodCalTou, MJurisdicciones ncodJurisdiccion, MMarcasmedidor vccodmarca, MProveedores mProveedores, MTarifas vccodtarifa) {
        this.movSuministrosPK = movSuministrosPK;
        this.vcnumMed = vcnumMed;
        this.vctipoMed = vctipoMed;
        this.vctipoLec = vctipoLec;
        this.vccentTec = vccentTec;
        this.tsful = tsful;
        this.tsflt = tsflt;
        this.tsfla = tsfla;
        this.nnic = nnic;
        this.vctipoVal = vctipoVal;
        this.lestado = lestado;
        this.vcnif = vcnif;
        this.nunicom = nunicom;
        this.vcruta = vcruta;
        this.vcitinerario = vcitinerario;
        this.vcciclo = vcciclo;
        this.nnumRegs = nnumRegs;
        this.lbloqueado = lbloqueado;
        this.nulReportada = nulReportada;
        this.nreg = nreg;
        this.norden = norden;
        this.vccodcalle = vccodcalle;
        this.vcpta = vcpta;
        this.vcdup = vcdup;
        this.vcpisoMed = vcpisoMed;
        this.vccgv = vccgv;
        this.vcaccPto = vcaccPto;
        this.vcidServ = vcidServ;
        this.vccodtconsumo = vccodtconsumo;
        this.vcdestconsumo = vcdestconsumo;
        this.vcpisoServ = vcpisoServ;
        this.vcnombre = vcnombre;
        this.nnumrue = nnumrue;
        this.vcdestarifa = vcdestarifa;
        this.vccodDep = vccodDep;
        this.vcnomDep = vcnomDep;
        this.vccodMun = vccodMun;
        this.vcnomMun = vcnomMun;
        this.vccodLoc = vccodLoc;
        this.vcnomLoc = vcnomLoc;
        this.vcdir = vcdir;
        this.vcsecmodCon = vcsecmodCon;
        this.vcrefDir = vcrefDir;
        this.vctipAsoc = vctipAsoc;
        this.vcdesAsoc = vcdesAsoc;
        this.nmulti = nmulti;
        this.numPadron = numPadron;
        this.vccodTserv = vccodTserv;
        this.vcdesTserv = vcdesTserv;
        this.ncodCalTou = ncodCalTou;
        this.ncodJurisdiccion = ncodJurisdiccion;
        this.vccodmarca = vccodmarca;
        this.mProveedores = mProveedores;
        this.vccodtarifa = vccodtarifa;
    }
    
    
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

    public MovSuministros(int ncodProv, BigInteger nnisRad, String vctipoEnergia) {
        this.movSuministrosPK = new MovSuministrosPK(ncodProv, nnisRad, vctipoEnergia);
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

    public Integer getNulReportada() {
        return nulReportada;
    }

    public void setNulReportada(Integer nulReportada) {
        this.nulReportada = nulReportada;
    }

    public BigInteger getNreg() {
        return nreg;
    }

    public void setNreg(BigInteger nreg) {
        this.nreg = nreg;
    }

    public BigInteger getNorden() {
        return norden;
    }

    public void setNorden(BigInteger norden) {
        this.norden = norden;
    }

    public String getVccodcalle() {
        return vccodcalle;
    }

    public void setVccodcalle(String vccodcalle) {
        this.vccodcalle = vccodcalle;
    }

    public String getVcpta() {
        return vcpta;
    }

    public void setVcpta(String vcpta) {
        this.vcpta = vcpta;
    }

    public String getVcdup() {
        return vcdup;
    }

    public void setVcdup(String vcdup) {
        this.vcdup = vcdup;
    }

    public String getVcpisoMed() {
        return vcpisoMed;
    }

    public void setVcpisoMed(String vcpisoMed) {
        this.vcpisoMed = vcpisoMed;
    }

    public String getVccgv() {
        return vccgv;
    }

    public void setVccgv(String vccgv) {
        this.vccgv = vccgv;
    }

    public String getVcaccPto() {
        return vcaccPto;
    }

    public void setVcaccPto(String vcaccPto) {
        this.vcaccPto = vcaccPto;
    }

    public String getVcidServ() {
        return vcidServ;
    }

    public void setVcidServ(String vcidServ) {
        this.vcidServ = vcidServ;
    }

    public String getVccodtconsumo() {
        return vccodtconsumo;
    }

    public void setVccodtconsumo(String vccodtconsumo) {
        this.vccodtconsumo = vccodtconsumo;
    }

    public String getVcdestconsumo() {
        return vcdestconsumo;
    }

    public void setVcdestconsumo(String vcdestconsumo) {
        this.vcdestconsumo = vcdestconsumo;
    }

    public String getVcpisoServ() {
        return vcpisoServ;
    }

    public void setVcpisoServ(String vcpisoServ) {
        this.vcpisoServ = vcpisoServ;
    }

    public String getVcnombre() {
        return vcnombre;
    }

    public void setVcnombre(String vcnombre) {
        this.vcnombre = vcnombre;
    }

    public BigInteger getNnumrue() {
        return nnumrue;
    }

    public void setNnumrue(BigInteger nnumrue) {
        this.nnumrue = nnumrue;
    }

    public String getVcdestarifa() {
        return vcdestarifa;
    }

    public void setVcdestarifa(String vcdestarifa) {
        this.vcdestarifa = vcdestarifa;
    }

    public String getVccodDep() {
        return vccodDep;
    }

    public void setVccodDep(String vccodDep) {
        this.vccodDep = vccodDep;
    }

    public String getVcnomDep() {
        return vcnomDep;
    }

    public void setVcnomDep(String vcnomDep) {
        this.vcnomDep = vcnomDep;
    }

    public String getVccodMun() {
        return vccodMun;
    }

    public void setVccodMun(String vccodMun) {
        this.vccodMun = vccodMun;
    }

    public String getVcnomMun() {
        return vcnomMun;
    }

    public void setVcnomMun(String vcnomMun) {
        this.vcnomMun = vcnomMun;
    }

    public String getVccodLoc() {
        return vccodLoc;
    }

    public void setVccodLoc(String vccodLoc) {
        this.vccodLoc = vccodLoc;
    }

    public String getVcnomLoc() {
        return vcnomLoc;
    }

    public void setVcnomLoc(String vcnomLoc) {
        this.vcnomLoc = vcnomLoc;
    }

    public String getVcdir() {
        return vcdir;
    }

    public void setVcdir(String vcdir) {
        this.vcdir = vcdir;
    }

    public String getVcsecmodCon() {
        return vcsecmodCon;
    }

    public void setVcsecmodCon(String vcsecmodCon) {
        this.vcsecmodCon = vcsecmodCon;
    }

    public String getVcrefDir() {
        return vcrefDir;
    }

    public void setVcrefDir(String vcrefDir) {
        this.vcrefDir = vcrefDir;
    }

    public String getVctipAsoc() {
        return vctipAsoc;
    }

    public void setVctipAsoc(String vctipAsoc) {
        this.vctipAsoc = vctipAsoc;
    }

    public String getVcdesAsoc() {
        return vcdesAsoc;
    }

    public void setVcdesAsoc(String vcdesAsoc) {
        this.vcdesAsoc = vcdesAsoc;
    }

    public BigInteger getNmulti() {
        return nmulti;
    }

    public void setNmulti(BigInteger nmulti) {
        this.nmulti = nmulti;
    }

    public String getNumPadron() {
        return numPadron;
    }

    public void setNumPadron(String numPadron) {
        this.numPadron = numPadron;
    }

    public String getVccodTserv() {
        return vccodTserv;
    }

    public void setVccodTserv(String vccodTserv) {
        this.vccodTserv = vccodTserv;
    }

    public String getVcdesTserv() {
        return vcdesTserv;
    }

    public void setVcdesTserv(String vcdesTserv) {
        this.vcdesTserv = vcdesTserv;
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

    public ArrayList<MovRegsSco> getMovRegsScoCollection() {
        return movRegsScoCollection;
    }

    public void setMovRegsScoCollection(ArrayList<MovRegsSco> movRegsScoCollection) {
        this.movRegsScoCollection = movRegsScoCollection;
    }

    public boolean isSuministroInvalidado() {
        return suministroInvalidado;
    }

    public void setSuministroInvalidado(boolean suministroInvalidado) {
        this.suministroInvalidado = suministroInvalidado;
    }

    public ArrayList<MovLectConsu> getMovLectConsuCollection() {
        return movLectConsuCollection;
    }

    public void setMovLectConsuCollection(ArrayList<MovLectConsu> movLectConsuCollection) {
        this.movLectConsuCollection = movLectConsuCollection;
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
    
    public void certificarLecturas() {
        for (MovLectConsu movLectConsu : movLectConsuCollection) {
            movLectConsu.setLcertificada(LECTURA_CERTIFICADA);
        }
    }
    
}
