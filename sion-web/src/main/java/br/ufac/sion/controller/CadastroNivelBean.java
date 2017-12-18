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
public class CadastroNivelBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private NivelFacadeLocal nivelFacade;

    private Nivel nivel = new Nivel();

    @PostConstruct
    public void inicializar() {
        limpar();
    }

    public CadastroNivelBean() {
    }

    public Nivel getNivel() {
        return nivel;
    }

    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }

    public void salvar() {
        try {
            this.nivelFacade.save(nivel);
            FacesUtil.addSuccessMessage("Nível salvo com sucesso!");
            inicializar();
        } catch (Exception e) {
            FacesUtil.addErrorMessage("Erro ao salvar o nível: " + e.getMessage());
        }
    }

    public void limpar() {
        this.nivel = new Nivel();
    }

    public boolean isEditando(){
        return this.nivel != null;
    }
}
