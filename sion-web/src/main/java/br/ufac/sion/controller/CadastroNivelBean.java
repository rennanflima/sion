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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Rennan
 */
@ManagedBean
@ViewScoped
public class CadastroNivelBean implements Serializable {

    @EJB
    private NivelFacadeLocal nivelFacade;

    private Nivel nivel;

    @PostConstruct
    public void init() {
        this.limpar();
    }

    public CadastroNivelBean() {
    }

    public void salvar() {
        try {
            this.nivelFacade.create(nivel);
            FacesUtil.addSuccessMessage("Nível salvo com sucesso!");
            this.init();
        } catch (Exception e) {
            FacesUtil.addErrorMessage("Erro ao salvar o nível: " + e.getMessage());
        }
    }

    public Nivel getNivel() {
        return nivel;
    }

    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }

    public void limpar() {
        this.nivel = new Nivel();
    }

}
