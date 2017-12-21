/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.controller;

import br.ufac.sion.model.Concurso;
import br.ufac.sion.service.ConcursoService;
import br.ufac.sion.util.jsf.Sion;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author rennan.lima
 */
@Named
@RequestScoped
public class RelatorioConcursoBean implements Serializable {

    @EJB
    private ConcursoService concursoService;

    @Inject  @Sion
    private HttpServletRequest request;

    private Concurso concurso;

    public RelatorioConcursoBean() {
        recuperaConcursoSessao();
    }

    public Concurso getConcurso() {
        return concurso;
    }

    public void setConcurso(Concurso concurso) {
        this.concurso = concurso;
    }

    public void recuperaConcursoSessao() {
        HttpSession session = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession();
        this.concurso = (Concurso) session.getAttribute("concursoGerenciado");
    }

}
