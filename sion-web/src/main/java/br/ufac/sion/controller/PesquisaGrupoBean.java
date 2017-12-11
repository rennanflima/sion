/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.controller;

import br.ufac.sion.dao.GrupoFacadeLocal;
import br.ufac.sion.model.Grupo;
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
public class PesquisaGrupoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private GrupoFacadeLocal grupoFacade;
    
    private List<Grupo> grupos = new ArrayList<>();
    
    private Grupo grupoSelecionado;
    private Grupo grupoSelecionadoParaExcluir;
    
    @PostConstruct
    public void inicializar(){
        this.grupos = grupoFacade.findAll();
    }

    public PesquisaGrupoBean() {
    }

    public List<Grupo> getGrupos() {
        return grupos;
    }

    public void setGrupos(List<Grupo> grupos) {
        this.grupos = grupos;
    }

    public Grupo getGrupoSelecionado() {
        return grupoSelecionado;
    }

    public void setGrupoSelecionado(Grupo grupoSelecionado) {
        this.grupoSelecionado = grupoSelecionado;
    }

    public Grupo getGrupoSelecionadoParaExcluir() {
        return grupoSelecionadoParaExcluir;
    }

    public void setGrupoSelecionadoParaExcluir(Grupo grupoSelecionadoParaExcluir) {
        this.grupoSelecionadoParaExcluir = grupoSelecionadoParaExcluir;
    }
    
     public void excluir() {
        try {
            grupoFacade.remove(grupoSelecionadoParaExcluir);
            FacesUtil.addSuccessMessage("O grupo '" + grupoSelecionadoParaExcluir.getNome() + "' foi excluído com sucesso.");
            inicializar();
        } catch (Exception e) {
            FacesUtil.addErrorMessage(e.getMessage());
        }
    }
    
     public void buscarGrupoComPermissoes(){
         this.grupoSelecionado = this.grupoFacade.findGrupoWithPermissoes(this.grupoSelecionado.getId());
     }

}
