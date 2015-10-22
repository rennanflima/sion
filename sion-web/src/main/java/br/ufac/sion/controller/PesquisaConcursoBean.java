/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.controller;

import br.ufac.sion.dao.CargoFacadeLocal;
import br.ufac.sion.dao.ConcursoFacadeLocal;
import br.ufac.sion.dao.NivelFacadeLocal;
import br.ufac.sion.model.Cargo;
import br.ufac.sion.model.Concurso;
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
public class PesquisaConcursoBean implements Serializable {

    @EJB
    private ConcursoFacadeLocal concursoFacade;

    private List<Concurso> concursos;

    private Concurso concursoSelecionadoParaExcluir;

    @PostConstruct
    public void inicializar() {
        this.concursos = concursoFacade.findAll();
        limpar();
    }

    public PesquisaConcursoBean() {
    }

    public List<Concurso> getConcursos() {
        return concursos;
    }

    public void setConcursos(List<Concurso> concursos) {
        this.concursos = concursos;
    }

    public Concurso getConcursoSelecionadoParaExcluir() {
        return concursoSelecionadoParaExcluir;
    }

    public void setConcursoSelecionadoParaExcluir(Concurso concursoSelecionadoParaExcluir) {
        this.concursoSelecionadoParaExcluir = concursoSelecionadoParaExcluir;
    }

    public void excluir() {
        try {
            concursoFacade.remove(concursoSelecionadoParaExcluir);
            FacesUtil.addSuccessMessage("O concurso '" + concursoSelecionadoParaExcluir.getDescricao() + "' foi excluído com sucesso.");
            inicializar();
        } catch (Exception e) {
            FacesUtil.addErrorMessage(e.getMessage());
        }
    }

    private void limpar() {
        concursoSelecionadoParaExcluir = new Concurso();
    }
}
