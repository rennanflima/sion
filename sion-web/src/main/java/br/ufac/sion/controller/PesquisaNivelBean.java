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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Rennan
 */
@ManagedBean
@ViewScoped
public class PesquisaNivelBean implements Serializable {

    @EJB
    private NivelFacadeLocal nivelFacade;

    private List<Nivel> niveis = new ArrayList<>();
    
    private Nivel nivelSelecionado;
    
    private Nivel nivelSelecionadoParaExcluir;

    public PesquisaNivelBean() {
    }

    @PostConstruct
    public void init(){
        this.niveis = nivelFacade.findAll();
    }
    
    public List<Nivel> getNiveis() {
        return niveis;
    }

    public void setNiveis(List<Nivel> niveis) {
        this.niveis = niveis;
    }

    public Nivel getNivelSelecionado() {
        return nivelSelecionado;
    }

    public void setNivelSelecionado(Nivel nivelSelecionado) {
        this.nivelSelecionado = nivelSelecionado;
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
            init();
        } catch (Exception e) {
            FacesUtil.addErrorMessage(e.getMessage());
        }
    }
}
