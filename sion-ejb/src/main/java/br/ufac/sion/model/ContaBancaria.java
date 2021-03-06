/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.model;

import br.ufac.sion.model.enuns.BancosSuportados;
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
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

/**
 *
 * @author rennan.lima
 */
@Entity
@Table(name = "conta_bancaria")
@Audited
@AuditTable(value = "conta_bancaria_AUD", schema = "auditing")
public class ContaBancaria implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name="conta_bancaria_id_seq", sequenceName = "conta_bancaria_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "conta_bancaria_id_seq")
    private Long id;
    @Column(length = 80)
    private String descricao;
    private Integer agencia;
    @Column(name = "digito_agencia")
    private String digitoAgencia;
    @Column(name = "numero_conta")
    private Integer numeroConta;
    @Column(name = "digito_conta")
    private String digitoConta;
    private Integer convenio;
    @Column(name = "codigo_carteira")
    private Integer codigoCarteira;
    private BancosSuportados banco;
    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = false)
    private Empresa cedente;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getAgencia() {
        return agencia;
    }

    public void setAgencia(Integer agencia) {
        this.agencia = agencia;
    }

    public String getDigitoAgencia() {
        return digitoAgencia;
    }

    public void setDigitoAgencia(String digitoAgencia) {
        this.digitoAgencia = digitoAgencia;
    }

    public Integer getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(Integer numeroConta) {
        this.numeroConta = numeroConta;
    }

    public String getDigitoConta() {
        return digitoConta;
    }

    public void setDigitoConta(String digitoConta) {
        this.digitoConta = digitoConta;
    }

    public Integer getCodigoCarteira() {
        return codigoCarteira;
    }

    public void setCodigoCarteira(Integer codigoCarteira) {
        this.codigoCarteira = codigoCarteira;
    }

    public Integer getConvenio() {
        return convenio;
    }

    public void setConvenio(Integer convenio) {
        this.convenio = convenio;
    }

    public Empresa getCedente() {
        return cedente;
    }

    public void setCedente(Empresa cedente) {
        this.cedente = cedente;
    }

    public BancosSuportados getBanco() {
        return banco;
    }

    public void setBanco(BancosSuportados banco) {
        this.banco = banco;
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
        if (!(object instanceof ContaBancaria)) {
            return false;
        }
        ContaBancaria other = (ContaBancaria) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.ufac.sion.model.ContaBancaria[ id=" + id + " ]";
    }

}
