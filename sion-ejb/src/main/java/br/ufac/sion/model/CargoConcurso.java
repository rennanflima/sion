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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.AuditMappedBy;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author Rennan
 */
@Entity
@Table(name = "cargo_concurso")
@Audited
@AuditTable(value = "cargo_concurso_AUD", schema = "auditing")
public class CargoConcurso implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name="cargo_concurso_id_seq", sequenceName = "cargo_concurso_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cargo_concurso_id_seq")
    private Long id;
    @NotBlank
    @Column(nullable = false, length = 10)
    private String codigo;
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valor = BigDecimal.ZERO;
    @ManyToOne
    @JoinColumn(name = "cargo_id", nullable = false)
    private Cargo cargo;
    @ManyToOne
    @JoinColumn(name = "concurso_id", nullable = false)
    private Concurso concurso;
    @ManyToOne
    @JoinColumn(name = "localidade_id", nullable = false)
    private Localidade localidade;
    @NotAudited
    @OneToMany(mappedBy = "cargoConcurso", cascade = CascadeType.ALL, orphanRemoval = true,
            fetch = FetchType.EAGER, targetEntity = CargoVaga.class)
    @Fetch(value = FetchMode.SUBSELECT)
    @AuditMappedBy(mappedBy = "cargoConcurso")
    private List<CargoVaga> vagas = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public Concurso getConcurso() {
        return concurso;
    }

    public void setConcurso(Concurso concurso) {
        this.concurso = concurso;
    }

    public Localidade getLocalidade() {
        return localidade;
    }

    public void setLocalidade(Localidade localidade) {
        this.localidade = localidade;
    }

    public List<CargoVaga> getVagas() {
        return vagas;
    }

    public void setVagas(List<CargoVaga> vagas) {
        this.vagas = vagas;
    }

    public void adicionaVaga(CargoVaga vaga, Integer linha) {
        if (linha == null) {
            this.vagas.add(vaga);
        } else {
            this.vagas.set(linha, vaga);
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
