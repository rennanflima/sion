/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author Rennan
 */
@Entity
@Table(name = "inscricao", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"candidato_id", "cargo_concurso_id", "numero"})})
public class Inscricao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100)
    private String numero;
    @Column(name = "data_inscricao")
    private LocalDateTime dataInscricao;
    @Embedded
    private Insencao insencao;
    @Embedded
    private NecessidadeEspecial NecessidadeEspecial;
    @Enumerated(EnumType.STRING)
    private SituacaoInscricao status = SituacaoInscricao.AGUARDANDO_PAGAMENTO;
    @OneToOne(mappedBy = "sacado", cascade = CascadeType.ALL)
    private Boleto boleto;
    @ManyToOne
    @JoinColumn(name = "candidato_id", nullable = false)
    private Candidato candidato;
    @ManyToOne
    @JoinColumn(name = "cargo_concurso_id", nullable = false)
    private CargoConcurso cargoConcurso;
    private boolean concordoEdital;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public LocalDateTime getDataInscricao() {
        return dataInscricao;
    }

    public void setDataInscricao(LocalDateTime dataInscricao) {
        this.dataInscricao = dataInscricao;
    }

    public Insencao getInsencao() {
        return insencao;
    }

    public void setInsencao(Insencao insencao) {
        this.insencao = insencao;
    }

    public NecessidadeEspecial getNecessidadeEspecial() {
        return NecessidadeEspecial;
    }

    public void setNecessidadeEspecial(NecessidadeEspecial NecessidadeEspecial) {
        this.NecessidadeEspecial = NecessidadeEspecial;
    }

    public SituacaoInscricao getStatus() {
        return status;
    }

    public void setStatus(SituacaoInscricao status) {
        this.status = status;
    }

    public Boleto getBoleto() {
        return boleto;
    }

    public void setBoleto(Boleto boleto) {
        this.boleto = boleto;
    }

    public Candidato getCandidato() {
        return candidato;
    }

    public void setCandidato(Candidato candidato) {
        this.candidato = candidato;
    }

    public CargoConcurso getCargoConcurso() {
        return cargoConcurso;
    }

    public void setCargoConcurso(CargoConcurso cargoConcurso) {
        this.cargoConcurso = cargoConcurso;
    }

    public boolean isConcordoEdital() {
        return concordoEdital;
    }

    public void setConcordoEdital(boolean concordoEdital) {
        this.concordoEdital = concordoEdital;
    }

    @Transient
    public boolean isConfirmada() {
        return this.status.equals(SituacaoInscricao.CONFIRMADA);
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
        if (!(object instanceof Inscricao)) {
            return false;
        }
        Inscricao other = (Inscricao) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.ufac.sion.model.Inscricao[ id=" + id + " ]";
    }

}
