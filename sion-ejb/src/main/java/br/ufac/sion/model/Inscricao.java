/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.model;

import br.ufac.sion.model.util.LocalDateConverter;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 *
 * @author Rennan
 */
@Entity
public class Inscricao implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Integer numero;
    private LocalDate dataInscricao;
    private Insencao insencao;
    private NecessidadeEspecial NecessidadeEspecial;
    private Boleto boleto;
    private Candidato candidato;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    @Column(name = "data_inscricao")
    public LocalDate getDataInscricao() {
        return dataInscricao;
    }

    public void setDataInscricao(LocalDate dataInscricao) {
        this.dataInscricao = dataInscricao;
    }

    @Embedded
    public Insencao getInsencao() {
        return insencao;
    }

    public void setInsencao(Insencao insencao) {
        this.insencao = insencao;
    }

    @Embedded
    public NecessidadeEspecial getNecessidadeEspecial() {
        return NecessidadeEspecial;
    }

    public void setNecessidadeEspecial(NecessidadeEspecial NecessidadeEspecial) {
        this.NecessidadeEspecial = NecessidadeEspecial;
    }

    @OneToOne
    @JoinColumn(name = "boleto_id")
    public Boleto getBoleto() {
        return boleto;
    }

    public void setBoleto(Boleto boleto) {
        this.boleto = boleto;
    }

    @ManyToOne
    @JoinColumn(name = "candidato_id", nullable = false)
    public Candidato getCandidato() {
        return candidato;
    }

    public void setCandidato(Candidato candidato) {
        this.candidato = candidato;
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
