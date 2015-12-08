/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.controller;

import br.ufac.sion.dao.FuncionarioFacadeLocal;
import br.ufac.sion.dao.UsuarioFacadeLocal;
import br.ufac.sion.exception.NegocioException;
import br.ufac.sion.model.Funcionario;
import br.ufac.sion.model.Usuario;
import br.ufac.sion.security.UsuarioSistema;
import br.ufac.sion.service.FuncionarioService;
import br.ufac.sion.util.jsf.FacesUtil;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private UsuarioFacadeLocal usuarioFacade;

    @EJB
    private FuncionarioFacadeLocal funcionarioFacade;

    @EJB
    private FuncionarioService funcionarioService;

    private Funcionario funcionario;
    private String login;
    private Integer mat;
    private String senha;
    private String oldSenha;
    private Usuario usuario;

    @PostConstruct
    public void inicializar() {
        if (getUsuarioLogado() != null) {
            this.usuario = usuarioFacade.findByLogin(getUsuarioLogado().getUsuario().getLogin());
        }
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

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public void trocarSenha() {
        try {
            usuarioFacade.alterarSenha(oldSenha, senha, usuario);
            FacesUtil.addSuccessMessage("Sua senha foi alterada com sucesso.");
        } catch (NegocioException ex) {
            FacesUtil.addErrorMessage(ex.getMessage());
        }
    }

    public void esqueceuSenha() {
        try {
            funcionarioService.esquecerSenha(mat, login);
            FacesUtil.addSuccessMessage("E-mail enviado com sucesso.");
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

    public void limpaRecupera() {
        login = null;
        mat = null;
    }
}
