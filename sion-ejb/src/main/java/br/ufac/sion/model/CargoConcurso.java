/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Rennan
 */
@Entity
@Table(name = "cargo_concurso")
public class CargoConcurso implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private BigDecimal valor;
    private Cargo cargo;
    private Concurso concurso;
    private List<CargoVaga> vagas = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(nullable = false, precision = 10, scale = 2)
    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    @ManyToOne
    @JoinColumn(name = "cargo_id", nullable = false)
    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    @ManyToOne
    @JoinColumn(name = "concurso_id", nullable = false)
    public Concurso getConcurso() {
        return concurso;
    }

    public void setConcurso(Concurso concurso) {
        this.concurso = concurso;
    }
    
    @OneToMany(mappedBy = "cargo", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<CargoVaga> getVagas() {
        return vagas;
    }

    public void setVagas(List<CargoVaga> vagas) {
        this.vagas = vagas;
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
        if (!(object instanceof CargoConcurso)) {
            return false;
        }
        CargoConcurso other = (CargoConcurso) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.ufac.sion.model.CargoConcurso[ id=" + id + " ]";
    }

}
