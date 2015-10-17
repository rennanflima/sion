/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Rennan
 */
@Entity
@Table(name = "cargo_vaga")
public class CargoVaga implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Integer quatidade;
    private Vaga tipoVaga;
    private Localidade localidade;
    private CargoConcurso cargo;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuatidade() {
        return quatidade;
    }

    public void setQuatidade(Integer quatidade) {
        this.quatidade = quatidade;
    }

    @ManyToOne
    @JoinColumn(name = "localidade_id", nullable = false)
    public Localidade getLocalidade() {
        return localidade;
    }

    public void setLocalidade(Localidade localidade) {
        this.localidade = localidade;
    }

    @ManyToOne
    @JoinColumn(name = "cargo_concurso_id", nullable = false)
    public CargoConcurso getCargo() {
        return cargo;
    }

    public void setCargo(CargoConcurso cargo) {
        this.cargo = cargo;
    }

    @ManyToOne
    @JoinColumn(name = "vaga_id", nullable = false)
    public Vaga getTipoVaga() {
        return tipoVaga;
    }

    public void setTipoVaga(Vaga tipoVaga) {
        this.tipoVaga = tipoVaga;
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
        if (!(object instanceof CargoVaga)) {
            return false;
        }
        CargoVaga other = (CargoVaga) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.ufac.sion.model.CargoVaga[ id=" + id + " ]";
    }

}
