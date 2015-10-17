/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.model;

import br.ufac.sion.model.util.LocalDateConverter;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
    private LocalDate dataInicioInscricao;
    private LocalDate dataTerminoIncricao;
    private List<CargoConcurso> cargos = new ArrayList<>();

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

    @Column(name = "local_inscricao", length = 100)
    public String getLocalInscricao() {
        return localInscricao;
    }

    public void setLocalInscricao(String localInscricao) {
        this.localInscricao = localInscricao;
    }

    @Convert(converter = LocalDateConverter.class)
    @Column(name = "data_inicio_inscricao")
    public LocalDate getDataInicioInscricao() {
        return dataInicioInscricao;
    }

    public void setDataInicioInscricao(LocalDate dataInicioInscricao) {
        this.dataInicioInscricao = dataInicioInscricao;
    }

    @Convert(converter = LocalDateConverter.class)
    @Column(name = "data_termino_inscricao")
    public LocalDate getDataTerminoIncricao() {
        return dataTerminoIncricao;
    }

    public void setDataTerminoIncricao(LocalDate dataTerminoIncricao) {
        this.dataTerminoIncricao = dataTerminoIncricao;
    }

    @OneToMany(mappedBy = "concurso", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<CargoConcurso> getCargos() {
        return cargos;
    }

    public void setCargos(List<CargoConcurso> cargos) {
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
