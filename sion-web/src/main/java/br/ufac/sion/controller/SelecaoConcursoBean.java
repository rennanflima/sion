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
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Rennan
 */
@Named
@ViewScoped
public class SelecaoConcursoBean implements Serializable {

    @EJB
    private ConcursoFacadeLocal concursoFacade;

    @Inject
    @Sion
    private HttpServletRequest request;

    private List<Concurso> concursos;

    private Concurso concursoSelecionado;

    @PostConstruct
    public void inicializar() {
        this.concursos = concursoFacade.findAll();
        limpar();
    }

    private void limpar() {
        concursoSelecionado = new Concurso();
    }

    public Concurso getConcursoSelecionado() {
        return concursoSelecionado;
    }

    public void setConcursoSelecionado(Concurso concursoSelecionado) {
        this.concursoSelecionado = concursoSelecionado;
    }

    public List<Concurso> getConcursos() {
        return concursos;
    }

    public String guardaConcursoSessao() {
        if (concursoSelecionado != null) {
            HttpSession session = request.getSession();
            session.setAttribute("concursoGerenciado", concursoSelecionado);
            System.out.println("valor atribuido na sessao");
            return "/gerenciar/concurso/home.xhtml";
        }
        FacesUtil.addErrorMessage("Ocorreu um erro ao selecionar o concurso!!");
        return null;
    }
}
