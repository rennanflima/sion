/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

/**
 *
 * @author Rennan
 */
@Entity
@Table(name = "cargo_vaga", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"quantidade", "vaga_id", "cargo_concurso_id"})})
@Audited
@AuditTable(value = "cargo_vaga_AUD", schema = "auditing")
public class CargoVaga implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name="cargo_vaga_id_seq", sequenceName = "cargo_vaga_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cargo_vaga_id_seq")
    private Long id;
    private Integer quantidade;
    @ManyToOne
    @JoinColumn(name = "vaga_id", nullable = false)
    private Vaga tipoVaga;
    @ManyToOne
    @JoinColumn(name = "cargo_concurso_id")
    private CargoConcurso cargoConcurso;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Vaga getTipoVaga() {
        return tipoVaga;
    }

    public void setTipoVaga(Vaga tipoVaga) {
        this.tipoVaga = tipoVaga;
    }

    public CargoConcurso getCargoConcurso() {
        return cargoConcurso;
    }

    public void setCargoConcurso(CargoConcurso cargoConcurso) {
        this.cargoConcurso = cargoConcurso;
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
        return this.cargoConcurso.getCodigo() + " - " + this.cargoConcurso.getCargo().getDescricao();
    }

}
