/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.controller;

import br.ufac.sion.dao.EstadoFacadeLocal;
import br.ufac.sion.dao.SetorFacadeLocal;
import br.ufac.sion.model.Estado;
import br.ufac.sion.model.Setor;
import br.ufac.sion.util.jsf.FacesUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author rennan.lima
 */
@Named
@ViewScoped
public class PesquisaEstadoBean implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EJB
    private EstadoFacadeLocal estadoFacade;
    
    private List<Estado> estados = new ArrayList<>();
    
    private Estado estadoSelecionadoParaExcluir;

    public PesquisaEstadoBean() {
    }
    
    @PostConstruct
    public void inicializar() {
        this.estados = estadoFacade.findAll();
    }

    public List<Estado> getEstados() {
        return estados;
    }

    public void setEstados(List<Estado> estados) {
        this.estados = estados;
    }

    public Estado getEstadoSelecionadoParaExcluir() {
        return estadoSelecionadoParaExcluir;
    }

    public void setEstadoSelecionadoParaExcluir(Estado estadoSelecionadoParaExcluir) {
        this.estadoSelecionadoParaExcluir = estadoSelecionadoParaExcluir;
    }
    
    public void excluir() {
        try {
            estadoFacade.remove(estadoSelecionadoParaExcluir);
            FacesUtil.addSuccessMessage("O estado '" + estadoSelecionadoParaExcluir.getNome() + "' foi excluído com sucesso.");
            inicializar();
        } catch (Exception e) {
            FacesUtil.addErrorMessage(e.getMessage());
        }
    }
}
