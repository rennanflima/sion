/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author rennan.lima
 */
@Entity
@Table(name = "setor")
public class Setor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name="setor_id_seq", sequenceName = "setor_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "setor_id_seq")
    private Long id;
    @Column(nullable = false, length = 60, unique = true)
    private String nome;
    @Column(length = 10, unique = true)
    private String sigla;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "cargo_setor", joinColumns = {
        @JoinColumn(name = "setor_id")}, inverseJoinColumns = {
        @JoinColumn(name = "cargo_id")})
    private List<Cargo> cargos = new ArrayList<>();

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

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public List<Cargo> getCargos() {
        return cargos;
    }

    public void setCargos(List<Cargo> cargos) {
        this.cargos = cargos;
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
        if (!(object instanceof Setor)) {
            return false;
        }
        Setor other = (Setor) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.ufac.sion.model.Setor[ id=" + id + " ]";
    }

}
