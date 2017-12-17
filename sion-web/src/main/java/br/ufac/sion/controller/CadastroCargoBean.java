/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.controller;

import br.ufac.sion.dao.CargoFacadeLocal;
import br.ufac.sion.dao.NivelFacadeLocal;
import br.ufac.sion.model.Cargo;
import br.ufac.sion.model.Nivel;
import br.ufac.sion.util.jsf.FacesUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Rennan
 */
@ManagedBean
@ViewScoped
public class CadastroCargoBean implements Serializable {

    @EJB
    private NivelFacadeLocal nivelFacade;

    @EJB
    private CargoFacadeLocal cargoFacade;

    private List<Nivel> niveis = new ArrayList<>();

    private Cargo cargo;

    public CadastroCargoBean() {
        limpar();
    }

    public void inicializar() {
        if (FacesUtil.isNotPostback()) {
            this.niveis = nivelFacade.findAll();
        }
    }

    public List<Nivel> getNiveis() {
        return niveis;
    }

    public void setNiveis(List<Nivel> niveis) {
        this.niveis = niveis;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public void salvar() {
        try {
            this.cargoFacade.save(cargo);
            FacesUtil.addSuccessMessage("Cargo salvo com sucesso!");
            limpar();
        } catch (Exception e) {
            FacesUtil.addErrorMessage("Erro ao salvar o cargo: " + e.getMessage());
        }
    }

    public void limpar() {
        cargo = new Cargo();
    }

    public boolean isEditando(){
        return this.cargo != null;
    }
}
