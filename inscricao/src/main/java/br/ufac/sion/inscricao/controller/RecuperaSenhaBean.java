/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.inscricao.controller;

import br.ufac.sion.exception.NegocioException;
import br.ufac.sion.inscricao.util.jsf.FacesUtil;
import br.ufac.sion.model.TokenRecuperacao;
import br.ufac.sion.service.CandidatoService;
import java.io.Serializable;
import javax.ejb.EJB;
import org.apache.commons.lang3.StringUtils;
import br.ufac.sion.dao.TokenRecuperacaoFacadeLocal;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Rennan
 */
@Named
@ViewScoped
public class RecuperaSenhaBean implements Serializable {
    
    @EJB
    private TokenRecuperacaoFacadeLocal tokenRecuperacaoFacade;
    
    @EJB
    private CandidatoService candidatoService;
    
    private String email;
    private String senha;
    private String token;
    private TokenRecuperacao tokenRecuperacao;
    private boolean expirado = true;
    
    public void inicializar() {
        if (FacesUtil.isNotPostback()) {
            if (StringUtils.isNotBlank(token)) {
                tokenRecuperacao = tokenRecuperacaoFacade.findByToken(token);
                if (tokenRecuperacao != null) {
                    setExpirado(false);
                } else {
                    setExpirado(true);
                }                
            }else{ 
                setExpirado(true);
            }
        }
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getSenha() {
        return senha;
    }
    
    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public boolean isExpirado() {
        return expirado;
    }
    
    public void setExpirado(boolean expirado) {
        this.expirado = expirado;
    }
    
    public void esqueceuSenha() {
        try {
            candidatoService.solicitarNovaSenha(email);
            FacesUtil.addSuccessMessage("Em instantes você receberá um e-mail com instruções para recuperar sua senha.");
        } catch (NegocioException ex) {
            FacesUtil.addErrorMessage(ex.getMessage());
        }
    }
    
    public void trocarSenha() {
        try {
            candidatoService.alterarSenhaComToken(senha, tokenRecuperacao);
            FacesUtil.addSuccessMessage("Sua senha foi alterada com sucesso.");
        } catch (NegocioException ex) {
            FacesUtil.addErrorMessage(ex.getMessage());
        }
    }
    
    public void limparSolicitacaoNovaSenha() {
        email = null;
    }
    
    public void limparAlterarSenha() {
        senha = null;
    }
}
