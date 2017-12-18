/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.controller;

import br.ufac.sion.dao.NivelFacadeLocal;
import br.ufac.sion.model.Nivel;
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
public class PesquisaNivelBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private NivelFacadeLocal nivelFacade;

    private List<Nivel> niveis = new ArrayList<>();

    private Nivel nivelSelecionadoParaExcluir;

    @PostConstruct
    public void inicializar() {
        this.niveis = nivelFacade.findAll();
    }

    public PesquisaNivelBean() {
    }

    public List<Nivel> getNiveis() {
        return niveis;
    }

    public void setNiveis(List<Nivel> niveis) {
        this.niveis = niveis;
    }

    public Nivel getNivelSelecionadoParaExcluir() {
        return nivelSelecionadoParaExcluir;
    }

    public void setNivelSelecionadoParaExcluir(Nivel nivelSelecionadoParaExcluir) {
        this.nivelSelecionadoParaExcluir = nivelSelecionadoParaExcluir;
    }

    public void excluir() {
        try {
            nivelFacade.remove(nivelSelecionadoParaExcluir);
            FacesUtil.addSuccessMessage("O " + nivelSelecionadoParaExcluir.getDescricao() + " foi excluído com sucesso.");
            inicializar();
        } catch (Exception e) {
            FacesUtil.addErrorMessage(e.getMessage());
        }
    }

}
