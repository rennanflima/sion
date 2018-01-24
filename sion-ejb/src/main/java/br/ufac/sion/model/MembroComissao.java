/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.envers.AuditJoinTable;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

/**
 *
 * @author Rennan
 */
@Entity
@Table(name = "membro_comissao")
@Audited
@AuditTable(value = "membro_comissao_AUD", schema = "auditing")
public class MembroComissao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "membro_comissao_id_seq", sequenceName = "membro_comissao_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "membro_comissao_id_seq")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "funcionario_id", nullable = false)
    private Funcionario funcionario;
    @ManyToOne
    @JoinColumn(name = "concurso_id", nullable = false)
    private Concurso concurso;
    private Boolean gerente;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "membro_comissao_permissao", joinColumns = @JoinColumn(name = "membro_comissao_id"),
            inverseJoinColumns = @JoinColumn(name = "permissao_comissao_id"))
    @AuditJoinTable(schema = "auditing")
    private List<PermissaoComissao> permissoes = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Concurso getConcurso() {
        return concurso;
    }

    public Boolean getGerente() {
        return gerente;
    }

    public void setGerente(Boolean gerente) {
        this.gerente = gerente;
    }

    public void setConcurso(Concurso concurso) {
        this.concurso = concurso;
    }

    public List<PermissaoComissao> getPermissoes() {
        return permissoes;
    }

    public void setPermissoes(List<PermissaoComissao> permissoes) {
        this.permissoes = permissoes;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MembroComissao other = (MembroComissao) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
