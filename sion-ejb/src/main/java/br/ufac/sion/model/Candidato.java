/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.model;

import br.ufac.sion.model.enuns.BracoDominante;
import br.ufac.sion.model.enuns.EstadoCivil;
import br.ufac.sion.model.enuns.Escolaridade;
import br.ufac.sion.model.enuns.Sexo;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.NaturalId;
import org.hibernate.envers.AuditJoinTable;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

/**
 *
 * @author Rennan
 */
@Entity
@Table(name = "candidato")
@Audited
@AuditTable(value = "candidato_AUD", schema = "auditing")
public class Candidato implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "candidato_id_seq", sequenceName = "candidato_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "candidato_id_seq")
    private Long id;
    @NotBlank
    @Column(length = 100, nullable = false)
    private String nome;
    @Column(name = "nome_mae", length = 100)
    private String mae;
    @Column(name = "nome_pai", length = 100)
    private String pai;
    private Escolaridade escolaridade = Escolaridade.NIVEL_FUNDAMENTAL;
    private Sexo sexo = Sexo.MASCULINO;
    @Column(name = "estado_civil")
    private EstadoCivil estadoCivil = EstadoCivil.SOLTEIRO;
    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;
    @CPF
    @NotBlank
    @NaturalId
    @Column(unique = true, length = 14, nullable = false)
    private String cpf;
    @Column(name = "braco_dominante")
    private BracoDominante bracoDominante = BracoDominante.DESTRO;
    @Embedded
    private RG rg;
    @Embedded
    private Endereco endereco;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "candidato_telefones",
            joinColumns = @JoinColumn(name = "candidato_id"))
    @AttributeOverrides({
        @AttributeOverride(name = "numero", column = @Column(name = "num_telefone"))
    })
    @AuditJoinTable(schema = "auditing")
    private List<Telefone> telefones = new ArrayList<>();
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "candidato")
    private List<Inscricao> inscricoes = new ArrayList<>();
    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMae() {
        return mae;
    }

    public void setMae(String mae) {
        this.mae = mae;
    }

    public String getPai() {
        return pai;
    }

    public void setPai(String pai) {
        this.pai = pai;
    }

    public Escolaridade getEscolaridade() {
        return escolaridade;
    }

    public void setEscolaridade(Escolaridade escolaridade) {
        this.escolaridade = escolaridade;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public EstadoCivil getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(EstadoCivil estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public BracoDominante getBracoDominante() {
        return bracoDominante;
    }

    public void setBracoDominante(BracoDominante bracoDominante) {
        this.bracoDominante = bracoDominante;
    }

    public RG getRg() {
        return rg;
    }

    public void setRg(RG rg) {
        this.rg = rg;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public List<Telefone> getTelefones() {
        return telefones;
    }

    public void setTelefones(List<Telefone> telefones) {
        this.telefones = telefones;
    }

    public List<Inscricao> getInscricoes() {
        return inscricoes;
    }

    public void setInscricoes(List<Inscricao> inscricoes) {
        this.inscricoes = inscricoes;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void adicionaTelefone(Telefone fone, Integer linha) {
        if (linha == null) {
            this.telefones.add(fone);
        } else {
            this.telefones.set(linha, fone);
        }
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
        if (!(object instanceof Candidato)) {
            return false;
        }
        Candidato other = (Candidato) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.ufac.sion.model.Candidato[ id=" + id + " ]";
    }

}
