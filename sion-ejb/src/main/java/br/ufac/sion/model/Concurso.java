/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Rennan
 */
@Entity
@Table(name = "concurso")
public class Concurso implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String titulo;
    private String descricao;
    private String localInscricao;
    private LocalDateTime dataInicioInscricao;
    private LocalDateTime dataTerminoIncricao;
    private LocalDate dataVencimentoBoleto;
    private List<CargoConcurso> cargos = new ArrayList<>();
    private StatusConcurso status = StatusConcurso.ABERTO;
    private ContaBancaria contaBancaria; 

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(length = 100)
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Lob
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Lob
    @Column(name = "local_inscricao")
    public String getLocalInscricao() {
        return localInscricao;
    }

    public void setLocalInscricao(String localInscricao) {
        this.localInscricao = localInscricao;
    }

    @Column(name = "data_inicio_inscricao")
    public LocalDateTime getDataInicioInscricao() {
        return dataInicioInscricao;
    }

    public void setDataInicioInscricao(LocalDateTime dataInicioInscricao) {
        this.dataInicioInscricao = dataInicioInscricao;
    }

    @Column(name = "data_termino_inscricao")
    public LocalDateTime getDataTerminoIncricao() {
        return dataTerminoIncricao;
    }

    public void setDataTerminoIncricao(LocalDateTime dataTerminoIncricao) {
        this.dataTerminoIncricao = dataTerminoIncricao;
    }

    @Column(name = "data_vencimento_boleto")
    public LocalDate getDataVencimentoBoleto() {
        return dataVencimentoBoleto;
    }

    public void setDataVencimentoBoleto(LocalDate dataVencimentoBoleto) {
        this.dataVencimentoBoleto = dataVencimentoBoleto;
    }

    @OneToMany(mappedBy = "concurso", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    public List<CargoConcurso> getCargos() {
        return cargos;
    }

    public void setCargos(List<CargoConcurso> cargos) {
        this.cargos = cargos;
    }

    @Enumerated(EnumType.STRING)
    public StatusConcurso getStatus() {
        return status;
    }

    public void setStatus(StatusConcurso status) {
        this.status = status;
    }

    @ManyToOne
    @JoinColumn(name = "conta_bancaria_id", nullable = false)
    public ContaBancaria getContaBancaria() {
        return contaBancaria;
    }

    public void setContaBancaria(ContaBancaria contaBancaria) {
        this.contaBancaria = contaBancaria;
    }

    public void adicionaCargo(CargoConcurso cargoConcurso, Integer linha) {
        if (linha == null) {
            this.cargos.add(cargoConcurso);
        } else {
            this.cargos.set(linha, cargoConcurso);
        }
    }

    @Transient
    public boolean isNovo() {
        return getId() == null;
    }

    @Transient
    public boolean isInscricoesAberta() {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(dataInicioInscricao) && now.isBefore(dataTerminoIncricao);
    }

    @Transient
    public boolean isInscricoesFechadas() {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(dataTerminoIncricao) && status.equals(StatusConcurso.INSCRICOES_ABERTAS);
    }

    @Transient
    public boolean isAberto() {
        LocalDateTime now = LocalDateTime.now();
        return now.isBefore(dataInicioInscricao);
    }

    @Transient
    public boolean isAlteravel() {
        return this.isAberto();
    }

    @Transient
    public boolean isNaoAlteravel() {
        return !this.isAlteravel();
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
        if (!(object instanceof Concurso)) {
            return false;
        }
        Concurso other = (Concurso) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.ufac.sion.model.Concurso[ id=" + id + " ]";
    }
}
