/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.controller;

import br.ufac.sion.dao.EstadoFacadeLocal;
import br.ufac.sion.model.Estado;
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
public class CadastroEstadoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private EstadoFacadeLocal estadoFacade;

    private Estado estado = new Estado();

    @PostConstruct
    public void inicializar() {
        limpar();
    }

    public CadastroEstadoBean() {
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public void salvar() {
        try {
            this.estadoFacade.save(estado);
            FacesUtil.addSuccessMessage("Estado salvo com sucesso!");
            inicializar();
        } catch (Exception e) {
            FacesUtil.addErrorMessage("Erro ao salvar o estado: " + e.getMessage());
        }
    }

    public void limpar() {
        this.estado = new Estado();
    }

    public boolean isEditando(){
        return this.estado.getId() != null;
    }
}
