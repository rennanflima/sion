/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.service.util;

import java.time.LocalDateTime;
import org.hibernate.envers.RevisionType;

/**
 *
 * @author Rennan
 */
public class FiltroAuditoria {
    
    private Class classe;
    private String entidade;
    private String login;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private RevisionType[] tiposRevisao;

    public Class getClasse() {
        return classe;
    }

    public void setClasse(Class classe) {
        this.classe = classe;
    }

    public String getEntidade() {
        return entidade;
    }

    public void setEntidade(String entidade) {
        this.entidade = entidade;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDateTime getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDateTime dataFim) {
        this.dataFim = dataFim;
    }

    public RevisionType[] getTiposRevisao() {
        return tiposRevisao;
    }

    public void setTiposRevisao(RevisionType[] tiposRevisao) {
        this.tiposRevisao = tiposRevisao;
    }
    
    
}
