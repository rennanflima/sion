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
import java.util.ArrayList;
import java.util.List;
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
public class PesquisaPermissaoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private PermissaoFacadeLocal localidadeFacade;
    
    private List<Permissao> permissoes = new ArrayList<>();
    
    private Permissao permissaoSelecionadaParaExcluir;
    
    @PostConstruct
    public void inicializar(){
        this.permissoes = localidadeFacade.findAll();
    }

    public PesquisaPermissaoBean() {
    }

    public List<Permissao> getPermissoes() {
        return permissoes;
    }

    public void setPermissoes(List<Permissao> permissoes) {
        this.permissoes = permissoes;
    }

    public Permissao getPermissaoSelecionadaParaExcluir() {
        return permissaoSelecionadaParaExcluir;
    }

    public void setPermissaoSelecionadaParaExcluir(Permissao permissaoSelecionadaParaExcluir) {
        this.permissaoSelecionadaParaExcluir = permissaoSelecionadaParaExcluir;
    }
    
     public void excluir() {
        try {
            localidadeFacade.remove(permissaoSelecionadaParaExcluir);
            FacesUtil.addSuccessMessage("A permissão '" + permissaoSelecionadaParaExcluir.getNome() + "' foi excluída com sucesso.");
            inicializar();
        } catch (Exception e) {
            FacesUtil.addErrorMessage(e.getMessage());
        }
    }
    

}
