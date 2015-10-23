/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.controller;

import br.ufac.sion.dao.SetorFacadeLocal;
import br.ufac.sion.model.Setor;
import br.ufac.sion.util.jsf.FacesUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author rennan.lima
 */
@ManagedBean
@ViewScoped
public class PesquisaSetorBean implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EJB
    private SetorFacadeLocal setorFacade;
    
    private List<Setor> setores = new ArrayList<>();
    
    private Setor setorSelecionadaParaExcluir;

    public PesquisaSetorBean() {
    }
    
    @PostConstruct
    public void inicializar() {
        this.setores = setorFacade.findAll();
    }

    public List<Setor> getSetores() {
        return setores;
    }

    public void setSetores(List<Setor> setores) {
        this.setores = setores;
    }

    public Setor getSetorSelecionadaParaExcluir() {
        return setorSelecionadaParaExcluir;
    }

    public void setSetorSelecionadaParaExcluir(Setor setorSelecionadaParaExcluir) {
        this.setorSelecionadaParaExcluir = setorSelecionadaParaExcluir;
    }
    
    public void excluir() {
        try {
            setorFacade.remove(setorSelecionadaParaExcluir);
            FacesUtil.addSuccessMessage("A setor '" + setorSelecionadaParaExcluir.getSigla() + "' foi excluída com sucesso.");
            inicializar();
        } catch (Exception e) {
            FacesUtil.addErrorMessage(e.getMessage());
        }
    }
}
