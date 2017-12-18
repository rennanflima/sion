/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.controller;

import br.ufac.sion.dao.PermissaoFacadeLocal;
import br.ufac.sion.model.Permissao;
import br.ufac.sion.util.jsf.FacesUtil;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author rennan.lima
 */
@Named
@ViewScoped
public class CadastroPermissaoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private PermissaoFacadeLocal permissaoFacade;

    private Permissao permissao;

    @PostConstruct
    public void inicializar() {
        limpar();
    }

    public CadastroPermissaoBean() {
    }

    public Permissao getPermissao() {
        return permissao;
    }

    public void setPermissao(Permissao permissao) {
        this.permissao = permissao;
    }



    public void salvar() {
        try {
            this.permissaoFacade.save(permissao);
            FacesUtil.addSuccessMessage("Permissão salva com sucesso!");
            inicializar();
        } catch (Exception e) {
            FacesUtil.addErrorMessage("Erro ao salvar a permissão: " + e.getMessage());
        }
    }

    public void limpar() {
        this.permissao = new Permissao();
    }

    public boolean isEditando(){
        return this.permissao != null;
    }
}
