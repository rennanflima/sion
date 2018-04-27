/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.controller;

import br.ufac.sion.dao.ConcursoFacadeLocal;
import br.ufac.sion.model.Concurso;
import br.ufac.sion.util.jsf.FacesUtil;
import br.ufac.sion.util.jsf.Sion;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Rennan
 */
@Named
@ViewScoped
public class PesquisaConcursoBean implements Serializable {

    @EJB
    private ConcursoFacadeLocal concursoFacade;

    @Inject
    @Sion
    private HttpServletRequest request;

    private List<Concurso> concursos;

    private Concurso concursoSelecionado;

    @Inject
    private ExternalContext context;

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

    public Concurso getConcursoSelecionado() {
        return concursoSelecionado;
    }

    public void setConcursoSelecionado(Concurso concursoSelecionado) {
        this.concursoSelecionado = concursoSelecionado;
    }

    public void excluir() {
        try {
            concursoFacade.remove(concursoSelecionado);
            FacesUtil.addSuccessMessage("O concurso '" + concursoSelecionado.getTitulo() + "' foi excluído com sucesso.");
            inicializar();
        } catch (Exception e) {
            FacesUtil.addErrorMessage(e.getMessage());
        }
    }

    private void limpar() {
        concursoSelecionado = new Concurso();
    }
}
