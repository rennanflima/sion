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
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Rennan
 */
@Named
@ViewScoped
public class PesquisaCargoBean implements Serializable {

    @EJB
    private NivelFacadeLocal nivelFacade;

    @EJB
    private CargoFacadeLocal cargoFacade;

    private List<Cargo> cargos;

    private List<Nivel> niveis = new ArrayList<>();
    
    private Cargo filtro;

    private Cargo cargoSelecionadoParaExcluir;

    @PostConstruct
    public void inicializar() {
        this.niveis = nivelFacade.findAll();
        this.filtro = new Cargo();
        pesquisar();
    }

    public PesquisaCargoBean() {
        this.filtro = new Cargo();
    }

    public List<Cargo> getCargos() {
        return cargos;
    }

    public void setCargos(List<Cargo> cargos) {
        this.cargos = cargos;
    }

    public Cargo getFiltro() {
        return filtro;
    }

    public void setFiltro(Cargo filtro) {
        this.filtro = filtro;
    }

    public Cargo getCargoSelecionadoParaExcluir() {
        return cargoSelecionadoParaExcluir;
    }

    public void setCargoSelecionadoParaExcluir(Cargo cargoSelecionadoParaExcluir) {
        this.cargoSelecionadoParaExcluir = cargoSelecionadoParaExcluir;
    }

    public List<Nivel> getNiveis() {
        return niveis;
    }

    public void setNiveis(List<Nivel> niveis) {
        this.niveis = niveis;
    }

    public void pesquisar(){
        this.cargos = cargoFacade.findByDescricaoAndNivel(filtro);
    }
    
    public void excluir() {
        try {
            cargoFacade.remove(cargoSelecionadoParaExcluir);
            FacesUtil.addSuccessMessage("O cargo '" + cargoSelecionadoParaExcluir.getDescricao() + "' foi excluído com sucesso.");
            inicializar();
        } catch (Exception e) {
            FacesUtil.addErrorMessage(e.getMessage());
        }
    }
}
