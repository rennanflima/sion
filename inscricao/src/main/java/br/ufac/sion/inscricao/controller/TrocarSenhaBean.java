/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.inscricao.controller;

import br.ufac.sion.dao.CandidatoFacadeLocal;
import br.ufac.sion.exception.NegocioException;
import br.ufac.sion.inscricao.security.UsuarioSistema;
import br.ufac.sion.inscricao.util.jsf.FacesUtil;
import br.ufac.sion.model.Candidato;
import br.ufac.sion.service.CandidatoService;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 *
 * @author Rennan
 */
@ManagedBean
@ViewScoped
public class TrocarSenhaBean implements Serializable {

    @EJB
    private CandidatoFacadeLocal candidatoFacade;

    @EJB
    private CandidatoService candidatoService;

    private Candidato candidato;
    private String login;
    private Integer mat;
    private String senha;
    private String oldSenha;

    @PostConstruct
    public void inicializar() {
        this.candidato = candidatoFacade.findByCPF(getUsuarioLogado().getCandidato().getCpf());
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Integer getMat() {
        return mat;
    }

    public void setMat(Integer mat) {
        this.mat = mat;
    }

    public String getOldSenha() {
        return oldSenha;
    }

    public void setOldSenha(String oldSenha) {
        this.oldSenha = oldSenha;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Candidato getCandidato() {
        return candidato;
    }

    public void setCandidato(Candidato candidato) {
        this.candidato = candidato;
    }

    public void trocarSenha() {
        try {
            candidatoService.alterarSenha(oldSenha, senha, candidato);
            FacesUtil.addSuccessMessage("Sua senha foi alterada com sucesso.");
        } catch (NegocioException ex) {
            FacesUtil.addErrorMessage(ex.getMessage());
        }

    }

    private UsuarioSistema getUsuarioLogado() {
        UsuarioSistema usuario = null;

        UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();

        if (auth != null && auth.getPrincipal() != null) {
            usuario = (UsuarioSistema) auth.getPrincipal();
        }

        return usuario;
    }

    public void limpar() {
        oldSenha = null;
        senha = null;
    }
}
