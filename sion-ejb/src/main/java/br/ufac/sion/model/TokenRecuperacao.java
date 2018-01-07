/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

/**
 *
 * @author Rennan
 */
@Entity
@Table(name = "token_recuperacao")
@Audited
@AuditTable(value = "token_recuperacao_AUD", schema = "auditing")
public class TokenRecuperacao implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(unique = true)
    private String token;
    @Column(name = "data_vencimento")
    private LocalDateTime dataVencimento;
    @OneToOne
    @JoinColumn(name = "candidato_id", nullable = false)
    private Candidato candidato;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDateTime dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Candidato getCandidato() {
        return candidato;
    }

    public void setCandidato(Candidato candidato) {
        this.candidato = candidato;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.token);
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
        final TokenRecuperacao other = (TokenRecuperacao) obj;
        if (!Objects.equals(this.token, other.token)) {
            return false;
        }
        return true;
    }
            
}
