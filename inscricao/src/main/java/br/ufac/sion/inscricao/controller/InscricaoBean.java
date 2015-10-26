/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.inscricao.controller;

import br.ufac.sion.dao.CargoConcursoFacadeLocal;
import br.ufac.sion.dao.ConcursoFacadeLocal;
import br.ufac.sion.dao.NivelFacadeLocal;
import br.ufac.sion.model.CargoConcurso;
import br.ufac.sion.model.Concurso;
import br.ufac.sion.model.Inscricao;
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
 * @author rennan.lima
 */
@ManagedBean
@ViewScoped
public class InscricaoBean implements Serializable {

    @EJB
    private ConcursoFacadeLocal concursoFacade;

    @EJB
    private NivelFacadeLocal nivelFacade;

    @EJB
    private CargoConcursoFacadeLocal cargoConcursoFacade;

    private Nivel nivel;

    private Inscricao inscricao;

    private Concurso concurso;

    private List<Nivel> niveis = new ArrayList<>();

    private List<CargoConcurso> cargosConcurso = new ArrayList<>();

    public void inicializar() {
        if (FacesUtil.isNotPostback()) {
            this.niveis = nivelFacade.findAll();
            this.concurso = concursoFacade.findById(concurso.getId());
        }
    }

    public InscricaoBean() {
        limpar();
    }

    public List<Nivel> getNiveis() {
        return niveis;
    }

    public List<CargoConcurso> getCargosConcurso() {
        return cargosConcurso;
    }

    public Nivel getNivel() {
        return nivel;
    }

    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }

    public Concurso getConcurso() {
        return concurso;
    }

    public void setConcurso(Concurso concurso) {
        this.concurso = concurso;
    }

    public Inscricao getInscricao() {
        return inscricao;
    }

    public void setInscricao(Inscricao inscricao) {
        this.inscricao = inscricao;
    }

    private void limpar() {
        this.concurso = new Concurso();
        this.nivel = new Nivel();
        this.inscricao = new Inscricao();
    }

    public void carregarCargos() {
        System.out.println("Chama ajx");
        this.cargosConcurso.clear();
        this.cargosConcurso = cargoConcursoFacade.findByConcursoAndNivel(concurso, nivel);
    }
}
