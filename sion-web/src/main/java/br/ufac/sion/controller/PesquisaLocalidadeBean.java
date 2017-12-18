/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.controller;

import br.ufac.sion.dao.LocalidadeFacadeLocal;
import br.ufac.sion.model.Localidade;
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
public class PesquisaLocalidadeBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private LocalidadeFacadeLocal localidadeFacade;
    
    private List<Localidade> localidades = new ArrayList<>();
    
    private Localidade localidadeSelecionadaParaExcluir;
    
    @PostConstruct
    public void inicializar(){
        this.localidades = localidadeFacade.findAll();
    }

    public PesquisaLocalidadeBean() {
    }

    public List<Localidade> getLocalidades() {
        return localidades;
    }

    public void setLocalidades(List<Localidade> localidades) {
        this.localidades = localidades;
    }

    public Localidade getLocalidadeSelecionadaParaExcluir() {
        return localidadeSelecionadaParaExcluir;
    }

    public void setLocalidadeSelecionadaParaExcluir(Localidade localidadeSelecionadaParaExcluir) {
        this.localidadeSelecionadaParaExcluir = localidadeSelecionadaParaExcluir;
    }
    
     public void excluir() {
        try {
            localidadeFacade.remove(localidadeSelecionadaParaExcluir);
            FacesUtil.addSuccessMessage("A localidade '" + localidadeSelecionadaParaExcluir.getNome() + "' foi excluída com sucesso.");
            inicializar();
        } catch (Exception e) {
            FacesUtil.addErrorMessage(e.getMessage());
        }
    }
    

}
